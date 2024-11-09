package com.example.smartmath.methods.dataclasses

/* All necessary fields for Dihotomy and Golden section */
data class StateMinABE(
    val a: Double,
    val b: Double,
    val middle: Double? = null,
    val x1: Double,
    val x2: Double,
    val f1: Double,
    val f2: Double,
    val fX1LessThanFX2: Boolean? = null,
    val aPlOne: Double? = null,
    val bPlOne: Double? = null,
    val condition: Double? = null,
    val xEnd: Double? = null,
    val fEnd: Double? = null
)
