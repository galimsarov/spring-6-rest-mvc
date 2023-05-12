package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.model.BeerDTO
import java.util.*

interface BeerService {
    fun listBeers(beerName: String = ""): List<BeerDTO>

    fun getBeerById(id: UUID): BeerDTO

    fun saveNewBeer(beer: BeerDTO): BeerDTO

    fun updateById(beerId: UUID, beer: BeerDTO): BeerDTO

    fun deleteById(beerId: UUID): Boolean

    fun patchBeerId(beerId: UUID, beer: BeerDTO): BeerDTO
}