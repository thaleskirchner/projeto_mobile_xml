package com.thales.minhaestante.data.model

import com.thales.minhaestante.data.mock.MockBooks
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ModelTest {

    @Test
    fun `fromName converte nomes validos`() {
        ReadingStatus.entries.forEach { status ->
            assertEquals(status, ReadingStatus.fromName(status.name))
        }
    }

    @Test
    fun `fromName retorna null para valor ausente ou invalido`() {
        assertNull(ReadingStatus.fromName(null))
        assertNull(ReadingStatus.fromName("ABANDONED"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `nota fora do intervalo e rejeitada`() {
        MockBooks.books.first().copy(rating = 7f)
    }

    @Test
    fun `mocks possuem ids unicos`() {
        val ids = MockBooks.books.map { it.id }
        assertEquals(ids.size, ids.toSet().size)
    }
}
