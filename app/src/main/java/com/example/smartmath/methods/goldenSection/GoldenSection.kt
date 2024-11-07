package com.example.smartmath.methods.goldenSection

import android.util.Log
import com.example.smartmath.methods.customExceptions.ComparisonFException
import com.example.smartmath.methods.customExceptions.ConditionExcpetion
import com.example.smartmath.methods.dataclasses.StateMinABE
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.abs
import kotlin.math.pow

const val TAG_GS = "GoldenSectionTag"

fun goldenSection(
    a: Double,
    b: Double,
    accuracy: Double,
    expression: String
): List<StateMinABE>{
    val ro = 1.618
    val resultList = mutableListOf<StateMinABE>(createInitState(a, b, ro, expression))

    //abs(resultList.last().b - resultList.last().a)
    try{
        while ((resultList.last().condition ?: throw ConditionExcpetion("Condition cannot be null")) > accuracy) {
            // F(x1) < F(x2)
            if (resultList.last().fX1LessThanFX2 ?: throw ComparisonFException("Comparison cannot be null")) {
                resultList.add(shiftRightSide(ro, expression, resultList.last()))
            } else {
                resultList.add(shiftLeftSide(ro, expression, resultList.last()))
            }
        }

        val finalValues = findFinalValues(a = resultList.last().a, b = resultList.last().b, expression)
        resultList[resultList.size - 1] = resultList.last().copy(
            xEnd = finalValues.first,
            fEnd = finalValues.second
        )
    } catch (e: ConditionExcpetion){
        Log.d(TAG_GS, "Condition is null. It is mistake.")
    } catch (e: ComparisonFException){
        Log.d(TAG_GS, "Comparison of F(x) is null. It is mistake.")
    }
    return resultList
}

//private fun solveExpression(x: Double): Double = 3 * x.pow(4) + 20 * x.pow(3) - 90 * x - 84
private fun solveExpression(input: String, x: Double): Double{
    val variables = mapOf("x" to x)
    val expression = ExpressionBuilder(input)
        .variables(variables.keys)
        .build()
        .setVariables(variables)
    return expression.evaluate()
}

// Creating the first element
private fun createInitState(a: Double, b: Double, t: Double, expression: String): StateMinABE {
    val x1My = findX(a, b, t, true)
    val x2My = findX(a, b, t, false)
    val fX1My = solveExpression(expression, x1My)
    val fX2My = solveExpression(expression, x2My)
    return StateMinABE(
        a = a,
        b = b,
        x1 = x1My,
        x2 = x2My,
        condition = abs(b - a),
        f1 = fX1My,
        f2 = fX2My,
        fX1LessThanFX2 = if (fX1My < fX2My) true else false
    )
}

// F(x1) < F(x2)
private fun shiftRightSide(t: Double, expression: String, previousEl: StateMinABE): StateMinABE {
    val x1My = findX(a = previousEl.a, b = previousEl.x2, t = t, doesTNeedDegree = true)
    val fX1My = solveExpression(expression, x1My)
    val fX2My = previousEl.f1

    return StateMinABE(
        a = previousEl.a,
        b = previousEl.x2,
        x1 = x1My,
        x2 = previousEl.x1,
        condition = abs(previousEl.x2 - previousEl.a),
        f1 = fX1My,
        f2 = fX2My,
        fX1LessThanFX2 = if (fX1My < fX2My) true else false
    )
}

// F(x1) => F(x2)
private fun shiftLeftSide(t: Double, expression: String, previousEl: StateMinABE): StateMinABE {
    val x2My = findX(a = previousEl.x1, b = previousEl.b, t, false)
    val fX1My = previousEl.f2
    val fX2My = solveExpression(expression, x2My)

    return StateMinABE(
        a = previousEl.x1,
        b = previousEl.b,
        x1 = previousEl.x2,
        x2 = x2My,
        condition = abs(previousEl.b - previousEl.x1),
        f1 = fX1My,
        f2 = fX2My,
        fX1LessThanFX2 = if (fX1My < fX2My) true else false
    )
}

private fun findX(a: Double, b: Double, t: Double, doesTNeedDegree: Boolean): Double {
    val finalT = if (doesTNeedDegree) t.pow(2) else t
    return a + 1 / finalT * (b - a)
}

private fun findXFinal(a: Double, b: Double) = (a + b) / 2

private fun findFinalValues(a: Double, b: Double, expression: String): Pair<Double, Double> {
    val xFinal = findXFinal(a, b)
    return xFinal to solveExpression(expression, xFinal)
}