package com.mateo.customer_service.customer;

import com.mateo.customer_service.client.ProductClient;
import com.mateo.customer_service.dto.CuentaResponseDTO;
import com.mateo.customer_service.dto.CustomerProfileDTO;
import com.mateo.customer_service.dto.CustomerRequestDTO;
import com.mateo.customer_service.dto.CustomerResponseDTO;
import com.mateo.customer_service.dto.PrestamoResponseDTO;
import com.mateo.customer_service.dto.TarjetaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ProductClient productClient;

    public List<CustomerResponseDTO> getCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toResponseDTO)
                .toList();
    }

    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente no encontrado con id: " + id));
        return customerMapper.toResponseDTO(customer);
    }

    public CustomerResponseDTO addCustomer(CustomerRequestDTO dto) {
        Customer customer = customerMapper.toEntity(dto);
        return customerMapper.toResponseDTO(customerRepository.save(customer));
    }

    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente no encontrado con id: " + id));
        customer.setNombre(dto.nombre());
        customer.setApellido(dto.apellido());
        customer.setDocumento(dto.documento());
        customer.setDireccion(dto.direccion());
        customer.setEmail(dto.email());
        return customerMapper.toResponseDTO(customerRepository.save(customer));
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    public CustomerProfileDTO getCustomerProfile(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente no encontrado con id: " + id));
        List<CuentaResponseDTO> cuentas = productClient.getCuentasByClienteId(id);
        List<PrestamoResponseDTO> prestamos = productClient.getPrestamosByClienteId(id);
        List<TarjetaResponseDTO> tarjetas = productClient.getTarjetasByClienteId(id);
        return new CustomerProfileDTO(customerMapper.toResponseDTO(customer), cuentas, prestamos, tarjetas);
    }
}
