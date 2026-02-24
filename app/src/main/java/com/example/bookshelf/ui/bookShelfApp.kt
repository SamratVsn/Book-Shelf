package com.example.bookshelf.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookshelf.ui.screen.BookDetailScreen
import com.example.bookshelf.ui.screen.BookShelfViewModel
import com.example.bookshelf.ui.screen.HomeScreen
import com.example.bookshelf.ui.screen.StartScreen

enum class BookShelfScreen(val title: String){
    Start(title = "Book Shelf"),
    Home(title = "Browse Books"),
    BookDetail(title = "Book Detail")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookShelfApp(
    viewModel: BookShelfViewModel = viewModel(factory = BookShelfViewModel.Factory),
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = BookShelfScreen.valueOf(
        backStackEntry?.destination?.route ?: BookShelfScreen.Start.name
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { BookTopAppBar(
            scrollBehavior = scrollBehavior,
            currentScreen = currentScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() }
        ) }
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BookShelfScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            composable(route = BookShelfScreen.Start.name){
                StartScreen(
                    onStartClicked = {
                        navController.navigate(BookShelfScreen.Home.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            composable(route = BookShelfScreen.Home.name){
                HomeScreen(
                    viewModel = viewModel,
                    bookUiState = viewModel.bookUiState,
                    onBookClicked = {
                        viewModel.setCurrentBook(id = it)
                        navController.navigate(BookShelfScreen.BookDetail.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp)
                )
            }
            composable(route = BookShelfScreen.BookDetail.name){
                BookDetailScreen(
                    retryAction = { viewModel.retryLastBook() },
                    bookshelfUiState = viewModel.bookUiState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    currentScreen: BookShelfScreen,
    navigateUp: () -> Unit,
    canNavigateBack: Boolean = false,
){
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(text = currentScreen.title)
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        modifier = modifier
    )
}