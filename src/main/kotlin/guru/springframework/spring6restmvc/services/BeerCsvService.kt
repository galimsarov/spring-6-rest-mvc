package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.model.BeerCSVRecord
import java.io.File

interface BeerCsvService {
    fun convertCSV(csvFile: File): List<BeerCSVRecord>
}