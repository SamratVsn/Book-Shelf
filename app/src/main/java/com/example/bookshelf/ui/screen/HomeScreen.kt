package com.example.bookshelf.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.BookShelf
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshelf.ui.theme.BookShelfTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    bookUiState: BookUiState,
    retryAction: () -> Unit,
    onBookClicked: (id: String) -> Unit = {},
){
    when(bookUiState){
        is BookUiState.Error -> ErrorScreen(retryAction)
        is BookUiState.Loading -> LoadingScreen()
        is BookUiState.Success -> BookGridScreen(
            bookshelf = bookUiState.bookShelf,
            onBookClicked = onBookClicked,
            modifier = modifier
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.loading),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = null,
            modifier = Modifier.size(72.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.loading_failed),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = retryAction) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun BookGridScreen(
    modifier: Modifier = Modifier,
    bookshelf: List<BookShelf>,
    onBookClicked: (id: String) -> Unit,
){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp)
    ) {
        items(items = bookshelf, key = {books -> books.id}) { books ->
            BookPhotoCard(
                bookshelf = books,
                onBookClicked = onBookClicked,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }
    }
}

@Composable
fun BookPhotoCard(
    modifier: Modifier = Modifier,
    bookshelf: BookShelf,
    onBookClicked: (id: String) -> Unit,
) {
    Card(onClick = { onBookClicked(bookshelf.id) }, modifier = modifier) {
        val imageLinkReplace = bookshelf.bookShelfInfo.imageLinks.thumbnail.replace("http", "https")
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    BookShelfTheme {
//        LoadingScreen()
        ErrorScreen(retryAction = { })
    }
}