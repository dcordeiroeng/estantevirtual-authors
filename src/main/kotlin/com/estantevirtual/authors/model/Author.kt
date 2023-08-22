package com.estantevirtual.authors.model

import lombok.Data
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
@Data
data class Author(
    @Id
    var id: UUID? = null,
    @field:NotNull(message = "First name is required")
    var firstName: String?,
    @field:NotNull(message = "Last name is required")
    var lastName: String?
)