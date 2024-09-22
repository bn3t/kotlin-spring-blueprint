package org.mycorp.training.kotlinboot.mapper

import org.mapstruct.Mapper
import org.mycorp.training.kotlinboot.dto.BookDTO
import org.mycorp.training.kotlinboot.model.Book

@Mapper(componentModel = "spring")
interface BookMapper {
    fun toDTO(book: Book): BookDTO
}
