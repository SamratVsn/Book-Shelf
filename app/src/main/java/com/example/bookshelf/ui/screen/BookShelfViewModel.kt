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
import coil.network.HttpException
import com.example.bookshelf.BookShelfApplication
import com.example.bookshelf.data.BookShelfRepository
import com.example.bookshelf.model.BookShelf
import com.example.bookshelf.model.BookShelfInfo
import com.example.bookshelf.model.BookshelfApiResponse
import kotlinx.coroutines.launch
import okio.IOException

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

    fun setCurrentBook(id: String){
        viewModelScope.launch {
            bookUiState = BookUiState.Loading

            bookUiState = try {
                val result = bookShelfRepository.getBookDetail(id)
                Log.d("result of book detail", "$result")
                BookUiState.Success()
                    .copy(
                        bookShelf = bookShelfRepository.getBookshelfResponse().items,
                        currentSelectedBook = result.bookShelfInfo
                    )
            } catch(e: IOException){
                BookUiState.Error
            } catch(e: HttpException){
                BookUiState.Error
            }
        }
    }

    fun getBookShelf(){
        viewModelScope.launch {
            bookUiState = BookUiState.Loading
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