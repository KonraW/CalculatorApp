package com.example.calculatorapp.ui.home

import androidx.lifecycle.ViewModel

data class HomeUiState(
    val displayText: String = "",
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
        homeUiState = if (homeUiState.isOperator) {
            homeUiState.copy(
                displayText = homeUiState.displayText + operator
            )
        } else {
            onEqualsClicked()
            homeUiState.copy(
                displayText = homeUiState.displayText + operator,
                isOperator = true
            )
        }
    }

    fun onEqualsClicked() {
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
    }

    fun onClearClicked() {
        homeUiState = homeUiState.copy(
            displayText = ""
        )
    }

}