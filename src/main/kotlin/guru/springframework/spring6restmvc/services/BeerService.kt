package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.model.BeerDTO
import org.springframework.data.domain.Page
import java.util.*

interface BeerService {
    fun listBeers(
        beerName: String = "",
        beerStyle: String = "",
        showInventory: Boolean? = null,
        pageNumber: Int = 1,
        pageSize: Int = 25
    ): Page<BeerDTO>

    fun getBeerById(id: UUID): BeerDTO

    fun saveNewBeer(beer: BeerDTO): BeerDTO

    fun updateById(beerId: UUID, beer: BeerDTO): BeerDTO

    fun deleteById(beerId: UUID): Boolean

    fun patchBeerId(beerId: UUID, beer: BeerDTO): BeerDTO
}