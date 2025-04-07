package com.yang.mybatisplusjoin.demo.domain.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String user;
    private Integer age;
    private String email;
    private String city;
    private String address;
}