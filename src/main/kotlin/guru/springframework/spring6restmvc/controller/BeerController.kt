package guru.springframework.spring6restmvc.controller

import guru.springframework.spring6restmvc.model.Beer
import guru.springframework.spring6restmvc.services.BeerService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@Suppress("unused")
class BeerController(private val beerService: BeerService) {
    @RequestMapping("/api/v1/beer")
    fun listBeers(): List<Beer> = beerService.listBeers()

    fun getBeerById(id: UUID): Beer = beerService.getBeerById(id)
}