package com.yang.mybatisplusjoin.demo.domain.dto;

import com.yang.mybatisplusjoin.demo.domain.Address;
import lombok.Data;

import java.util.List;

@Data
public class UserAddressDTO {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private List<Address> address;


}