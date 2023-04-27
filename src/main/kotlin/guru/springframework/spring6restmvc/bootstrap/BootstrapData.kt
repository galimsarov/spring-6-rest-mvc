package guru.springframework.spring6restmvc.bootstrap

import guru.springframework.spring6restmvc.entities.Beer
import guru.springframework.spring6restmvc.entities.Customer
import guru.springframework.spring6restmvc.model.BeerStyle
import guru.springframework.spring6restmvc.repositories.BeerRepository
import guru.springframework.spring6restmvc.repositories.CustomerRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime

@Component
class BootstrapData(private val beerRepository: BeerRepository, private val customerRepository: CustomerRepository) :
    CommandLineRunner {
    override fun run(vararg args: String?) {
        val beer1 = Beer(
            beerName = "Galaxy Cat",
            beerStyle = BeerStyle.PALE_ALE,
            upc = "12356",
            price = BigDecimal("12.99"),
            quantityOnHand = 122,
            createdDate = LocalDateTime.now(),
            updateDate = LocalDateTime.now()
        )
        val beer2 = Beer(
            beerName = "Crank",
            beerStyle = BeerStyle.PALE_ALE,
            upc = "12356222",
            price = BigDecimal("11.99"),
            quantityOnHand = 392,
            createdDate = LocalDateTime.now(),
            updateDate = LocalDateTime.now()
        )
        val beer3 = Beer(
            beerName = "Sunshine City",
            beerStyle = BeerStyle.IPA,
            upc = "123456",
            price = BigDecimal("13.99"),
            quantityOnHand = 144,
            createdDate = LocalDateTime.now(),
            updateDate = LocalDateTime.now()
        )
        beerRepository.saveAll(listOf(beer1, beer2, beer3))

        val customer1 = Customer(
            customerName = "Alex Ferguson",
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )
        val customer2 = Customer(
            customerName = "John Lennon",
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )
        customerRepository.saveAll(listOf(customer1, customer2))
    }
}