package com.example.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CardValidationApp()
        }
    }
}
fun isValidCardNumber(cardNumber: String): Boolean {
    val digits = cardNumber.map { it.toString().toInt() }.toIntArray()

    for (i in digits.indices) {
        if (i % 2 == 0) {
            digits[i] = digits[i] * 2
            if (digits[i] > 9) {
                digits[i] = digits[i] - 9
            }
        }
    }
    val sum = digits.sum()
    return sum % 10 == 0
}

@Composable
fun CardValidationApp() {
    var cardNumber by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(false) }
    var isButtonClicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        BasicTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp)
                .border(1.dp, Color.Blue, shape = RoundedCornerShape(8.dp))
                .padding(50.dp)
        )

        val enabled = cardNumber.length == 16

        Button(
            onClick = {
                isValid = isValidCardNumber(cardNumber)
                isButtonClicked = true
            },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = if (enabled) Icons.Default.Check else Icons.Default.Close,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Check")
        }

        if (isButtonClicked) {
            val message = if (isValid) "Valid card number." else "Invalid card number."
            Text(text = message, modifier = Modifier.padding(top = 16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardValidationApp() {
    CardValidationApp()
}

