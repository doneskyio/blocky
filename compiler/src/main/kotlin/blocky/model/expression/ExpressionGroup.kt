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

data class ExpressionGroup(
    val children: List<Expression>,
    val negative: Boolean = false,
    override val operator: Operator = Operator.And
) : Expression {

    enum class EvaluationState {
        Unknown,
        Equals
    }

    override fun evaluate(context: Context): Boolean {
        var evaluation = EvaluationState.Unknown
        var lastOperator = Operator.And
        children.forEachIndexed { index, expression ->
            if (expression.evaluate(context)) {
                if (evaluation != EvaluationState.Equals) {
                    evaluation = when {
                        index == 0 -> EvaluationState.Equals
                        lastOperator == Operator.Or -> EvaluationState.Equals
                        else -> return negative
                    }
                }
            } else if (index > 0 && !(expression.operator == Operator.Or && evaluation == EvaluationState.Equals)) {
                return negative
            }
            lastOperator = expression.operator
        }
        if (negative)
            return evaluation != EvaluationState.Equals
        return evaluation == EvaluationState.Equals
    }

    override fun toString(): String {
        val expressions = buildString {
            children.forEachIndexed { index, expression ->
                append(expression)
                if (index + 1 != children.size) {
                    append(" ")
                    append(expression.operator)
                    append(" ")
                }
            }
        }
        return "${if (negative) "!" else ""}($expressions)"
    }
}