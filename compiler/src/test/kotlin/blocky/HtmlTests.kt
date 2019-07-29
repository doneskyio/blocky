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

import blocky.model.Context
import blocky.model.expression.ContextExpression
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.nio.file.Path
import kotlin.test.BeforeTest
import kotlin.test.Test

class HtmlTests {

    @BeforeTest
    fun flushCache() = Blocky.removeAllFromCache()

    @Test
    fun testIndexAndTemplate() {
        Blocky.loader = object : BlockyLoader {
            override fun load(path: Path): InputStream {
                return BlockyLoader::class.java.getResourceAsStream("/$path")
            }
        }
        val template = Blocky["index.html"]
        val context = Context(
            mapOf(
                "user" to "hello",
                "admin" to object : ContextExpression.ExpressionCallback {
                    override fun evaluate(context: Context): Boolean = true
                }
            )
        )
        val content = ByteArrayOutputStream().use {
            template.write(context, it)
            it.toString()
        }
        println(content)
    }

    @Test
    fun testIndexAndTemplate2() {
        Blocky.loader = object : BlockyLoader {
            override fun load(path: Path): InputStream {
                return BlockyLoader::class.java.getResourceAsStream("/$path")
            }
        }
        val template = Blocky["subdir/index.html"]
        val context = Context(
            mapOf(
                "user" to "hello",
                "admin" to object : ContextExpression.ExpressionCallback {
                    override fun evaluate(context: Context): Boolean = true
                }
            )
        )
        val content = ByteArrayOutputStream().use {
            template.write(context, it)
            it.toString()
        }
        println(content)
    }
}