package org.mycorp.training.kotlinboot

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mycorp.training.kotlinboot.dto.BookDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

data class Error(
    val timestamp: String,
    val status: Int,
    val error: String,
    val trace: String?,
    val message: String?,
    val path: String
)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KotlinBootApplicationIT {
    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `Call to API api-books returns Books`() {
        val actual = restTemplate.getForEntity<Array<BookDTO>>("/api/books/")

        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(actual.body).contains(BookDTO("Java Programming", "1234567890"))
    }

    @Test
    fun `Call to API api-books by ID returns one Book`() {
        val actual = restTemplate.getForEntity<BookDTO>("/api/books/1")

        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(actual.body).isEqualTo(BookDTO("Java Programming", "1234567890"))
    }

    @Test
    fun `Call to API api-books by ID unknown returns 404`() {
        val actual = restTemplate.getForEntity<Error>("/api/books/1000")

        assertThat(actual).extracting("statusCode").isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(actual.body).extracting("error").isEqualTo("Not Found")
    }

    @Test
    fun `Call to API api-books by isbn returns one Book`() {
        val actual = restTemplate.getForEntity<BookDTO>("/api/books/isbn/1234567890")

        assertThat(actual.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(actual.body).isEqualTo(BookDTO("Java Programming", "1234567890"))
    }

    @Test
    fun `Call to API api-books by isbn unknown returns 404`() {
        val actual = restTemplate.getForEntity<Error>("/api/books/isbn/876543")

        assertThat(actual.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(actual.body).extracting("error").isEqualTo("Not Found")

    }
}
