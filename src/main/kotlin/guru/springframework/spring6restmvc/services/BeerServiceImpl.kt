package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.model.BeerDTO
import guru.springframework.spring6restmvc.model.BeerStyle
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Service
@Suppress("unused")
class BeerServiceImpl : BeerService {
    private val beerMap: MutableMap<UUID, BeerDTO> = mutableMapOf()

    init {
        val firstId = UUID.randomUUID()
        beerMap[firstId] = BeerDTO(
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
        beerMap[secondId] = BeerDTO(
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
        beerMap[thirdId] = BeerDTO(
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

    override fun listBeers(beerName: String, beerStyle: String): List<BeerDTO> {
        return beerMap.values.toList()
    }

    override fun getBeerById(id: UUID): BeerDTO {
        return beerMap[id] ?: BeerDTO()
    }

    override fun saveNewBeer(beer: BeerDTO): BeerDTO {
        val newBeer = beer.copy()
        beerMap[newBeer.id] = newBeer
        return newBeer
    }

    override fun updateById(beerId: UUID, beer: BeerDTO): BeerDTO {
        val beerFromMap: BeerDTO? = beerMap[beerId]
        beerFromMap.let {
            beer.id = beerId
            beerMap[beerId] = beer
            return beer
        }
    }

    override fun deleteById(beerId: UUID): Boolean {
        beerMap.remove(beerId)
        return true
    }

    override fun patchBeerId(beerId: UUID, beer: BeerDTO): BeerDTO {
        beerMap[beerId].apply {
            if (this != null) {
                var beerChanged = false
                if (beer.beerName.isNotBlank()) {
                    beerName = beer.beerName
                    beerChanged = true
                }
                if (beer.beerStyle != BeerStyle.NONE) {
                    beerStyle = beer.beerStyle
                    beerChanged = true
                }
                if (beer.price > BigDecimal(0)) {
                    price = beer.price
                    beerChanged = true
                }
                if (beer.quantityOnHand > 0) {
                    quantityOnHand = beer.quantityOnHand
                    beerChanged = true
                }
                if (beer.upc.isNotBlank()) {
                    upc = beer.upc
                    beerChanged = true
                }
                if (beerChanged) {
                    updateDate = LocalDateTime.now()
                }
                return this
            } else {
                return BeerDTO()
            }
        }
    }
}