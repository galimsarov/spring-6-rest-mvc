package guru.springframework.spring6restmvc.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class BeerControllerTest {
    @Autowired
    private lateinit var beerController: BeerController

    @Test
    fun getBeerById() {
        println(beerController.getBeerById(UUID.randomUUID()))
    }
}