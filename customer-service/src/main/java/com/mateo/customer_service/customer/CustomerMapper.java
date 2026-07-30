package com.mateo.customer_service.customer;


import com.mateo.customer_service.dto.CustomerRequestDTO;
import com.mateo.customer_service.dto.CustomerResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setNombre(dto.nombre());
        customer.setApellido(dto.apellido());
        customer.setDocumento(dto.documento());
        customer.setDireccion(dto.direccion());
        customer.setEmail(dto.email());
        return customer;
    }

    public CustomerResponseDTO toResponseDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getNombre(),
                customer.getApellido(),
                customer.getDocumento(),
                customer.getDireccion(),
                customer.getEmail(),
                customer.getCreatedAt()
        );
    }
}
