package com.example.calculatorapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column {
        Display()
        Keypad()
    }
}

@Composable
fun Display() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .height(200.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column {
            Text(
                text = "0+7-2+3",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "0",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun Keypad() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DigitKey("7")
            DigitKey("8")
            DigitKey("9")
            OperatorKey("/")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            DigitKey("4")
            DigitKey("5")
            DigitKey("6")
            OperatorKey("x")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            DigitKey("1")
            DigitKey("2")
            DigitKey("3")
            OperatorKey("-")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            OperatorKey("C")
            DigitKey("0")
            Key("=", color = MaterialTheme.colorScheme.primary)
            OperatorKey("+")
        }
    }

}


@Composable
fun DigitKey(
    value: String,
    color: Color = MaterialTheme.colorScheme.secondaryContainer,
    modifier: Modifier = Modifier
) {
    Key(value = value, color = color)
}

@Composable
fun OperatorKey(
    value: String,
    color: Color = MaterialTheme.colorScheme.secondary,
    modifier: Modifier = Modifier
) {
    Key(value = value, color = color)
}

@Composable
fun Key(
    value: String,
    color: Color = MaterialTheme.colorScheme.secondary,
    modifier: Modifier = Modifier
) {

    Card(
        shape = CircleShape,
        colors = CardDefaults.cardColors(color),
        modifier = Modifier
//                .fillMaxSize()
            .padding(4.dp)
            .size(72.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,

            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}