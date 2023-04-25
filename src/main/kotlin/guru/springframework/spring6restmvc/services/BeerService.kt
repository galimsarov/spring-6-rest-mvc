package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.model.Beer
import java.util.*

interface BeerService {
    fun listBeers(): List<Beer>

    fun getBeerById(id: UUID): Beer

    fun saveNewBeer(beer: Beer): Beer

    fun updateById(beerId: UUID, beer: Beer)

    fun deleteById(beerId: UUID)

    fun patchBeerId(beerId: UUID, beer: Beer)
}