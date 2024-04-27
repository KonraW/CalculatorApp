package com.example.calculatorapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculatorapp.ui.AppViewModelProvider

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)//, key = HomeViewModel::class.java.name)
) {
    val homeUiState = viewModel.homeUiState
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Display(viewModel, homeUiState = homeUiState)
        Keypad(viewModel, homeUiState = homeUiState)
    }
}

@Composable
fun Display(
    viewModel: HomeViewModel, homeUiState: HomeUiState, modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .height(200.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {

                Text(
                    text = homeUiState.displayTextHistory,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
//                NegateButton()
                Text(
                    text = homeUiState.firstDigit,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = homeUiState.operator,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = homeUiState.secondDigit,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(end = 16.dp)
                )
                BackspaceButton(viewModel = viewModel)
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
                ) {}
            }
        }
    }
}

@Composable
fun BackspaceButton(
    viewModel: HomeViewModel, modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { viewModel.onBackspaceClicked() }, modifier = Modifier.size(72.dp)
    ) {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.Backspace,
            contentDescription = null,
            Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

//@Composable
//fun NegateButton() {
//    IconButton(
//        onClick = { /*TODO*/ }, modifier = Modifier
//            .padding(start = 8.dp, end = 16.dp)
//            .size(48.dp)
//    ) {
//        Text(
//            text = "+/-",
//            style = MaterialTheme.typography.titleLarge,
//            color = MaterialTheme.colorScheme.secondary
//        )
//    }
//}

@Composable
fun Keypad(
    viewModel: HomeViewModel, homeUiState: HomeUiState, modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Key(value = "+/-",
                color = MaterialTheme.colorScheme.primary,
                onClick = { viewModel.onFirstNegateClicked() })
            Key(value = "+/-",
                color = MaterialTheme.colorScheme.tertiary,
                onClick = { viewModel.onSecondNegateClicked() })
            Key("C",
                color = MaterialTheme.colorScheme.secondary,
                onClick = { viewModel.onClearClicked() })
            OperatorKey("/")
        }
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            DigitKey("7")
            DigitKey("8")
            DigitKey("9")
            OperatorKey("*")
        }
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            DigitKey("4")
            DigitKey("5")
            DigitKey("6")
            OperatorKey("-")
        }
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            DigitKey("1")
            DigitKey("2")
            DigitKey("3")
            OperatorKey("+")
        }
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            DigitKey("00")
            DigitKey("0")
            Key(value = ".", color = MaterialTheme.colorScheme.secondary, onClick = { viewModel.onDotClicked() })
            Key("=",
                color = MaterialTheme.colorScheme.primary,
                onClick = { viewModel.onEqualsClicked() })
        }
    }

}


@Composable
fun DigitKey(
    value: String,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    Key(
        value = value,
        onClick = { viewModel.onDigitClicked(value) },
        color = MaterialTheme.colorScheme.secondaryContainer
    )
}

@Composable
fun OperatorKey(
    value: String,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    Key(
        value = value,
        color = MaterialTheme.colorScheme.secondary,
        onClick = { viewModel.onOperatorClicked(value) })
}

@Composable
fun Key(
    value: String, color: Color, onClick: () -> Unit, modifier: Modifier = Modifier
) {

    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(color),
        modifier = Modifier
            .padding(4.dp)
            .size(72.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,

            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onClick)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}