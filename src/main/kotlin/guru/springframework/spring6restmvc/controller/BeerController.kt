package guru.springframework.spring6restmvc.controller

import guru.springframework.spring6restmvc.model.Beer
import guru.springframework.spring6restmvc.services.BeerService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/beer")
@Suppress("unused")
class BeerController(private val beerService: BeerService) {
    @RequestMapping(method = [RequestMethod.GET])
    fun listBeers(): List<Beer> = beerService.listBeers()

    @RequestMapping(value = ["/{beerId}"], method = [RequestMethod.GET])
    fun getBeerById(@PathVariable("beerId") beerId: UUID): Beer = beerService.getBeerById(beerId)

    @PostMapping
    fun handlePost(@RequestBody beer: Beer): ResponseEntity<Beer> {
        val savedBeer: Beer = beerService.saveNewBeer(beer)
        val headers = HttpHeaders()
        headers.add("Location", "/api/v1/beer/${savedBeer.id}")
        return ResponseEntity(headers, HttpStatus.CREATED)
    }

    @PutMapping("/{beerId}")
    fun updateById(@PathVariable("beerId") beerId: UUID, @RequestBody beer: Beer): ResponseEntity<Beer> {
        beerService.updateById(beerId, beer)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @DeleteMapping("/{beerId}")
    fun deleteById(@PathVariable("beerId") beerId: UUID): ResponseEntity<Beer> {
        beerService.deleteById(beerId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PatchMapping("/{beerId}")
    fun updateBeerPatchById(@PathVariable("beerId") beerId: UUID, @RequestBody beer: Beer): ResponseEntity<Beer> {
        beerService.patchBeerId(beerId, beer)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}