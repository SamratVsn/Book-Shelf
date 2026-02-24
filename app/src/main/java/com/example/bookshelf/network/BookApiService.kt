package com.example.bookshelf.network

import com.example.bookshelf.model.BookShelf
import com.example.bookshelf.model.BookshelfApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApiService {
    @GET("volumes?q=android&key=AIzaSyD65sBWsSfGUe3HKNmjSb0oFOJaaF9WHg8")
    suspend fun getBookshelfResponse() : BookshelfApiResponse

    @GET("volumes/{id}&key=AIzaSyD65sBWsSfGUe3HKNmjSb0oFOJaaF9WHg8")
    suspend fun getBookDetail(@Path("id") id: String): BookShelf
}