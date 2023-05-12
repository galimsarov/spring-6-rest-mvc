package guru.springframework.spring6restmvc.repositories

import guru.springframework.spring6restmvc.bootstrap.BootstrapData
import guru.springframework.spring6restmvc.entities.Beer
import guru.springframework.spring6restmvc.services.BeerCsvServiceImpl
import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(BootstrapData::class, BeerCsvServiceImpl::class)
class BeerRepositoryTest {
    @Autowired
    private lateinit var beerRepository: BeerRepository

    @Test
    fun testSavedBeer() {
        val savedBeer = beerRepository.save(Beer(beerName = "My Beer", upc = "234234"))

        beerRepository.flush()

        assert(savedBeer.id.toString().isNotBlank())
    }

    @Test
    fun testSaveBeerNameTooLong() {
        assertThrows<ConstraintViolationException> {
            beerRepository.save(
                Beer(
                    beerName = "My Beer 01234567890123456789012345678901234567890123456789",
                    upc = "234234"
                )
            )

            beerRepository.flush()
        }
    }

    @Test
    fun testBeerListByName() {
        val list: List<Beer> = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%")

        assert(list.size == 336)
    }
}