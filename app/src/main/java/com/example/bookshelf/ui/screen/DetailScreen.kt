package com.example.bookshelf.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.BookShelfInfo

@Composable
fun BookDetailScreen(
    bookshelfUiState: BookUiState,
    modifier: Modifier = Modifier
) {
    when (bookshelfUiState) {
        is BookUiState.Error -> ErrorScreen(retryAction = { })
        is BookUiState.Loading -> LoadingScreen()
        is BookUiState.Success -> BookDetail(
            book = bookshelfUiState.currentSelectedBook,
            modifier = modifier
        )
    }
}

@Composable
fun BookDetail(book: BookShelfInfo?, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        item {
            BookPhotoCard(bookshelf = book)
            Column(modifier = Modifier.padding(vertical = 20.dp)) {
                Text(
                    text = book?.title ?: "No title",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Subtitle :", fontWeight = FontWeight.Bold)
                Text(text = book?.subtitle ?: "N/A")
                Text(text = "Authors:", fontWeight = FontWeight.Medium)
                book?.authors?.forEach { author ->
                    Text(text = author)
                }
                Text(text = "Publisher :", fontWeight = FontWeight.Bold)
                Text(text = book?.publisher ?: "N/A")
                Text(text = "Publisher date :", fontWeight = FontWeight.Bold)
                Text(text = book?.publishedDate ?: "N/A")
                Text(text = "Description :", fontWeight = FontWeight.Bold)
                Text(text = book?.description ?: "No description available")
            }
        }
    }
}

@Composable
fun BookPhotoCard(
    modifier: Modifier = Modifier,
    bookshelf: BookShelfInfo?,
) {
    Card(modifier = modifier) {
        val imageLinkReplace = bookshelf?.imageLinks?.thumbnail?.replace("http://", "https://")
        AsyncImage(
            model =
                ImageRequest.Builder(context = LocalContext.current)
                .data(imageLinkReplace).crossfade(true).build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}
