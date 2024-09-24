package org.mycorp.training.kotlinboot

import org.junit.jupiter.api.Test
import org.mycorp.training.kotlinboot.dto.BookDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KotlinBootWebTestClientIT {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `Call to API api-books returns Books`() {
        webTestClient.get().uri("/api/books")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(BookDTO::class.java)
            .hasSize(2)
            .contains(BookDTO("Java Programming", "1234567890"))
    }

    @Test
    fun `Call to API api-books by ID returns one Book`() {
        webTestClient.get().uri("/api/books/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody(BookDTO::class.java)
            .isEqualTo(BookDTO("Java Programming", "1234567890"))
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
            .expectBody(BookDTO::class.java)
            .isEqualTo(BookDTO("Java Programming", "1234567890"))
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