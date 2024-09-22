package org.mycorp.training.kotlinboot.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
import org.mycorp.training.kotlinboot.dto.BookDTO
import org.mycorp.training.kotlinboot.model.Book

class BookMapperTest {
    val mapper = Mappers.getMapper(BookMapper::class.java)

    @Test
    fun `Map from entity to DTO`() {
        val book = Book("title 1", "isbn 1", 1)

        val actual = mapper.toDTO(book)

        assertThat(actual).isEqualTo(BookDTO("title 1", "isbn 1"))
    }
}
