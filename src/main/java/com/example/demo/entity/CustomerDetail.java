package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Data
@Entity
public class CustomerDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdId;

    @OneToOne
    @JoinColumn(name = "cust_Id")
    private Customer custId;
    private String cityName;
    private String townName;
    private String address1;
    private String address2;
    private int pincode;
}

