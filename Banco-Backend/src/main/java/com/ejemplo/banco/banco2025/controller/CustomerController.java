package com.ejemplo.banco.banco2025.controller;


import com.ejemplo.banco.banco2025.DTO.CustomerDTO;
import com.ejemplo.banco.banco2025.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //Obtener todos los clientes
    //Recusros HTTP --> URL
    //Metodos HTTP --> GET, POST, PUT, DELETE
    //Represntación del Recurso JSON / XML / Texto Plano
    //Cofigos de respuesta HTTP (200 OK), (201 CREATED), 404 (NOT FOUND)

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAll(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    //Obtener un cliente por un ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    //Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO){
        if(customerDTO.getBalance()==null){
            throw new IllegalArgumentException("El saldo no puede ser nulo");
        }
        return ResponseEntity.ok(customerService.createCustomer(customerDTO));
    }

    // Eliminar un cliente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
