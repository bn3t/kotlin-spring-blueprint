package org.mycorp.training.kotlinboot.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mycorp.training.kotlinboot.dto.BookDTO
import org.mycorp.training.kotlinboot.service.BookService
import org.mockito.Mockito.`when` as whenever

/**
 *
 */
@ExtendWith(MockitoExtension::class)
class BookControllerTest {
    @Mock
    lateinit var bookService: BookService

    @InjectMocks
    lateinit var bookController: BookController

    @Test
    fun getBooks() {
        val books = listOf(BookDTO("title1", "isbn1"), BookDTO("title2", "isbn2"))
        whenever(bookService.getBooks()).thenReturn(books)

        val actual = bookController.getBooks()

        assertThat(actual).isSameAs(books)
    }

    @Test
    fun getBook() {
        val book = BookDTO("title1", "isbn1")
        whenever(bookService.getBook(1)).thenReturn(book);

        val actual = bookController.getBook(1)

        assertThat(actual).isSameAs(book)
    }
}