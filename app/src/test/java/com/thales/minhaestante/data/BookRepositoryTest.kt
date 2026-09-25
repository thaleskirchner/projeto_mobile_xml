package com.thales.minhaestante.data

import com.thales.minhaestante.data.model.Book
import com.thales.minhaestante.data.model.ReadingStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test

class BookRepositoryTest {

    private lateinit var repository: BookRepository

    @Before
    fun setUp() {
        repository = BookRepository(
            listOf(
                book(id = "a", status = ReadingStatus.WANT_TO_READ),
                book(id = "b", status = ReadingStatus.READING),
                book(id = "c", status = ReadingStatus.READING),
            )
        )
    }

    @Test
    fun `getBooks sem filtro retorna todos os livros`() {
        assertEquals(listOf("a", "b", "c"), repository.getBooks().map { it.id })
    }

    @Test
    fun `getBooks com filtro retorna apenas o status pedido`() {
        assertEquals(listOf("b", "c"), repository.getBooks(ReadingStatus.READING).map { it.id })
        assertEquals(emptyList<Book>(), repository.getBooks(ReadingStatus.READ))
    }

    @Test
    fun `getBook retorna null para id inexistente`() {
        assertNull(repository.getBook("nao-existe"))
    }

    @Test
    fun `updateStatus gera uma copia sem alterar a instancia original`() {
        val original = repository.getBook("a")!!

        val updated = repository.updateStatus("a", ReadingStatus.READ)

        assertEquals(ReadingStatus.READ, updated?.status)
        assertEquals(ReadingStatus.WANT_TO_READ, original.status)
        assertNotSame(original, updated)
        assertEquals(updated, repository.getBook("a"))
    }

    @Test
    fun `updateStatus com o mesmo status devolve o livro atual`() {
        val current = repository.getBook("b")

        assertSame(current, repository.updateStatus("b", ReadingStatus.READING))
    }

    @Test
    fun `updateStatus retorna null para id inexistente`() {
        assertNull(repository.updateStatus("nao-existe", ReadingStatus.READ))
    }

    private fun book(id: String, status: ReadingStatus) = Book(
        id = id,
        title = "Livro $id",
        subtitle = null,
        author = "Autor",
        publisher = null,
        publicationYear = null,
        pageCount = null,
        genres = emptyList(),
        synopsis = null,
        rating = null,
        status = status,
    )
}
