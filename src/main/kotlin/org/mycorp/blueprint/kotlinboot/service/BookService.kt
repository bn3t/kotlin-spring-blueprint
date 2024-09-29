package org.mycorp.blueprint.kotlinboot.service

import org.mycorp.blueprint.kotlinboot.dto.BookCreationDTO
import org.mycorp.blueprint.kotlinboot.dto.BookDTO
import org.mycorp.blueprint.kotlinboot.dto.BookUpdateDTO

/**
 * Book service interface.
 * Provides methods to interact with books.
 */
interface BookService {
    /**
     * Get all books.
     * @return List of books.
     */
    fun getBooks() : List<BookDTO>

    /**
     * Get a book by its ID.
     * @param bookId The ID of the book.
     * @return The book.
     */
    fun getBook(bookId: Long) : BookDTO

    /**
     * Get a book by its ISBN.
     * @param isbn The ISBN of the book.
     * @return The book.
     */
    fun getBookByIsbn(isbn: String): BookDTO

    fun addBook(bookRequest: BookCreationDTO): BookDTO

    fun updateBook(bookId: Long, bookRequest: BookUpdateDTO): BookDTO

    fun deleteBook(bookId: Long)
}