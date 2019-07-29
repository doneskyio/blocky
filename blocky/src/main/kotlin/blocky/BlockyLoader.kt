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
package blocky

import blocky.compiler.Compiler
import java.io.InputStream
import java.nio.file.Path

interface BlockyLoader {

    fun load(path: Path): BlockyTemplate
}

abstract class BlockyCompilerLoader : BlockyLoader {

    protected abstract fun openInputStream(path: Path): InputStream

    protected open fun compile(path: Path, stream: InputStream) = Compiler.compile(path, stream)

    override fun load(path: Path) = openInputStream(path).use { compile(path, it) }
}