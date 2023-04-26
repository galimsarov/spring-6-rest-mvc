package guru.springframework.spring6restmvc.controller

import guru.springframework.spring6restmvc.model.Customer
import guru.springframework.spring6restmvc.services.CustomerService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

const val CUSTOMER_PATH = "/api/v1/customer"
const val CUSTOMER_PATH_ID = "$CUSTOMER_PATH/{customerId}"

@RestController
@Suppress("unused")
class CustomerController(private val customerService: CustomerService) {
    @GetMapping(CUSTOMER_PATH)
    fun listCustomers(): List<Customer> = customerService.listCustomers()

    @GetMapping(CUSTOMER_PATH_ID)
    fun getCustomerById(@PathVariable("customerId") customerId: UUID): Customer =
        customerService.getCustomerById(customerId)

    @PostMapping(CUSTOMER_PATH)
    fun handlePost(@RequestBody customer: Customer): ResponseEntity<Customer> {
        val savedCustomer: Customer = customerService.saveNewCustomer(customer)
        val headers = HttpHeaders()
        headers.add("Location", "/api/v1/customer/${savedCustomer.id}")
        return ResponseEntity(headers, HttpStatus.CREATED)
    }

    @PutMapping(CUSTOMER_PATH_ID)
    fun updateById(
        @PathVariable("customerId") customerId: UUID,
        @RequestBody customer: Customer
    ): ResponseEntity<Customer> {
        customerService.updateById(customerId, customer)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    fun deleteById(@PathVariable("customerId") customerId: UUID): ResponseEntity<Customer> {
        customerService.deleteById(customerId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    fun updateCustomerPatchById(
        @PathVariable("customerId") customerId: UUID,
        @RequestBody customer: Customer
    ): ResponseEntity<Customer> {
        customerService.patchCustomerId(customerId, customer)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}