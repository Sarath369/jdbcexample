package com.example.demo.repository;

import com.example.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email);
    //Customer findById(Long id);
//    @Query(value = "SELECT * FROM `customer`"
//            + "  WHERE name equals  %?1% ", nativeQuery = true)
//    CustomerResponceDto findById(Long id);
}
