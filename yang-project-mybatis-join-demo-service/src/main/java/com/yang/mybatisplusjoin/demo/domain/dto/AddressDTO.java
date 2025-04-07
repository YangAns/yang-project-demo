package com.yang.mybatisplusjoin.demo.domain.dto;

import com.yang.mybatisplusjoin.demo.domain.User;
import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    private Long userId;
    private String city;
    private String address;
    private User user;    //用户信息作为一个对象返回
}