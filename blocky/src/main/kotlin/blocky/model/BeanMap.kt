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

import blocky.BlockyException
import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

internal class BeanMap private constructor(private val clazz: Class<*>) {

    private val propertyCache = ConcurrentHashMap<String, Method>()

    private fun findMethod(clazz: Class<*>, name: String): Method? =
        clazz.superclass?.let { findMethod(it, name) }
            ?: clazz.interfaces?.let { i -> i.forEach { iface -> findMethod(iface, name)?.let { return it } }; null }
            ?: try { clazz.getDeclaredMethod(name) } catch (e: Exception) { null }
            ?: try { clazz.getMethod(name) } catch (e: Exception) { null }

    private fun findProperty(name: String): Method? {
        var property = propertyCache[name]
        if (property != null) {
            return property
        }
        val upperName = name[0].toUpperCase() + name.substring(1)
        val getterName = "get$upperName"
        property = findMethod(clazz, getterName)
            ?: findMethod(clazz, name)
            ?: throw BlockyException("Failed to find property: $name")
        propertyCache[name] = property
        return property
    }

    fun get(`object`: Any, path: List<String>): Any? {
        var result: Any? = null
        var currentMap = this
        var currentObj = `object`
        path.forEach { name ->
            val property =
                currentMap.findProperty(name) ?: throw IllegalArgumentException("Cannot find path: $path on $`object`")
            result = try { property.invoke(currentObj) } catch (e: IllegalAccessException) { null }
            result?.let {
                currentMap = BeanMap(it.javaClass)
                currentObj = it
            } ?: return null
        }
        return result
    }

    companion object {

        private val beanMapCache = ConcurrentHashMap<Class<*>, BeanMap>()

        operator fun get(clazz: KClass<*>): BeanMap {
            val javaClass = clazz.java
            var bean = beanMapCache[javaClass]
            if (bean == null) {
                bean = BeanMap(javaClass)
                beanMapCache[javaClass] = bean
            }
            return bean
        }
    }
}