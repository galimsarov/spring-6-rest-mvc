package guru.springframework.spring6restmvc.controller

import guru.springframework.spring6restmvc.model.Beer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Suppress("unused")
class ExceptionController {
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(): ResponseEntity<Beer> {
        return ResponseEntity.notFound().build()
    }
}