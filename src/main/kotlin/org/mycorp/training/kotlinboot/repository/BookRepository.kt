package org.mycorp.training.kotlinboot.repository

import org.mycorp.training.kotlinboot.model.Book
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 */
interface BookRepository: JpaRepository<Book, Long>  {
    fun findByIsbn(isbn: String): Book?
}