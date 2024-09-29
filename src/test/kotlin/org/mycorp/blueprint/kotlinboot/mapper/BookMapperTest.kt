package org.mycorp.blueprint.kotlinboot.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
import org.mycorp.blueprint.kotlinboot.dto.BookCreationDTO
import org.mycorp.blueprint.kotlinboot.dto.BookDTO
import org.mycorp.blueprint.kotlinboot.dto.BookUpdateDTO
import org.mycorp.blueprint.kotlinboot.model.Book
import org.mycorp.blueprint.kotlinboot.rest.model.BookCreationRequest
import org.mycorp.blueprint.kotlinboot.rest.model.BookDetailsResponse
import org.mycorp.blueprint.kotlinboot.rest.model.BookListResponse
import org.mycorp.blueprint.kotlinboot.rest.model.BookUpdateRequest
import java.math.BigDecimal
import java.time.LocalDate

class BookMapperTest {
    val mapper = Mappers.getMapper(BookMapper::class.java)

    @Test
    fun `Map from entity to DTO - toBookDTO`() {
        val book = Book(
            "title 1",
            "isbn 1",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1), 1
        )

        val actual = mapper.toBookDTO(book)

        assertThat(actual).isEqualTo(
            BookDTO(
                1,
                "title 1",
                "isbn 1",
                BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
            )
        )
    }

    @Test
    fun `Map from BookUpdateRequest to BookUpdateDTO - toBookUpdateDTO`() {
        val bookUpdateRequest = BookUpdateRequest().title("title 1")
            .price(BigDecimal("10.00"))
            .publicationDate(LocalDate.of(2020, 1, 1))

        val actual = mapper.toBookUpdateDTO(bookUpdateRequest)

        assertThat(actual).isEqualTo(
            BookUpdateDTO(
                "title 1",
                BigDecimal("10.00"),
                LocalDate.of(2020, 1, 1)
            )
        )
    }

    @Test
    fun `Map from BookCreationRequest to BookUpdateDTO - toBookCreationDTO`() {
        val bookCreationRequest = BookCreationRequest().title("title 1")
            .isbn("isbn 1")
            .price(BigDecimal("10.00"))
            .publicationDate(LocalDate.of(2020, 1, 1))

        val actual = mapper.toBookCreationDTO(bookCreationRequest)

        assertThat(actual).isEqualTo(
            BookCreationDTO(
                "title 1",
                "isbn 1",
                BigDecimal("10.00"),
                LocalDate.of(2020, 1, 1)
            )
        )
    }

    @Test
    fun `Map from Book DTO to BookDetailsResponse - toBookDetailsResponse`() {
        val bookDTO = BookDTO(
            1,
            "title 1",
            "isbn 1",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
        )

        val actual = mapper.toBookDetailsResponse(bookDTO)

        assertThat(actual).isEqualTo(
            BookDetailsResponse().id(1)
                .title("title 1")
                .isbn("isbn 1")
                .price(BigDecimal("10.00"))
                .publicationDate(LocalDate.of(2020, 1, 1))
        )
    }

    @Test
    fun `Map from Book DTO to BookDetailsResponse - toBookListResponse`() {
        val bookDTO = BookDTO(
            1,
            "title 1",
            "isbn 1",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
        )

        val actual = mapper.toBookListResponse(bookDTO)

        assertThat(actual).isEqualTo(
            BookListResponse().id(1)
                .title("title 1")
                .isbn("isbn 1")
        )
    }

    @Test
    fun `Map from Book DTO to Book - toEntity`() {
        val bookDTO = BookDTO(
            1,
            "title 1",
            "isbn 1",
            BigDecimal("10.00"), LocalDate.of(2020, 1, 1)
        )

        val actual = mapper.toEntity(bookDTO)

        assertThat(actual).isEqualTo(
            Book(
                "title 1",
                "isbn 1",
                BigDecimal("10.00"), LocalDate.of(2020, 1, 1), 1
            )
        )
    }

    @Test
    fun `Map from BookCreationDTO to Book - toEntity`() {
        val bookCreationDTO = BookCreationDTO(
            "title 1",
            "isbn 1",
            BigDecimal("10.00"),
            LocalDate.of(2020, 1, 1)
        )

        val actual = mapper.toEntity(bookCreationDTO)

        assertThat(actual).isEqualTo(
            Book(
                "title 1",
                "isbn 1",
                BigDecimal("10.00"), LocalDate.of(2020, 1, 1), 0
            )
        )
    }

}
