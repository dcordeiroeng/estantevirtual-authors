package com.estantevirtual.authors.model

import java.time.LocalDateTime

class ErrorMessages(val errors: Map<String, String>) {
    val date = LocalDateTime.now()
}
