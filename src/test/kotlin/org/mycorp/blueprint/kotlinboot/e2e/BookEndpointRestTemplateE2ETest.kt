package org.mycorp.blueprint.kotlinboot.e2e

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mycorp.blueprint.kotlinboot.model.Book
import org.mycorp.blueprint.kotlinboot.repository.BookRepository
import org.mycorp.blueprint.kotlinboot.rest.model.BookCreationRequest
import org.mycorp.blueprint.kotlinboot.rest.model.BookDetailsResponse
import org.mycorp.blueprint.kotlinboot.rest.model.BookListResponse
import org.mycorp.blueprint.kotlinboot.rest.model.BookUpdateRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import java.math.BigDecimal
import java.time.LocalDate

private data class Error(
    val timestamp: String,
    val status: Int,
    val error: String,
    val trace: String?,
    val message: String?,
    val path: String
)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class BookEndpointRestTemplateE2ETest() {
    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var bookRepository: BookRepository

    @Test
    fun `Call to API api-books returns Books`() {
        val actual = restTemplate.getForEntity<Array<BookListResponse>>("/api/books")

        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(actual.body).contains(
            BookListResponse(
                1,
                "Java Programming",
                "1234567890",
            )
        )
    }

    @Test
    fun `Call to API api-books by ID returns one Book`() {
        val actual = restTemplate.getForEntity<BookDetailsResponse>("/api/books/1")

        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(actual.body).isEqualTo(
            BookDetailsResponse(
                1,
                "Java Programming",
                "1234567890",
                BigDecimal("100.00"),
                LocalDate.of(2020, 1, 1)
            )
        )
    }

    @Test
    fun `Call to API api-books by ID unknown returns 404`() {
        val actual = restTemplate.getForEntity<Error>("/api/books/1000")

        assertThat(actual).extracting("statusCode").isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(actual.body).extracting("error").isEqualTo("Not Found")
    }

    @Test
    fun `Call to API api-books by isbn returns one Book`() {
        val actual = restTemplate.getForEntity<BookDetailsResponse>("/api/books/isbn/1234567890")

        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(actual.body).isEqualTo(
            BookDetailsResponse(
                1,
                "Java Programming",
                "1234567890",
                BigDecimal("100.00"),
                LocalDate.of(2020, 1, 1)
            )
        )
    }

    @Test
    fun `Call to API api-books by isbn unknown returns 404`() {
        val actual = restTemplate.getForEntity<Error>("/api/books/isbn/876543")

        assertThat(actual.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(actual.body).extracting("error").isEqualTo("Not Found")
    }

    @Test
    fun `Call to API api-books to add returns 201`() {
        val actual = restTemplate.postForEntity<BookDetailsResponse>(
            "/api/books",
            BookCreationRequest(
                "A new title",
                "98765",
                BigDecimal("200.00"),
                LocalDate.of(2024, 1, 1)
            )
        )

        assertThat(actual.statusCode).isEqualTo(HttpStatus.CREATED)

        assertThat(actual.body).isEqualTo(
            BookDetailsResponse(
                951,
                "A new title",
                "98765",
                BigDecimal("200.00"),
                LocalDate.of(2024, 1, 1)
            )
        )

        val actualGet = restTemplate.getForEntity<BookDetailsResponse>("/api/books/951")
        assertThat(actualGet.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(actualGet.body).isEqualTo(
            BookDetailsResponse(
                951,
                "A new title",
                "98765",
                BigDecimal("200.00"),
                LocalDate.of(2024, 1, 1)
            )
        )

        val book = bookRepository.findById(951).get()
        assertThat(book).isNotNull()
            .isEqualTo(Book("A new title", "98765", BigDecimal("200.00"), LocalDate.of(2024, 1, 1), 951))
    }


    @Test
    fun `Call to API api-books to update a book returns 200`() {
        val actual = restTemplate.exchange<BookDetailsResponse>(
            "/api/books/1",
            HttpMethod.PUT,
            HttpEntity(
                BookUpdateRequest(
                    "A new title",
                    BigDecimal("200.00"),
                    LocalDate.of(2024, 1, 1)
                )
            ),
            BookDetailsResponse::class.java
        )

        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)

        assertThat(actual.body).isEqualTo(
            BookDetailsResponse(
                1,
                "A new title",
                "1234567890",
                BigDecimal("200.00"),
                LocalDate.of(2024, 1, 1)
            )
        )

        val actualGet = restTemplate.getForEntity<BookDetailsResponse>("/api/books/1")
        assertThat(actualGet.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(actualGet.body).isEqualTo(
            BookDetailsResponse(
                1,
                "A new title",
                "1234567890",
                BigDecimal("200.00"),
                LocalDate.of(2024, 1, 1)
            )
        )

        val book = bookRepository.findById(1).get()
        assertThat(book).isNotNull()
            .isEqualTo(Book("A new title", "1234567890", BigDecimal("200.00"), LocalDate.of(2024, 1, 1), 1))
    }
}
