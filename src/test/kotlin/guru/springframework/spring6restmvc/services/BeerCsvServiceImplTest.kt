package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.model.BeerCSVRecord
import org.junit.jupiter.api.Test
import org.springframework.util.ResourceUtils
import java.io.File

class BeerCsvServiceImplTest {
    private val beerCsvService = BeerCsvServiceImpl()

    @Test
    fun convertCsv() {
        val file: File = ResourceUtils.getFile("classpath:csvdata/beers.csv")
        val recs: List<BeerCSVRecord> = beerCsvService.convertCSV(file)
        println(recs.size)
        assert(recs.isNotEmpty())
    }
}