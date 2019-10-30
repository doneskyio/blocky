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
package blocky.model

import blocky.model.expression.Expression
import java.io.OutputStream
import java.util.concurrent.atomic.AtomicBoolean

internal class IfBlock(
    children: List<Node>,
    internal val expression: Expression
) : Block("if", children) {

    private val elseBlocks by lazy { children.filterIsInstance<ElseBlock>() }
    private val blocks by lazy { children.filter { it !is ElseBlock } }

    override fun write(context: Context, out: OutputStream) {
        if (expression.evaluate(context)) {
            blocks.forEach { it.write(context, out) }
        } else {
            val done = AtomicBoolean(false)
            elseBlocks.forEach {
                if (done.get())
                    return
                it.write(context, out, done)
            }
        }
    }

    override fun toString(): String {
        return "[if ($expression) ${children.joinToString(separator = " ")}]"
    }
}

internal class ElseBlock(
    parent: Node,
    children: List<Node>,
    name: String,
    internal val expression: Expression?
) : Block(name, children) {

    init {
        require(!(parent !is IfBlock && parent !is ElseBlock)) { "Else block must be from an if/else block" }
        require(!(parent is ElseBlock && parent.expression == null && expression == null)) { "Duplicate catch all else block" }
        require(!(name == "elseif" && expression == null)) { "elseif must have an expression" }
        require(!(name == "else" && expression != null)) { "else cannot have an expression" }
    }

    private val elseBlocks by lazy { children.filterIsInstance<ElseBlock>() }
    private val blocks by lazy { children.filter { it !is ElseBlock } }

    override fun write(context: Context, out: OutputStream) {
        write(context, out, AtomicBoolean(false))
    }

    internal fun write(context: Context, out: OutputStream, done: AtomicBoolean) {
        if (done.get())
            return
        if (expression == null || expression.evaluate(context)) {
            done.set(true)
            blocks.forEach { it.write(context, out) }
        } else {
            elseBlocks.forEach { it.write(context, out, done) }
        }
    }

    override fun toString(): String {
        return "[$name ($expression) ${children.joinToString(separator = " ")}]"
    }
}