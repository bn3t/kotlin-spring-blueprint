package org.mycorp.training.kotlinboot.service

import org.mycorp.training.kotlinboot.dto.BookDTO

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
}