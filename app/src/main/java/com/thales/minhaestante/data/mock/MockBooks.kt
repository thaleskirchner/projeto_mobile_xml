package com.thales.minhaestante.data.mock

import com.thales.minhaestante.data.model.Book
import com.thales.minhaestante.data.model.ReadingStatus

object MockBooks {

    val books: List<Book> = listOf(
        Book(
            id = "1",
            title = "Dom Casmurro",
            subtitle = null,
            author = "Machado de Assis",
            publisher = null,
            publicationYear = 1899,
            pageCount = 256,
            genres = listOf("Romance", "Clássico"),
            synopsis = "Bento Santiago revisita a própria vida para tentar entender a relação " +
                "com Capitu, em uma narrativa marcada pela dúvida e pela memória.",
            rating = 4.5f,
            status = ReadingStatus.READ,
        ),
        Book(
            id = "2",
            title = "Torto Arado",
            subtitle = null,
            author = "Itamar Vieira Junior",
            publisher = "Todavia",
            publicationYear = 2019,
            pageCount = 264,
            genres = listOf("Romance", "Literatura brasileira"),
            synopsis = "Duas irmãs crescem em uma fazenda no sertão baiano, unidas por um " +
                "acidente de infância e pela luta de sua comunidade pela terra.",
            rating = 5f,
            status = ReadingStatus.READING,
        ),
        Book(
            id = "3",
            title = "O Hobbit",
            subtitle = "ou Lá e de Volta Outra Vez",
            author = "J. R. R. Tolkien",
            publisher = "HarperCollins",
            publicationYear = 1937,
            pageCount = 336,
            genres = listOf("Fantasia", "Aventura"),
            synopsis = "Bilbo Bolseiro deixa o conforto de sua toca para acompanhar um grupo " +
                "de anões em uma jornada até a montanha guardada por um dragão.",
            rating = null,
            status = ReadingStatus.WANT_TO_READ,
        ),
        Book(
            id = "4",
            title = "Código Limpo",
            subtitle = "Habilidades Práticas do Agile Software",
            author = "Robert C. Martin",
            publisher = "Alta Books",
            publicationYear = 2008,
            pageCount = 425,
            genres = listOf("Tecnologia", "Programação"),
            synopsis = "Princípios e exemplos para escrever código legível, fácil de manter " +
                "e de evoluir em equipe.",
            rating = 4f,
            status = ReadingStatus.READING,
        ),
        Book(
            id = "5",
            title = "Vidas Secas",
            subtitle = null,
            author = "Graciliano Ramos",
            publisher = "Record",
            publicationYear = 1938,
            pageCount = 176,
            genres = listOf("Romance", "Clássico"),
            synopsis = "Uma família de retirantes atravessa o sertão em busca de sobrevivência, " +
                "em capítulos curtos e linguagem seca como a paisagem.",
            rating = 4f,
            status = ReadingStatus.READ,
        ),
        Book(
            id = "6",
            title = "Sapiens",
            subtitle = "Uma Breve História da Humanidade",
            author = "Yuval Noah Harari",
            publisher = "L&PM",
            publicationYear = 2011,
            pageCount = 464,
            genres = listOf("História", "Não ficção"),
            synopsis = "Um panorama de como o Homo sapiens passou a dominar o planeta, das " +
                "revoluções cognitiva e agrícola até a era científica.",
            rating = null,
            status = ReadingStatus.WANT_TO_READ,
        ),
        Book(
            id = "7",
            title = "A Hora da Estrela",
            subtitle = null,
            author = "Clarice Lispector",
            publisher = null,
            publicationYear = 1977,
            pageCount = null,
            genres = emptyList(),
            synopsis = null,
            rating = null,
            status = ReadingStatus.WANT_TO_READ,
        ),
        Book(
            id = "8",
            title = "O Programador Pragmático",
            subtitle = "De Aprendiz a Mestre",
            author = "David Thomas e Andrew Hunt",
            publisher = "Bookman",
            publicationYear = 1999,
            pageCount = 352,
            genres = listOf("Tecnologia", "Carreira"),
            synopsis = "Conselhos práticos sobre ofício, responsabilidade e boas práticas " +
                "para quem desenvolve software.",
            rating = null,
            status = ReadingStatus.WANT_TO_READ,
        ),
    )
}
