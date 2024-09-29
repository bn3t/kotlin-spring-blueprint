package org.mycorp.blueprint.kotlinboot.controller

import jakarta.validation.constraints.NotNull
import org.mycorp.blueprint.kotlinboot.mapper.BookMapper
import org.mycorp.blueprint.kotlinboot.rest.BookApi
import org.mycorp.blueprint.kotlinboot.rest.model.BookCreationRequest
import org.mycorp.blueprint.kotlinboot.rest.model.BookDetailsResponse
import org.mycorp.blueprint.kotlinboot.rest.model.BookListResponse
import org.mycorp.blueprint.kotlinboot.rest.model.BookUpdateRequest
import org.mycorp.blueprint.kotlinboot.service.BookService
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
    val bookService: BookService, val bookMapper: BookMapper
) : BookApi {

    override fun getBookById(@NotNull bookId: Long): BookDetailsResponse {
        return bookMapper.toBookDetailsResponse(bookService.getBook(bookId))
    }

    override fun getBookByIsbn(isbn: String?): BookDetailsResponse {
        return bookMapper.toBookDetailsResponse(bookService.getBookByIsbn(isbn!!))
    }

    override fun getBooks(): MutableList<BookListResponse> {
        return bookService.getBooks().map { bookMapper.toBookListResponse(it) }.toMutableList()
    }

    override fun addBook(@NotNull bookRequest: BookCreationRequest): BookDetailsResponse {
        val newBook = bookService.addBook(bookMapper.toBookCreationDTO(bookRequest))
        return bookMapper.toBookDetailsResponse(newBook)
    }

    override fun updateBook(@NotNull bookId: Long, @NotNull bookRequest: BookUpdateRequest): BookDetailsResponse {
        val updatedBook = bookService.updateBook(bookId, bookMapper.toBookUpdateDTO(bookRequest))
        return bookMapper.toBookDetailsResponse(updatedBook)
    }

    override fun deleteBook(@NotNull bookId: Long) {
        bookService.deleteBook(bookId)
    }
}