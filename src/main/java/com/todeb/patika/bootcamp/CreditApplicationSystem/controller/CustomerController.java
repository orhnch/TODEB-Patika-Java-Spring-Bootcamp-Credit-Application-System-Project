package com.todeb.patika.bootcamp.CreditApplicationSystem.controller;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.CustomerDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity getAllCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();
        return ResponseEntity.ok(allCustomers);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity getCustomerById(@PathVariable Long id) {
        Customer customerById = customerService.getCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(customerById);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity createNewCustomer(@RequestBody CustomerDTO customer) {
        Customer respCustomer = customerService.create(customer);
        if (respCustomer == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Customer could not be created successfully");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(respCustomer);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestParam(name = "id") Long id) {
        customerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Related Customer deleted successfully");
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("update/{nationalNumberId}")
    public ResponseEntity updateCustomer(
            @PathVariable String nationalNumberId,
            @RequestBody CustomerDTO customer) {
        Customer update = customerService.update(nationalNumberId, customer);
        return ResponseEntity.status(HttpStatus.OK).body(update);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("delete/all")
    public ResponseEntity deleteAllCustomers() {
        customerService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("All customers were deleted successfully");
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("do/{id}")
    public ResponseEntity doApplication(@PathVariable Long id) {
        customerService.doApplication(id);
        return ResponseEntity.status(HttpStatus.OK).body("Credit application was made successfully!");

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("sms/{id}")
    public ResponseEntity sendSMS(@PathVariable Long id) {
        String string = customerService.sendSMS(id);
        return ResponseEntity.status(HttpStatus.OK).body(string);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/{nationalNumberId}")
    public ResponseEntity getCustomerByNationalNumberId(@PathVariable String nationalNumberId) {
        Customer customerByNationalNumberId = customerService.getCustomerByNationalNumberId(nationalNumberId);
        return ResponseEntity.status(HttpStatus.OK).body(customerByNationalNumberId);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_CLIENT')")
    @GetMapping("/get/credits/{nationalNumberId}")
    public ResponseEntity getCreditsByNationalNumberId(@PathVariable String nationalNumberId) {
        List<Credit> credits = customerService.getCreditsByNationalNumberId(nationalNumberId);
        return ResponseEntity.status(HttpStatus.OK).body(credits);
    }
}
