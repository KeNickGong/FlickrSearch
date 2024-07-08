package com.example.flickrsearch.data

import com.example.flickrsearch.data.model.FlickrImage

class FlickrRepository(private val apiService: FlickrApiService) {

    suspend fun searchPhotos(tags: String): List<FlickrImage> {
        return apiService.searchPhotos(tags).items
    }
}