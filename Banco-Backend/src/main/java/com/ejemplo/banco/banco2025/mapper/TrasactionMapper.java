package com.ejemplo.banco.banco2025.mapper;

import com.ejemplo.banco.banco2025.DTO.TransactionDTO;
import com.ejemplo.banco.banco2025.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrasactionMapper {
    TrasactionMapper INSTANCE = Mappers.getMapper(TrasactionMapper.class);
    TransactionDTO toDTO(Transaction transaction);

}
