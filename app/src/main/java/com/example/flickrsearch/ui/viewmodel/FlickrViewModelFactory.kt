package com.example.flickrsearch.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flickrsearch.data.FlickrRepository


class FlickrViewModelFactory(private val repository: FlickrRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlickrViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlickrViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}