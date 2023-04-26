package guru.springframework.spring6restmvc.repositories

import guru.springframework.spring6restmvc.entities.Customer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Test
    fun testSavedCustomer() {
        val savedCustomer = customerRepository.save(Customer(customerName = "Alex Ferguson"))

        assert(savedCustomer.id.toString().isNotBlank())
    }
}