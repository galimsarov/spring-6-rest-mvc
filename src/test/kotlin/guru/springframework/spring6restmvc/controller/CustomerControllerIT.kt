package guru.springframework.spring6restmvc.controller

import guru.springframework.spring6restmvc.entities.Customer
import guru.springframework.spring6restmvc.mappers.toDto
import guru.springframework.spring6restmvc.model.CustomerDTO
import guru.springframework.spring6restmvc.repositories.CustomerRepository
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
class CustomerControllerIT {
    @Autowired
    private lateinit var customerController: CustomerController

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Test
    fun testListCustomers() {
        val dtoList: List<CustomerDTO> = customerController.listCustomers()

        assert(dtoList.size == 2)
    }

    @Rollback
    @Test
    @Transactional
    fun testEmptyList() {
        customerRepository.deleteAll()
        val dtoList: List<CustomerDTO> = customerController.listCustomers()

        assert(dtoList.isEmpty())
    }

    @Test
    fun testGetById() {
        val customer = customerRepository.findAll()[0]

        val dto = customerController.getCustomerById(customer.id)

        assert(dto.id.toString() != "0")
    }

    @Test
    fun testCustomerIdNotFound() {
        assertThrows<NotFoundException> { customerController.getCustomerById(UUID.randomUUID()) }
    }

    @Rollback
    @Test
    @Transactional
    fun saveNewCustomerTest() {
        val customerDTO = CustomerDTO(customerName = "New Customer")

        val responseEntity: ResponseEntity<CustomerDTO> = customerController.handlePost(customerDTO)

        assert(responseEntity.statusCode == HttpStatusCode.valueOf(201))
        assert(responseEntity.headers.location != null)

        val locationUUID: List<String> = responseEntity.headers.location?.path?.split("/") ?: listOf()
        val savedUUID = UUID.fromString(locationUUID[4])

        val customer: Customer = customerRepository.findById(savedUUID).get()
        assert(customer.customerName.isNotBlank())
    }

    @Rollback
    @Test
    @Transactional
    fun updateExistingCustomer() {
        val customer: Customer = customerRepository.findAll()[0]
        val customerDTO = customer.toDto()
        customerDTO.id = UUID.randomUUID()
        customerDTO.version = 0
        val customerName = "UPDATED"
        customerDTO.customerName = customerName

        val responseEntity: ResponseEntity<CustomerDTO> = customerController.updateById(customer.id, customerDTO)
        assert(responseEntity.statusCode == HttpStatusCode.valueOf(204))

        val updatedCustomer: Customer = customerRepository.findById(customer.id).get()
        assert(updatedCustomer.customerName == customerName)
    }

    @Test
    fun testUpdateNotFound() {
        assertThrows<NotFoundException> { customerController.updateById(UUID.randomUUID(), CustomerDTO()) }
    }

    @Rollback
    @Test
    @Transactional
    fun deleteByIdFound() {
        val customer: Customer = customerRepository.findAll()[0]

        val responseEntity: ResponseEntity<CustomerDTO> = customerController.deleteById(customer.id)
        assert(responseEntity.statusCode == HttpStatusCode.valueOf(204))

        assert(customerRepository.findById(customer.id).isEmpty)
    }

    @Test
    fun testDeleteNotFound() {
        assertThrows<NotFoundException> { customerController.deleteById(UUID.randomUUID()) }
    }

    @Rollback
    @Test
    @Transactional
    fun patchExistingCustomer() {
        val testCustomerName = "UPDATED"

        val customer: Customer = customerRepository.findAll()[0]
        val customerDTO = customer.toDto().apply {
            id = UUID.randomUUID()
            version = 0
            customerName = testCustomerName
        }

        val responseEntity: ResponseEntity<CustomerDTO> =
            customerController.updateCustomerPatchById(customer.id, customerDTO)
        assert(responseEntity.statusCode == HttpStatusCode.valueOf(204))

        val updatedCustomer: Customer = customerRepository.findById(customer.id).get()
        assert(updatedCustomer.customerName == testCustomerName)
    }

    @Test
    fun testPatchNotFound() {
        assertThrows<NotFoundException> { customerController.updateCustomerPatchById(UUID.randomUUID(), CustomerDTO()) }
    }
}