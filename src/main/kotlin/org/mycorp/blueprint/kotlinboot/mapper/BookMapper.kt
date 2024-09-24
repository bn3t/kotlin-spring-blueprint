package org.mycorp.blueprint.kotlinboot.mapper

import org.mapstruct.Mapper
import org.mycorp.blueprint.kotlinboot.dto.BookDTO
import org.mycorp.blueprint.kotlinboot.model.Book
import org.mycorp.blueprint.kotlinboot.rest.model.BookResponse

@Mapper(componentModel = "spring")
interface BookMapper {
    fun toDTO(book: Book): BookDTO

    fun toApiResponse(bookDTO: BookDTO): BookResponse
}
