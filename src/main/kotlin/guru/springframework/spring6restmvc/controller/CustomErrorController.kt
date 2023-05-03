package guru.springframework.spring6restmvc.controller

import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.transaction.TransactionSystemException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Suppress("unused")
class CustomErrorController {
    @ExceptionHandler()
    fun handleJPAViolations(exception: TransactionSystemException): ResponseEntity<Any> {
        val responseEntity: ResponseEntity.BodyBuilder = ResponseEntity.badRequest()

        val ve = exception.cause?.cause
        if (ve is ConstraintViolationException) {
            val errors: List<Map<String, String>> =
                ve.constraintViolations.map { constraintViolation -> mapOf(constraintViolation.propertyPath.toString() to constraintViolation.message) }
            return responseEntity.body(errors)
        }

        return responseEntity.build()
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleBindErrors(exception: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errorList: List<Map<String, String>> =
            exception.fieldErrors.map { fieldError -> mapOf(fieldError.field to (fieldError.defaultMessage ?: "")) }
        return ResponseEntity.badRequest().body(errorList)
    }
}