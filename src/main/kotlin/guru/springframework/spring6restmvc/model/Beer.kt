package guru.springframework.spring6restmvc.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Beer(
    var id: UUID = UUID.randomUUID(),
    val version: Int = 0,
    var beerName: String = "",
    var beerStyle: BeerStyle = BeerStyle.NONE,
    var upc: String = "",
    var quantityOnHand: Int = 0,
    var price: BigDecimal = BigDecimal(0),
    val createdDate: LocalDateTime = LocalDateTime.now(),
    var updateDate: LocalDateTime = LocalDateTime.now(),
)