package guru.springframework.spring6restmvc.model

import java.time.LocalDateTime
import java.util.*

data class Customer(
    var id: UUID = UUID.randomUUID(),
    var version: Int = 0,
    var customerName: String = "",
    val createdDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),
)
