package org.mycorp.training.kotlinboot.controller

import org.mycorp.training.kotlinboot.mapper.BookMapper
import org.mycorp.training.kotlinboot.rest.BookApi
import org.mycorp.training.kotlinboot.rest.model.BookResponse
import org.mycorp.training.kotlinboot.service.BookService
import org.springframework.web.bind.annotation.RestController

/**
 *
 */

@RestController
class BookController(
    val bookService: BookService, val bookMapper: BookMapper
) : BookApi {

    override fun getBookById(bookId: Long?): BookResponse {
        return bookMapper.toApiResponse(bookService.getBook(bookId!!))
    }

    override fun getBookByIsbn(isbn: String?): BookResponse {
        return bookMapper.toApiResponse(bookService.getBookByIsbn(isbn!!))
    }

    override fun getBooks(): MutableList<BookResponse> {
        return bookService.getBooks().map { bookMapper.toApiResponse(it) }.toMutableList()
    }
}