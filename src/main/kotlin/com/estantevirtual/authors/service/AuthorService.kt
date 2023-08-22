package com.estantevirtual.authors.service

import com.estantevirtual.authors.exception.ResourceAlreadyExistsException
import com.estantevirtual.authors.model.Author
import com.estantevirtual.authors.model.Authors
import com.estantevirtual.authors.model.Options
import com.estantevirtual.authors.repository.AuthorRepository
import com.estantevirtual.authors.validator.OptionsValidator
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Service
class AuthorService(
    private val authorRepository: AuthorRepository,
    private val optionsValidator: OptionsValidator
) {
    fun getAuthor(id: UUID): Optional<Author>? {
        return authorRepository.findById(id)
    }

    fun getAuthors(options: Options): Authors {
        optionsValidator.validateParams(options)
        val authors = authorRepository.findAll(
            PageRequest.of(
                options.page,
                options.pageSize,
                Sort.Direction.valueOf(options.sort!!),
                options.orderBy
            )
        )

        val nextPage = if (authors.hasNext()) {
            buildPageUrl(options.page + 1, UriComponentsBuilder.fromPath("/v1/books"), options)
        } else null

        val prevPage = if (authors.hasPrevious() && authors.hasContent()) {
            buildPageUrl(options.page - 1, UriComponentsBuilder.fromPath("/v1/books"), options)
        } else null

        return Authors(
            authors,
            authors.totalElements,
            options.page,
            options.pageSize,
            nextPage,
            prevPage
        )
    }

    fun save(author: Author?): Author {
        author ?: throw IllegalArgumentException("Author cannot be null")
        if (author.id == null) author.id = UUID.randomUUID()
        val response = authorRepository.findById(author.id!!)
        if (response.isPresent) {
            throw ResourceAlreadyExistsException("Resource already exists")
        }
        authorRepository.save(author)
        return author
    }

    fun update(id: UUID, author: Author?): Boolean {
        author ?: throw IllegalArgumentException("Author cannot be null")
        val existingAuthor = authorRepository.findById(id)
        if (existingAuthor.isPresent) {
            val authorToUpdate = existingAuthor.get()
            BeanUtils.copyProperties(author, authorToUpdate, "id")
            authorRepository.save(authorToUpdate)
            return true
        }
        return false
    }

    fun delete(id: UUID): Boolean {
        if (authorRepository.findById(id).isPresent) {
            authorRepository.deleteById(id)
            return true
        }
        return false
    }

    private fun buildPageUrl(page: Int, uriBuilder: UriComponentsBuilder, options: Options): String {
        return uriBuilder.replaceQueryParam("page", page)
            .queryParam("per_page", options.pageSize)
            .queryParam("order_by", options.orderBy)
            .queryParam("sort", options.sort)
            .toUriString()
    }
}