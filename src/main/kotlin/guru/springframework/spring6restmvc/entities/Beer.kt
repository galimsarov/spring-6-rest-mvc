package guru.springframework.spring6restmvc.entities

import guru.springframework.spring6restmvc.model.BeerStyle
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
data class Beer(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    var id: UUID = UUID.randomUUID(),

    @Version
    var version: Int = 0,

    @Column(length = 50)
    @field:NotBlank
    @field:Size(max = 50)
    var beerName: String = "",
    var beerStyle: BeerStyle = BeerStyle.NONE,

    @field:NotBlank
    @field:Size(max = 255)
    var upc: String = "",
    var quantityOnHand: Int = 0,
    var price: BigDecimal = BigDecimal(0),
    var createdDate: LocalDateTime = LocalDateTime.now(),
    var updateDate: LocalDateTime = LocalDateTime.now(),
)
