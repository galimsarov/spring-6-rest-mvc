package guru.springframework.spring6restmvc.controller

import guru.springframework.spring6restmvc.model.BeerDTO
import guru.springframework.spring6restmvc.repositories.BeerRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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
}