package com.example.randomstringapp.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.randomstringapp.ui.viewmodel.RandomStringViewModel

@Composable
fun RandomStringScreen(viewModel: RandomStringViewModel) {
    val randomStrings by viewModel.randomStrings.observeAsState(emptyList())
    val errorMessage by viewModel.errorMessage.observeAsState()

    var inputLength by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = inputLength,
            onValueChange = {
                inputLength = it
                viewModel.clearError()
            },
            label = { Text("Enter String Length") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(onClick = {
                inputLength.toIntOrNull()?.let { viewModel.generateRandomString(it) }
            }) {
                Text("Generate String")
            }
            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = { viewModel.clearAllStrings() }) {
                Text("Clear All")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        LazyColumn {
            itemsIndexed(randomStrings) { index, randomString ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("String: ${randomString.value}")
                        Text("Length: ${randomString.length}")
                        Text("Created: ${randomString.created}")

                        Row {
                            Button(onClick = { viewModel.deleteString(index) }) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


    }
}
