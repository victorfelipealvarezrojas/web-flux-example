package com.reactive.playground.sec03.mapper;

import com.reactive.playground.sec03.dto.CustomerDto;
import com.reactive.playground.sec03.entity.Customer;

public class EntityDtoMapper {
    public static Customer toEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.id());
        customer.setName(customerDto.name());
        customer.setEmail(customerDto.email());
        return customer;
    }

    public static CustomerDto toDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail());
    }
}
