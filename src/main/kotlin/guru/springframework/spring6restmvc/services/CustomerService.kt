package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.model.CustomerDTO
import java.util.*

interface CustomerService {
    fun listCustomers(): List<CustomerDTO>

    fun getCustomerById(id: UUID): CustomerDTO

    fun saveNewCustomer(customer: CustomerDTO): CustomerDTO

    fun updateById(customerId: UUID, customer: CustomerDTO): CustomerDTO

    fun deleteById(customerId: UUID): Boolean

    fun patchCustomerId(customerId: UUID, customer: CustomerDTO)
}