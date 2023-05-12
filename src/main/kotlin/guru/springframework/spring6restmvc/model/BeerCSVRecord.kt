package guru.springframework.spring6restmvc.model

import com.opencsv.bean.CsvBindByName

data class BeerCSVRecord(
    @CsvBindByName
    var row: Int = 0,

    @CsvBindByName(column = "count.x")
    var count: Int = 0,

    @CsvBindByName
    var abv: String = "",

    @CsvBindByName
    var ibu: String = "",

    @CsvBindByName
    var id: Int = 0,

    @CsvBindByName
    var beer: String = "",

    @CsvBindByName
    var style: String = "",

    @CsvBindByName(column = "brewery_id")
    var breweryId: Int = 0,

    @CsvBindByName
    var ounces: Float = 0.0F,

    @CsvBindByName
    var style2: String = "",

    @CsvBindByName(column = "count.y")
    var countY: String = "",

    @CsvBindByName
    var city: String = "",

    @CsvBindByName
    var state: String = "",

    @CsvBindByName
    var label: String = "",
)