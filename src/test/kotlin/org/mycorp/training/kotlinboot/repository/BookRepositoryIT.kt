package org.mycorp.training.kotlinboot.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mycorp.training.kotlinboot.model.Book
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.jdbc.Sql

@DataJpaTest(properties = arrayOf("spring.jpa.hibernate.ddl-auto=create-drop"))
@Sql(scripts = arrayOf("data-books-h2.sql"))
class BookRepositoryIT {
    @Autowired
    lateinit var bookRepository: BookRepository

    @Test
    fun `Find 1 book by isbn`() {
        val book = bookRepository.findByIsbn("test-isbn-2")

        assertThat(book).isNotNull()
        assertThat(book).isEqualTo(Book("Test book 2", "test-isbn-2", 2))
    }
}