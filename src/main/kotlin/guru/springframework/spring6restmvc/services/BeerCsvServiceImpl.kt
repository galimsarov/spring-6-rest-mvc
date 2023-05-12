package guru.springframework.spring6restmvc.services

import com.opencsv.bean.CsvToBeanBuilder
import guru.springframework.spring6restmvc.model.BeerCSVRecord
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileReader

@Service
class BeerCsvServiceImpl : BeerCsvService {
    override fun convertCSV(csvFile: File): List<BeerCSVRecord> {
        return CsvToBeanBuilder<BeerCSVRecord>(FileReader(csvFile)).withType(BeerCSVRecord::class.java).build().parse()
    }
}