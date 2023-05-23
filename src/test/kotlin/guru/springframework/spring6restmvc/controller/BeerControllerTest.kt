package guru.springframework.spring6restmvc.controller

import com.fasterxml.jackson.databind.ObjectMapper
import guru.springframework.spring6restmvc.config.SpringSecurityConfig
import guru.springframework.spring6restmvc.model.BeerDTO
import guru.springframework.spring6restmvc.services.BeerService
import guru.springframework.spring6restmvc.services.BeerServiceImpl
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.Instant
import java.util.*

@Import(SpringSecurityConfig::class)
@WebMvcTest(BeerController::class)
class BeerControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var beerService: BeerService

    private lateinit var beerServiceImpl: BeerServiceImpl
    private lateinit var beer: BeerDTO
    private lateinit var beerPathTestId: String

    private val jwtRequestPostProcessor: SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor =
        jwt().jwt { jwt ->
            jwt.claims { claims ->
                claims["scope"] = "message-read"
                claims["scope"] = "message-write"
            }.subject("messaging-client").notBefore(Instant.now().minusSeconds(5))
        }

    @BeforeEach
    fun setUp() {
        beerServiceImpl = BeerServiceImpl()
        beer = beerServiceImpl.listBeers().first()
        beerPathTestId = BEER_PATH + "/${beer.id}"
    }

    @Test
    fun getBeerByIdTest() {
        `when`(beerService.getBeerById(beer.id)).thenReturn(beer)

        mockMvc
            .perform(get(beerPathTestId).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`(beer.id.toString())))
            .andExpect(jsonPath("$.beerName", `is`(beer.beerName)))
    }

    @Test
    fun getBeerByIdNotFound() {
        val badId = UUID.randomUUID()
        val pathWithBadId = "$BEER_PATH/$badId"

        `when`(beerService.getBeerById(badId)).thenThrow(NotFoundException::class.java)

        mockMvc.perform(get(pathWithBadId).with(jwtRequestPostProcessor)).andExpect(status().isNotFound)
    }

    @Test
    fun listBeersTest() {
        val listBeers = beerServiceImpl.listBeers()

        `when`(beerService.listBeers()).thenReturn(listBeers)

        mockMvc
            .perform(get(BEER_PATH).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.length()", `is`(listBeers.size)))
    }

    @Test
    fun testCreateNewBeer() {
        `when`(beerService.saveNewBeer(beer)).thenReturn(beerServiceImpl.listBeers().elementAt(1))

        mockMvc
            .perform(
                post(BEER_PATH).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(beer))
            )
            .andExpect(status().isCreated)
            .andExpect(header().exists("Location"))
    }

    @Test
    fun testCreateBeerEmptyBeerName() {
        val emptyBeer = BeerDTO()

        `when`(beerService.saveNewBeer(emptyBeer)).thenReturn(beerServiceImpl.listBeers().elementAt(1))

        val mvcResult: MvcResult =
            mockMvc
                .perform(
                    post(BEER_PATH).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyBeer))
                )
                .andExpect(jsonPath("$.length()", `is`(2)))
                .andExpect(status().isBadRequest).andReturn()

        println(mvcResult.response.contentAsString)
    }

    @Test
    fun testUpdateBeer() {
        mockMvc
            .perform(
                put(beerPathTestId).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(beer))
            )
            .andExpect(status().isNoContent)

        verify(beerService).updateById(beer.id, beer)
    }

    @Test
    fun testUpdateBeerEmptyBeerName() {
        val emptyBeer = BeerDTO()

        `when`(beerService.updateById(emptyBeer.id, emptyBeer)).thenReturn(beerServiceImpl.listBeers().elementAt(1))

        val mvcResult: MvcResult =
            mockMvc
                .perform(
                    put(beerPathTestId).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyBeer))
                )
                .andExpect(jsonPath("$.length()", `is`(2)))
                .andExpect(status().isBadRequest).andReturn()

        println(mvcResult.response.contentAsString)
    }

    @Test
    fun testDeleteBeer() {
        `when`(beerService.deleteById(beer.id)).thenReturn(true)

        mockMvc
            .perform(delete(beerPathTestId).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent)

        verify(beerService).deleteById(beer.id)
    }

    @Test
    fun testPatchBeer() {
        mockMvc
            .perform(
                patch(beerPathTestId).with(jwtRequestPostProcessor).contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(beer))
            )
            .andExpect(status().isNoContent)

        verify(beerService).patchBeerId(beer.id, beer)
    }
}