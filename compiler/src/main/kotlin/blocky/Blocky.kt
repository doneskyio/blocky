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
import blocky.formatters.CurrencyFormatter
import blocky.formatters.DateFormatter
import blocky.model.BlockyTemplate
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap

object Blocky {

    var loader: BlockyLoader = object : BlockyLoader {

        override fun load(path: Path): InputStream =
            FileInputStream(path.toFile())
    }

    operator fun get(template: String): BlockyTemplate {
        val pathArray = template.split("/").toTypedArray()
        val path =
            if (pathArray.size == 1) {
                Path.of(pathArray.first())
            } else {
                Path.of("", *pathArray)
            }.normalize()
        return this[path]
    }

    operator fun get(path: Path): BlockyTemplate {
        val template = path.normalize().toString()
        var compiledTemplate = cache[template]
        if (compiledTemplate == null) {
            loader.load(path).use {
                compiledTemplate = Compiler.compile(path, it)
            }
            cache[template] = compiledTemplate!!
        }
        return compiledTemplate!!
    }

    fun getFormatter(name: String): BlockyFormatter = formatters.getValue(name)
    fun setFormatter(name: String, formatter: BlockyFormatter) {
        formatters[name] = formatter
    }

    private val cache = ConcurrentHashMap<String, BlockyTemplate>()
    private val formatters = ConcurrentHashMap<String, BlockyFormatter>().apply {
        this["date"] = DateFormatter()
        this["currency"] = CurrencyFormatter()
    }

    fun removeAllFromCache() = cache.clear()
    fun removeFromCache(path: Path) = cache.remove(path.normalize().toString())
}