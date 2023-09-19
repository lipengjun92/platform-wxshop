package com.platform.util.wechat;

import lombok.Data;

@Data
public class WechatRefundApiResult {
	private String return_code;
	private String return_msg;

	private String result_code;
	private String err_code;
	private String err_code_des;
	private String appid;
	private String mch_id;
	private String device_info;
	private String nonce_str;
	private String sign;
	private String transaction_id;
	private String out_trade_no;
	private String out_refund_no;
	private String refund_id;
	private String refund_channel;
	private String refund_fee;
	private String settlement_refund_fee;
	private String total_fee;
	private String settlement_total_fee;
	private String fee_type;
	private String cash_fee;
	private String cash_refund_fee;
	private String refund_status;
}
