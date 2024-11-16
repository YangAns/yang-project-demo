package com.yang.common.webresult;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Resp {

	R_200(200, "操作成功"),
	R_260(260, "请输入提取码"),
	R_210(210, "检测到敏感词"),
	R_220(220, "数据正在导入中"),
	R_400(400, "参数异常"),
	R_999(999, "正版验证异常"),
	R_302(302, "登录认证异常"),
	R_403(403, "权限认证异常"),
	R_404(404, "无法找到接口"),
	R_405(405, "不支持的访问方式"),
	R_500(500, "系统异常");

	private final Integer code;

	private final String message;
}
