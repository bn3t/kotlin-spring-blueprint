package org.mycorp.blueprint.kotlinboot.service

import org.mycorp.blueprint.kotlinboot.dto.BookCreationDTO
import org.mycorp.blueprint.kotlinboot.dto.BookDTO
import org.mycorp.blueprint.kotlinboot.dto.BookUpdateDTO
import org.mycorp.blueprint.kotlinboot.exception.NotFoundException
import org.mycorp.blueprint.kotlinboot.mapper.BookMapper
import org.mycorp.blueprint.kotlinboot.repository.BookRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookServiceImpl(
    val bookRepository: BookRepository,
    val bookMapper: BookMapper
) : BookService {

    @Transactional(readOnly = true)
    override fun getBooks(): List<BookDTO> = bookRepository.findAll().map(bookMapper::toBookDTO)

    @Transactional(readOnly = true)
    override fun getBook(bookId: Long): BookDTO =
        bookRepository.findByIdOrNull(bookId)?.let { bookMapper.toBookDTO(it) }
            ?: throw NotFoundException("Book with ID $bookId not found")

    @Transactional(readOnly = true)
    override fun getBookByIsbn(isbn: String): BookDTO =
        bookRepository.findByIsbn(isbn)?.let { bookMapper.toBookDTO(it) }
            ?: throw NotFoundException("Book with ISBN $isbn not found")

    @Transactional
    override fun addBook(bookRequest: BookCreationDTO): BookDTO {
        val savedBook = bookRepository.save(bookMapper.toEntity(bookRequest))
        return bookMapper.toBookDTO(savedBook)
    }

    @Transactional
    override fun updateBook(bookId: Long, bookRequest: BookUpdateDTO): BookDTO {
        val existingBook = bookRepository.findByIdOrNull(bookId)
            ?: throw NotFoundException("Book with ID $bookId not found")

        existingBook.title = bookRequest.title
        existingBook.price = bookRequest.price
        existingBook.publicationDate = bookRequest.publicationDate

        return bookMapper.toBookDTO(existingBook)
    }

    @Transactional
    override fun deleteBook(bookId: Long) {
        val existingBook = bookRepository.findByIdOrNull(bookId)
            ?: throw NotFoundException("Book with ID $bookId not found")
        bookRepository.delete(existingBook)
    }
}
