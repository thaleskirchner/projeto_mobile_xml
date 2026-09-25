package com.thales.minhaestante

import com.thales.minhaestante.data.BookRepository
import com.thales.minhaestante.data.mock.MockBooks

object AppContainer {
    val bookRepository: BookRepository by lazy { BookRepository(MockBooks.books) }
}
