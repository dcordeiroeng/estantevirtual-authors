package com.estantevirtual.authors.controller

import com.estantevirtual.authors.model.Author
import com.estantevirtual.authors.repository.AuthorRepository
import com.estantevirtual.authors.service.AuthorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthorController(
    private val authorService: AuthorService
) {

    @GetMapping("/authors")
    fun getAuthors(): ResponseEntity<Set<Author>> {
        val authors = authorService.getAllAuthors()
        return ResponseEntity(authors, HttpStatus.OK)
    }

    @GetMapping("/authors/{id}")
    fun getAuthorById(@PathVariable id: Long): ResponseEntity<Author> {
        val author = authorService.getAuthorById(id)
        return ResponseEntity(author, HttpStatus.OK)
    }

    @PostMapping("/authors")
    fun saveAuthor(@RequestBody author: Author): ResponseEntity<Author> {
        val response = authorService.save(author)
        return ResponseEntity(response, HttpStatus.CREATED)
    }
}