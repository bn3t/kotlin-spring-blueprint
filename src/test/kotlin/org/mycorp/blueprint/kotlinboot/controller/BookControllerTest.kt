package org.mycorp.blueprint.kotlinboot.controller

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mycorp.blueprint.kotlinboot.dto.BookCreationDTO
import org.mycorp.blueprint.kotlinboot.dto.BookDTO
import org.mycorp.blueprint.kotlinboot.dto.BookUpdateDTO
import org.mycorp.blueprint.kotlinboot.mapper.BookMapper
import org.mycorp.blueprint.kotlinboot.rest.model.BookCreationRequest
import org.mycorp.blueprint.kotlinboot.rest.model.BookDetailsResponse
import org.mycorp.blueprint.kotlinboot.rest.model.BookListResponse
import org.mycorp.blueprint.kotlinboot.rest.model.BookUpdateRequest
import org.mycorp.blueprint.kotlinboot.service.BookService
import java.math.BigDecimal
import java.time.LocalDate

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
        val books = listOf(
            BookDTO(
                1,
                "title 1",
                "isbn 1",
                BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
            ),
            BookDTO(
                2,
                "title 2",
                "isbn 2",
                BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
            )
        )
        val bookResponse1 =
            BookListResponse(
                1,
                "title 1",
                "isbn 1",
            )
        val bookResponse2 =
            BookListResponse(
                2,
                "title 2",
                "isbn 2",
            )

        every { bookService.getBooks() } returns books
        every { bookMapper.toBookListResponse(any()) } returnsMany listOf(bookResponse1, bookResponse2)

        val actual = bookController.getBooks()

        assertThat(actual).isEqualTo(listOf(bookResponse1, bookResponse2))
    }

    @Test
    fun `Return 1 book by id`() {
        val bookDTO = BookDTO(
            1,
            "title 1",
            "isbn 1",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
        )
        val bookResponse1 =
            BookDetailsResponse().id(1).title("title1").isbn("isbn1")
                .price(BigDecimal("10.00"))
                .publicationDate(LocalDate.of(2020, 1, 1))

        every { bookService.getBook(eq(1)) } returns bookDTO
        every { bookMapper.toBookDetailsResponse(refEq(bookDTO)) } returns bookResponse1


        val actual = bookController.getBookById(1)

        assertThat(actual).isSameAs(bookResponse1)
    }

    @Test
    fun `Return 1 book by isbn`() {
        val bookDTO = BookDTO(
            1,
            "title 1",
            "isbn 1",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
        )
        val bookResponse1 =
            BookDetailsResponse().id(1).title("title1").isbn("isbn1").publicationDate(LocalDate.of(2020, 1, 1))

        every { bookService.getBookByIsbn(eq("isbn1")) } returns bookDTO
        every { bookMapper.toBookDetailsResponse(refEq(bookDTO)) } returns bookResponse1

        val actual = bookController.getBookByIsbn("isbn1")

        assertThat(actual).isSameAs(bookResponse1)
    }

    @Test
    fun `Add a book call a service to add`() {
        val bookCreationRequest = BookCreationRequest(
            "title 1",
            "isbn 1",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
        )
        val bookDTO = BookDTO(
            1,
            "title 1",
            "isbn 1",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
        )
        val bookCreationDTO = BookCreationDTO(
            "title 1",
            "isbn 1",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
        )
        val bookResponse1 =
            BookDetailsResponse(
                1,
                "title 1",
                "isbn 1",
                BigDecimal("10.00"),
                LocalDate.of(2020, 1, 1)
            )

        every { bookMapper.toBookCreationDTO(refEq(bookCreationRequest)) } returns bookCreationDTO
        every { bookService.addBook(refEq(bookCreationDTO)) } returns bookDTO
        every { bookMapper.toBookDetailsResponse(refEq(bookDTO)) } returns bookResponse1

        val actual = bookController.addBook(bookCreationRequest)

        assertThat(actual).isSameAs(bookResponse1)
    }

    @Test
    fun `Update a book call a service to update`() {
        val bookUpdateRequest = BookUpdateRequest().title("title 1")
            .price(BigDecimal("10.00"))
            .publicationDate(LocalDate.of(2020, 1, 1))
        val bookDTO = BookDTO(
            1,
            "title 1",
            "isbn 1",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
        )
        val bookUpdateDTO = BookUpdateDTO(
            "title 1",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
        )
        val bookResponse1 =
            BookDetailsResponse(
                1,
                "title 1",
                "isbn 1",
                BigDecimal("10.00"),
                LocalDate.of(2020, 1, 1)
            )

        every { bookMapper.toBookUpdateDTO(refEq(bookUpdateRequest)) } returns bookUpdateDTO
        every { bookService.updateBook(eq(1L), refEq(bookUpdateDTO)) } returns bookDTO
        every { bookMapper.toBookDetailsResponse(refEq(bookDTO)) } returns bookResponse1

        val actual = bookController.updateBook(1L, bookUpdateRequest)

        assertThat(actual).isSameAs(bookResponse1)
    }

    @Test
    fun `Delete a book call a service to delete`() {
        every { bookService.deleteBook(eq(1L)) } returns Unit

        bookController.deleteBook(1L)

        verify { bookService.deleteBook(eq(1L)) }
    }
}