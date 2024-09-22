package org.mycorp.training.kotlinboot.service

import org.mycorp.training.kotlinboot.dto.BookDTO
import org.mycorp.training.kotlinboot.exception.NotFoundException
import org.mycorp.training.kotlinboot.mapper.BookMapper
import org.mycorp.training.kotlinboot.repository.BookRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 *
 */
@Service
class BookServiceImpl(
    val bookRepository: BookRepository,
    val bookMapper: BookMapper
) : BookService {

    @Transactional(readOnly = true)
    override fun getBooks(): List<BookDTO> = bookRepository.findAll().map(bookMapper::toDTO)

    @Transactional(readOnly = true)
    override fun getBook(bookId: Long): BookDTO = bookRepository.findByIdOrNull(bookId)?.let { bookMapper.toDTO(it) }
        ?: throw NotFoundException("Book with ID $bookId not found")

    @Transactional(readOnly = true)
    override fun getBookByIsbn(isbn: String): BookDTO =
        bookRepository.findByIsbn(isbn)?.let { bookMapper.toDTO(it) }
            ?: throw NotFoundException("Book with ISBN $isbn not found")
}