package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.model.Beer
import java.util.*

interface BeerService {
    fun listBeers(): List<Beer>

    fun getBeerById(id: UUID): Beer
}