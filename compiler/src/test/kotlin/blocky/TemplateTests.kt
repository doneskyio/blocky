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
import blocky.model.Context
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TemplateTests {

    @BeforeTest
    fun flushCache() = Blocky.flushCache()

    val template1 =
        """
        |{template name="tpl1"}
        |<html>
        |<head>
        |<title>{ctx:title}</title>
        |</head>
        |<body>
        |{if [a == b]}
        |Hello
        |{/if}
        |{if [(title == "Hello World")]}
        |Hello title == "Hello World"
        |{/if}
        |{if [(x == 1)]}
        |Hello x == 1
        |{/if}
        |{if [(x > 1)]}
        |Hello x > 1
        |{/if}
        |{if [(z > 1)]}
        |Hello z > 1
        |{/if}
        |{if [(x > 1 || z > 1)]}
        |Hello x > 1 || z > 1
        |{/if}
        |{if [x > 1 || z > 1]}
        |Hello x > 1 || z > 1 -- 2
        |{/if}
        |</body>
        |</html>
        |{/template}
        """.trimMargin()

    val template2 =
        """
        |{template name="tpl2"}
        |{if [a == b]}
        |Hello
        |{else}
        |Hello Else
        |{/if}
        |{/template}
        """.trimMargin()

    val template3 =
        """
        |{template name="tpl3"}
        |{if [a != b]}
        |Hello a != b
        |{elseif [a == b]}
        |Hello Else a == b
        |{elseif [a != c]}
        |Hello Else a != c
        |{else}
        |Else!
        |{/if}
        |{/template}
        """.trimMargin()

    @Test
    fun testTemplate1() {
        val template = Compiler.compile(template1)
        val context = Context(mapOf("title" to "Hello World"))
        val content = ByteArrayOutputStream().use {
            template.write(context, it)
            it.toString()
        }
        val expected =
            """
        |<html>
        |<head>
        |<title>Hello World</title>
        |</head>
        |<body>
        |Hello title == "Hello World"
        |</body>
        |</html>
        |
        """.trimMargin()
        assertEquals(expected, content)
    }

    @Test
    fun testTemplate1_2() {
        val template = Compiler.compile(template1)
        val context = Context(mapOf("title" to "Hello World", "x" to 1))
        val content = ByteArrayOutputStream().use {
            template.write(context, it)
            it.toString()
        }
        val expected =
            """
        |<html>
        |<head>
        |<title>Hello World</title>
        |</head>
        |<body>
        |Hello title == "Hello World"
        |Hello x == 1
        |</body>
        |</html>
        |
        """.trimMargin()
        assertEquals(expected, content)
    }

    @Test
    fun testTemplate1_3() {
        val template = Compiler.compile(template1)
        val context = Context(mapOf("title" to "Hello World", "x" to 2))
        val content = ByteArrayOutputStream().use {
            template.write(context, it)
            it.toString()
        }
        val expected =
            """
        |<html>
        |<head>
        |<title>Hello World</title>
        |</head>
        |<body>
        |Hello title == "Hello World"
        |Hello x > 1
        |</body>
        |</html>
        |
        """.trimMargin()
        assertEquals(expected, content)
    }

    @Test
    fun testTemplate2() {
        val template = Compiler.compile(template2)
        val context = Context(mapOf("title" to "Hello World"))
        val content = ByteArrayOutputStream().use {
            template.write(context, it)
            it.toString()
        }
        val expected =
            """
        |Hello Else
        |
        """.trimMargin()
        assertEquals(expected, content)
    }

    @Test
    fun testTemplate2_1() {
        val y = System.currentTimeMillis()
        val template = Compiler.compile(template2)
        println("Compile MS: ${System.currentTimeMillis() - y}")
        val context = Context(mapOf("a" to 1, "b" to 1))
        val content = ByteArrayOutputStream().use {
            val x = System.currentTimeMillis()
            template.write(context, it)
            println("Write MS: ${System.currentTimeMillis() - x}")
            it.toString()
        }
        val expected =
            """
        |Hello
        |
        """.trimMargin()
        assertEquals(expected, content)
    }

    @Test
    fun testTemplate3_1() {
        val y = System.currentTimeMillis()
        val template = Compiler.compile(template3)
        println("Compile MS: ${System.currentTimeMillis() - y}")
        val context = Context(mapOf("a" to 1, "b" to 1))
        val content = ByteArrayOutputStream().use {
            val x = System.currentTimeMillis()
            template.write(context, it)
            println("Write MS: ${System.currentTimeMillis() - x}")
            it.toString()
        }
        val expected =
            """
        |Hello Else a == b
        |
        """.trimMargin()
        assertEquals(expected, content)
    }

    @Test
    fun testTemplate3_2() {
        val y = System.currentTimeMillis()
        val template = Compiler.compile(template3)
        println("Compile MS: ${System.currentTimeMillis() - y}")
        val context = Context(mapOf("a" to 1, "b" to 2))
        val content = ByteArrayOutputStream().use {
            val x = System.currentTimeMillis()
            template.write(context, it)
            println("Write MS: ${System.currentTimeMillis() - x}")
            it.toString()
        }
        val expected =
            """
        |Hello a != b
        |
        """.trimMargin()
        assertEquals(expected, content)
    }

    @Test
    fun testTemplate3_3() {
        val y = System.currentTimeMillis()
        val template = Compiler.compile(template3)
        println("Compile MS: ${System.currentTimeMillis() - y}")
        val context = Context(mapOf())
        val content = ByteArrayOutputStream().use {
            val x = System.currentTimeMillis()
            template.write(context, it)
            println("Write MS: ${System.currentTimeMillis() - x}")
            it.toString()
        }
        val expected =
            """
        |Else!
        |
        """.trimMargin()
        assertEquals(expected, content)
    }

    @Test
    fun testTemplate4() {
        val y = System.currentTimeMillis()
        val template = Compiler.compile(
            """
        |{template name="fortpl"}
        |{for items="item"}
        |{ctx:item} 
        |{/for}
        |{/template}
        """.trimMargin()
        )
        println("Compile MS: ${System.currentTimeMillis() - y}")
        val items = listOf("1", "2")
        val context = Context(mapOf("items" to items))
        val content = ByteArrayOutputStream().use {
            val x = System.currentTimeMillis()
            template.write(context, it)
            println("Write MS: ${System.currentTimeMillis() - x}")
            it.toString()
        }
        val expected =
            """
        |1 
        |2 
        |
        """.trimMargin()
        assertEquals(expected, content)
    }

    @Test
    fun testTemplate5() {
        val y = System.currentTimeMillis()
        val template = Compiler.compile(
            """
        |{template name="fortpl"}
        |{for items="item"}
        |{ctx:item.name} 
        |{/for}
        |{/template}
        """.trimMargin()
        )
        println("Compile MS: ${System.currentTimeMillis() - y}")
        val items = listOf(TestObject("name1"), TestObject("name2"))
        val context = Context(mapOf("items" to items))
        val content = ByteArrayOutputStream().use {
            val x = System.currentTimeMillis()
            template.write(context, it)
            println("Write MS: ${System.currentTimeMillis() - x}")
            it.toString()
        }
        val expected =
            """
        |name1 
        |name2 
        |
        """.trimMargin()
        assertEquals(expected, content)
    }

    @Test
    fun testTemplate6() {
        val y = System.currentTimeMillis()
        val template = Compiler.compile(
            """
        |{template name="fortpl"}
        |{for items="item"}
        |{ctx:item.obj.name} 
        |{/for}
        |{/template}
        """.trimMargin()
        )
        println("Compile MS: ${System.currentTimeMillis() - y}")
        val items = listOf(TestNestedObject(TestObject("nestedname1")), TestNestedObject(TestObject("nestedname2")))
        val context = Context(mapOf("items" to items))
        val content = ByteArrayOutputStream().use {
            val x = System.currentTimeMillis()
            template.write(context, it)
            println("Write MS: ${System.currentTimeMillis() - x}")
            it.toString()
        }
        val expected =
            """
        |nestedname1 
        |nestedname2 
        |
        """.trimMargin()
        assertEquals(expected, content)
    }

    @Test
    fun testTemplate7() {
        val y = System.currentTimeMillis()
        val template = Compiler.compile(
            """
        |{template name="fortpl"}
        |{for items="item"}
        |{if [item.obj.name == "nestedname1"]}
        |{ctx:item.obj.name} 
        |{/if}
        |{/for}
        |{/template}
        """.trimMargin()
        )
        println("Compile MS: ${System.currentTimeMillis() - y}")
        val items = listOf(TestNestedObject(TestObject("nestedname1")), TestNestedObject(TestObject("nestedname2")))
        val context = Context(mapOf("items" to items))
        val content = ByteArrayOutputStream().use {
            val x = System.currentTimeMillis()
            template.write(context, it)
            println("Write MS: ${System.currentTimeMillis() - x}")
            it.toString()
        }
        val expected =
            """
        |nestedname1 
        |
        """.trimMargin()
        assertEquals(expected, content)
    }

    @Test
    fun testTemplate8() {
        val y = System.currentTimeMillis()
        Blocky.loader = object : BlockyLoader {
            override fun load(template: String): InputStream {
                return ByteArrayInputStream(
                    if (template == "template1") {
                        """
                        |{template name="template1"}
                        |Hello
                        |{ref template="template2"}
                        |Thanks!
                        |{/template}
                        """.trimMargin()
                    } else {
                        """
                        |{template name="template2"}
                        |World
                        |{for items="item"}
                        |{if [item.obj.name == "nestedname1"]}
                        |{ctx:item.obj.name} 
                        |{/if}
                        |{/for}
                        |{/template}
                        """.trimMargin()
                    }.toByteArray()
                )
            }
        }
        val template = Blocky["template1"]
        println("Compile MS: ${System.currentTimeMillis() - y}")
        val items = listOf(TestNestedObject(TestObject("nestedname1")), TestNestedObject(TestObject("nestedname2")))
        val context = Context(mapOf("items" to items))
        val content = ByteArrayOutputStream().use {
            val x = System.currentTimeMillis()
            template.write(context, it)
            println("testTemplate8 - Write MS: ${System.currentTimeMillis() - x}")
            it.toString()
        }
        val expected =
            """
        |Hello
        |World
        |nestedname1 
        |Thanks!
        |
        """.trimMargin()
        assertEquals(expected, content)
    }

    @Test
    fun testTemplate9() {
        val y = System.currentTimeMillis()
        val template = Compiler.compile(
            """
        |{template name="fortpl"}
        |{ctx:dt format="date" args="MM/dd/yy"}
        |{/template}
        """.trimMargin()
        )
        println("Compile MS: ${System.currentTimeMillis() - y}")
        val dt = Date()
        val context = Context(mapOf("dt" to dt))
        val content = ByteArrayOutputStream().use {
            val x = System.currentTimeMillis()
            template.write(context, it)
            println("testTemplate9 - Write MS: ${System.currentTimeMillis() - x}")
            it.toString()
        }
        val expected =
            """
        |${SimpleDateFormat("MM/dd/yy").format(dt)}
        """.trimMargin()
        assertEquals(expected, content)
    }

    @Test
    fun testTemplate10() {
        val y = System.currentTimeMillis()
        val template = Compiler.compile(
            """
        |{template name="fortpl"}
        |{ctx:dt format="currency"}
        |{/template}
        """.trimMargin()
        )
        println("Compile MS: ${System.currentTimeMillis() - y}")
        val context = Context(mapOf("dt" to BigDecimal("100.00")))
        val content = ByteArrayOutputStream().use {
            val x = System.currentTimeMillis()
            template.write(context, it)
            println("testTemplate10 - Write MS: ${System.currentTimeMillis() - x}")
            it.toString()
        }
        val expected =
            """
        |$100.00
        """.trimMargin()
        assertEquals(expected, content)
    }

    @Test
    fun testTemplate11() {
        val y = System.currentTimeMillis()
        Blocky.loader = object : BlockyLoader {
            override fun load(template: String): InputStream {
                return ByteArrayInputStream(
                    if (template == "template1") {
                        """
                        |{template name="template1"}
                        |Hello
                        |{ref placeholder="content"}
                        |Thanks!
                        |{/template}
                        """.trimMargin()
                    } else {
                        """
                        |{template name="template2" parent="template1"}
                        |{placeholder name="content"}
                        |World
                        |{for items="item"}
                        |{if [item.obj.name == "nestedname1"]}
                        |{ctx:item.obj.name} 
                        |{/if}
                        |{/for}
                        |{/placeholder}
                        |{/template}
                        """.trimMargin()
                    }.toByteArray()
                )
            }
        }
        val template = Blocky["template2"]
        println("Compile MS: ${System.currentTimeMillis() - y}")
        val items = listOf(TestNestedObject(TestObject("nestedname1")), TestNestedObject(TestObject("nestedname2")))
        val context = Context(mapOf("items" to items))
        val content = ByteArrayOutputStream().use {
            val x = System.currentTimeMillis()
            template.write(context, it)
            println("testTemplate11 - Write MS: ${System.currentTimeMillis() - x}")
            it.toString()
        }
        val expected =
            """
        |Hello
        |World
        |nestedname1 
        |Thanks!
        |
        """.trimMargin()
        assertEquals(expected, content)
    }
}

data class TestObject(val name: String)

data class TestNestedObject(val obj: TestObject)