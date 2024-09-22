package org.mycorp.training.kotlinboot.service

import org.mycorp.training.kotlinboot.dto.BookDTO
import org.mycorp.training.kotlinboot.exception.NotFoundException
import org.mycorp.training.kotlinboot.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 *
 */
@Service
class BookServiceImpl(
    val bookRepository: BookRepository
) : BookService {

    @Transactional(readOnly = true)
    override fun getBooks(): List<BookDTO> = bookRepository.findAll().map { book -> BookDTO(book.title, book.isbn) }

    @Transactional(readOnly = true)
    override fun getBook(bookId: Long): BookDTO = bookRepository.findById(bookId).map { BookDTO(it.title, it.isbn) }
        .orElseThrow { NotFoundException("Book $bookId not found") }

    @Transactional(readOnly = true)
    override fun getBookByIsbn(isbn: String): BookDTO =
        bookRepository.findByIsbn(isbn)?.let { BookDTO(it.title, it.isbn) }
            ?: throw NotFoundException("Book with ISBN $isbn not found")
}