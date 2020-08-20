<!-- 昵称（邮箱或者个人主页） -->
<template>
	<view class="container">
		<view class="pay-result">
			<view class="success" v-if="status == true">
				<view class="msg">付款成功</view>
				<view class="btns">
					<navigator class="btn" url="/pages/ucenter/order/order" open-type="redirect">查看订单</navigator>
					<navigator class="btn" url="/pages/index/index" open-type="switchTab">继续逛</navigator>
				</view>
			</view>
			<view class="error" v-if="status != true">
				<view class="msg">付款失败</view>
				<view class="tips">
					<view class="p">请在
						<text class="time">1小时</text>
						内完成付款
					</view>
					<view class="p">否则订单将会被系统取消</view>
				</view>
				<view class="btns">
					<navigator class="btn" url="/pages/ucenter/order/order" open-type="redirect">查看订单</navigator>
					<view class="btn" @tap='payOrder'>重新付款</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	const util = require("@/utils/util.js");
	const api = require('@/utils/api.js');
	export default {
		data() {
			return {
				status: false,
				orderId: 0
			}
		},
		methods: {
			updateSuccess: function() {
				let that = this
				util.request(api.OrderQuery, {
					orderId: that.orderId
				}).then(function(res) {})
			},
			payOrder() {
				util.payOrder(parseInt(this.orderId)).then(res => {
					that.status = true
				}).catch(res => {
					util.toast('支付失败');
				});
			}
		},
		onLoad: function(options) {
			// 页面初始化 options为页面跳转所带来的参数
			this.orderId = options.orderId || 24
			this.status = options.status
		}
	}
</script>

<style lang="scss">
	page {
		min-height: 100%;
		width: 100%;
		background: #fff;
	}

	.container {
		height: 100%;
		background: #fff;
	}

	.pay-result {
		background: #fff;
	}

	.pay-result .msg {
		text-align: center;
		margin: 100rpx auto;
		color: #2bab25;
		font-size: 36rpx;
	}

	.pay-result .btns {
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.pay-result .btn {
		text-align: center;
		height: 80rpx;
		margin: 0 20rpx;
		width: 200rpx;
		line-height: 78rpx;
		border: 1px solid #868686;
		color: #000000;
		border-radius: 5rpx;
	}

	.pay-result .error .msg {
		color: #b4282d;
		margin-bottom: 60rpx;
	}

	.pay-result .error .tips {
		color: #7f7f7f;
		margin-bottom: 70rpx;
	}

	.pay-result .error .tips .p {
		font-size: 24rpx;
		line-height: 42rpx;
		text-align: center;
	}

	.pay-result .error .tips .p {
		line-height: 42rpx;
		text-align: center;
	}
</style>
