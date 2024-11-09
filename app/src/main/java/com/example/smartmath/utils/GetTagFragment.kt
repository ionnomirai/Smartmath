package com.example.smartmath.utils

/* Create and return tag, according to from and where the user wanted to move. */
fun getTagFragment(direction: Directions): String =
    when(direction){
        Directions.FromMainToDichotomy ->
            "Move from FrMainScreen to FrOneDimensionalMinABE (${MethodNames.Dichotomy.title})"
        Directions.FromMainToGoldenSection ->
            "Move from FrMainScreen to FrOneDimensionalMinABE (${MethodNames.GoldenSection.title})"
        Directions.FromMethodMinABEToDetailed -> "Move from FrOneDimensionalMinABE to FrOneDimMinABEDetails"
    }