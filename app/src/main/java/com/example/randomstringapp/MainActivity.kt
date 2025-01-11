package com.example.randomstringapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import com.example.randomstringapp.data.repository.RandomStringRepository
import com.example.randomstringapp.ui.theme.RandomStringAppTheme
import com.example.randomstringapp.ui.view.RandomStringScreen
import com.example.randomstringapp.ui.viewmodel.RandomStringViewModel
import com.example.randomstringapp.ui.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private val repository = RandomStringRepository(this)
    private val viewmodel by viewModels<RandomStringViewModel> { ViewModelFactory(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomStringAppTheme {
                Scaffold(topBar = {
                    TopAppBar(
                        title = { Text("Random String App") }
                    )
                }) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        RandomStringScreen(viewmodel)
                    }
                }
            }
        }
    }
}
