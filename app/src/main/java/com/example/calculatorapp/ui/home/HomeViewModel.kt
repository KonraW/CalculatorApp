package com.example.calculatorapp.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.roundToLong

data class HomeUiState(
//    val displayText: String = "",
//    val displayTextResult: String = "",
    val displayTextHistory: String = "",
    val displayError: String = "",
    val lastEntered: LastEntered = LastEntered.FIRST_DIGIT,
    val firstDigit: String = "",
    val secondDigit: String = "",
    val operator: String = "",
    val isFirstDigit: Boolean = false,
    val isSecondMinus: Boolean = false,
    val isSecondDigit: Boolean = false,
    val isOperator: Boolean = false,
    val isFirstMinus: Boolean = false,
    val isFirstDot: Boolean = false,
    val isSecondDot: Boolean = false,
)

enum class LastEntered {
    FIRST_DIGIT, SECOND_DIGIT, OPERATOR
}


class HomeViewModel() : ViewModel() {
    var homeUiState by mutableStateOf(HomeUiState())
//        private set


    fun onBackspaceClicked() {
        if (homeUiState.isSecondDigit) {
            if (homeUiState.secondDigit.last() == '.') {
                homeUiState = homeUiState.copy(
                    isSecondDot = false
                )
            } else if (homeUiState.firstDigit.last() == '-') {
                homeUiState = homeUiState.copy(
                    isSecondMinus = false
                )
            }
            homeUiState = homeUiState.copy(
                secondDigit = homeUiState.secondDigit.dropLast(1)
            )
            if (homeUiState.secondDigit.isEmpty()) {
                homeUiState = homeUiState.copy(
                    isSecondDigit = false, lastEntered = LastEntered.OPERATOR
                )
            }
        } else if (homeUiState.isOperator) {
            homeUiState = homeUiState.copy(
                operator = "", isOperator = false, lastEntered = LastEntered.FIRST_DIGIT
            )
        } else if (homeUiState.isFirstDigit) {
            if (homeUiState.firstDigit.last() == '.') {
                homeUiState = homeUiState.copy(
                    isFirstDot = false
                )
            } else if (homeUiState.firstDigit.last() == '-') {
                homeUiState = homeUiState.copy(
                    isFirstMinus = false
                )
            }
            homeUiState = homeUiState.copy(
                firstDigit = homeUiState.firstDigit.dropLast(1)
            )
            if (homeUiState.firstDigit.isEmpty()) {
                homeUiState = homeUiState.copy(
                    isFirstDigit = false,
                )
            }
        }
        deleteError()
    }

    fun onDigitClicked(digit: String) {
        Log.d("HomeViewModel", "onDigitClicked: $digit")
        if (homeUiState.isOperator) {
            if (digit == "00" && (homeUiState.secondDigit.isEmpty() || homeUiState.secondDigit == "0" || homeUiState.secondDigit == "-")) {
                return
            }
            if (digit == "0" && (homeUiState.secondDigit == "0" || homeUiState.secondDigit == "-")) {
                return
            }

            homeUiState = homeUiState.copy(
                secondDigit = homeUiState.secondDigit + digit,
                isSecondDigit = true,
                lastEntered = LastEntered.SECOND_DIGIT
            )
        } else {
            if (digit == "00" && (homeUiState.firstDigit.isEmpty() || homeUiState.firstDigit == "0" || homeUiState.firstDigit == "-")) {
                return
            }
            if (digit == "0" && (homeUiState.firstDigit == "0" || homeUiState.firstDigit == "-")) {
                return
            }
            homeUiState = homeUiState.copy(
                firstDigit = homeUiState.firstDigit + digit,
                isFirstDigit = true,
                lastEntered = LastEntered.FIRST_DIGIT
            )
        }
        deleteError()
    }

    fun deleteError() {
        homeUiState = homeUiState.copy(
            displayError = ""
        )
    }

    fun onDotClicked() {
        if (homeUiState.lastEntered == LastEntered.OPERATOR || homeUiState.secondDigit == "-" && !homeUiState.isSecondDot) {
            homeUiState = homeUiState.copy(
                secondDigit = homeUiState.secondDigit + "0.",
                isSecondDigit = true,
                isSecondDot = true
            )
        } else if (homeUiState.lastEntered == LastEntered.SECOND_DIGIT && !homeUiState.isSecondDot) {
            homeUiState = homeUiState.copy(
                secondDigit = homeUiState.secondDigit + ".",
                isSecondDigit = true,
                isSecondDot = true
            )
        } else if (homeUiState.lastEntered == LastEntered.FIRST_DIGIT && (!homeUiState.isFirstDigit || homeUiState.firstDigit == "-") && !homeUiState.isFirstDot) {
            homeUiState = homeUiState.copy(
                firstDigit = homeUiState.firstDigit + "0.",
                isFirstDigit = true,
                isFirstDot = true
            )
        } else if (homeUiState.lastEntered == LastEntered.FIRST_DIGIT && !homeUiState.isFirstDot && homeUiState.isFirstDigit) {
            homeUiState = homeUiState.copy(
                firstDigit = homeUiState.firstDigit + ".", isFirstDigit = true, isFirstDot = true
            )
        }
        deleteError()
    }

    fun onOperatorClicked(operator: String) {
        if (homeUiState.firstDigit.isEmpty() || homeUiState.firstDigit == "-") {
            homeUiState = homeUiState.copy(
                displayError = "Enter a number first"
            )
            return
        }
        if (homeUiState.isOperator) {
            if (homeUiState.lastEntered == LastEntered.OPERATOR) {
                homeUiState = homeUiState.copy(
                    operator = operator
                )
            } else {
                onEqualsClicked()
                homeUiState = homeUiState.copy(
                    operator = operator,
                    isOperator = true
                )
            }
        } else if (homeUiState.isFirstDigit) {
            homeUiState = homeUiState.copy(
                operator = operator, isOperator = true
            )
        } else {
            return
        }
        homeUiState = homeUiState.copy(
            lastEntered = LastEntered.OPERATOR
        )
        deleteError()
    }

    //    fun onMinusOperatorClicked() {
//        if (homeUiState.isOperator) {
//            homeUiState = homeUiState.copy(
//                displayText = homeUiState.displayText + "-",
//                isMinusOperator = true
//            )
//        } else {
//            homeUiState = homeUiState.copy(
//                displayText = homeUiState.displayText + "-",
//                isOperator = true
//            )
//        }
//    }

    fun onFirstNegateClicked() {
        if (homeUiState.isFirstDigit) {
            if (homeUiState.isFirstMinus) {
                homeUiState = homeUiState.copy(
                    isFirstMinus = false,
                    firstDigit = homeUiState.firstDigit.substring(1),
                    displayError = ""
                )
            } else {
                homeUiState = homeUiState.copy(
                    isFirstMinus = true,
                    firstDigit = "-${homeUiState.firstDigit}",
                    displayError = ""
                )
            }
        }
        deleteError()
    }

    fun onSecondNegateClicked() {
        if (homeUiState.isSecondDigit) {
            if (homeUiState.isSecondMinus) {
                homeUiState = homeUiState.copy(
                    isSecondMinus = false,
                    secondDigit = homeUiState.secondDigit.substring(1),
                    displayError = ""
                )
            } else {
                homeUiState = homeUiState.copy(
                    isSecondMinus = true,
                    secondDigit = "-${homeUiState.secondDigit}",
                    displayError = ""
                )
            }
        }
        deleteError()
    }

//    fun resultDisplay() {
//        var result = ""
//        if (homeUiState.isSecondDigit) {
//            result = homeUiState.secondDigit
//        }
//        if (homeUiState.isSecondMinus) {
//            result = " -$result"
//        }
//        if (homeUiState.isOperator) {
//            result = " " + homeUiState.operator + result
//        }
//        if (homeUiState.isFirstDigit) {
//            result = homeUiState.firstDigit + result
//        }
//        if (homeUiState.isFirstMinus) {
//            result = " -$result"
//        }
//
//        homeUiState = homeUiState.copy(
//            displayTextResult = result
//        )
//    }

    fun onEqualsClicked() {
        if ((homeUiState.secondDigit.toDouble().compareTo(0)==0) && homeUiState.operator == "/") {
            homeUiState = homeUiState.copy(
                displayError = "Cannot divide by zero"
            )
            return
        }
        if (validateEquals()) {

            val firstDigit = homeUiState.firstDigit.toDouble().toBigDecimal()
            val secondDigit = homeUiState.secondDigit.toDouble().toBigDecimal()
            var result = when (homeUiState.operator) {
                "+" -> firstDigit + secondDigit
                "-" -> firstDigit - secondDigit
                "*" -> firstDigit * secondDigit
                "/" -> firstDigit.divide(secondDigit, MathContext.DECIMAL128) //firstDigit / secondDigit
                else -> BigDecimal(0)
            }
            result= result.stripTrailingZeros()
//            if (isIntegerWithZeroFraction(result.toDouble())) {
//                result = result2.toInt()
//                homeUiState = homeUiState.copy(
//                    isFirstDot = false,
//                )
//            } else {
//                homeUiState = homeUiState.copy(
//                    isFirstDot = true,
//                )
//            }
//            var bigDecimalResult = result.toDouble()

            if (isInfinite(result.toDouble())) {
                homeUiState = homeUiState.copy(
                    displayError = "Cannot divide by zero"
                )
                return
            }
            //isMinus(result)
            if (result.toDouble() < 0.0) {
                homeUiState = homeUiState.copy(
                    isFirstMinus = true,
                )
            }

            homeUiState = homeUiState.copy(
                displayTextHistory = homeUiState.firstDigit + " " + homeUiState.operator + " " + homeUiState.secondDigit + " = " + result.toString(),
                firstDigit = result.toString(),
                secondDigit = "",
                operator = "",
                isOperator = false,
                isSecondDigit = false,
                isSecondMinus = false,
                isFirstDigit = true,
                lastEntered = LastEntered.FIRST_DIGIT,
                isSecondDot = false,
            )
        } else {
            homeUiState = homeUiState.copy(
                displayError = "Invalid operation"
            )
            return
        }
        deleteError()
    }


    fun isInfinite(value: Double): Boolean {
        return value.isInfinite()
    }

    fun isIntegerWithZeroFraction(value: Double): Boolean {
        return value == value.toInt().toDouble()
    }

    private fun validateEquals(): Boolean {
        return homeUiState.isFirstDigit && homeUiState.isSecondDigit && homeUiState.isOperator && homeUiState.firstDigit != "-" && homeUiState.secondDigit != "-"
    }

    fun onClearClicked() {
        homeUiState = homeUiState.copy(
//            displayTextResult = "",
            displayTextHistory = "",
            firstDigit = "",
            secondDigit = "",
            operator = "",
            isFirstDigit = false,
            isSecondDigit = false,
            isOperator = false,
            isSecondMinus = false,
            isFirstMinus = false,
            isFirstDot = false,
            isSecondDot = false,
            displayError = ""
        )
    }

}