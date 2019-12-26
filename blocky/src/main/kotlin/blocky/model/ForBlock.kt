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

import java.io.OutputStream

internal class ForBlock(
    private val variableName: String,
    private val itemName: String,
    private val indexName: String?,
    children: List<Node>
) : Block(indexName?.let { "for:index" } ?: "for", children) {

    override fun write(context: Context, out: OutputStream) {
        val collection = context[variableName] as? Collection<*>
        collection?.forEachIndexed { index, any ->
            val itemContext = context.newChildContext(
                indexName?.let { mapOf(itemName to any, it to index) } ?: mapOf(itemName to any)
            )
            children.forEach { child ->
                child.write(itemContext, out)
            }
        }
    }
}