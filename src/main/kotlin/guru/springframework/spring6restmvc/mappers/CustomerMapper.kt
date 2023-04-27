package guru.springframework.spring6restmvc.mappers

import guru.springframework.spring6restmvc.entities.Customer
import guru.springframework.spring6restmvc.model.CustomerDTO

fun Customer.toDto(): CustomerDTO = CustomerDTO(
    id = id,
    version = version,
    customerName = customerName,
    createdDate = createdDate,
    lastModifiedDate = lastModifiedDate
)

fun CustomerDTO.toCustomer(): Customer = Customer(
    id = id,
    version = version,
    customerName = customerName,
    createdDate = createdDate,
    lastModifiedDate = lastModifiedDate
)