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
import blocky.model.expression.NullValue
import blocky.model.expression.StringValue
import java.io.OutputStream

internal open class VariableBlock(
    name: String,
    private val formatter: String?,
    private val formatterConfig: String?,
    defaultValue: String?
) : Node {

    private val contextName = name.substring(contextPrefix.length)
    private val defaultValue = defaultValue?.toByteArray(Charsets.UTF_8)

    override fun write(context: Context, out: OutputStream) {
        val output =
            if (formatter != null) {
                val formatter = Blocky.getFormatter(formatter)
                formatter.format(context, formatterConfig, contextName)
            } else {
                context[contextName]?.let {
                    when (it) {
                        is StringValue -> it.value
                        is NullValue -> null
                        else -> it
                    }
                }?.toString()?.toByteArray(Charsets.UTF_8)
            } ?: defaultValue
        output?.let { out.write(it) }
    }

    override fun toString(): String {
        return "Variable(name=$contextName)"
    }

    companion object {

        const val contextPrefix = "ctx:"
    }
}
