package org.mycorp.training.kotlinboot.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mycorp.training.kotlinboot.dto.BookDTO
import org.mycorp.training.kotlinboot.model.Book
import org.mycorp.training.kotlinboot.repository.BookRepository
import org.mockito.Mockito.`when` as whenever

/**
 *
 */
@ExtendWith(MockitoExtension::class)
class BookServiceImplTest {
    @Mock
    lateinit var bookRepository: BookRepository

    @InjectMocks
    lateinit var bookService: BookServiceImpl

    @Test
    fun getBooks() {
        val books = listOf(
            Book(
                "title 1",
                "isbn 1",
                1,
            ), Book(
                "title 2",
                "isbn 2",
                2,
            )
        )

        whenever(bookRepository.findAll()).thenReturn(books)

        val actual = bookService.getBooks()

        assertThat(actual).isEqualTo(
            listOf(BookDTO("title 1", "isbn 1"), BookDTO("title 2", "isbn 2"))
        )
    }

    @Test
    fun getBook() {
        val book = Book(
            "title 1",
            "isbn 1",
            1,
        )

        whenever(bookRepository.findById(1)).thenReturn(java.util.Optional.of(book))

        val actual = bookService.getBook(1)

        assertThat(actual).isEqualTo(BookDTO("title 1", "isbn 1"))
    }

    @Test
    fun getBookByIsbn() {
        val book = Book(
            "title 1",
            "isbn 1",
            1,
        )

        whenever(bookRepository.findByIsbn("isbn 1")).thenReturn(book)

        val actual = bookService.getBookByIsbn("isbn 1")

        assertThat(actual).isEqualTo(BookDTO("title 1", "isbn 1"))
    }
}