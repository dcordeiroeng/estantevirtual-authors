package com.estantevirtual.authors.repository

import com.estantevirtual.authors.model.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AuthorRepository : JpaRepository<Author, UUID> {

    override fun findById(id: UUID): Optional<Author>
}