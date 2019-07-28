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

import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

internal class BeanMap private constructor(private val clazz: Class<*>) {

    private val propertyCache = ConcurrentHashMap<String, Method>()

    private fun findProperty(name: String): Method? {
        var property = propertyCache[name]
        if (property != null) {
            return property
        }
        val upperName = name[0].toUpperCase() + name.substring(1)
        val getterName = "get$upperName"
        property = clazz.getDeclaredMethod(getterName) ?: clazz.getMethod(getterName)
        propertyCache[name] = property
        return property
    }

    fun get(`object`: Any, path: List<String>): Any? {
        var result: Any? = null
        var currentMap = this
        var currentObj = `object`
        path.forEachIndexed { _, name ->
            val property = currentMap.findProperty(name) ?: throw IllegalArgumentException("Cannot find path: $path on $`object`")
            result = property.invoke(currentObj)
            result?.let {
                currentMap = BeanMap(it.javaClass)
                currentObj = it
            }
        }
        return result
    }

    companion object {

        private val beanCache = ConcurrentHashMap<Class<*>, BeanMap>()

        operator fun get(clazz: KClass<*>): BeanMap {
            val javaClass = clazz.java
            var bean = beanCache[javaClass]
            if (bean == null) {
                bean = BeanMap(javaClass)
                beanCache[javaClass] = bean
            }
            return bean
        }
    }
}