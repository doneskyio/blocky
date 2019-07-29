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
package blocky.formatters

import blocky.BlockyFormatter
import blocky.model.Context
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.concurrent.ConcurrentHashMap

class DateFormatter : BlockyFormatter {

    override fun format(context: Context, config: String?, name: String): ByteArray? {
        val contextDate = context[name] ?: return null
        val date = contextDate as? Date ?: throw IllegalArgumentException("DateFormatter requires a date.")
        val formatter = getFormat(config ?: "default")
        val offset = ZonedDateTime.now().offset
        return formatter.format(date.toInstant().atOffset(offset).toLocalDateTime()).toByteArray(Charsets.UTF_8)
    }

    private fun getFormat(config: String): DateTimeFormatter {
        var format = formats[config]
        if (format == null) {
            if (config == "default") {
                format = DateTimeFormatter.ofPattern("MM/dd/yy")
                formats[config] = format
            } else {
                format = DateTimeFormatter.ofPattern(config)
                formats[config] = format
            }
        }
        return format!!
    }

    private val formats = ConcurrentHashMap<String, DateTimeFormatter>()
}