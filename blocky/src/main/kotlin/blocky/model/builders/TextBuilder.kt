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

import blocky.model.Node
import blocky.model.TextBlock

internal class TextBuilder(var text: String = "") : BaseNodeBuilder() {

    override fun build(parent: Node): Node? {
        val text = when { // remove new line because of block
            text.startsWith("\n") -> text.substring(1)
            text.startsWith("\r\n") -> text.substring(2)
            else -> text
        }.toByteArray(Charsets.UTF_8)
        return if (text.isNotEmpty()) TextBlock(text) else null
    }
}
