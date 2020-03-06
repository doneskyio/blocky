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

import java.math.BigDecimal

interface NumberComparator {

    fun compareTo(number: Number, other: Number): Int
    fun notEquals(number: Number, other: Number): Boolean

    companion object {

        var comparator: NumberComparator = BaseNumberComparator()
    }
}

open class BaseNumberComparator : NumberComparator {

    override fun compareTo(number: Number, other: Number): Int = number.compareTo(other)
    override fun notEquals(number: Number, other: Number) = number != other
}

fun Number.compareTo(other: Number): Int = when (this) {
    is Int -> compareTo(other.toInt())
    is Float -> compareTo(other.toFloat())
    is Long -> compareTo(other.toLong())
    is Double -> compareTo(other.toDouble())
    is Short -> compareTo(other.toShort())
    is Byte -> compareTo(other.toByte())
    is BigDecimal -> {
        if (other is BigDecimal) {
            compareTo(other)
        } else {
            TODO("Unsupported $this compareTo $other")
        }
    }
    else -> TODO("Unsupported $this compareTo $other")
}
