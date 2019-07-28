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
import blocky.model.ForBlock
import blocky.model.IfBlock
import blocky.model.Node
import blocky.model.Template
import blocky.model.VariableBlock

open class BlockBuilder : NodeBuilder, NodeBuilderContainer {

    private val _children = mutableListOf<NodeBuilder>()

    var name: String? = null
    val attributes = mutableMapOf<String, String>()

    override val children: List<NodeBuilder>
        get() = _children

    override fun addNode(node: NodeBuilder) {
        node.parent = this
        _children.add(node)
    }

    override var parent: NodeBuilder? = null

    override fun build(parent: Node): Node {
        val name = name ?: throw IllegalArgumentException("Missing block name")
        return when {
            name == "root" -> {
                if (_children.size != 1) {
                    throw IllegalArgumentException("Invalid number of children: ${_children.size}")
                }
                _children.first().build(parent)
            }
            name == "template" -> {
                if (parent == RootBuilder.root) {
                    val children = mutableListOf<Node>()
                    val templateName = attributes.getValue("name")
                    val block = Template(children, templateName)
                    _children.forEach {
                        children.add(it.build(block))
                    }
                    block
                } else {
                    TODO()
                }
            }
            name == "if" -> {
                val children = mutableListOf<Node>()
                val block = newIfBlock(parent, children)
                _children.forEach {
                    children.add(it.build(block))
                }
                block
            }
            name == "else" || name == "elseif" -> {
                val children = mutableListOf<Node>()
                val block = newElseBlock(parent, name, children)
                _children.forEach {
                    children.add(it.build(block))
                }
                block
            }
            name == "for" -> {
                val children = mutableListOf<Node>()
                val variableName = attributes.entries.first()
                val block = ForBlock(variableName.key, variableName.value, children)
                _children.forEach {
                    children.add(it.build(block))
                }
                block
            }
            name.startsWith(VariableBlock.contextPrefix) -> VariableBlock(name)
            else -> throw IllegalArgumentException("Unsupported block: $name")
        }
    }

    protected open fun newIfBlock(parent: Node, children: List<Node>): IfBlock = TODO()

    protected open fun newElseBlock(parent: Node, name: String, children: List<Node>): ElseBlock = TODO()
}