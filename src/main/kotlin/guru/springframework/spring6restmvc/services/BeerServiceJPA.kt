package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.controller.NotFoundException
import guru.springframework.spring6restmvc.mappers.toDto
import guru.springframework.spring6restmvc.model.BeerDTO
import guru.springframework.spring6restmvc.repositories.BeerRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.util.*

@Primary
@Service
@Suppress("unused")
class BeerServiceJPA(private val beerRepository: BeerRepository) : BeerService {
    override fun listBeers(): List<BeerDTO> = beerRepository.findAll().map { it.toDto() }

    override fun getBeerById(id: UUID): BeerDTO = try {
        beerRepository.findById(id).get().toDto()
    } catch (_: NoSuchElementException) {
        throw NotFoundException()
    }

    override fun saveNewBeer(beer: BeerDTO): BeerDTO {
        TODO("Not yet implemented")
    }

    override fun updateById(beerId: UUID, beer: BeerDTO) {
        TODO("Not yet implemented")
    }

    override fun deleteById(beerId: UUID) {
        TODO("Not yet implemented")
    }

    override fun patchBeerId(beerId: UUID, beer: BeerDTO) {
        TODO("Not yet implemented")
    }
}