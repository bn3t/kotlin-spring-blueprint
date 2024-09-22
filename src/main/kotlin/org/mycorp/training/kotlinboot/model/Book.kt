package org.mycorp.training.kotlinboot.model

import jakarta.persistence.Entity
import org.hibernate.annotations.NaturalId

/**
 *
 */
@Entity
class Book(
    var title: String,
    @NaturalId
    var isbn: String,
    id: Long? = null,
) : BaseEntity(id) {

    override fun toString(): String {
        return "Book(title='$title', isbn='$isbn', id=$id)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as Book

        if (isbn != other.isbn) return false

        return true
    }

    override fun hashCode(): Int {
        return isbn.hashCode()
    }
}