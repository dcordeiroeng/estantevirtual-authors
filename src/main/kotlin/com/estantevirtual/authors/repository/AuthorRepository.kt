package com.estantevirtual.authors.repository

import com.estantevirtual.authors.model.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository: JpaRepository<Author, Long> {


}