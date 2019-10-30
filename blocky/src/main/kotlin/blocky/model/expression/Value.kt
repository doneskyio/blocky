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
package blocky.model.expression

import blocky.compiler.CompilerException
import blocky.model.Context
import java.util.Date

internal interface Value {

    fun compareTo(context: Context, other: Any?, comparator: Comparator): Boolean
}

internal object NullValue : Value {

    override fun compareTo(context: Context, other: Any?, comparator: Comparator) = other == this

    override fun toString(): String = "null"
}

internal class BooleanValue(internal val value: Boolean) : Value {

    override fun compareTo(context: Context, other: Any?, comparator: Comparator) = compareTo(value, other)

    override fun toString(): String = value.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BooleanValue

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {

        fun compareTo(value: Boolean, other: Any?) = when (other) {
            is BooleanValue -> value == other.value
            is Boolean -> value == other
            else -> false
        }
    }
}

internal class ContextValue(internal val name: String) : Value {

    override fun compareTo(context: Context, other: Any?, comparator: Comparator): Boolean =
        ContextValueComparator.comparator.compareTo(context, name, other, comparator)

    override fun toString() = name
}

internal class StringValue(internal val value: String) : Value {

    override fun compareTo(context: Context, other: Any?, comparator: Comparator) = compareTo(value, other, comparator)

    override fun toString() = "\"$value\""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StringValue

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {

        fun compareTo(value: String, other: Any?, comparator: Comparator) =
            when (other) {
                is String -> value.compareTo(other, comparator)
                is StringValue -> value.compareTo(other.value, comparator)
                else -> false
            }
    }
}

internal class DateValue(internal val value: Date) : Value {

    override fun compareTo(context: Context, other: Any?, comparator: Comparator) = compareTo(value, other, comparator)

    override fun toString() = value.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DateValue

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {

        fun compareTo(value: Date, other: Any?, comparator: Comparator) =
            when (other) {
                is Date -> value.compareTo(other, comparator)
                is DateValue -> value.compareTo(other.value, comparator)
                else -> false
            }
    }
}

internal open class NumberValue<out T : Number>(internal val value: T) : Value {

    override fun compareTo(context: Context, other: Any?, comparator: Comparator) = compareTo(value, other, comparator)

    override fun toString() = value.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NumberValue<*>

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {

        fun compareTo(value: Number, other: Any?, comparator: Comparator) =
            when (other) {
                is Number -> value.compareTo(other, comparator)
                is NumberValue<*> -> value.compareTo(other.value, comparator)
                else -> false
            }
    }
}

internal open class EnumValue(internal val value: Enum<*>) : Value {

    override fun compareTo(context: Context, other: Any?, comparator: Comparator) = compareTo(value, other, comparator)

    override fun toString() = value.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EnumValue

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {

        fun compareTo(value: Enum<*>, other: Any?, comparator: Comparator): Boolean {
            if (comparator != Comparator.Equals && comparator != Comparator.NotEquals)
                throw CompilerException("Unsupported comparator: $comparator")
            return when (other) {
                is Enum<*> -> if (comparator == Comparator.Equals) value == other else value != other
                is EnumValue -> if (comparator == Comparator.Equals) value == other.value else value != other.value
                else -> false
            }
        }
    }
}