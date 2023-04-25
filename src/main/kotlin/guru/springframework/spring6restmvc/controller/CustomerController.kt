package guru.springframework.spring6restmvc.controller

import guru.springframework.spring6restmvc.model.Customer
import guru.springframework.spring6restmvc.services.CustomerService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/customer")
@Suppress("unused")
class CustomerController(private val customerService: CustomerService) {
    @RequestMapping(method = [RequestMethod.GET])
    fun listCustomers(): List<Customer> = customerService.listCustomers()

    @RequestMapping(value = ["/{customerId}"], method = [RequestMethod.GET])
    fun getCustomerById(@PathVariable("customerId") customerId: UUID): Customer =
        customerService.getCustomerById(customerId)

    @PostMapping
    fun handlePost(@RequestBody customer: Customer): ResponseEntity<Customer> {
        val savedCustomer: Customer = customerService.saveNewCustomer(customer)
        val headers = HttpHeaders()
        headers.add("Location", "/api/v1/customer/${savedCustomer.id}")
        return ResponseEntity(headers, HttpStatus.CREATED)
    }

    @PutMapping("/{customerId}")
    fun updateById(
        @PathVariable("customerId") customerId: UUID,
        @RequestBody customer: Customer
    ): ResponseEntity<Customer> {
        customerService.updateById(customerId, customer)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @DeleteMapping("/{customerId}")
    fun deleteById(@PathVariable("customerId") customerId: UUID): ResponseEntity<Customer> {
        customerService.deleteById(customerId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PatchMapping("/{customerId}")
    fun updateCustomerPatchById(
        @PathVariable("customerId") customerId: UUID,
        @RequestBody customer: Customer
    ): ResponseEntity<Customer> {
        customerService.patchCustomerId(customerId, customer)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}