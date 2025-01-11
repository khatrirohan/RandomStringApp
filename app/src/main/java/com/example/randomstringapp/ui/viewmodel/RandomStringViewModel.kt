package com.example.randomstringapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomstringapp.data.model.RandomString
import com.example.randomstringapp.data.repository.RandomStringRepository
import kotlinx.coroutines.launch

class RandomStringViewModel(private val repository: RandomStringRepository) : ViewModel() {

    private val _randomStrings = MutableLiveData<List<RandomString>>(emptyList())
    val randomStrings: LiveData<List<RandomString>> = _randomStrings

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun generateRandomString(length: Int) {
        if (length <= 0) {
            _errorMessage.value = "Please enter a valid String length"
            return
        }
        if (length > 100) {
            _errorMessage.value = "String length cannot exceed 100"
            return
        }
        viewModelScope.launch {
            val result = repository.fetchRandomString(length)
            result.onSuccess { randomString ->
                _randomStrings.value = _randomStrings.value?.plus(randomString)
            }.onFailure { e ->
                _errorMessage.value = e.message
            }
        }
    }

    fun clearAllStrings() {
        _randomStrings.value = emptyList()
    }

    fun deleteString(index: Int) {
        _randomStrings.value = _randomStrings.value?.filterIndexed { i, _ -> i != index }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
