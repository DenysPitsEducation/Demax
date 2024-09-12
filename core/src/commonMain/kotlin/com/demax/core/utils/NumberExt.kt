package com.demax.core.utils

import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt

/**
 * Converts 50.0 to "50", 50.50 to "50.5", 50.1192 to "50.12"
 */
fun Double.formatFractionalPart(): String {
    val roundedValue = round(digitsAfterPoint = 2)

    val separator = "."

    val integerDigits = roundedValue.toInt()
    val floatDigits = ((roundedValue - integerDigits) * 100f).roundToInt()

    val formattedFloatPart = when (floatDigits) {
        0 -> ""
        in 1..9 -> separator + "0" + floatDigits
        else -> separator + floatDigits.toString().trimEnd('0')
    }

    return "$integerDigits$formattedFloatPart"
}

fun Float.formatFractionalPart() = toDouble().formatFractionalPart()

fun Double.round(digitsAfterPoint: Int): Double {
    val helperNumber = 10.0.pow(digitsAfterPoint)
    return round(this * helperNumber) / helperNumber
}
