package com.ejemplo.banco.banco2025.service;


import com.ejemplo.banco.banco2025.DTO.CustomerDTO;
import com.ejemplo.banco.banco2025.entity.Customer;
import com.ejemplo.banco.banco2025.mapper.CustomerMapper;
import com.ejemplo.banco.banco2025.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    //Obtener la informacion de todos los clientes
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toDTO).toList();
    }

    //Obtener el dato de un cliente por un ID
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id).map(customerMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    //Crear nuevo cliente
    public CustomerDTO createCustomer(CustomerDTO customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        return customerMapper.toDTO(customerRepository.save(customer));
    }

    // Eliminar un cliente por ID
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }
        customerRepository.deleteById(id);
    }
}
