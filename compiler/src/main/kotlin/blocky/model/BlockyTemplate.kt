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
import java.io.OutputStream

class BlockyTemplate(
    children: List<Node>,
    name: String,
    private val parentRef: String?
) : Block(name, children) {

    private val placeholders by lazy {
        children.mapNotNull { it as? Placeholder }
    }

    override fun write(context: Context, out: OutputStream) {
        if (parentRef == null) {
            super.write(context, out)
        } else {
            placeholders.forEach { context.setPlaceholder(it.name, it) }
            Blocky[parentRef].write(context, out)
        }
    }

    override fun toString(): String {
        return "Template name=$name children=$children"
    }
}