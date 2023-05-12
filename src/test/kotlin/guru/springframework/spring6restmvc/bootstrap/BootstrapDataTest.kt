package guru.springframework.spring6restmvc.bootstrap

import guru.springframework.spring6restmvc.repositories.BeerRepository
import guru.springframework.spring6restmvc.repositories.CustomerRepository
import guru.springframework.spring6restmvc.services.BeerCsvService
import guru.springframework.spring6restmvc.services.BeerCsvServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(BeerCsvServiceImpl::class)
class BootstrapDataTest {
    @Autowired
    private lateinit var beerRepository: BeerRepository

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var csvService: BeerCsvService

    private lateinit var bootstrapData: BootstrapData

    @BeforeEach
    fun setUp() {
        bootstrapData = BootstrapData(beerRepository, customerRepository, csvService)
    }

    @Test
    fun runTest() {
        bootstrapData.run()

        assert(beerRepository.count() == 2410L)
        assert(customerRepository.count() == 2L)
    }
}