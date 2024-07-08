package com.example.flickrsearch.data

import com.example.flickrsearch.data.model.FlickrResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {
    @GET("services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    suspend fun searchPhotos(@Query("tags") tags: String): FlickrResponse

    companion object {
        private const val BASE_URL = "https://api.flickr.com/"

        fun create(): FlickrApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FlickrApiService::class.java)
        }
    }
}