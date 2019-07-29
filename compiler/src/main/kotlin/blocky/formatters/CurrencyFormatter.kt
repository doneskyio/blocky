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
import java.text.NumberFormat

class CurrencyFormatter : BlockyFormatter {

    override fun format(context: Context, config: String?, name: String): ByteArray {
        val nbr = context[name] as? Number ?: throw IllegalArgumentException("$name must be a number.")
        return NumberFormat.getCurrencyInstance().format(nbr).toByteArray(Charsets.UTF_8)
    }
}