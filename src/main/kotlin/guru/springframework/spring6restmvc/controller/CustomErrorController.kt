package guru.springframework.spring6restmvc.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Suppress("unused")
class CustomErrorController {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleBindErrors(exception: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errorList: List<Map<String, String>> =
            exception.fieldErrors.map { fieldError -> mapOf(fieldError.field to (fieldError.defaultMessage ?: "")) }
        return ResponseEntity.badRequest().body(errorList)
    }
}