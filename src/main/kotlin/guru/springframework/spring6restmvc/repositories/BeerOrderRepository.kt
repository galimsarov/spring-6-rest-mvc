package guru.springframework.spring6restmvc.repositories

import guru.springframework.spring6restmvc.entities.BeerOrder
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BeerOrderRepository : JpaRepository<BeerOrder, UUID>