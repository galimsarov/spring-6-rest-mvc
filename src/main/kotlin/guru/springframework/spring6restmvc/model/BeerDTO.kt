package guru.springframework.spring6restmvc.model

import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class BeerDTO(
    var id: UUID = UUID.randomUUID(),
    var version: Int = 0,

    @field:NotBlank
    var beerName: String = "",
    var beerStyle: BeerStyle = BeerStyle.NONE,

    @field:NotBlank
    var upc: String = "",
    var quantityOnHand: Int = 0,
    var price: BigDecimal = BigDecimal(0),
    val createdDate: LocalDateTime = LocalDateTime.now(),
    var updateDate: LocalDateTime = LocalDateTime.now(),
)