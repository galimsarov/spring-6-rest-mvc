package guru.springframework.spring6restmvc.repositories

import guru.springframework.spring6restmvc.entities.Beer
import guru.springframework.spring6restmvc.model.BeerStyle
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BeerRepository : JpaRepository<Beer, UUID> {
    fun findAllByBeerNameIsLikeIgnoreCase(beerName: String, pageable: Pageable): Page<Beer>

    fun findAllByBeerStyle(beerStyle: BeerStyle, pageable: Pageable): Page<Beer>

    fun findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(
        beerName: String,
        beerStyle: BeerStyle,
        pageable: Pageable
    ): Page<Beer>

    override fun findAll(pageable: Pageable): Page<Beer>
}