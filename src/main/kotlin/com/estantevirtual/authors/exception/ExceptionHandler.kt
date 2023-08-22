package com.estantevirtual.authors.exception

import com.estantevirtual.authors.model.ErrorMessage
import com.estantevirtual.authors.model.ErrorMessages
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.util.function.Consumer
import javax.validation.ConstraintViolationException


@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(ex: ResourceNotFoundException): ErrorMessage {
        return ErrorMessage(ex.message!!)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgument(ex: IllegalArgumentException, req: WebRequest?): ErrorMessage {
        return ErrorMessage(ex.message!!)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationError(ex: MethodArgumentNotValidException): ErrorMessages {
        val errors: MutableMap<String, String> = HashMap()
        ex.bindingResult.fieldErrors.forEach(Consumer { e: FieldError -> errors["message"] = e.defaultMessage!! })
        return ErrorMessages(errors)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolation(ex: ConstraintViolationException, req: WebRequest?): ErrorMessages {
        val errors: MutableMap<String, String> = HashMap()
        ex.constraintViolations.forEach { e -> errors["message"] = e.message!! }
        return ErrorMessages(errors)
    }

    @ExceptionHandler(ResourceAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleAlreadyExists(ex: ResourceAlreadyExistsException): ErrorMessage {
        return ErrorMessage(ex.message!!)
    }

    @ExceptionHandler(OrderByException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleOrderByException(ex: OrderByException, req: WebRequest?): ErrorMessage {
        return ErrorMessage(ex.message!!)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(ex: Exception, req: WebRequest?): ErrorMessage {
        return ErrorMessage("An unexpected error occurred")
    }
}
