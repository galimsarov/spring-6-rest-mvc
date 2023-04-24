package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.model.Beer
import guru.springframework.spring6restmvc.model.BeerStyle
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Service
@Suppress("unused")
class BeerServiceImpl : BeerService {
    private val beerMap: MutableMap<UUID, Beer> = mutableMapOf()

    init {
        val firstId = UUID.randomUUID()
        beerMap[firstId] = Beer(
            id = firstId,
            version = 1,
            beerName = "Galaxy Cat",
            beerStyle = BeerStyle.PALE_ALE,
            upc = "12356",
            price = BigDecimal("12.99"),
            quantityOnHand = 122,
            createdDate = LocalDateTime.now(),
            updateDate = LocalDateTime.now()
        )

        val secondId = UUID.randomUUID()
        beerMap[secondId] = Beer(
            id = secondId,
            version = 1,
            beerName = "Crank",
            beerStyle = BeerStyle.PALE_ALE,
            upc = "12356222",
            price = BigDecimal("11.99"),
            quantityOnHand = 392,
            createdDate = LocalDateTime.now(),
            updateDate = LocalDateTime.now()
        )

        val thirdId = UUID.randomUUID()
        beerMap[thirdId] = Beer(
            id = thirdId,
            version = 1,
            beerName = "Sunshine City",
            beerStyle = BeerStyle.IPA,
            upc = "123456",
            price = BigDecimal("13.99"),
            quantityOnHand = 144,
            createdDate = LocalDateTime.now(),
            updateDate = LocalDateTime.now()
        )
    }

    override fun listBeers(): List<Beer> {
        return beerMap.values.toList()
    }

    override fun getBeerById(id: UUID): Beer {
        return beerMap[id] ?: Beer()
    }
}