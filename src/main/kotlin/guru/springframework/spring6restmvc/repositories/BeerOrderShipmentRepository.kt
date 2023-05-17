package guru.springframework.spring6restmvc.repositories

import guru.springframework.spring6restmvc.entities.BeerOrderShipment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BeerOrderShipmentRepository : JpaRepository<BeerOrderShipment, UUID>