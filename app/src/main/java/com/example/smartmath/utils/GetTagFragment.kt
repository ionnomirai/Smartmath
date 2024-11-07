package com.example.smartmath.utils

fun getTagFragment(direction: Directions): String{
    return when(direction){
        Directions.FromMainToDichotomy -> "Move from FrMainScreen to FrOneDimensionalMinABE " +
                "(${MethodNames.Dichotomy.title})"
        Directions.FromMainToGoldenSection -> "Move from FrMainScreen to FrOneDimensionalMinABE " +
                "(${MethodNames.GoldenSection.title})"
        Directions.FromMethodMinABEToDetailed -> "Move from FrOneDimensionalMinABE to FrOneDimMinABEDetails"
    }
}