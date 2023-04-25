package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.model.Customer
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
@Suppress("unused")
class CustomerServiceImpl : CustomerService {
    private val customerMap: MutableMap<UUID, Customer> = mutableMapOf()

    init {
        val firstId = UUID.randomUUID()
        customerMap[firstId] = Customer(
            id = firstId,
            version = 1,
            customerName = "Alex Ferguson",
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )
        val secondId = UUID.randomUUID()
        customerMap[secondId] = Customer(
            id = secondId,
            version = 1,
            customerName = "John Lennon",
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )
    }

    override fun listCustomers(): List<Customer> = customerMap.values.toList()

    override fun getCustomerById(id: UUID): Customer {
        return customerMap[id] ?: Customer()
    }

    override fun saveNewCustomer(customer: Customer): Customer {
        val newCustomer = customer.copy()
        customerMap[newCustomer.id] = newCustomer
        return newCustomer
    }

    override fun updateById(customerId: UUID, customer: Customer) {
        val customerFromMap: Customer? = customerMap[customerId]
        customerFromMap.let {
            customer.id = customerId
            customerMap[customerId] = customer
        }
    }

    override fun deleteById(customerId: UUID) {
        customerMap.remove(customerId)
    }

    override fun patchCustomerId(customerId: UUID, customer: Customer) {
        customerMap[customerId].apply {
            if (this != null) {
                var customerChanged = false
                if (customer.version != 0) {
                    version = customer.version
                    customerChanged = true
                }
                if (customer.customerName.isNotBlank()) {
                    customerName = customer.customerName
                    customerChanged = true
                }
                if (customerChanged) {
                    lastModifiedDate = LocalDateTime.now()
                }
            }
        }
    }
}