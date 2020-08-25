<template>
	<view class="container">
		<picker @change="bindPickerChange" v-model="index" :range="array">
			<view class="picker">
				<view class="fb-type">
					<view class="type-label">{{array[index]}}</view>
					<image class="type-icon" src="http://yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/icon-normal/pickerArrow-a8b918f05f.png"></image>
				</view>
			</view>
		</picker>
		<view class="fb-body">
			<textarea class="content" placeholder="对我们网站、商品、服务，你还有什么建议吗？你还希望在严选上买到什么？请告诉我们..." @input="contentInput" :maxlength="500"
			 :auto-focus="true" v-model="content"></textarea>
			<view class="text-count">{{contentLength}}/500</view>
		</view>
		<view class="fb-mobile">
			<view class="label">手机号码</view>
			<view class="mobile-box">
				<input class="mobile" :maxlength="11" type="number" placeholder="方便我们与你联系" @input="mobileInput" v-model="mobile" />
			</view>
		</view>
		<view class="fb-btn" @tap="sbmitFeedback">提交</view>
	</view>
</template>

<script>
	const util = require("@/utils/util.js");
	const api = require('@/utils/api.js');
	export default {
		data() {
			return {
				array: ['请选择反馈类型', '商品相关', '物流状况', '客户服务', '优惠活动', '功能异常', '产品建议', '其他'],
				index: 0,
				content: '',
				contentLength: 0,
				mobile: ''
			}
		},
		methods: {
			bindPickerChange: function(e) {
				this.index = e.detail.value
			},
			mobileInput: function(e) {
				let that = this;
				this.mobile = e.detail.value
			},
			contentInput: function(e) {
				let that = this;
				that.contentLength = e.detail.cursor
				that.content = e.detail.value
			},
			sbmitFeedback: function(e) {
				let that = this;
				if (that.index == 0) {
					util.toast('请选择反馈类型');
					return false;
				}

				if (that.content == '') {
					util.toast('请输入反馈内容');
					return false;
				}

				if (that.mobile == '') {
					util.toast('请输入手机号码');
					return false;
				}

				util.request(api.FeedbackAdd, {
					mobile: that.mobile,
					index: that.index,
					content: that.content
				}, "POST", "application/json").then(function(res) {
					if (res.errno === 0) {
						uni.showToast({
							title: res.data,
							icon: 'success',
							duration: 2000,
							complete: function() {
								setTimeout(function() {
									uni.switchTab({
										url: '/pages/ucenter/index/index',
									});
								}, 2000)
							}
						});
					} else {
						util.toast(res.data);
					}
				});
			}
		}
	}
</script>

<style lang="scss">
	page {
		background: #f4f4f4;
		min-height: 100%;
	}

	.container {
		background: #f4f4f4;
		min-height: 100%;
		padding-top: 30rpx;
	}

	.fb-type {
		height: 104rpx;
		width: 100%;
		background: #fff;
		margin-bottom: 20rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
		padding-left: 30rpx;
		padding-right: 30rpx;
	}

	.fb-type .type-label {
		height: 36rpx;
		flex: 1;
		color: #333;
		font-size: 28rpx;
	}

	.fb-type .type-icon {
		height: 36rpx;
		width: 36rpx;
	}

	.fb-body {
		width: 100%;
		background: #fff;
		height: 374rpx;
		padding: 18rpx 30rpx 64rpx 30rpx;
	}

	.fb-body .content {
		width: 100%;
		height: 100%;
		color: #333;
		line-height: 40rpx;
		font-size: 28rpx;
	}

	.fb-body .text-count {
		padding-top: 17rpx;
		line-height: 30rpx;
		float: right;
		color: #666;
		font-size: 24rpx;
	}

	.fb-mobile {
		height: 162rpx;
		width: 100%;
	}

	.fb-mobile .label {
		height: 58rpx;
		width: 100%;
		padding-top: 14rpx;
		padding-bottom: 11rpx;
		color: #7f7f7f;
		font-size: 24rpx;
		padding-left: 30rpx;
		padding-right: 30rpx;
		line-height: 33rpx;
	}

	.fb-mobile .mobile-box {
		height: 104rpx;
		width: 100%;
		color: #333;
		padding-left: 30rpx;
		padding-right: 30rpx;
		font-size: 24rpx;
		background: #fff;
		position: relative;
	}

	.fb-mobile .mobile {
		position: absolute;
		top: 27rpx;
		left: 30rpx;
		height: 50rpx;
		width: 100%;
		color: #333;
		line-height: 50rpx;
		font-size: 24rpx;
	}

	.clear-icon {
		position: absolute;
		top: 43rpx;
		right: 30rpx;
		width: 28rpx;
		height: 28rpx;
	}

	.fb-btn {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 100%;
		height: 98rpx;
		line-height: 98rpx;
		background: #b4282d;
		position: fixed;
		bottom: 0;
		left: 0;
		border-radius: 0;
		color: #fff;
		font-size: 28rpx;
	}
</style>
