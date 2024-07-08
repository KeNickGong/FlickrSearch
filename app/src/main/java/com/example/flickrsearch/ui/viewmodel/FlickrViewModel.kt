package com.example.flickrsearch.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.flickrsearch.data.FlickrApiService
import com.example.flickrsearch.data.FlickrRepository
import com.example.flickrsearch.data.model.FlickrImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlickrViewModel(private val repository: FlickrRepository) : ViewModel() {

    private val _images = MutableStateFlow<List<FlickrImage>>(emptyList())
    val images: StateFlow<List<FlickrImage>> get() = _images

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    fun searchImages(query: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.searchPhotos(query)
                _images.value = response
            } catch (e: Exception) {
                _images.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }
}