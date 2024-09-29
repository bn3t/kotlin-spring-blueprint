package org.mycorp.blueprint.kotlinboot.mapper

import org.mapstruct.Mapper
import org.mycorp.blueprint.kotlinboot.dto.BookCreationDTO
import org.mycorp.blueprint.kotlinboot.dto.BookDTO
import org.mycorp.blueprint.kotlinboot.dto.BookUpdateDTO
import org.mycorp.blueprint.kotlinboot.model.Book
import org.mycorp.blueprint.kotlinboot.rest.model.BookCreationRequest
import org.mycorp.blueprint.kotlinboot.rest.model.BookDetailsResponse
import org.mycorp.blueprint.kotlinboot.rest.model.BookListResponse
import org.mycorp.blueprint.kotlinboot.rest.model.BookUpdateRequest

@Mapper(componentModel = "spring")
interface BookMapper {
    fun toBookDTO(book: Book): BookDTO
    fun toBookUpdateDTO(bookRequest: BookUpdateRequest): BookUpdateDTO
    fun toBookCreationDTO(bookRequest: BookCreationRequest): BookCreationDTO

    fun toBookDetailsResponse(bookDTO: BookDTO): BookDetailsResponse
    fun toBookListResponse(bookDTO: BookDTO): BookListResponse

    fun toEntity(bookDTO: BookDTO): Book
    fun toEntity(bookCreationDTO: BookCreationDTO): Book
}
