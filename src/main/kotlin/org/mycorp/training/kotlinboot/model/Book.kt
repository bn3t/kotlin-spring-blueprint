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
) : BaseEntity(id)