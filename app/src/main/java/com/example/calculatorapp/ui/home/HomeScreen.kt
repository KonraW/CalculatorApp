package com.example.calculatorapp.ui.home

import androidx.compose.foundation.background
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

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Display()
        Keypad()
    }
}

@Composable
fun Display() {
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                Text(
                    text = "0+7-2+3",
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

                Text(
                    text = "0",
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(16.dp)
                )
                BackspaceButton()
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
                ) {
                }
            }
        }
    }
}

@Composable
fun BackspaceButton() {
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier.padding(start=8.dp, end=16.dp).size(48.dp)
    ) {

        Icon(
            imageVector = Icons.AutoMirrored.Filled.Backspace,
            contentDescription = null,
            Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.secondary
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
    modifier: Modifier = Modifier
) {
    Key(value = value, color = MaterialTheme.colorScheme.secondaryContainer)
}

@Composable
fun OperatorKey(
    value: String,
    modifier: Modifier = Modifier
) {
    Key(value = value, color = MaterialTheme.colorScheme.secondary)
}

@Composable
fun Key(
    value: String,
    color: Color,
    modifier: Modifier = Modifier
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

            modifier = Modifier.fillMaxSize()
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