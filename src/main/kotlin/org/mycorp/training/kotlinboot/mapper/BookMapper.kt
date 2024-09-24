package org.mycorp.training.kotlinboot.mapper

import org.mapstruct.Mapper
import org.mycorp.training.kotlinboot.dto.BookDTO
import org.mycorp.training.kotlinboot.model.Book
import org.mycorp.training.kotlinboot.rest.model.BookResponse

@Mapper(componentModel = "spring")
interface BookMapper {
    fun toDTO(book: Book): BookDTO

    fun toApiResponse(bookDTO: BookDTO): BookResponse
}
