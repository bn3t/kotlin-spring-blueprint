package org.mycorp.training.kotlinboot.controller

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mycorp.training.kotlinboot.dto.BookDTO
import org.mycorp.training.kotlinboot.service.BookService

/**
 *
 */
@ExtendWith(MockKExtension::class)
class BookControllerTest {
    @MockK
    lateinit var bookService: BookService

    @InjectMockKs
    lateinit var bookController: BookController

    @Test
    fun `Return a list of books`() {
        val books = listOf(BookDTO("title1", "isbn1"), BookDTO("title2", "isbn2"))
        every { bookService.getBooks() } returns books

        val actual = bookController.getBooks()

        assertThat(actual).isSameAs(books)
    }

    @Test
    fun `Return 1 book by id`() {
        val book = BookDTO("title1", "isbn1")
        every { bookService.getBook(eq(1)) } returns book

        val actual = bookController.getBook(1)

        assertThat(actual).isSameAs(book)
    }

    @Test
    fun `Return 1 book by isbn`() {
        val book = BookDTO("title1", "isbn1")
        every { bookService.getBookByIsbn(eq("isbn1")) } returns book

        val actual = bookController.getBookByIsbn("isbn1")

        assertThat(actual).isSameAs(book)
    }
}