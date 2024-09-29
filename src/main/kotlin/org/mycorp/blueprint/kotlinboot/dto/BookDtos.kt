package org.mycorp.blueprint.kotlinboot.dto

import java.math.BigDecimal
import java.time.LocalDate

data class BookDTO(
    val id: Long,
    val title: String,
    val isbn: String,
    val price: BigDecimal,
    val publicationDate: LocalDate
)

data class BookUpdateDTO(
    val title: String,
    val price: BigDecimal,
    val publicationDate: LocalDate
)

data class BookCreationDTO(
    val title: String,
    val isbn: String,
    val price: BigDecimal,
    val publicationDate: LocalDate
)
