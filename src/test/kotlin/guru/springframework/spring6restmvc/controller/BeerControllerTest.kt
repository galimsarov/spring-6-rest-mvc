package guru.springframework.spring6restmvc.controller

import guru.springframework.spring6restmvc.services.BeerService
import guru.springframework.spring6restmvc.services.BeerServiceImpl
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest(BeerController::class)
class BeerControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var beerService: BeerService

    private val beerServiceImpl = BeerServiceImpl()

    @Test
    fun getBeerByIdTest() {
        val testBeer = beerServiceImpl.listBeers()[0]

        `when`(beerService.getBeerById(testBeer.id)).thenReturn(testBeer)

        mockMvc
            .perform(get("/api/v1/beer/${testBeer.id}").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`(testBeer.id.toString())))
            .andExpect(jsonPath("$.beerName", `is`(testBeer.beerName)))
    }

    @Test
    fun listBeersTest() {
        val listBeers = beerServiceImpl.listBeers()

        `when`(beerService.listBeers()).thenReturn(listBeers)

        mockMvc
            .perform(get("/api/v1/beer").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()", `is`(listBeers.size)))
    }
}