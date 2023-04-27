package guru.springframework.spring6restmvc.bootstrap

import guru.springframework.spring6restmvc.repositories.BeerRepository
import guru.springframework.spring6restmvc.repositories.CustomerRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class BootstrapDataTest {
    @Autowired
    private lateinit var beerRepository: BeerRepository

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    private lateinit var bootstrapData: BootstrapData

    @BeforeEach
    fun setUp() {
        bootstrapData = BootstrapData(beerRepository, customerRepository)
    }

    @Test
    fun runTest() {
        bootstrapData.run()

        assert(beerRepository.count() == 3L)
        assert(customerRepository.count() == 2L)
    }
}