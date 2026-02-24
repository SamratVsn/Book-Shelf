package com.example.bookshelf.network

import com.example.bookshelf.model.BookShelf
import com.example.bookshelf.model.BookshelfApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val APIKEY = "AIzaSyD65sBWsSfGUe3HKNmjSb0oFOJaaF9WHg8"

interface BookApiService {
    @GET("volumes")
    suspend fun getBookshelfResponse(
        @Query("q") query: String = "android",
        @Query("key") apiKey: String = APIKEY
    ): BookshelfApiResponse

    @GET("volumes/{id}")
    suspend fun getBookDetail(
        @Path("id") id: String,
        @Query("key") apiKey: String = APIKEY
    ): BookShelf
}