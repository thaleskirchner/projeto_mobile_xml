package com.thales.minhaestante.data

import com.thales.minhaestante.data.model.Book
import com.thales.minhaestante.data.model.ReadingStatus

class BookRepository(initialBooks: List<Book>) {

    private var books: List<Book> = initialBooks.toList()

    fun getBooks(status: ReadingStatus? = null): List<Book> =
        if (status == null) books else books.filter { it.status == status }

    fun getBook(id: String): Book? = books.firstOrNull { it.id == id }

    fun updateStatus(id: String, newStatus: ReadingStatus): Book? {
        val current = getBook(id) ?: return null
        if (current.status == newStatus) return current

        val updated = current.copy(status = newStatus)
        books = books.map { if (it.id == id) updated else it }
        return updated
    }
}
