package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.model.CustomerDTO
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
@Suppress("unused")
class CustomerServiceImpl : CustomerService {
    private val customerMap: MutableMap<UUID, CustomerDTO> = mutableMapOf()

    init {
        val firstId = UUID.randomUUID()
        customerMap[firstId] = CustomerDTO(
            id = firstId,
            version = 1,
            customerName = "Alex Ferguson",
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )
        val secondId = UUID.randomUUID()
        customerMap[secondId] = CustomerDTO(
            id = secondId,
            version = 1,
            customerName = "John Lennon",
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now()
        )
    }

    override fun listCustomers(): List<CustomerDTO> = customerMap.values.toList()

    override fun getCustomerById(id: UUID): CustomerDTO {
        return customerMap[id] ?: CustomerDTO()
    }

    override fun saveNewCustomer(customer: CustomerDTO): CustomerDTO {
        val newCustomer = customer.copy()
        customerMap[newCustomer.id] = newCustomer
        return newCustomer
    }

    override fun updateById(customerId: UUID, customer: CustomerDTO): CustomerDTO {
        val customerFromMap: CustomerDTO? = customerMap[customerId]
        customerFromMap.let {
            customer.id = customerId
            customerMap[customerId] = customer
            return customer
        }
    }

    override fun deleteById(customerId: UUID): Boolean {
        customerMap.remove(customerId)
        return true
    }

    override fun patchCustomerId(customerId: UUID, customer: CustomerDTO): CustomerDTO {
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
                return this
            } else {
                return CustomerDTO()
            }
        }
    }
}