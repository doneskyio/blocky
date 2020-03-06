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
import blocky.compiler.CompilerException
import java.io.OutputStream
import java.nio.file.Path

internal class TemplateRef(
    private val path: Path,
    private val templateRef: Path?,
    private val templateCtxRef: String?
) : Node {

    init {
        if (templateRef == null && templateCtxRef == null) {
            throw CompilerException("placeholder or ctx is required")
        }
    }

    override fun write(context: Context, out: OutputStream) {
        val template = Blocky[
            templateCtxRef?.let {
                val ctxPath = context[it]?.toString() ?: throw NullPointerException("$it is not defined")
                path.resolveSibling(ctxPath)
            } ?: templateRef!!
        ]
        template.write(context, out)
    }
}
