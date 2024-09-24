package org.mycorp.blueprint.kotlinboot.repository

import org.mycorp.blueprint.kotlinboot.model.Book
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 */
interface BookRepository: JpaRepository<Book, Long>  {
    fun findByIsbn(isbn: String): Book?
}