package com.example.calculatorapp.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
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
        private set


    fun onBackspaceClicked() {
        if (homeUiState.isSecondDigit) {
            if (homeUiState.secondDigit.last() == '.') {
                homeUiState = homeUiState.copy(
                    isSecondDot = false
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
            }
            homeUiState = homeUiState.copy(
                firstDigit = homeUiState.firstDigit.dropLast(1)
            )
            if (homeUiState.firstDigit.isEmpty()) {
                homeUiState = homeUiState.copy(
                    isFirstDigit = false,
                )
            }
        } else {
            homeUiState = homeUiState.copy(
                displayError = "Nothing to delete"
            )
        }
    }

    fun onDigitClicked(digit: String) {
        Log.d("HomeViewModel", "onDigitClicked: $digit")
        if (homeUiState.isOperator) {
            homeUiState = homeUiState.copy(
                secondDigit = homeUiState.secondDigit + digit, isSecondDigit = true
            )
        } else {
            homeUiState = homeUiState.copy(
                firstDigit = homeUiState.firstDigit + digit, isFirstDigit = true
            )
        }
    }

    fun onDotClicked() {
        if (homeUiState.isOperator && !homeUiState.isSecondDot) {
            homeUiState = homeUiState.copy(
                secondDigit = homeUiState.secondDigit + ".",
                isSecondDigit = true,
                isSecondDot = true
            )
        } else if (homeUiState.isFirstDigit && !homeUiState.isFirstDot) {
            homeUiState = homeUiState.copy(
                firstDigit = homeUiState.firstDigit + ".", isFirstDigit = true, isFirstDot = true
            )
        }
    }

    fun onOperatorClicked(operator: String) {
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
        } else {
            homeUiState = homeUiState.copy(
                operator = operator, isOperator = true
            )
        }
        homeUiState = homeUiState.copy(
            lastEntered = LastEntered.OPERATOR,
            displayError = ""
        )
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
        if (validateEquals()) {

            val firstDigit = homeUiState.firstDigit.toDouble().toBigDecimal()
            val secondDigit = homeUiState.secondDigit.toDouble().toBigDecimal()
            var result = when (homeUiState.operator) {
                "+" -> firstDigit + secondDigit
                "-" -> firstDigit - secondDigit
                "*" -> firstDigit * secondDigit
                "/" -> firstDigit / secondDigit
                else -> 0
            }
            if (isIntegerWithZeroFraction(result.toDouble())) {
                result = result.toInt()
                homeUiState = homeUiState.copy(
                    isFirstDot = false,
                )
            } else {
                homeUiState = homeUiState.copy(
                    isFirstDot = true,
                )
            }
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
                displayTextHistory = homeUiState.firstDigit + homeUiState.operator + homeUiState.secondDigit + "=" + result.toString(),
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
        }
    }



    fun isInfinite(value: Double): Boolean {
        return value.isInfinite()
    }

    fun isIntegerWithZeroFraction(value: Double): Boolean {
        return value == value.toInt().toDouble()
    }

    private fun validateEquals(): Boolean {
        return homeUiState.isFirstDigit && homeUiState.isSecondDigit && homeUiState.isOperator
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