package com.aibabel.fyt_play.js;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */

public class JsClickInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String category;// 类型
	private String orderId;// 订单Id
	private String isSuccess;//是否成功


	private boolean isPay_success;//支付成功接口
	private boolean isPay_fail;//支付成功接口

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public boolean isPay_success() {
		return isPay_success;
	}
	public boolean isPay_fail() {
		return isPay_fail;
	}

	public void setPay_success(boolean pay_success) {
		isPay_success = pay_success;
	}
	public void setPay_fail(boolean pay_fail) {
		isPay_fail = pay_fail;
	}


	public void init() {
		this.isPay_success = eq(this.category, "pay_success");//
		this.isPay_fail = eq(this.category,"pay_fail");
	}

	private boolean eq(String eq, String eq0) {
		if (eq != null && eq0 != null) {
			return eq.equals(eq0);
		}
		return false;
	}

}
