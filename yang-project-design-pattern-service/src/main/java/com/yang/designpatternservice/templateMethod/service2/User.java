package com.yang.designpatternservice.templateMethod.service2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "ID不能为空")
    private String id;

    @NotBlank(message = "用户名不能为空", groups = RETIRE_TX.class)
    private String name;

    @NotBlank(message = "密码不能为空", groups = {RETIRE_TX.class,RETIRE_LX.class})
    private String password;

    @NotNull
    private Long birth;

    private String type;

    public User(String id, String name, String password, Long birth, String type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.birth = birth;
    }
}
