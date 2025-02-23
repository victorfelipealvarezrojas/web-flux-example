package com.reactive.playground.sec03.service;

import com.reactive.playground.sec03.dto.CustomerDto;
import com.reactive.playground.sec03.mapper.EntityDtoMapper;
import com.reactive.playground.sec03.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Flux<CustomerDto> getAllCustomer() {
        return this.customerRepository.findAll()
                .map(EntityDtoMapper::toDto);
    }

    public Flux<CustomerDto> getAllCustomer(int page, int size) {
        return this.customerRepository.findBy(Pageable.ofSize(size).withPage(page))
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> getCustomerById(Integer id) {
        return this.customerRepository.findById(id)
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> createCustomer(Mono<CustomerDto> customerDtoMono) {
        return customerDtoMono
                .map(EntityDtoMapper::toEntity)
                .flatMap(this.customerRepository::save) // save returns Mono<Customer>
                .map(EntityDtoMapper::toDto);
    }

    public Mono<CustomerDto> updateCustomer(Integer id, Mono<CustomerDto> customerDtoMono) {
        return this.customerRepository.findById(id)
                .flatMap(entity -> customerDtoMono) // Si encuentra el cliente, DESCARTA la entidad encontrada y usa el nuevo cliente
                .map(EntityDtoMapper::toEntity)
                .doOnNext(e -> e.setId(id))
                .flatMap(this.customerRepository::save)
                .map(EntityDtoMapper::toDto);
    }

    public Mono<Boolean> deleteCustomer(Integer id) {
        return this.customerRepository.deleteCustomerById(id);
    }
}
