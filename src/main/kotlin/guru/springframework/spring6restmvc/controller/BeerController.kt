package guru.springframework.spring6restmvc.controller

import guru.springframework.spring6restmvc.model.BeerDTO
import guru.springframework.spring6restmvc.services.BeerService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

const val BEER_PATH = "/api/v1/beer"
const val BEER_PATH_ID = "$BEER_PATH/{beerId}"

@RestController
@Suppress("unused")
class BeerController(private val beerService: BeerService) {
    @GetMapping(BEER_PATH)
    fun listBeers(): List<BeerDTO> = beerService.listBeers()

    @GetMapping(BEER_PATH_ID)
    fun getBeerById(@PathVariable("beerId") beerId: UUID): BeerDTO = beerService.getBeerById(beerId)

    @PostMapping(BEER_PATH)
    fun handlePost(@RequestBody beer: BeerDTO): ResponseEntity<BeerDTO> {
        val savedBeer: BeerDTO = beerService.saveNewBeer(beer)
        val headers = HttpHeaders()
        headers.add("Location", "$BEER_PATH/${savedBeer.id}")
        return ResponseEntity(headers, HttpStatus.CREATED)
    }

    @PutMapping(BEER_PATH_ID)
    fun updateById(@PathVariable("beerId") beerId: UUID, @RequestBody beer: BeerDTO): ResponseEntity<BeerDTO> {
        beerService.updateById(beerId, beer)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @DeleteMapping(BEER_PATH_ID)
    fun deleteById(@PathVariable("beerId") beerId: UUID): ResponseEntity<BeerDTO> {
        beerService.deleteById(beerId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PatchMapping(BEER_PATH_ID)
    fun updateBeerPatchById(@PathVariable("beerId") beerId: UUID, @RequestBody beer: BeerDTO): ResponseEntity<BeerDTO> {
        beerService.patchBeerId(beerId, beer)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}