package com.example.bookshelf.data

import com.example.bookshelf.model.BookShelf
import com.example.bookshelf.model.BookshelfApiResponse
import com.example.bookshelf.network.BookApiService

interface BookShelfRepository {
    suspend fun getBookshelfResponse(): BookshelfApiResponse
    suspend fun getBookDetail(id: String): BookShelf
}

class NetworkBookshelfRepository(private val bookApiService: BookApiService) :BookShelfRepository {
    override suspend fun getBookshelfResponse(): BookshelfApiResponse =
        bookApiService.getBookshelfResponse()

    override suspend fun getBookDetail(id: String): BookShelf =
        bookApiService.getBookDetail(id)
}
