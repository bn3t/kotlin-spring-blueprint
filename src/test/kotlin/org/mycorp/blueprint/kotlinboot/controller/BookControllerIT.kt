package org.mycorp.blueprint.kotlinboot.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.mycorp.blueprint.kotlinboot.dto.BookDTO
import org.mycorp.blueprint.kotlinboot.mapper.BookMapper
import org.mycorp.blueprint.kotlinboot.rest.model.BookResponse
import org.mycorp.blueprint.kotlinboot.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

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
        val bookDTO = BookDTO("title 1", "isbn 1")
        val bookResponse = BookResponse().title("title 1").isbn("isbn 1")
        every { bookService.getBooks() } returns listOf(bookDTO)
        every { bookMapper.toApiResponse(refEq(bookDTO)) } returns bookResponse

        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("title 1"))
    }

    @Test
    fun `Call to API api-book by ID returns Books`() {
        val bookResponse = BookResponse().title("title 1").isbn("isbn 1")
        every { bookService.getBook(eq(1L)) } returns BookDTO("title 1", "isbn 1")
        every { bookMapper.toApiResponse(any()) } returns bookResponse
        mockMvc.perform(get("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("title").value("title 1"))
    }

    @Test
    fun `Call to API api-book by isbn returns Books`() {
        val bookResponse = BookResponse().title("title 1").isbn("isbn 1")
        val bookDTO = BookDTO("title 1", "isbn 1")
        every { bookService.getBookByIsbn(eq("isbn 1")) } returns bookDTO
        every { bookMapper.toApiResponse(refEq(bookDTO)) } returns bookResponse
        mockMvc.perform(get("/api/books/isbn/isbn 1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("title").value("title 1"))
    }
}
