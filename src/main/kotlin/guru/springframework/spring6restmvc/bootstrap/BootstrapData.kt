package guru.springframework.spring6restmvc.bootstrap

import guru.springframework.spring6restmvc.entities.Beer
import guru.springframework.spring6restmvc.entities.Customer
import guru.springframework.spring6restmvc.model.BeerCSVRecord
import guru.springframework.spring6restmvc.model.BeerStyle
import guru.springframework.spring6restmvc.repositories.BeerRepository
import guru.springframework.spring6restmvc.repositories.CustomerRepository
import guru.springframework.spring6restmvc.services.BeerCsvService
import org.apache.commons.lang3.StringUtils
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.ResourceUtils
import java.io.File
import java.math.BigDecimal
import java.time.LocalDateTime

@Component
class BootstrapData(
    private val beerRepository: BeerRepository,
    private val customerRepository: CustomerRepository,
    private val beerCsvService: BeerCsvService
) :
    CommandLineRunner {
    @Transactional
    override fun run(vararg args: String?) {
        loadCsvData()
        loadCustomerData()
    }

    private fun loadCsvData() {
        if (beerRepository.count() < 10) {
            val file: File = ResourceUtils.getFile("classpath:csvdata/beers.csv")
            val recs: List<BeerCSVRecord> = beerCsvService.convertCSV(file)

            recs.forEach { beerCSVRecord ->
                val beerStyle: BeerStyle = when (beerCSVRecord.style) {
                    "American Pale Lager" -> BeerStyle.LAGER
                    "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                        BeerStyle.ALE

                    "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA
                    "American Porter" -> BeerStyle.PORTER
                    "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT
                    "Saison / Farmhouse Ale" -> BeerStyle.SAISON
                    "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT
                    "English Pale Ale" -> BeerStyle.PALE_ALE
                    else -> BeerStyle.PILSNER
                }

                beerRepository.save(
                    Beer(
                        beerName = StringUtils.abbreviate(beerCSVRecord.beer, 50),
                        beerStyle = beerStyle,
                        price = BigDecimal.TEN,
                        upc = beerCSVRecord.row.toString(),
                        quantityOnHand = beerCSVRecord.count
                    )
                )
            }
        }
    }

    private fun loadBeerData() {
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
    }

    private fun loadCustomerData() {
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