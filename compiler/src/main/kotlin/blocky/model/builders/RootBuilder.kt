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

import blocky.model.Context
import blocky.model.Node
import blocky.model.BlockyTemplate
import java.io.OutputStream

class RootBuilder : BlockBuilder() {

    init { name = "root" }

    fun build(): BlockyTemplate = build(root) as BlockyTemplate

    companion object {

        internal val root = object : Node {
            override fun write(context: Context, out: OutputStream) = throw UnsupportedOperationException()
        }
    }
}