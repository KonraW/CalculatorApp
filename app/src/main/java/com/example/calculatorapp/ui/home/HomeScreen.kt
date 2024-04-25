package com.example.calculatorapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
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
            .background(Color.Gray),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = "0",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(16.dp)
        )
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
            Key("7")
            Key("8")
            Key("9")
            Key("/")a
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            Key("4")
            Key("5")
            Key("6")
            Key("x")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            Key("1")
            Key("2")
            Key("3")
            Key("-")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            Key("C")
            Key("0")
            Key("=")
            Key("+")
        }
    }

}

@Composable
fun Key(
    value: String,
    color: Color = MaterialTheme.colorScheme.secondary,
    modifier: Modifier = Modifier
) {

    Card(
        shape = CircleShape,
        modifier = modifier
//                .fillMaxSize()
            .padding(4.dp)
            .size(64.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
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