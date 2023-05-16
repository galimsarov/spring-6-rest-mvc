package guru.springframework.spring6restmvc.repositories

import guru.springframework.spring6restmvc.entities.Beer
import guru.springframework.spring6restmvc.entities.Category
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var beerRepository: BeerRepository

    private lateinit var testBeer: Beer

    @BeforeEach
    fun setUp() {
        testBeer = beerRepository.findAll()[0]
    }

    @Test
    @Transactional
    fun testAddCategory() {
        val savedCat: Category = categoryRepository.save(Category(description = "Ales"))

        testBeer.addCategory(savedCat)
        val savedBeer: Beer = beerRepository.save(testBeer)

        println(savedBeer.beerName)
    }
}