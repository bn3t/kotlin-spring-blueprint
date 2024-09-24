package org.mycorp.blueprint.kotlinboot.controller

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mycorp.blueprint.kotlinboot.dto.BookDTO
import org.mycorp.blueprint.kotlinboot.mapper.BookMapper
import org.mycorp.blueprint.kotlinboot.rest.model.BookResponse
import org.mycorp.blueprint.kotlinboot.service.BookService

/**
 *
 */
@ExtendWith(MockKExtension::class)
class BookControllerTest {
    @MockK
    lateinit var bookService: BookService

    @MockK
    lateinit var bookMapper: BookMapper

    @InjectMockKs
    lateinit var bookController: BookController

    @Test
    fun `Return a list of books`() {
        val books = listOf(BookDTO("title1", "isbn1"), BookDTO("title2", "isbn2"))
        val bookResponse1 = BookResponse().title("title1").isbn("isbn1")
        val bookResponse2 = BookResponse().title("title2").isbn("isbn2")

        every { bookService.getBooks() } returns books
        every { bookMapper.toApiResponse(any()) } returnsMany listOf(bookResponse1, bookResponse2)

        val actual = bookController.getBooks()

        assertThat(actual).isEqualTo(listOf(bookResponse1, bookResponse2))
    }

    @Test
    fun `Return 1 book by id`() {
        val book = BookDTO("title1", "isbn1")
        val bookResponse1 = BookResponse().title("title1").isbn("isbn1")

        every { bookService.getBook(eq(1)) } returns book
        every { bookMapper.toApiResponse(refEq(book)) } returns bookResponse1


        val actual = bookController.getBookById(1)

        assertThat(actual).isSameAs(bookResponse1)
    }

    @Test
    fun `Return 1 book by isbn`() {
        val book = BookDTO("title1", "isbn1")
        val bookResponse1 = BookResponse().title("title1").isbn("isbn1")

        every { bookService.getBookByIsbn(eq("isbn1")) } returns book
        every { bookMapper.toApiResponse(refEq(book)) } returns bookResponse1

        val actual = bookController.getBookByIsbn("isbn1")

        assertThat(actual).isSameAs(bookResponse1)
    }
}