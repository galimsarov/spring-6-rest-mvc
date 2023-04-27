package guru.springframework.spring6restmvc.controller

import guru.springframework.spring6restmvc.model.CustomerDTO
import guru.springframework.spring6restmvc.repositories.CustomerRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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
}