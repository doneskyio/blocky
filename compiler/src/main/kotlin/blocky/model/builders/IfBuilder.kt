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
package blocky.model.builders

import blocky.model.ElseBlock
import blocky.model.IfBlock
import blocky.model.Node
import blocky.model.expression.Comparator
import blocky.model.expression.ContextExpression
import blocky.model.expression.Expression
import blocky.model.expression.ExpressionGroup
import blocky.model.expression.Operator
import blocky.model.expression.Value
import blocky.model.expression.ValueExpression
import java.nio.file.Path

internal class IfBuilder(path: Path) : BlockBuilder(path) {

    var expression: Expression? = null

    override fun newIfBlock(parent: Node, context: String?, children: List<Node>) =
        IfBlock(
            children,
            context?.let { ContextExpression(it) } ?: expression
                ?: throw IllegalArgumentException("Expression is required")
        )

    override fun newElseBlock(parent: Node, name: String, children: List<Node>) =
        ElseBlock(parent, children, name, expression)
}

internal abstract class MutableExpression {

    var operator: Operator = Operator.And
    abstract fun build(): Expression
}

internal class MutableValueExpression(
    var left: Value? = null,
    var right: Value? = null,
    var comparator: Comparator = Comparator.Equals
) : MutableExpression() {

    override fun build(): Expression = ValueExpression(left!!, right!!, comparator, operator)
}

internal class MutableExpressionGroup : MutableExpression() {

    val children = mutableListOf<MutableExpression>()
    var negative = false

    override fun build(): Expression {
        return if (children.size == 1 && children.first() is MutableExpressionGroup) {
            val child = children.first()
            (child as MutableExpressionGroup).negative = negative
            child.build()
        } else {
            ExpressionGroup(children.map { it.build() }, negative, operator)
        }
    }
}
