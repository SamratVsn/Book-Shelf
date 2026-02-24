package com.example.bookshelf.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookshelfApiResponse(
    @SerialName("items") val items: List<BookShelf>
)

@Serializable
data class BookShelf(
    @SerialName("id") val id: String,
    @SerialName("volumeInfo") val bookShelfInfo: BookShelfInfo,
)

@Serializable
data class BookShelfInfo(
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("authors") val authors: List<String>? = null,
    @SerialName("publisher") val publisher: String? = null,
    @SerialName("publishedDate") val publishedDate: String? = null,
    @SerialName("imageLinks") val imageLinks: ImageLinks? = null,
)

@Serializable
data class ImageLinks(
    @SerialName("smallThumbnail") val smallThumbnail: String,
    @SerialName("thumbnail") val thumbnail: String
) {
    val secureSmallThumbnail get() = smallThumbnail.replace("http://", "https://")
    val secureThumbnail get() = thumbnail.replace("http://", "https://")
}