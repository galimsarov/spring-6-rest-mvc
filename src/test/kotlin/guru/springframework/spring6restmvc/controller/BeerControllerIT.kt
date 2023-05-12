package guru.springframework.spring6restmvc.controller

import com.fasterxml.jackson.databind.ObjectMapper
import guru.springframework.spring6restmvc.entities.Beer
import guru.springframework.spring6restmvc.mappers.toDto
import guru.springframework.spring6restmvc.model.BeerDTO
import guru.springframework.spring6restmvc.repositories.BeerRepository
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import java.util.*

@SpringBootTest
class BeerControllerIT {
    @Autowired
    private lateinit var beerController: BeerController

    @Autowired
    private lateinit var beerRepository: BeerRepository

    @Autowired
    private lateinit var wac: WebApplicationContext

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }

    @Test
    fun testListBeers() {
        val dtoList: List<BeerDTO> = beerController.listBeers()

        assert(dtoList.size == 2410)
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
        val testBeerName = "UPDATED"

        val beer: Beer = beerRepository.findAll()[0]
        val beerDTO = beer.toDto().apply {
            id = UUID.randomUUID()
            version = 0
            beerName = testBeerName
        }

        val responseEntity: ResponseEntity<BeerDTO> = beerController.updateById(beer.id, beerDTO)
        assert(responseEntity.statusCode == HttpStatusCode.valueOf(204))

        val updatedBeer: Beer = beerRepository.findById(beer.id).get()
        assert(updatedBeer.beerName == testBeerName)
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
        val testBeerName = "UPDATED"

        val beer: Beer = beerRepository.findAll()[0]
        val beerDTO = beer.toDto().apply {
            id = UUID.randomUUID()
            version = 0
            beerName = testBeerName
        }

        val responseEntity: ResponseEntity<BeerDTO> = beerController.updateBeerPatchById(beer.id, beerDTO)
        assert(responseEntity.statusCode == HttpStatusCode.valueOf(204))

        val updatedBeer: Beer = beerRepository.findById(beer.id).get()
        assert(updatedBeer.beerName == testBeerName)
    }

    @Test
    fun testPatchNotFound() {
        assertThrows<NotFoundException> { beerController.updateBeerPatchById(UUID.randomUUID(), BeerDTO()) }
    }

    @Test
    fun testPatchBeerBadName() {
        val beer: Beer = beerRepository.findAll()[0].apply {
            beerName = "New Name 01234567890123456789012345678901234567890123456789"
        }
        val beerPathTestId = BEER_PATH + "/${beer.id}"

        val result: MvcResult = mockMvc.perform(
            patch(beerPathTestId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.length()", `is`(1)))
            .andReturn()

        println(result.response.contentAsString)
    }

    @Test
    fun testListBeersByName() {
        mockMvc.perform(
            get(BEER_PATH)
                .queryParam("beerName", "IPA")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()", `is`(336)))
    }
}