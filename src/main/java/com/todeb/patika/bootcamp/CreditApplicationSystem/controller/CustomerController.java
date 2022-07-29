package com.todeb.patika.bootcamp.CreditApplicationSystem.controller;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.CustomerDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity getAllCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();
        return ResponseEntity.ok(allCustomers);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCustomerById(@PathVariable Long id) {
        Customer customerById = customerService.getCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(customerById);
    }

    @PostMapping("/create")
    public ResponseEntity createNewCustomer(@RequestBody CustomerDTO customer) {
        Customer respCustomer = customerService.create(customer);
        if (respCustomer == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Customer could not be created successfully");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(respCustomer);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestParam(name = "id") Long id) {
        customerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Related Customer deleted successfully");
    }

    @PutMapping("update/{phoneNumber}")
    public ResponseEntity updateCustomer(
            @PathVariable String phoneNumber,
            @RequestBody CustomerDTO customer) {
        Customer update = customerService.update(phoneNumber, customer);
        return ResponseEntity.status(HttpStatus.OK).body(update);
    }

    @DeleteMapping("delete/all")
    public ResponseEntity deleteAllCustomers() {
        customerService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("All customers were deleted successfully");
    }
}
