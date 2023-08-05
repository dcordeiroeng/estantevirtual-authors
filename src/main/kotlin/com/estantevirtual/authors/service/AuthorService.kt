package com.estantevirtual.authors.service

import com.estantevirtual.authors.model.Author
import com.estantevirtual.authors.repository.AuthorRepository
import org.springframework.stereotype.Service

@Service
class AuthorService(
    private val authorRepository: AuthorRepository
) {
    fun getAuthorById(id: Long): Author {
        return authorRepository.findById(id).get()
    }

    fun getAllAuthors(): Set<Author> {
        return authorRepository.findAll().toSet()
    }

    fun save(author: Author): Author {
        return authorRepository.save(author)
    }
}