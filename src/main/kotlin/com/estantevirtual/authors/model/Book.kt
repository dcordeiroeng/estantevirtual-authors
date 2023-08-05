package com.estantevirtual.authors.model

import lombok.Getter
import lombok.Setter
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

@Entity
@Getter
@Setter
class Book {

    @Id
    var id: String? = null
    var title: String? = null
    var pages: Int? = null

    @ManyToMany
    @JoinTable(name = "author_book", joinColumns = [JoinColumn(name = "book_id")],
        inverseJoinColumns = [JoinColumn(name = "author_id")])
    var author: Set<Author>? = null
}
