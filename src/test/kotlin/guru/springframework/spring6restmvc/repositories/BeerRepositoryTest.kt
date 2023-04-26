package guru.springframework.spring6restmvc.repositories

import guru.springframework.spring6restmvc.entities.Beer
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    private lateinit var beerRepository: BeerRepository

    @Test
    fun testSavedBeer() {
        val savedBeer = beerRepository.save(Beer(beerName = "My Beer"))

        assert(savedBeer.id.toString().isNotBlank())
    }
}