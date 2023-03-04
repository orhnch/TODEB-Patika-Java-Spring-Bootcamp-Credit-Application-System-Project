package com.todeb.patika.bootcamp.CreditApplicationSystem.controller;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.dto.payload.CustomerPayloadDTO;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Customer;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.mapper.CustomerMapper;
import com.todeb.patika.bootcamp.CreditApplicationSystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    private final CustomerMapper CUSTOMER_MAPPER = Mappers.getMapper(CustomerMapper.class);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity getAllCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();
        return ResponseEntity.ok(allCustomers);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity getCustomerById(@PathVariable @Min(1) Long id) {
        Customer customerById = customerService.getCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(customerById);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity createNewCustomer(@Valid @RequestBody CustomerPayloadDTO customer) {
        Customer respCustomer = customerService.create(CUSTOMER_MAPPER.toEntity(customer));
        if (respCustomer == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Customer could not be created successfully");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(respCustomer);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestParam(name = "id") @Min(1) Long id) {
        customerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Related Customer deleted successfully");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("update/{nationalNumberId}")
    public ResponseEntity updateCustomer(
            @PathVariable @Pattern(regexp = "[1-9][0-9]{9}[02468]") String nationalNumberId,
            @Valid @RequestBody CustomerPayloadDTO customer) {
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
    @GetMapping("/get/{nationalNumberId}")
    public ResponseEntity getCustomerByNationalNumberId(@PathVariable @Pattern(regexp = "[1-9][0-9]{9}[02468]") String nationalNumberId) {
        Customer customerByNationalNumberId = customerService.getCustomerByNationalNumberId(nationalNumberId);
        return ResponseEntity.status(HttpStatus.OK).body(customerByNationalNumberId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_CUSTOMER')")
    @GetMapping("/get/credits/{nationalNumberId}")
    public ResponseEntity getCreditsByNationalNumberId(@PathVariable @Pattern(regexp = "[1-9][0-9]{9}[02468]") String nationalNumberId) {
        List<Credit> credits = customerService.getCreditsByNationalNumberId(nationalNumberId);
        return ResponseEntity.status(HttpStatus.OK).body(credits);
    }
}
