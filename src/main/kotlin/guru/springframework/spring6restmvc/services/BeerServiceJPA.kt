package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.controller.NotFoundException
import guru.springframework.spring6restmvc.mappers.toBeer
import guru.springframework.spring6restmvc.mappers.toDto
import guru.springframework.spring6restmvc.model.BeerDTO
import guru.springframework.spring6restmvc.model.BeerStyle
import guru.springframework.spring6restmvc.repositories.BeerRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Primary
@Service
@Suppress("unused")
class BeerServiceJPA(private val beerRepository: BeerRepository) : BeerService {
    override fun listBeers(beerName: String, beerStyle: String): List<BeerDTO> =
        beerRepository.findAll()
            .filter {
                if (beerName.isNotBlank())
                    if (!it.beerName.contains(beerName, true))
                        return@filter false
                if (beerStyle.isNotBlank())
                    if (!it.beerStyle.toString().contains(beerStyle, true))
                        return@filter false
                true
            }.map { it.toDto() }

    override fun getBeerById(id: UUID): BeerDTO = beerRepository.findById(id).get().toDto()

    override fun saveNewBeer(beer: BeerDTO): BeerDTO = beerRepository.save(beer.toBeer()).toDto()

    override fun updateById(beerId: UUID, beer: BeerDTO): BeerDTO {
        var result = BeerDTO()
        beerRepository.findById(beerId).ifPresent { foundBeer ->
            foundBeer.apply {
                beerName = beer.beerName
                beerStyle = beer.beerStyle
                upc = beer.upc
                price = beer.price
                quantityOnHand = beer.quantityOnHand
            }
            beerRepository.save(foundBeer)
            result = foundBeer.toDto()
        }
        if (result.beerName.isNotBlank()) return result
        else throw NotFoundException()
    }

    override fun deleteById(beerId: UUID): Boolean {
        return if (beerRepository.existsById(beerId)) {
            beerRepository.deleteById(beerId)
            true
        } else {
            false
        }
    }

    override fun patchBeerId(beerId: UUID, beer: BeerDTO): BeerDTO {
        var result = BeerDTO()
        beerRepository.findById(beerId).ifPresent { foundBeer ->
            foundBeer.apply {
                if (beer.beerName.isNotBlank()) beerName = beer.beerName
                if (beer.beerStyle != BeerStyle.NONE) beerStyle = beer.beerStyle
                if (beer.upc.isNotBlank()) upc = beer.upc
                if (beer.price > BigDecimal(0)) price = beer.price
                if (beer.quantityOnHand > 0) quantityOnHand = beer.quantityOnHand
            }
            beerRepository.save(foundBeer)
            result = foundBeer.toDto()
        }
        if (result.beerName.isNotBlank()) return result
        else throw NotFoundException()
    }
}