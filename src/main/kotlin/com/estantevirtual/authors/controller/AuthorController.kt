package com.estantevirtual.authors.controller

import com.estantevirtual.authors.exception.ResourceNotFoundException
import com.estantevirtual.authors.model.Author
import com.estantevirtual.authors.model.Authors
import com.estantevirtual.authors.model.Options
import com.estantevirtual.authors.service.AuthorService
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
class AuthorController(
    private val authorService: AuthorService,
    private val logger: Logger
) {

    @GetMapping("/authors/{id}")
    fun getAuthorById(@PathVariable id: UUID): ResponseEntity<Author> {
        val author = authorService.getAuthor(id)?.orElse(null)
        author ?: run {
            logger.error("Not found ID: $id")
            throw ResourceNotFoundException()
        }
        return ResponseEntity(author, HttpStatus.OK)
    }

    @GetMapping("/authors")
    fun getAuthors(
        @RequestParam(value = "page", defaultValue = "0", required = false) page: Int,
        @RequestParam(value = "per_page", defaultValue = "10", required = false) perPage: Int,
        @RequestParam(value = "order_by", defaultValue = "id", required = false) orderBy: String?,
        @RequestParam(value = "sort", defaultValue = "ASC", required = false) sort: String?
    ): ResponseEntity<Authors> {
        val options = Options(page, perPage, orderBy, sort)
        val authors = authorService.getAuthors(options)
        return ResponseEntity(authors, HttpStatus.OK)
    }

    @PostMapping("/authors")
    fun saveAuthor(@RequestBody @Valid author: Author?): ResponseEntity<Author> {
        return ResponseEntity(authorService.save(author), HttpStatus.CREATED)
    }

    @PutMapping("/authors/{id}")
    fun updateAuthor(
        @PathVariable id: UUID,
        @RequestBody @Valid author: Author
    ): ResponseEntity<*> {
        return if (authorService.update(id, author)) {
            author.id = id
            ResponseEntity(author, HttpStatus.OK)
        } else {
            author.id = id
            ResponseEntity(authorService.save(author), HttpStatus.CREATED)
        }
    }

    @DeleteMapping("/authors/{id}")
    fun deleteAuthor(@PathVariable id: UUID): ResponseEntity<*> {
        return if (authorService.delete(id)) {
            ResponseEntity<Any>(HttpStatus.NO_CONTENT)
        } else {
            logger.error("Not found ID: $id")
            throw ResourceNotFoundException()
        }
    }
}