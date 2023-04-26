package guru.springframework.spring6restmvc.repositories

import guru.springframework.spring6restmvc.entities.Customer
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CustomerRepository : JpaRepository<Customer, UUID>