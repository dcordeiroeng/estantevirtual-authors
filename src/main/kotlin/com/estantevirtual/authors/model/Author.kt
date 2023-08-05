package com.estantevirtual.authors.model

import lombok.Getter
import lombok.Setter
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
@Getter
@Setter
class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var firstName: String? = null
    var lastName: String? = null

    @ManyToMany(mappedBy = "author")
    var books: Set<Book>? = null
}