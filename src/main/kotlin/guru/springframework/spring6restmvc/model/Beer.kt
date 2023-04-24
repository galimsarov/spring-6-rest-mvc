package guru.springframework.spring6restmvc.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Beer(
    var id: UUID = UUID.randomUUID(),
    val version: Int = 0,
    val beerName: String = "",
    val beerStyle: BeerStyle = BeerStyle.NONE,
    val upc: String = "",
    val quantityOnHand: Int = 0,
    val price: BigDecimal = BigDecimal(0),
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val updateDate: LocalDateTime = LocalDateTime.now(),
)