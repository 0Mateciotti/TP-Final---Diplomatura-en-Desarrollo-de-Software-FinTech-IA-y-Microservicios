package com.mateo.customer_service.customer;

import com.mateo.customer_service.dto.CustomerProfileDTO;
import com.mateo.customer_service.dto.CustomerRequestDTO;
import com.mateo.customer_service.dto.CustomerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping
    public List<CustomerResponseDTO> getCustomers() {
        return customerService.getCustomers();
    }
    @GetMapping("/{id}")
    public CustomerResponseDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
    @PostMapping
    public CustomerResponseDTO createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        return customerService.addCustomer(customerRequestDTO);
    }
    @PutMapping("/{id}")
    public CustomerResponseDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerRequestDTO customerRequestDTO) {
        return customerService.updateCustomer(id, customerRequestDTO);
    }
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
    }
    @GetMapping("/{id}/products")
    public CustomerProfileDTO getCustomerProfile(@PathVariable Long id) {
        return customerService.getCustomerProfile(id);
    }
}

