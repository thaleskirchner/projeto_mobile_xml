package com.thales.minhaestante.data.model

data class Book(
    val id: String,
    val title: String,
    val subtitle: String?,
    val author: String,
    val publisher: String?,
    val publicationYear: Int?,
    val pageCount: Int?,
    val genres: List<String>,
    val synopsis: String?,
    val rating: Float?,
    val status: ReadingStatus,
) {
    init {
        require(id.isNotBlank()) { "O id do livro não pode ser vazio" }
        require(title.isNotBlank()) { "O título do livro não pode ser vazio" }
        require(rating == null || rating in 0f..MAX_RATING) { "A nota deve estar entre 0 e $MAX_RATING" }
        require(pageCount == null || pageCount > 0) { "O número de páginas deve ser positivo" }
    }

    companion object {
        const val MAX_RATING = 5f
    }
}
