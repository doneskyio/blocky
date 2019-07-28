/*
 * Copyright 2019 Donesky, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package blocky.compiler

import blocky.BlockyLexer
import blocky.BlockyParser
import blocky.BlockyParserBaseListener
import blocky.model.Template
import blocky.model.builders.BlockBuilder
import blocky.model.builders.IfBuilder
import blocky.model.builders.MutableExpression
import blocky.model.builders.MutableExpressionGroup
import blocky.model.builders.MutableValueExpression
import blocky.model.builders.NodeBuilderContainer
import blocky.model.builders.RootBuilder
import blocky.model.builders.TextBuilder
import blocky.model.expression.BooleanValue
import blocky.model.expression.Comparator
import blocky.model.expression.ContextValue
import blocky.model.expression.NullValue
import blocky.model.expression.NumberValue
import blocky.model.expression.Operator
import blocky.model.expression.StringValue
import blocky.model.expression.Value
import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer
import org.antlr.v4.runtime.tree.TerminalNode
import java.io.ByteArrayInputStream
import java.io.InputStream

object Compiler {

    fun compile(`in`: String): Template =
        compile(ByteArrayInputStream(`in`.toByteArray(Charsets.UTF_8)))

    fun compile(`in`: InputStream): Template {
        val stream = CharStreams.fromStream(`in`)
        val lexer = BlockyLexer(stream)
        val tokenStream = CommonTokenStream(lexer)
        val listener = TemplateParserListenerImpl()
        val parser = BlockyParser(tokenStream)
        // parser.isTrace = true
        parser.addParseListener(listener)
        parser.addErrorListener(ErrorListener())
        parser.template()
        return listener.template
    }
}

private class ErrorListener : BaseErrorListener() {

    override fun syntaxError(
        recognizer: Recognizer<*, *>?,
        offendingSymbol: Any?,
        line: Int,
        charPositionInLine: Int,
        msg: String?,
        e: RecognitionException?
    ) {
        throw CompilerException("line $line: $charPositionInLine $msg")
    }
}

private class TemplateParserListenerImpl : BlockyParserBaseListener() {

    lateinit var template: Template

    override fun exitTemplate(ctx: BlockyParser.TemplateContext) {
        template = ctx.build()
    }
}

private fun BlockyParser.TemplateContext.build(): Template {
    val root = RootBuilder()
    children?.forEach {
        when (it) {
            is BlockyParser.BlocksContext -> it.addNodes(root)
            else -> throw CompilerException("Unsupported: ${it.javaClass.simpleName}")
        }
    }
    return root.build()
}

private fun BlockyParser.BlocksContext.addNodes(parent: NodeBuilderContainer) {
    children.forEach {
        when (it) {
            is BlockyParser.BlockMiscContext -> { /* ignore */
            }
            is BlockyParser.BlockContext -> it.addNodes(parent)
            else -> throw CompilerException("Unsupported: ${it.javaClass.simpleName}")
        }
    }
}

private fun BlockyParser.BlockContext.addNodes(parent: NodeBuilderContainer) {
    var block: BlockBuilder? = null
    children.forEach {
        when (it) {
            is TerminalNode -> { /* ignore */
            }
            is BlockyParser.BlockNameContext -> {
                if (block == null) {
                    block = when (it.text) {
                        "if" -> IfBuilder()
                        else -> BlockBuilder()
                    }.apply { name = it.text }
                    parent.addNode(block!!)
                } else if (it.text != block?.name) {
                    throw CompilerException("Invalid closing tag: ${block?.name} != ${it.text}")
                }
            }
            is BlockyParser.BlockAttributeContext -> it.addTo(block ?: throw CompilerException("Block not initialized"))
            is BlockyParser.BlockExpressionContext -> {
                when (val b = block ?: throw CompilerException("Block not initialized")) {
                    is IfBuilder -> it.addTo(b)
                    else -> throw CompilerException("Unsupported expression: ${b.javaClass.simpleName}")
                }
            }
            is BlockyParser.BlockContentContext -> it.addNodes(block ?: throw IllegalArgumentException())
            is BlockyParser.BlockContext -> it.addNodes(block ?: throw IllegalArgumentException())
            else -> throw CompilerException("Unsupported: ${it.javaClass.simpleName}")
        }
    }
}

private fun BlockyParser.BlockAttributeContext.addTo(builder: BlockBuilder) {
    val name = blockAttributeName().BLOCK_NAME().text
    val value = blockAttributeValue().ATTVALUE_VALUE().text
    if (builder.attributes.containsKey(name))
        throw CompilerException("Duplicate attribute names: $name")
    val attributeValue = value.substring(1)
    builder.attributes[name] = attributeValue.substring(0, value.length - 2)
}

private fun BlockyParser.BlockContentContext.addNodes(parent: BlockBuilder) {
    children?.forEach {
        when (it) {
            is BlockyParser.BlockTextContext -> parent.addNode(TextBuilder().apply { text = it.text })
            is BlockyParser.BlockContext -> it.addNodes(parent)
            is BlockyParser.BlockElseContext -> it.addNodes(parent)
            else -> throw CompilerException("Unsupported: ${it.javaClass.simpleName}")
        }
    }
}

private fun BlockyParser.BlockElseContext.addNodes(parent: BlockBuilder) {
    val block = IfBuilder()
    parent.addNode(block)
    children?.forEach {
        when (it) {
            is TerminalNode -> { /* ignore */ }
            is BlockyParser.BlockElseNameContext -> block.name = it.text
            is BlockyParser.BlockAttributeContext -> it.addTo(block)
            is BlockyParser.BlockExpressionContext -> it.addTo(block)
            is BlockyParser.BlockContentContext -> it.addNodes(block)
            else -> throw CompilerException("Unsupported: ${it.javaClass.simpleName}")
        }
    }
}

private fun BlockyParser.BlockExpressionContext.addTo(parent: IfBuilder) {
    children?.forEach {
        when (it) {
            is TerminalNode -> { /* ignore */ }
            is BlockyParser.ExpressionContext -> {
                val parentGroup = MutableExpressionGroup()
                it.addTo(parentGroup)
                parent.expression = parentGroup.build()
            }
            else -> throw CompilerException("Unsupported: ${it.javaClass.simpleName}")
        }
    }
}

private fun BlockyParser.ExpressionContext.addTo(parent: MutableExpressionGroup) {
    children?.forEach {
        when (it) {
            is TerminalNode -> {
                when (it.text) {
                    "!" -> parent.negative = true
                    "(", ")" -> {
                    }
                    else -> throw CompilerException("Unsupported: ${it.text}")
                }
            }
            is BlockyParser.ValueExpressionContext -> it.addTo(parent)
            is BlockyParser.ExpressionContext -> it.addTo(parent)
            is BlockyParser.ExpressionGroupContext -> it.addTo(parent)
            is BlockyParser.LogicExpressionContext -> it.addTo(parent)
            else -> throw CompilerException("Unsupported: ${it.javaClass.simpleName}")
        }
    }
}

private fun BlockyParser.ExpressionGroupContext.addTo(parent: MutableExpressionGroup) {
    val group = MutableExpressionGroup()
    parent.children.add(group)
    children.forEach {
        when (it) {
            is TerminalNode -> {
                when (it.text) {
                    "!" -> group.negative = true
                    "(", ")" -> {
                    }
                    else -> throw CompilerException("Unsupported: ${it.text}")
                }
            }
            is BlockyParser.ExpressionContext -> it.addTo(group)
            else -> throw CompilerException("Unsupported: ${it.javaClass.simpleName}")
        }
    }
}

private fun BlockyParser.ValueExpressionContext.addTo(parent: MutableExpressionGroup) {
    val valueExpression = MutableValueExpression()
    parent.children.add(valueExpression)
    children?.forEach {
        when (it) {
            is TerminalNode -> {
                valueExpression.comparator = when (it.text) {
                    "==" -> Comparator.Equals
                    ">=" -> Comparator.GreaterThanEquals
                    "<=" -> Comparator.LessThanEquals
                    ">" -> Comparator.GreaterThan
                    "<" -> Comparator.LessThan
                    "!=" -> Comparator.NotEquals
                    else -> throw CompilerException("Unsupported: ${it.text}")
                }
            }
            is BlockyParser.ValueContext -> it.addTo(valueExpression)
            is BlockyParser.ExpressionContext -> it.addTo(parent)
            is BlockyParser.LogicExpressionContext -> it.addTo(valueExpression)
            else -> throw CompilerException("Unsupported: ${it.javaClass.simpleName}")
        }
    }
}

private fun BlockyParser.LogicExpressionContext.addTo(expression: MutableExpression) {
    EXPRESSION_LOGIC()?.let {
        expression.operator = when (it.text) {
            "&&" -> Operator.And
            "||" -> Operator.Or
            else -> throw CompilerException("Unsupported: ${it.text}")
        }
    }
}

private fun BlockyParser.ValueContext.addTo(valueExpression: MutableValueExpression) {
    children?.forEach {
        when (it) {
            is BlockyParser.IdentifierNameContext -> {
                if (valueExpression.left == null) {
                    valueExpression.left = ContextValue(it.text)
                } else {
                    valueExpression.right = ContextValue(it.text)
                }
            }
            is BlockyParser.LiteralContext -> {
                var literal: Value? = null
                it.EXPRESSION_NULL()?.let { literal = NullValue }
                it.EXPRESSION_STRING()?.let { string ->
                    val value = string.text.substring(1)
                    literal = StringValue(value.substring(0, value.length - 1))
                }
                it.EXPRESSION_NUMBER()?.let { number ->
                    val i = number.text?.toIntOrNull()
                    if (i == null) {
                        val l = number.text?.toLongOrNull()
                        literal = if (l == null) {
                            val f = number.text?.toFloatOrNull()
                            if (f == null) {
                                val d = number.text?.toDoubleOrNull()
                                if (d == null) {
                                    throw CompilerException("Unsupported numeric type: ${number.text}")
                                } else {
                                    NumberValue(d)
                                }
                            } else {
                                NumberValue(f)
                            }
                        } else {
                            NumberValue(l)
                        }
                    } else {
                        literal = NumberValue(i)
                    }
                }
                it.EXPRESSION_BOOLEAN()?.let { boolean -> literal = BooleanValue(boolean.text == "true") }
                if (valueExpression.left == null) {
                    valueExpression.left = literal!!
                } else {
                    valueExpression.right = literal!!
                }
            }
            else -> throw CompilerException("Unsupported: ${it.javaClass.simpleName}")
        }
    }
}