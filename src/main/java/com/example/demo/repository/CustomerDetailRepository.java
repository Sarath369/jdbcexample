package com.example.demo.repository;

import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDetailRepository extends JpaRepository<CustomerDetail, Long> {

    Optional<CustomerDetail> findByCdId(Long cdId);
    Optional<CustomerDetail> findByCustId(Customer custId);
}
