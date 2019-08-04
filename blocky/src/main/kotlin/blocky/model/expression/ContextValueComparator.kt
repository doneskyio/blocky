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
import java.util.Date

interface ContextValueComparator {

    fun compareTo(context: Context, name: String, other: Any?, comparator: Comparator): Boolean

    companion object {

        var comparator: ContextValueComparator = BaseContextValueComparator()
    }
}

open class BaseContextValueComparator : ContextValueComparator {

    protected open fun getValue(context: Context, other: Any?): Any? = if (other is ContextValue) {
        context[other.name]
    } else {
        other
    }

    protected open fun compareTo(value: Any, otherValue: Any, comparator: Comparator) =
        when (value) {
            is String -> StringValue.compareTo(value, otherValue, comparator)
            is Number -> NumberValue.compareTo(value, otherValue, comparator)
            is Boolean -> BooleanValue.compareTo(value, otherValue)
            is Date -> DateValue.compareTo(value, otherValue, comparator)
            else -> TODO("Unsupported value type: $value")
        }

    override fun compareTo(context: Context, name: String, other: Any?, comparator: Comparator): Boolean {
        val otherValue = getValue(context, other) ?: return false
        val value = context[name] ?: return false
        if (comparator == Comparator.Equals && value == otherValue)
            return true
        if (comparator == Comparator.NotEquals && value != otherValue)
            return true
        if (value == NullValue || otherValue == NullValue)
            return false
        return compareTo(value, otherValue, comparator)
    }
}

internal fun Number.compareTo(other: Number, comparator: Comparator) = when (comparator) {
    Comparator.Equals -> this == other
    Comparator.NotEquals -> this != other
    else -> {
        when (comparator) {
            Comparator.GreaterThan -> NumberComparator.comparator.compareTo(this, other) == 1
            Comparator.GreaterThanEquals -> {
                val c = NumberComparator.comparator.compareTo(this, other)
                c == 1 || c == 0
            }
            Comparator.LessThan -> NumberComparator.comparator.compareTo(this, other) == -1
            Comparator.LessThanEquals -> {
                val c = NumberComparator.comparator.compareTo(this, other)
                c == -1 || c == 0
            }
            else -> throw UnsupportedOperationException("Unsupported comparator: $comparator")
        }
    }
}

internal fun <T> Comparable<T>.compareTo(other: T, comparator: Comparator) = when (comparator) {
    Comparator.Equals -> this == other
    Comparator.NotEquals -> this != other
    Comparator.GreaterThan -> this > other
    Comparator.GreaterThanEquals -> this >= other
    Comparator.LessThan -> this < other
    Comparator.LessThanEquals -> this <= other
}