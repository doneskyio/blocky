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

import blocky.compiler.CompilerException
import java.io.OutputStream

internal class PlaceholderRef(
    private val placeholderRef: String?,
    private val placeholderCtxRef: String?
) : Node {

    init {
        if (placeholderRef == null && placeholderCtxRef == null) {
            throw CompilerException("placeholder or ctx is required")
        }
    }

    override fun write(context: Context, out: OutputStream) {
        context.getPlaceholder(
            placeholderCtxRef?.let {
                context[it]?.toString() ?: throw NullPointerException("$it is not defined")
            } ?: placeholderRef!!
        )?.write(context, out)
    }
}
