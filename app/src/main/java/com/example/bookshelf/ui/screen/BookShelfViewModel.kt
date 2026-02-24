package com.example.bookshelf.ui.screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookShelfApplication
import com.example.bookshelf.data.BookShelfRepository
import com.example.bookshelf.model.BookShelf
import com.example.bookshelf.model.BookShelfInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

interface BookUiState{
    data class Success(
        val bookShelf: List<BookShelf> = emptyList(),
        val currentSelectedBook: BookShelfInfo? = bookShelf.firstOrNull()?.bookShelfInfo
    ) : BookUiState
    object Error : BookUiState
    object Loading : BookUiState
}

class BookShelfViewModel(private val bookShelfRepository: BookShelfRepository) : ViewModel(){
    var bookUiState: BookUiState by mutableStateOf(BookUiState.Loading)
            private set

    init{
        getBookShelf()
    }

    private var lastSelectedId: String = ""

    fun setCurrentBook(id: String) {
        lastSelectedId = id
        val currentBooks = (bookUiState as? BookUiState.Success)?.bookShelf ?: return
        viewModelScope.launch {
            bookUiState = BookUiState.Loading
            delay(1000L)
            bookUiState = try {
                val result = bookShelfRepository.getBookDetail(id)
                BookUiState.Success(
                    bookShelf = currentBooks,
                    currentSelectedBook = result.bookShelfInfo
                )
            } catch (e: IOException) {
                BookUiState.Error
            } catch (e: HttpException) {
                BookUiState.Error
            }
        }
    }

    fun getBookShelf(){
        viewModelScope.launch {
            bookUiState = BookUiState.Loading
            delay(1000L)
            bookUiState = try {
                val listResult = bookShelfRepository.getBookshelfResponse().items
                Log.d("BookshelfDebug", "✅ Data retrieved: ${listResult.size} items")
                Log.d("BookShelfDebug", listResult.firstOrNull()?.bookShelfInfo?.imageLinks!!.smallThumbnail)
                BookUiState.Success(
                    bookShelf = listResult,
                    currentSelectedBook = listResult.firstOrNull()!!.bookShelfInfo
                )
            } catch(e: IOException){
                Log.e("bookshelf debug", "${e.message}")
                BookUiState.Error
            } catch (e: HttpException) {
                Log.e("bookshelf debug", "${e.message}")
                BookUiState.Error
            }
        }
    }

    fun retryLastBook() {
        if (lastSelectedId.isNotEmpty()) setCurrentBook(lastSelectedId)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookShelfApplication).container
                val bookShelfRepository = application.bookshelfRepository
                BookShelfViewModel(bookShelfRepository = bookShelfRepository)
            }
        }
    }
}