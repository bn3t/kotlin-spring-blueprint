package org.mycorp.training.kotlinboot.controller

import org.mycorp.training.kotlinboot.dto.BookDTO
import org.mycorp.training.kotlinboot.service.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 */

@RestController
@RequestMapping("/api/books")
class BookController(
    val bookService: BookService
) {

    @GetMapping("/")
    fun getBooks(): List<BookDTO> {
        return bookService.getBooks()
    }

    @GetMapping("/{bookId}")
    fun getBook(@PathVariable("bookId") bookId: Long): BookDTO {
        return bookService.getBook(bookId)
    }

    @GetMapping("/isbn/{isbn}")
    fun getBookByIsbn(@PathVariable("isbn") isbn: String): BookDTO {
        return bookService.getBookByIsbn(isbn)
    }
}