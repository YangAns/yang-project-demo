package com.yang.mybatisplusjoin.demo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private Long id;
    private Long userId;
    private String city;
    private String address;
}