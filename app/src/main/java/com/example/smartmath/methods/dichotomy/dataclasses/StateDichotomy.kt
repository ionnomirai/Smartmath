package com.example.smartmath.methods.dichotomy.dataclasses

data class StateDichotomy(
    val a: Double,
    val b: Double,
    val middle: Double,
    val x1: Double,
    val x2: Double,
    val f1: Double,
    val f2: Double,
    val aPlOne: Double? = null,
    val bPlOne: Double? = null,
    val condition: Double? = null,
    val xEnd: Double? = null,
    val fEnd: Double? = null
)
