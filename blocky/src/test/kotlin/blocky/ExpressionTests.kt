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
import blocky.model.CompiledTemplate
import blocky.model.Context
import blocky.model.IfBlock
import blocky.model.expression.Expression
import java.io.ByteArrayInputStream
import java.nio.file.Path
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

enum class TestEnum {
    A,
    B
}

class ExpressionTests {

    private val String.expression: Expression
        get() {
            val template = Compiler.compile(
                Path.of("template.html"),
                ByteArrayInputStream("[template][if [$this]][/if][/template]".toByteArray())
            )
            val block = (template as CompiledTemplate).children.first() as IfBlock
            return block.expression
        }

    @Test
    fun testContextEnum1() {
        val expression = "a == b".expression
        assertEquals("(a == b)", expression.toString())
        assertTrue(expression.evaluate(Context(mapOf("a" to TestEnum.A, "b" to TestEnum.A))))
    }

    @Test
    fun testContextEnum2() {
        val expression = "a != b".expression
        assertEquals("(a != b)", expression.toString())
        assertTrue(expression.evaluate(Context(mapOf("a" to TestEnum.A, "b" to TestEnum.B))))
    }

    @Test
    fun testContextEnum3() {
        val expression = "a == b".expression
        assertEquals("(a == b)", expression.toString())
        assertFalse(expression.evaluate(Context(mapOf("a" to TestEnum.A, "b" to TestEnum.B))))
    }

    @Test
    fun testContextEnum4() {
        val expression = "a != b".expression
        assertEquals("(a != b)", expression.toString())
        assertFalse(expression.evaluate(Context(mapOf("a" to TestEnum.A, "b" to TestEnum.A))))
    }

    @Test
    fun testContextBoolean1() {
        val expression = "a == true".expression
        assertEquals("(a == true)", expression.toString())
        assertTrue(expression.evaluate(Context(mapOf("a" to true))))
    }

    @Test
    fun testContextBoolean2() {
        val expression = "a == b".expression
        assertEquals("(a == b)", expression.toString())
        assertTrue(expression.evaluate(Context(mapOf("a" to true, "b" to true))))
    }

    @Test
    fun testContextBoolean3() {
        val expression = "a == b".expression
        assertEquals("(a == b)", expression.toString())
        assertFalse(expression.evaluate(Context(mapOf("a" to true, "b" to false))))
    }

    @Test
    fun testContextInt1() {
        val expression = "a == 1".expression
        assertEquals("(a == 1)", expression.toString())
        assertFalse(expression.evaluate(Context(mapOf("a" to 2))))
    }

    @Test
    fun testContextFloat1() {
        val expression = "a == 1.0".expression
        assertEquals("(a == 1.0)", expression.toString())
        assertTrue(expression.evaluate(Context(mapOf("a" to 1.0f))))
    }

    @Test
    fun testBoolean1() {
        val expression = "true == true".expression
        assertEquals("(true == true)", expression.toString())
        assertTrue(expression.evaluate(Context()))
    }

    @Test
    fun testBoolean2() {
        val expression = "true == false".expression
        assertEquals("(true == false)", expression.toString())
        assertFalse(expression.evaluate(Context()))
    }

    @Test
    fun testBoolean3() {
        val expression = "false == false".expression
        assertEquals("(false == false)", expression.toString())
        assertTrue(expression.evaluate(Context()))
    }

    @Test
    fun test1eq1() {
        val expression = "1 == 1".expression
        assertEquals("(1 == 1)", expression.toString())
        assertTrue(expression.evaluate(Context()))
    }

    @Test
    fun test1neq1() {
        val expression = "1 != 1".expression
        assertEquals("(1 != 1)", expression.toString())
        assertFalse(expression.evaluate(Context()))
    }

    @Test
    fun test1gt1() {
        val expression = "1 > 1".expression
        assertEquals("(1 > 1)", expression.toString())
        assertFalse(expression.evaluate(Context()))
    }

    @Test
    fun testOr() {
        val expression = "1 > 1 || 1 == 1".expression
        assertEquals("(1 > 1 || 1 == 1)", expression.toString())
        assertTrue(expression.evaluate(Context()))
    }

    @Test
    fun testGroup() {
        val expression = "1 > 1 || (1 > 1 || 1 == 1)".expression
        assertEquals("(1 > 1 || (1 > 1 || 1 == 1))", expression.toString())
        assertTrue(expression.evaluate(Context()))
    }

    @Test
    fun testGroup2() {
        val expression = "1 > 1 && (1 > 1 || 1 == 1)".expression
        assertEquals("(1 > 1 && (1 > 1 || 1 == 1))", expression.toString())
        assertFalse(expression.evaluate(Context()))
    }

    @Test
    fun testNegateGroup2() {
        val expression = "!(1 > 1 && (1 > 1 || 1 == 1))".expression
        assertEquals("!(1 > 1 && (1 > 1 || 1 == 1))", expression.toString())
        assertTrue(expression.evaluate(Context()))
    }

    @Test
    fun testDate() {
        val expression = "a == b".expression
        assertEquals("(a == b)", expression.toString())
        val now = Date()
        assertTrue(expression.evaluate(Context(mapOf("a" to now, "b" to now))))
    }

    @Test
    fun testDate2() {
        val expression = "a < b".expression
        assertEquals("(a < b)", expression.toString())
        val now = System.currentTimeMillis()
        val a = Date(now)
        val b = Date(now + 60_000)
        assertTrue(expression.evaluate(Context(mapOf("a" to a, "b" to b))))
    }

    @Test
    fun testGt() {
        val expression = "a > b".expression
        assertEquals("(a > b)", expression.toString())
        assertFalse(expression.evaluate(Context(mapOf("a" to 1, "b" to 2))))
        assertTrue(expression.evaluate(Context(mapOf("a" to 2, "b" to 1))))
    }

    @Test
    fun testLt() {
        val expression = "a < b".expression
        assertEquals("(a < b)", expression.toString())
        assertFalse(expression.evaluate(Context(mapOf("a" to 2, "b" to 1))))
        assertTrue(expression.evaluate(Context(mapOf("a" to 1, "b" to 2))))
    }

    @Test
    fun testLongToInt() {
        val expression = "a == b".expression
        assertEquals("(a == b)", expression.toString())
        assertTrue(expression.evaluate(Context(mapOf("a" to 0, "b" to 0L))))
    }
}