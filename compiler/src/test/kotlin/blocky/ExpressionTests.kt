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
import blocky.model.IfBlock
import blocky.model.expression.Expression
import java.io.ByteArrayInputStream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ExpressionTests {

    private val String.expression: Expression
        get() {
            val template = Compiler.compile(ByteArrayInputStream("[template name=\"test\"][if [$this]][/if][/template]".toByteArray()))
            val block = template.children.first() as IfBlock
            return block.expression
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
}