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

import blocky.model.Context

interface Value {

    fun compareTo(context: Context, other: Any?, comparator: Comparator): Boolean
}

object NullValue : Value {

    override fun compareTo(context: Context, other: Any?, comparator: Comparator): Boolean {
        return other == this
    }

    override fun toString(): String = "null"
}

class BooleanValue(internal val value: Boolean) : Value {

    override fun compareTo(context: Context, other: Any?, comparator: Comparator) =
        when (other) {
            is BooleanValue -> value == other.value
            is Boolean -> value == other
            else -> false
        }

    override fun toString(): String = value.toString()
}

class ContextValue(private val name: String) : Value {

    override fun compareTo(context: Context, other: Any?, comparator: Comparator): Boolean {
        val otherValue = if (other is ContextValue) {
            context[other.name] ?: return false
        } else {
            other
        }
        return when (val value = context[name] ?: return false) {
            is String -> {
                when (otherValue) {
                    is String -> value.compareTo(otherValue, comparator)
                    is StringValue -> value.compareTo(otherValue.value, comparator)
                    else -> false
                }
            }
            is Number -> {
                when (otherValue) {
                    is Number -> value.compareTo(otherValue, comparator)
                    is NumberValue<*> -> value.compareTo(otherValue.value, comparator)
                    else -> false
                }
            }
            is Boolean -> {
                when (otherValue) {
                    is BooleanValue -> value == otherValue.value
                    is Boolean -> value == otherValue
                    else -> false
                }
            }
            else -> TODO("Unsupported value type: $value")
        }
    }

    override fun toString() = name
}

class StringValue(internal val value: String) : Value {

    override fun compareTo(context: Context, other: Any?, comparator: Comparator) =
        when (other) {
            is String -> value.compareTo(other, comparator)
            is StringValue -> value.compareTo(other.value, comparator)
            else -> false
        }

    override fun toString() = "\"$value\""
}

open class NumberValue<out T : Number>(internal val value: T) : Value {

    override fun compareTo(context: Context, other: Any?, comparator: Comparator): Boolean =
        when (other) {
            is Number -> value.compareTo(other, comparator)
            is NumberValue<*> -> value.compareTo(other.value, comparator)
            else -> false
        }

    override fun toString() = value.toString()
}

private fun Number.compareTo(other: Number): Int = when (this) {
    is Int -> compareTo(other.toInt())
    is Float -> compareTo(other.toFloat())
    is Long -> compareTo(other.toLong())
    is Double -> compareTo(other.toDouble())
    else -> TODO("Unsupported $this compareTo $other")
}

private fun Number.compareTo(other: Number, comparator: Comparator) = when (comparator) {
    Comparator.Equals -> this == other
    Comparator.NotEquals -> this != other
    else -> {
        when (comparator) {
            Comparator.GreaterThan -> compareTo(other) == 1
            Comparator.GreaterThanEquals -> {
                val c = compareTo(other)
                c == 1 || c == 0
            }
            Comparator.LessThan -> compareTo(other) == -1
            Comparator.LessThanEquals -> {
                val c = compareTo(other)
                c == -1 || c == 0
            }
            else -> throw UnsupportedOperationException("Unsupported comparator: $comparator")
        }
    }
}

private fun <T> Comparable<T>.compareTo(other: T, comparator: Comparator) = when (comparator) {
    Comparator.Equals -> this == other
    Comparator.NotEquals -> this != other
    Comparator.GreaterThan -> this < other
    Comparator.GreaterThanEquals -> this <= other
    Comparator.LessThan -> this > other
    Comparator.LessThanEquals -> this >= other
}
