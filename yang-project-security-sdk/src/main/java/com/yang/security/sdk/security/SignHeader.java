package com.yang.security.sdk.security;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/12/3
 */
@Data
public class SignHeader {
    // 用户ID
    private String secretId;

    // 随机数
    private Integer nonce;

    // 时间戳
    private Long timestamp;

    // 签名
    private String signature;


}
