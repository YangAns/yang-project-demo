
package com.yang.common.webresult;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;

@SuppressWarnings("unused")
@Data
@NoArgsConstructor
public final class JsonResult implements Serializable {

    private static final long serialVersionUID = 1L;
    // 返回code
    private Integer code;
    // 返回消息
    private String message;
    // 正常返回结果
    private Object data;
    // 扩展返回结果
    private Object extData;
    // 分页时的total
    private Long total = 0L;


    public static JsonResult fail(String message) {
        return new JsonResult(Resp.R_500, message);
    }

    public static JsonResult fail_302(String message) {
        return new JsonResult(Resp.R_302, message);
    }

    public static JsonResult fail_400(String message) {
        return new JsonResult(Resp.R_400, message);
    }
    public static JsonResult fail_400() {
        return new JsonResult(Resp.R_400);
    }

    public static JsonResult emptyList() {
        return new JsonResult(Resp.R_200, Collections.emptyList());
    }

    public static JsonResult emptyModel() {
        return new JsonResult(Resp.R_200, Collections.emptyMap());
    }

    public static JsonResult success() {
        return new JsonResult(Resp.R_200, Resp.R_200.getMessage());
    }

    public static JsonResult success(Object data) {
        return new JsonResult(Resp.R_200, data);
    }

    public static JsonResult success(Object data, Long total) {
        return new JsonResult(Resp.R_200, data, null, total);
    }

    public static JsonResult success(IPage<?> page) {
        if (null == page) {
            return new JsonResult(Resp.R_200, Collections.emptyList(), 0);
        }
        return new JsonResult(Resp.R_200, page.getRecords(), page.getTotal());
    }

    public JsonResult extData(Object extData) {
        this.extData = extData;
        return this;
    }

    public JsonResult message(String message) {
        this.message = message;
        return this;
    }


    public JsonResult(Resp resp, Object data) {
        this(resp, data, null, 0);
    }

    public JsonResult(Resp resp, Object data, long total) {
        this(resp, data, null, total);
    }

    public JsonResult(Resp resp, Object extData, Object data) {
        this(resp, data, extData, 0);
    }


    public JsonResult(Resp resp, Object data, Object extData, long total) {
        this(resp.getCode(), resp.getMessage(), data, extData, total);
    }

    public JsonResult(Resp resp, String message) {
        this(resp.getCode(), message, null, null, 0);
    }

    public JsonResult(Resp resp) {
        this(resp.getCode(), resp.getMessage(), null, null, 0);
    }

    public JsonResult(Integer code, String message, Object data, Object extData, long total) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.extData = extData;
        this.total = total;
    }
}
