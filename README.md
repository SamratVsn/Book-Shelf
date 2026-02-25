# Bookshelf

> Loading and displaying images from the internet

An Android app that fetches book data from the Google Books API and displays book covers in a grid. Built as part of **Android Basics with Compose - Unit 5, Pathway 2** to learn networking, image loading, and modern Android architecture.

---

## 📚 About

**Bookshelf** is a learning project demonstrating how to build a complete Android app that connects to the internet, fetches data, and displays images. The app uses the Google Books API to retrieve book information and displays book covers using Coil image loading library.

This project is part of Google's **Android Basics with Compose** course:
- **Unit 5**: Connect to the internet
- **Pathway 2**: Load and display images from the internet

---

## 🎯 Learning Objectives

This project helped me understand:

- **Modern Android Architecture**: Separating UI and Data layers
- **Repository Pattern**: Abstracting data sources
- **Dependency Injection**: Manual DI using AppContainer
- **REST APIs**: Consuming the Google Books API
- **Image Loading**: Using Coil for async image display
- **State Management**: Handling loading, success, and error states

---

## ✨ Features

- **Book Search**: Fetch books from Google Books API
- **Grid Display**: Show book covers in a scrollable grid
- **Image Loading**: Async loading with Coil
- **State Handling**: Loading indicators and error messages
- **Retry Functionality**: Retry button on network errors

---

## 🛠️ Tech Stack

### Core Technologies
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Repository Pattern

### Networking & Data
- **Retrofit**: HTTP client for API calls
- **Kotlinx Serialization**: JSON parsing
- **Google Books API**: Data source
- **Coroutines**: Asynchronous operations

### UI & Libraries
- **Coil**: Image loading library
- **LazyVerticalGrid**: Performant grid display
- **Material 3**: Design system
- **Scaffold**: App structure

### Dependencies
- Retrofit 2
- Kotlinx Serialization Converter
- Coil Compose
- Lifecycle ViewModel Compose

---

## 🏗️ Architecture

### Layers

**UI Layer**
- `BooksScreen` (Composable)
- `BooksViewModel` (State management)

**Data Layer**
- `BooksRepository` (Interface)
- `NetworkBooksRepository` (Implementation)
- `BooksApiService` (Retrofit service)

**Dependency Injection**
- `AppContainer` (Manual DI container)
- `DefaultAppContainer` (Implementation)

---

## 📦 Data Models
```kotlin
@Serializable
data class Book(
    val id: String,
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val imageLinks: ImageLinks?
)

@Serializable
data class ImageLinks(
    val thumbnail: String
)
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio (latest stable version)
- JDK 8 or higher
- Internet connection (for API calls)

### Installation

1. **Clone the repository**:
```bash
   git clone https://github.com/SamratVsn/Book-Shelf.git
```

2. **Open in Android Studio**:
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory

3. **Add Internet Permission** (already included in `AndroidManifest.xml`):
```xml
   <uses-permission android:name="android.permission.INTERNET" />
```

4. **Run the app**:
   - Connect an Android device or start an emulator
   - Ensure internet connectivity
   - Click the "Run" button (▶️)

---

## 🎨 UI States

The app handles three distinct states:

1. **Loading**: Shows `CircularProgressIndicator` while fetching data
2. **Success**: Displays books in `LazyVerticalGrid`
3. **Error**: Shows error message with retry button

---

## 📚 Key Concepts Implemented

### Architecture Patterns
- **Separation of Concerns**: Clear boundaries between UI and Data
- **Repository Pattern**: Single source of truth for data
- **UDF (Unidirectional Data Flow)**: Events up, state down
- **Dependency Injection**: AppContainer manages dependencies

### Networking
- **Retrofit Setup**: Type-safe API interface
- **JSON Parsing**: Kotlinx Serialization with data classes
- **Error Handling**: Try-catch blocks for network failures
- **Coroutines**: Background thread operations

### UI Components
- **LazyVerticalGrid**: Efficient scrollable grid
- **AsyncImage (Coil)**: Async image loading with placeholders
- **Card**: Material Design book items
- **Scaffold**: Standard app structure with TopAppBar

---

## 🔄 How It Works

1. **App Initialization** → `AppContainer` provides dependencies
2. **ViewModel Created** → `BooksViewModel` gets repository instance
3. **API Call** → Repository fetches data via Retrofit
4. **JSON Parsing** → Response converted to Kotlin objects
5. **State Update** → ViewModel updates UI state
6. **Image Loading** → Coil loads book cover images
7. **Display** → LazyVerticalGrid shows books

---

## 💡 What I Learned

- Setting up Retrofit with Kotlinx Serialization
- Implementing the Repository pattern correctly
- Manual dependency injection with AppContainer
- Loading images efficiently with Coil
- Handling different UI states properly
- Working with the Google Books API
- Modern Android app architecture principles

---

## 🤝 Acknowledgments

Built following Google's **Android Basics with Compose** course:
- [Unit 5 - Pathway 2: Load and display images from the internet](https://developer.android.com/courses/pathways/android-basics-compose-unit-5-pathway-2)

---

## 📄 License

This project is open source and available for educational purposes.

---

> "Fetching data, loading images, building apps."
