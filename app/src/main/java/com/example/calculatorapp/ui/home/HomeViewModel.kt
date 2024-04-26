package com.example.calculatorapp.ui.home

import androidx.lifecycle.ViewModel

data class HomeUiState(
    val displayText: String = "",
    val displayResultText: String = "",
    val displayTextHistory: String = "",
    val displayError: String = "",
    val firstDigit: String = "",
    val secondDigit: String = "",
    val operator: String = "",
    val isFirstDigit: Boolean = false,
    val isSecondDigit: Boolean = false,
    val isOperator: Boolean = false,
    val isMinusOperator: Boolean = false
)

class HomeViewModel() : ViewModel() {
    var homeUiState = HomeUiState()
        private set

    fun onDigitClicked(digit: String) {
        homeUiState = homeUiState.copy(
            displayText = homeUiState.displayText + digit
        )
    }

    fun onOperatorClicked(operator: String) {
        if (homeUiState.isOperator) {
            if (homeUiState.isSecondDigit) {
                onEqualsClicked()
            } else{
                homeUiState = homeUiState.copy(
                    displayText = homeUiState.displayText.dropLast(1) + operator
                )
            }

            onEqualsClicked()
//            homeUiState.copy(
//                displayText = homeUiState.displayText + operator
//            )
        } else {
            homeUiState = homeUiState.copy(
                displayText = homeUiState.displayText + operator,
                isOperator = true
            )
        }
    }

    fun onMinusOperatorClicked() {
        if (homeUiState.isOperator) {
            homeUiState = homeUiState.copy(
                displayText = homeUiState.displayText + "-",
                isMinusOperator = true
            )
        } else {
            homeUiState = homeUiState.copy(
                displayText = homeUiState.displayText + "-",
                isOperator = true
            )
        }
    }



    fun onEqualsClicked() {
        if (validateEquals()) {

            val firstDigit = homeUiState.firstDigit.toDouble()
            val secondDigit = if (homeUiState.isMinusOperator) homeUiState.secondDigit.toDouble()
                .unaryMinus() else homeUiState.secondDigit.toDouble()
            val result = when (homeUiState.operator) {
                "+" -> firstDigit + secondDigit
                "-" -> firstDigit - secondDigit
                "*" -> firstDigit * secondDigit
                "/" -> firstDigit / secondDigit
                else -> 0
            }

            homeUiState = homeUiState.copy(
                displayTextHistory = homeUiState.displayText,
                displayText = result.toString(),
            )
        } else {
            homeUiState = homeUiState.copy(
                displayError = "Invalid operation"
            )
        }
    }

    private fun validateEquals(): Boolean {
        if (homeUiState.isFirstDigit && homeUiState.isSecondDigit && homeUiState.isOperator) {
            return true
        }
        return false
    }

    fun onClearClicked() {
        homeUiState = homeUiState.copy(
            displayText = "",
            displayTextHistory = "",
            firstDigit = "",
            secondDigit = "",
            operator = "",
        )
    }

}