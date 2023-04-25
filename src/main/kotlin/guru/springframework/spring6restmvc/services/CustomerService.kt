package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.model.Customer
import java.util.*

interface CustomerService {
    fun listCustomers(): List<Customer>

    fun getCustomerById(id: UUID): Customer
    fun saveNewCustomer(customer: Customer): Customer

    fun updateById(customerId: UUID, customer: Customer)

    fun deleteById(customerId: UUID)

    fun patchCustomerId(customerId: UUID, customer: Customer)
}