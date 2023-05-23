package guru.springframework.spring6restmvc.controller

import com.fasterxml.jackson.databind.ObjectMapper
import guru.springframework.spring6restmvc.config.SpringSecurityConfig
import guru.springframework.spring6restmvc.model.CustomerDTO
import guru.springframework.spring6restmvc.services.CustomerService
import guru.springframework.spring6restmvc.services.CustomerServiceImpl
import org.hamcrest.core.Is
import org.junit.jupiter.api.Assertions.*
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.Instant
import java.util.*

@Import(SpringSecurityConfig::class)
@WebMvcTest(CustomerController::class)
class CustomerControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var customerService: CustomerService

    private lateinit var customerServiceImpl: CustomerServiceImpl
    private lateinit var customer: CustomerDTO
    private lateinit var customerPathTestId: String

    private val jwtRequestPostProcessor: SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor =
        jwt().jwt { jwt ->
            jwt.claims { claims ->
                claims["scope"] = "message-read"
                claims["scope"] = "message-write"
            }.subject("messaging-client").notBefore(Instant.now().minusSeconds(5))
        }

    @BeforeEach
    fun setUp() {
        customerServiceImpl = CustomerServiceImpl()
        customer = customerServiceImpl.listCustomers()[0]
        customerPathTestId = CUSTOMER_PATH + "/${customer.id}"
    }

    @Test
    fun getCustomerByIdTest() {
        `when`(customerService.getCustomerById(customer.id)).thenReturn(customer)

        mockMvc
            .perform(get(customerPathTestId).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", Is.`is`(customer.id.toString())))
            .andExpect(jsonPath("$.customerName", Is.`is`(customer.customerName)))
    }

    @Test
    fun getCustomerByIdNotFound() {
        val badId = UUID.randomUUID()
        val pathWithBadId = "$CUSTOMER_PATH/$badId"

        `when`(customerService.getCustomerById(badId)).thenThrow(NotFoundException::class.java)

        mockMvc.perform(get(pathWithBadId).with(jwtRequestPostProcessor)).andExpect(status().isNotFound)
    }

    @Test
    fun listCustomersTest() {
        val listCustomers = customerServiceImpl.listCustomers()

        `when`(customerService.listCustomers()).thenReturn(listCustomers)

        mockMvc
            .perform(get(CUSTOMER_PATH).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()", Is.`is`(listCustomers.size)))
    }

    @Test
    fun testCreateNewCustomer() {
        `when`(customerService.saveNewCustomer(customer)).thenReturn(customerServiceImpl.listCustomers()[1])

        mockMvc
            .perform(
                post(CUSTOMER_PATH).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer))
            )
            .andExpect(status().isCreated)
            .andExpect(header().exists("Location"))
    }

    @Test
    fun testUpdateCustomer() {
        mockMvc
            .perform(
                put(customerPathTestId).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer))
            )
            .andExpect(status().isNoContent)

        verify(customerService).updateById(customer.id, customer)
    }

    @Test
    fun testDeleteCustomer() {
        `when`(customerService.deleteById(customer.id)).thenReturn(true)

        mockMvc
            .perform(delete(customerPathTestId).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent)

        verify(customerService).deleteById(customer.id)
    }

    @Test
    fun testPatchCustomer() {
        mockMvc
            .perform(
                patch(customerPathTestId).with(jwtRequestPostProcessor).contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer))
            )
            .andExpect(status().isNoContent)

        verify(customerService).patchCustomerId(customer.id, customer)
    }
}