package org.mycorp.training.kotlinboot.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.mycorp.training.kotlinboot.dto.BookDTO
import org.mycorp.training.kotlinboot.service.BookService
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

    @Test
    fun `Call to API api-books returns Books`() {
        every { bookService.getBooks() } returns listOf(BookDTO("title 1", "isbn 1"))
        mockMvc.perform(get("/api/books/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("title 1"))
    }

    @Test
    fun `Call to API api-book by ID returns Books`() {
        every { bookService.getBook(eq(1L)) } returns BookDTO("title 1", "isbn 1")
        mockMvc.perform(get("/api/books/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("title").value("title 1"))
    }

    @Test
    fun `Call to API api-book by isbn returns Books`() {
        every { bookService.getBookByIsbn(eq("isbn 1")) } returns BookDTO("title 1", "isbn 1")
        mockMvc.perform(get("/api/books/isbn/isbn 1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("title").value("title 1"))
    }
}
