package guru.springframework.spring6restmvc.controller

import guru.springframework.spring6restmvc.entities.Beer
import guru.springframework.spring6restmvc.mappers.toDto
import guru.springframework.spring6restmvc.model.BeerDTO
import guru.springframework.spring6restmvc.repositories.BeerRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
class BeerControllerIT {
    @Autowired
    private lateinit var beerController: BeerController

    @Autowired
    private lateinit var beerRepository: BeerRepository

    @Test
    fun testListBeers() {
        val dtoList: List<BeerDTO> = beerController.listBeers()

        assert(dtoList.size == 3)
    }

    @Rollback
    @Test
    @Transactional
    fun testEmptyList() {
        beerRepository.deleteAll()
        val dtoList: List<BeerDTO> = beerController.listBeers()

        assert(dtoList.isEmpty())
    }

    @Test
    fun testGetById() {
        val beer = beerRepository.findAll()[0]

        val dto = beerController.getBeerById(beer.id)

        assert(dto.id.toString() != "0")
    }

    @Test
    fun testBeerIdNotFound() {
        assertThrows<NotFoundException> { beerController.getBeerById(UUID.randomUUID()) }
    }

    @Rollback
    @Test
    @Transactional
    fun saveNewBeerTest() {
        val beerDTO = BeerDTO(beerName = "New Beer")

        val responseEntity: ResponseEntity<BeerDTO> = beerController.handlePost(beerDTO)

        assert(responseEntity.statusCode == HttpStatusCode.valueOf(201))
        assert(responseEntity.headers.location != null)

        val locationUUID: List<String> = responseEntity.headers.location?.path?.split("/") ?: listOf()
        val savedUUID = UUID.fromString(locationUUID[4])

        val beer: Beer = beerRepository.findById(savedUUID).get()
        assert(beer.beerName.isNotBlank())
    }

    @Rollback
    @Test
    @Transactional
    fun updateExistingBeer() {
        val beer: Beer = beerRepository.findAll()[0]
        val beerDTO = beer.toDto()
        beerDTO.id = UUID.randomUUID()
        beerDTO.version = 0
        val beerName = "UPDATED"
        beerDTO.beerName = beerName

        val responseEntity: ResponseEntity<BeerDTO> = beerController.updateById(beer.id, beerDTO)
        assert(responseEntity.statusCode == HttpStatusCode.valueOf(204))

        val updatedBeer: Beer = beerRepository.findById(beer.id).get()
        assert(updatedBeer.beerName == beerName)
    }

    @Test
    fun testUpdateNotFound() {
        assertThrows<NotFoundException> { beerController.updateById(UUID.randomUUID(), BeerDTO()) }
    }

    @Rollback
    @Test
    @Transactional
    fun deleteByIdFound() {
        val beer: Beer = beerRepository.findAll()[0]

        val responseEntity: ResponseEntity<BeerDTO> = beerController.deleteById(beer.id)
        assert(responseEntity.statusCode == HttpStatusCode.valueOf(204))

        assert(beerRepository.findById(beer.id).isEmpty)
    }

    @Test
    fun testDeleteNotFound() {
        assertThrows<NotFoundException> { beerController.deleteById(UUID.randomUUID()) }
    }

    @Rollback
    @Test
    @Transactional
    fun patchExistingBeer() {
        val beer: Beer = beerRepository.findAll()[0]
        val beerDTO = beer.toDto()
        beerDTO.id = UUID.randomUUID()
        beerDTO.version = 0
        val beerName = "UPDATED"
        beerDTO.beerName = beerName

        val responseEntity: ResponseEntity<BeerDTO> = beerController.updateBeerPatchById(beer.id, beerDTO)
        assert(responseEntity.statusCode == HttpStatusCode.valueOf(204))

        val updatedBeer: Beer = beerRepository.findById(beer.id).get()
        assert(updatedBeer.beerName == beerName)
    }

    @Test
    fun testPatchNotFound() {
        assertThrows<NotFoundException> { beerController.updateBeerPatchById(UUID.randomUUID(), BeerDTO()) }
    }
}