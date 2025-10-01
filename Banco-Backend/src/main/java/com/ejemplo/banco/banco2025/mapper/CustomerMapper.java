package com.ejemplo.banco.banco2025.mapper;

import com.ejemplo.banco.banco2025.DTO.CustomerDTO;
import com.ejemplo.banco.banco2025.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel ="spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    CustomerDTO toDTO(Customer customer);
    Customer toEntity(CustomerDTO customerDTO);
}
