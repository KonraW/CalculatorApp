package com.example.calculatorapp.ui.home

import androidx.lifecycle.ViewModel

data class HomeUiState(
//    val displayText: String = "",
    val displayTextResult: String = "",
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
    val isFirstMinus: Boolean = false
)

enum class LastEntered {
    FIRST_DIGIT,
    SECOND_DIGIT,
    OPERATOR
}


class HomeViewModel() : ViewModel() {
    var homeUiState = HomeUiState()
        private set

    fun onDigitClicked(digit: String) {
        if (homeUiState.isOperator) {
            homeUiState = homeUiState.copy(
                secondDigit = homeUiState.secondDigit + digit,
                isSecondDigit = true
            )
        } else {
            homeUiState = homeUiState.copy(
                firstDigit = homeUiState.firstDigit + digit,
                isFirstDigit = true
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
            }

//            homeUiState.copy(
//                displayText = homeUiState.displayText + operator
//            )
        } else {
            homeUiState = homeUiState.copy(
                operator = operator,
                isOperator = true
            )
        }
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
    fun onFirstMinusClicked() {
        homeUiState = homeUiState.copy(
            isFirstMinus = true
        )
    }

    fun onSecondMinusClicked() {
        homeUiState = homeUiState.copy(
            isSecondMinus = true
        )
    }

    fun resultDisplay() {
        var result = ""
        if (homeUiState.isSecondDigit) {
            result = homeUiState.secondDigit
        }
        if (homeUiState.isSecondMinus) {
            result = " -$result"
        }
        if (homeUiState.isOperator) {
            result = " " + homeUiState.operator + result
        }
        if (homeUiState.isFirstDigit) {
            result = homeUiState.firstDigit + result
        }
        if (homeUiState.isFirstMinus) {
            result = " -$result"
        }

        homeUiState = homeUiState.copy(
            displayTextResult = result
        )
    }

    fun onEqualsClicked() {
        if (validateEquals()) {

            val firstDigit = if (homeUiState.isFirstMinus) homeUiState.firstDigit.toDouble()
                .unaryMinus() else homeUiState.firstDigit.toDouble()
            val secondDigit = if (homeUiState.isSecondMinus) homeUiState.secondDigit.toDouble()
                .unaryMinus() else homeUiState.secondDigit.toDouble()
            val result = when (homeUiState.operator) {
                "+" -> firstDigit + secondDigit
                "-" -> firstDigit - secondDigit
                "*" -> firstDigit * secondDigit
                "/" -> firstDigit / secondDigit
                else -> 0
            }

            homeUiState = homeUiState.copy(
                displayTextHistory = homeUiState.displayTextResult,
                firstDigit = result.toString(),
            )
        } else {
            homeUiState = homeUiState.copy(
                displayError = "Invalid operation"
            )
        }
    }

    private fun validateEquals(): Boolean {
        return homeUiState.isFirstDigit && homeUiState.isSecondDigit && homeUiState.isOperator
    }

    fun onClearClicked() {
        homeUiState = homeUiState.copy(
            displayTextResult = "",
            displayTextHistory = "",
            firstDigit = "",
            secondDigit = "",
            operator = "",
        )
    }

}