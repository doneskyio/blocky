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

import blocky.model.expression.NullValue
import blocky.model.expression.StringValue

class Context(context: Map<String, Any?> = emptyMap()) {

    private val context: MutableMap<String, Any?> = context.toMutableMap()
    private val placeholders = mutableMapOf<String, Placeholder>()
    private lateinit var parentContext: Context

    private constructor(context: MutableMap<String, Any?>, parentContext: Context) : this(context) {
        this.parentContext = parentContext
    }

    internal fun getPlaceholder(name: String): Placeholder? =
        placeholders[name] ?: if (::parentContext.isInitialized) parentContext.getPlaceholder(name) else null

    internal fun hasPlaceholder(name: String): Boolean = placeholders.containsKey(name) || (::parentContext.isInitialized && parentContext.hasPlaceholder(name))

    internal fun setPlaceholder(name: String, placeholder: Placeholder) {
        placeholders[name] = placeholder
    }

    operator fun get(name: String): Any? {
        val path = name.split(".")
        val itemName = path.first()
        if (!contains(itemName)) {
            return null
        }
        if (path.size > 1) {
            val item = internalGet(itemName) ?: return NullValue
            val bean = BeanMap[item::class]
            return bean.get(item, path.subList(1, path.size))
        } else {
            return internalGet(name)
        }
    }

    internal operator fun set(name: String, value: String): Boolean {
        if (parentContains(name)) {
            return parentContext.set(name, value)
        }
        context[name] = StringValue(value)
        return true
    }

    internal fun remove(name: String): Boolean {
        if (parentContains(name)) {
            return parentContext.remove(name)
        }
        context.remove(name)
        return true
    }

    private fun internalGet(name: String) =
        context[name] ?: if (::parentContext.isInitialized) parentContext[name] else null

    private fun contains(name: String) =
        context.containsKey(name) || parentContains(name)

    private fun parentContains(name: String): Boolean =
        if (::parentContext.isInitialized) parentContext.contains(name) else false

    fun newChildContext(context: Map<String, Any?>): Context =
        Context(context.toMutableMap(), this)
}
