package org.mycorp.blueprint.kotlinboot

import org.junit.jupiter.api.Test
import org.mycorp.blueprint.kotlinboot.rest.model.BookDetailsResponse
import org.mycorp.blueprint.kotlinboot.rest.model.BookListResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.reactive.server.WebTestClient
import java.math.BigDecimal
import java.time.LocalDate


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class BookEndpointWebTestClientE2ETest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `Call to API api-books returns Books`() {
        webTestClient.get().uri("/api/books")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(BookListResponse::class.java)
            .hasSize(2)
            .contains(
                BookListResponse(
                ).id(1)
                    .title("Java Programming")
                    .isbn("1234567890"),
            ).contains(
                BookListResponse(
                ).id(2)
                    .title("Kotlin Programming")
                    .isbn("1234567891")
            )
    }

    @Test
    fun `Call to API api-books by ID returns one Book`() {
        webTestClient.get().uri("/api/books/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody(BookDetailsResponse::class.java)
            .isEqualTo(
                BookDetailsResponse().id(1)
                    .title("Java Programming")
                    .isbn("1234567890").price(BigDecimal("100.00")).publicationDate(
                        LocalDate.of(2020, 1, 1)
                    )
            )
    }

    @Test
    fun `Call to API api-books by ID unknown returns 404`() {
        webTestClient.get().uri("/api/books/1000")
            .exchange()
            .expectStatus().isNotFound()
            .expectBody()
            .jsonPath("$.error").isEqualTo("Not Found")
    }

    @Test
    fun `Call to API api-books by isbn returns one Book`() {
        webTestClient.get().uri("/api/books/isbn/1234567890")
            .exchange()
            .expectStatus().isOk()
            .expectBody(BookDetailsResponse::class.java)
            .isEqualTo(
                BookDetailsResponse().id(1)
                    .title("Java Programming")
                    .isbn("1234567890").price(BigDecimal("100.00")).publicationDate(
                        LocalDate.of(2020, 1, 1)
                    )
            )
    }

    @Test
    fun `Call to API api-books by isbn unknown returns 404`() {
        webTestClient.get().uri("/api/books/isbn/876543")
            .exchange()
            .expectStatus().isNotFound()
            .expectBody()
            .jsonPath("$.error").isEqualTo("Not Found")
    }
}