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

    private val formatters = ConcurrentHashMap<String, BlockyFormatter>().apply {
        this["date"] = DateFormatter()
        this["currency"] = CurrencyFormatter()
    }

    private var loader: BlockyLoader = object : BlockyLoader {

        override fun load(path: Path): InputStream =
            FileInputStream(path.toFile())
    }

    private var cache: BlockyCache = object : BlockyCache {

        private val cache = ConcurrentHashMap<String, BlockyTemplate>()

        override fun get(path: Path): BlockyTemplate? =
            cache[path.toString()]

        override fun set(path: Path, template: BlockyTemplate) {
            cache[path.toString()] = template
        }

        override fun remove(path: Path) {
            cache.remove(path.toString())
        }

        override fun removeAll() {
            cache.clear()
        }
    }

    operator fun get(template: String): BlockyTemplate {
        val pathArray = template.split("/").toTypedArray()
        val path = Path.of("", *pathArray)
        return this[path]
    }

    operator fun get(path: Path): BlockyTemplate {
        val normalized = path.normalize()
        var compiledTemplate = cache[normalized]
        if (compiledTemplate == null) {
            loader.load(normalized).use {
                compiledTemplate = Compiler.compile(normalized, it)
            }
            cache[normalized] = compiledTemplate!!
        }
        return compiledTemplate!!
    }

    fun getFormatter(name: String): BlockyFormatter = formatters.getValue(name)
    fun setFormatter(name: String, formatter: BlockyFormatter) {
        formatters[name] = formatter
    }

    fun removeAllFromCache() = cache.removeAll()
    fun removeFromCache(path: Path) = cache.remove(path.normalize())

    fun setLoader(loader: BlockyLoader) {
        this.loader = loader
    }

    fun setCache(cache: BlockyCache) {
        this.cache = cache
    }
}