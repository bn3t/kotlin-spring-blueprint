package org.mycorp.blueprint.kotlinboot.repository

import org.assertj.core.api.Assertions.assertThat
import org.hibernate.Session
import org.hibernate.jdbc.Work
import org.junit.jupiter.api.Test
import org.mycorp.blueprint.kotlinboot.model.Book
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.jdbc.Sql
import java.math.BigDecimal
import java.sql.Connection
import java.time.LocalDate

@DataJpaTest(properties = arrayOf("spring.jpa.hibernate.ddl-auto=create-drop"))
@Sql(scripts = arrayOf("data-books-h2.sql"))
class BookRepositoryIT {
    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Test
    fun testDatabaseIsH2() {
        (entityManager.getEntityManager().getDelegate() as Session).doWork(Work { connection: Connection? ->
            assertThat(connection!!.isValid(1)).isTrue()
            assertThat(connection.getMetaData().getDatabaseProductName()).isEqualTo("H2")
        })
    }

    @Test
    fun `Find 1 book by isbn`() {
        val book = bookRepository.findByIsbn("test-isbn-2")

        assertThat(book).isNotNull()
        assertThat(book).isEqualTo(
            Book(
                "Test book 2", "test-isbn-2",
                BigDecimal("10.00"), LocalDate.of(2020, 1, 1), 2
            )
        )
    }
}