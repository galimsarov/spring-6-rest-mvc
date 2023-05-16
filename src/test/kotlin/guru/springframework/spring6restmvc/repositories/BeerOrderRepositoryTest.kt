package guru.springframework.spring6restmvc.repositories

import guru.springframework.spring6restmvc.entities.Beer
import guru.springframework.spring6restmvc.entities.BeerOrder
import guru.springframework.spring6restmvc.entities.Customer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class BeerOrderRepositoryTest {
    @Autowired
    private lateinit var beerOrderRepository: BeerOrderRepository

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var beerRepository: BeerRepository

    private lateinit var testCustomer: Customer
    private lateinit var testBeer: Beer

    @BeforeEach
    fun setUp() {
        testCustomer = customerRepository.findAll()[0]
        testBeer = beerRepository.findAll()[0]
    }

    @Test
    @Transactional
    fun testBeerOrders() {
        val beerOrder = BeerOrder(customerRef = "Test order").apply { customer = testCustomer }
        val savedBeerOrder = beerOrderRepository.save(beerOrder)

        println(savedBeerOrder.customerRef)

    }
}