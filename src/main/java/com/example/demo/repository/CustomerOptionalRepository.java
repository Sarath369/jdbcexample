package com.example.demo.repository;

import com.example.demo.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerOptionalRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findById(Long id);
    Page<Customer> findById(Long id, Pageable pageable);
        @Query(value = "SELECT * FROM `customer`"
           + "  WHERE name LIKE  %?1% ", nativeQuery = true)
    Page<Customer> findBySearch(String search, Pageable pageable);
    Page<Customer> findAll(Pageable pageable);
    Optional<Customer> findByEmail(String email);
}
