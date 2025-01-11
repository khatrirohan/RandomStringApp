package com.example.randomstringapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.randomstringapp.data.repository.RandomStringRepository

class ViewModelFactory(private val repository: RandomStringRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RandomStringViewModel::class.java)) {
            return RandomStringViewModel(repository) as T
        }
        throw IllegalArgumentException("Unable to create viewmodel instance")
    }

}