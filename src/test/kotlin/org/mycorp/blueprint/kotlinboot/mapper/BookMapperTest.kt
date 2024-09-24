package org.mycorp.blueprint.kotlinboot.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
import org.mycorp.blueprint.kotlinboot.dto.BookDTO
import org.mycorp.blueprint.kotlinboot.model.Book
import org.mycorp.blueprint.kotlinboot.rest.model.BookResponse

class BookMapperTest {
    val mapper = Mappers.getMapper(BookMapper::class.java)

    @Test
    fun `Map from entity to DTO`() {
        val book = Book("title 1", "isbn 1", 1)

        val actual = mapper.toDTO(book)

        assertThat(actual).isEqualTo(BookDTO("title 1", "isbn 1"))
    }

    @Test
    fun `Map from Book DTO to BookResponse`() {
        val bookDTO = BookDTO("title 1", "isbn 1")

        val actual = mapper.toApiResponse(bookDTO)

        assertThat(actual).isEqualTo(BookResponse().title("title 1").isbn("isbn 1"))
    }
}
