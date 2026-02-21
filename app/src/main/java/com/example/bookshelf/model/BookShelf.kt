package com.example.bookshelf.model

import kotlinx.serialization.SerialName

data class BookshelfApiResponse(
    @SerialName("items") val items: List<BookShelf>
)

data class BookShelf(
    @SerialName("id") val id: String,
    @SerialName("volumeInfo") val bookShelfInfo: BookShelfInfo,
)

data class BookShelfInfo(
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String,
    @SerialName("description") val description: String,
    @SerialName("authors") val authors: List<String>,
    @SerialName("publisher") val publisher: String,
    @SerialName("publishedDate") val publishedDate: String,
    @SerialName("imageLinks") val imageLinks: ImageLinks,
)

data class ImageLinks(
    @SerialName("smallThumbnail") val smallThumbnail: String,
    @SerialName("thumbnail") val thumbnail: String
)