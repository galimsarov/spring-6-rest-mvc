package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.controller.NotFoundException
import guru.springframework.spring6restmvc.mappers.toCustomer
import guru.springframework.spring6restmvc.mappers.toDto
import guru.springframework.spring6restmvc.model.CustomerDTO
import guru.springframework.spring6restmvc.repositories.CustomerRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.util.*

@Primary
@Service
@Suppress("unused")
class CustomerServiceJPA(private val customerRepository: CustomerRepository) : CustomerService {
    override fun listCustomers(): List<CustomerDTO> = customerRepository.findAll().map { it.toDto() }

    override fun getCustomerById(id: UUID): CustomerDTO = customerRepository.findById(id).get().toDto()

    override fun saveNewCustomer(customer: CustomerDTO): CustomerDTO =
        customerRepository.save(customer.toCustomer()).toDto()

    override fun updateById(customerId: UUID, customer: CustomerDTO): CustomerDTO {
        var result = CustomerDTO()
        customerRepository.findById(customerId).ifPresent { foundCustomer ->
            foundCustomer.apply { customerName = customer.customerName }
            customerRepository.save(foundCustomer)
            result = foundCustomer.toDto()
        }
        if (result.customerName.isNotBlank()) return result
        else throw NotFoundException()
    }

    override fun deleteById(customerId: UUID): Boolean {
        return if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId)
            true
        } else {
            false
        }
    }

    override fun patchCustomerId(customerId: UUID, customer: CustomerDTO): CustomerDTO {
        var result = CustomerDTO()
        customerRepository.findById(customerId).ifPresent { foundCustomer ->
            foundCustomer.apply {
                if (customer.customerName.isNotBlank()) customerName = customer.customerName
            }
            customerRepository.save(foundCustomer)
            result = foundCustomer.toDto()
        }
        if (result.customerName.isNotBlank()) return result
        else throw NotFoundException()
    }
}