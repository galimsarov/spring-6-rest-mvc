package guru.springframework.spring6restmvc.repositories

import guru.springframework.spring6restmvc.entities.Beer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BeerRepository : JpaRepository<Beer, UUID> {
    override fun findAll(pageable: Pageable): Page<Beer>
}