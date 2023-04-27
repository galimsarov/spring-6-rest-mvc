package guru.springframework.spring6restmvc.services

import guru.springframework.spring6restmvc.controller.NotFoundException
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

    override fun getCustomerById(id: UUID): CustomerDTO = try {
        customerRepository.findById(id).get().toDto()
    } catch (_: NoSuchElementException) {
        throw NotFoundException()
    }

    override fun saveNewCustomer(customer: CustomerDTO): CustomerDTO {
        TODO("Not yet implemented")
    }

    override fun updateById(customerId: UUID, customer: CustomerDTO) {
        TODO("Not yet implemented")
    }

    override fun deleteById(customerId: UUID) {
        TODO("Not yet implemented")
    }

    override fun patchCustomerId(customerId: UUID, customer: CustomerDTO) {
        TODO("Not yet implemented")
    }
}