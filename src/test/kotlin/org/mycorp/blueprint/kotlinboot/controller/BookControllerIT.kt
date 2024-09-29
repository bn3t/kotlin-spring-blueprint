package org.mycorp.blueprint.kotlinboot.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.mycorp.blueprint.kotlinboot.dto.BookCreationDTO
import org.mycorp.blueprint.kotlinboot.dto.BookDTO
import org.mycorp.blueprint.kotlinboot.dto.BookUpdateDTO
import org.mycorp.blueprint.kotlinboot.mapper.BookMapper
import org.mycorp.blueprint.kotlinboot.rest.model.BookCreationRequest
import org.mycorp.blueprint.kotlinboot.rest.model.BookDetailsResponse
import org.mycorp.blueprint.kotlinboot.rest.model.BookListResponse
import org.mycorp.blueprint.kotlinboot.rest.model.BookUpdateRequest
import org.mycorp.blueprint.kotlinboot.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.time.LocalDate

@WebMvcTest(BookController::class)
class BookControllerIT {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var bookService: BookService

    @MockkBean
    lateinit var bookMapper: BookMapper

    @Test
    fun `Call to API api-books returns Books`() {
        val bookDTO = BookDTO(
            1,
            "Java Programming", "1234567890",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
        )
        val bookListResponse = BookListResponse(
            1,
            "title 1",
            "isbn 1",
        )
        every { bookService.getBooks() } returns listOf(bookDTO)
        every { bookMapper.toBookListResponse(refEq(bookDTO)) } returns bookListResponse

        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("title 1"))
    }

    @Test
    fun `Call to API api-book by ID returns Books`() {
        val bookDetailsResponse = BookDetailsResponse(
            1,
            "title 1",
            "isbn 1",
            BigDecimal("10.00"),
            LocalDate.of(2020, 1, 1)
        )
        every { bookService.getBook(eq(1L)) } returns BookDTO(
            1,
            "Java Programming", "1234567890",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
        )
        every { bookMapper.toBookDetailsResponse(any()) } returns bookDetailsResponse
        mockMvc.perform(get("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("title").value("title 1"))
    }

    @Test
    fun `Call to API api-book by isbn returns Books`() {
        val bookDetailsResponse = BookDetailsResponse(
            1,
            "title 1",
            "isbn 1",
            BigDecimal("10.00"),
            LocalDate.of(2020, 1, 1)
        )
        val bookDTO = BookDTO(
            1,
            "Java Programming", "1234567890",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
        )
        every { bookService.getBookByIsbn(eq("isbn 1")) } returns bookDTO
        every { bookMapper.toBookDetailsResponse(refEq(bookDTO)) } returns bookDetailsResponse
        mockMvc.perform(get("/api/books/isbn/isbn 1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("title").value("title 1"))
    }

    @Test
    fun `Call to  API api-book to add returns 201`() {
        val newBookDTO = BookDTO(
            1,
            "Java Programming", "1234567890",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
        )
        val bookCreationDTO = BookCreationDTO(
            "title 1",
            "isbn 1",
            BigDecimal("10.00"),
            LocalDate.of(2020, 1, 1)
        )
        val bookDetailsResponse = BookDetailsResponse(
            1,
            "title 1",
            "isbn 1",
            BigDecimal("10.00"),
            LocalDate.of(2020, 1, 1)
        )
        val bookRequest = BookCreationRequest(
            "title 1",
            "isbn 1",
            BigDecimal("10.00"),
            LocalDate.of(2020, 1, 1)
        )

        every { bookMapper.toBookCreationDTO(eq(bookRequest)) } returns bookCreationDTO
        every { bookService.addBook(refEq(bookCreationDTO)) } returns newBookDTO
        every { bookMapper.toBookDetailsResponse(refEq(newBookDTO)) } returns bookDetailsResponse

        val bookRequestJson = """
        {
            "title": "title 1",
            "isbn": "isbn 1",
            "price": 10.00,
            "publicationDate": "2020-01-01"
        }
    """.trimIndent()

        mockMvc.perform(
            post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookRequestJson)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.title").value("title 1"))
            .andExpect(jsonPath("$.isbn").value("isbn 1"))

    }

    @Test
    fun `Call to API api-books to update a book returns 200`() {
        val bookDTO = BookDTO(
            1,
            "Java Programming", "1234567890",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
        )
        val bookDetailsResponse = BookDetailsResponse(
            1,
            "title 1",
            "isbn 1",
            BigDecimal("10.00"),
            LocalDate.of(2020, 1, 1)
        )
        val bookUpdateDTO = BookUpdateDTO(
            "title 1",
            BigDecimal("10.00"),
            LocalDate.of(2020, 1, 1)
        )
        val bookUpdateRequest = BookUpdateRequest().title("title 1")

        every { bookMapper.toBookUpdateDTO(eq(bookUpdateRequest)) } returns bookUpdateDTO
        every { bookService.updateBook(eq(1L), refEq(bookUpdateDTO)) } returns bookDTO
        every { bookMapper.toBookDetailsResponse(refEq(bookDTO)) } returns bookDetailsResponse

        val bookRequestJson = """
        {
            "title": "title 1"
        }
    """.trimIndent()

        mockMvc.perform(
            put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookRequestJson)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("title 1"))
            .andExpect(jsonPath("$.isbn").value("isbn 1"))
    }

    @Test
    fun `Call to API api-books to delete a book returns 204`() {
        every { bookService.deleteBook(eq(1L)) } returns Unit
        mockMvc.perform(delete("/api/books/1"))
            .andExpect(status().isNoContent)

        verify { bookService.deleteBook(eq(1L)) }
    }
}
