package org.mycorp.blueprint.kotlinboot.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mycorp.blueprint.kotlinboot.dto.BookDTO
import org.mycorp.blueprint.kotlinboot.exception.NotFoundException
import org.mycorp.blueprint.kotlinboot.mapper.BookMapper
import org.mycorp.blueprint.kotlinboot.model.Book
import org.mycorp.blueprint.kotlinboot.repository.BookRepository
import org.springframework.data.repository.findByIdOrNull

/**
 *
 */
@ExtendWith(MockKExtension::class)
class BookServiceImplTest {
    @MockK
    lateinit var bookRepository: BookRepository

    @MockK
    lateinit var bookMapper: BookMapper

    @InjectMockKs
    lateinit var bookService: BookServiceImpl

    @Test
    fun `Return a list of books - no exception`() {
        val book1 = Book(
            "title 1",
            "isbn 1",
            1,
        )
        val book2 = Book(
            "title 2",
            "isbn 2",
            2,
        )
        val books = listOf(
            book1, book2
        )

        every { bookRepository.findAll() } returns books
        every { bookMapper.toDTO(refEq(book1)) } returns BookDTO("title 1", "isbn 1")
        every { bookMapper.toDTO(refEq(book2)) } returns BookDTO("title 2", "isbn 2")

        val actual = bookService.getBooks()

        assertThat(actual).isEqualTo(
            listOf(BookDTO("title 1", "isbn 1"), BookDTO("title 2", "isbn 2"))
        )
    }

    @Test
    fun `Return 1 book by id - no exception`() {
        val book = Book(
            "title 1",
            "isbn 1",
            1,
        )

        every { bookRepository.findByIdOrNull(eq(1L)) } returns book
        every { bookMapper.toDTO(refEq(book)) } returns BookDTO("title 1", "isbn 1")

        val actual = bookService.getBook(1)

        assertThat(actual).isEqualTo(BookDTO("title 1", "isbn 1"))
    }

    @Test
    fun `Return 1 book by id - NotFound exception`() {
        every { bookRepository.findByIdOrNull(eq(1L)) } returns null

        assertThatExceptionOfType(NotFoundException::class.java).isThrownBy {
            bookService.getBook(1)
        }.withMessage("Book with ID 1 not found")
    }

    @Test
    fun `Return 1 by isbn - no exception`() {
        val book = Book(
            "title 1",
            "isbn 1",
            1,
        )

        every { bookRepository.findByIsbn(eq("isbn 1")) } returns book
        every { bookMapper.toDTO(refEq(book)) } returns BookDTO("title 1", "isbn 1")

        val actual = bookService.getBookByIsbn("isbn 1")

        assertThat(actual).isEqualTo(BookDTO("title 1", "isbn 1"))
    }

    @Test
    fun `Return 1 by isbn - NotFound exception`() {
        every { bookRepository.findByIsbn(eq("isbn 1")) } returns null

        assertThatExceptionOfType(NotFoundException::class.java).isThrownBy {
            bookService.getBookByIsbn("isbn 1")
        }.withMessage("Book with ISBN isbn 1 not found")
    }
}