package guru.springframework.spring6restmvc.mappers

import guru.springframework.spring6restmvc.entities.Customer
import guru.springframework.spring6restmvc.model.CustomerDTO
import org.mapstruct.Mapper

@Mapper
interface CustomerMapper {
    fun customerDtoToCustomer(dto: CustomerDTO): Customer

    fun customerToCustomerDTO(customer: Customer): CustomerDTO
}