package guru.springframework.spring6restmvc.entities

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime
import java.util.*

@Entity
data class BeerOrder(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    var id: UUID = UUID.randomUUID(),

    @Version
    var version: Int = 0,

    var createdDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),

    var customerRef: String = "",
) {
    @ManyToOne
    var customer: Customer = Customer()
        set(value) {
            field = value
            customer.beerOrders.add(this)
        }


    @OneToMany(mappedBy = "beerOrder")
    var beerOrderLines: MutableSet<BeerOrderLine> = mutableSetOf()

    @OneToOne(cascade = [CascadeType.PERSIST])
    var beerOrderShipment: BeerOrderShipment? = null
        set(value) {
            field = value
            beerOrderShipment?.beerOrder = this
        }

    fun isNew(): Boolean = id.toString().isBlank()
}