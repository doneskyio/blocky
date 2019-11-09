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

import blocky.Blocky
import blocky.BlockyTemplate
import java.io.OutputStream
import java.nio.file.Path

internal class CompiledTemplate(
    val path: Path,
    children: List<Node>,
    private val parentRef: Path?
) : Block("template", children), BlockyTemplate {

    private val placeholders by lazy {
        children.filterIsInstance<Placeholder>()
    }

    private val nonPlaceholders by lazy {
        children.filter { it !is Placeholder }
    }

    private val contextModifiers by lazy {
        children.filterIsInstance<ContextModifierNode>()
    }

    init {
        if (parentRef != null) {
            check(nonPlaceholders.size == contextModifiers.size) { "If there is a parent template, only placeholders and context modifiers are supported." }
        }
    }

    override fun write(context: Context, out: OutputStream) {
        placeholders.forEach {
            if (!context.hasPlaceholder(it.name))
                context.setPlaceholder(it.name, it)
        }
        if (parentRef == null) {
            nonPlaceholders.forEach { it.write(context, out) }
        } else {
            contextModifiers.forEach { it.modify(context) }
            Blocky[parentRef].write(context, out)
        }
    }

    override fun toString(): String {
        return "Template name=$name children=$children"
    }
}