package com.example.smartmath.methods.dichotomy

import com.example.smartmath.methods.dichotomy.dataclasses.StateDichotomy
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.abs

fun dichotomy(a: Double, b: Double, e: Double, expression: String): List<StateDichotomy>{

    val stateList = mutableListOf<StateDichotomy>() // save all iterations
    var curAk: Double = a // left border
    var curBk: Double = b // right border
    var condition = abs(curBk - curAk) // temp condition (it defines should we go further or stop

    while (condition >= e) {

        val middleCur = findMiddle(curAk, curBk)
        val x1 = findX12(middleCur, e, '-')
        val x2 = findX12(middleCur, e, '+')

        val currentState = StateDichotomy(
            a = curAk,
            b = curBk,
            middle = middleCur,
            x1 = x1,
            x2 = x2,
            f1 = solveExpression(expression, x1),
            f2 = solveExpression(expression, x2)
        )

        if (currentState.f1 < currentState.f2) {
            curBk = middleCur
        } else {
            curAk = middleCur
        }
        condition = abs(curBk - curAk)


        if (condition <= e) {
            val xEnd = findMiddle(curAk, curBk)
            stateList.add(
                currentState.copy(
                    aPlOne = curAk,
                    bPlOne = curBk,
                    condition = condition,
                    xEnd = xEnd,
                    fEnd = solveExpression(expression, xEnd)
                )
            )
            break
        }
        stateList.add(currentState.copy(aPlOne = curAk, bPlOne = curBk, condition = condition))
    }

    return stateList
}

private fun findMiddle(a: Double, b: Double): Double = (a + b) / 2

private fun findX12(m: Double, e: Double, sign: Char): Double =
    when (sign) {
        '+' -> m + (e / 2)
        '-' -> m - (e / 2)
        else -> throw Exception("incorrect sign")
    }

private fun solveExpression(input: String, x: Double): Double {
    val variables = mapOf("x" to x)
    val expression = ExpressionBuilder(input)
        .variables(variables.keys)
        .build()
        .setVariables(variables)
    return expression.evaluate()
}
