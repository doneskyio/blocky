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
import java.util.concurrent.ConcurrentHashMap

object Blocky {

    lateinit var loader: BlockyLoader

    operator fun get(template: String): BlockyTemplate {
        var compiledTemplate = cache[template]
        if (compiledTemplate == null) {
            loader.load(template).use {
                compiledTemplate = Compiler.compile(it)
            }
            cache[template] = compiledTemplate!!
        }
        return compiledTemplate!!
    }

    fun remove(template: String) = cache.remove(template)

    fun getFormatter(name: String): BlockyFormatter = formatters.getValue(name)
    fun setFormatter(name: String, formatter: BlockyFormatter) {
        formatters[name] = formatter
    }

    private val cache = ConcurrentHashMap<String, BlockyTemplate>()
    private val formatters = ConcurrentHashMap<String, BlockyFormatter>().apply {
        this["date"] = DateFormatter()
        this["currency"] = CurrencyFormatter()
    }

    fun flushCache() = cache.clear()
}