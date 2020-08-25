<template>
	<view>
		<view class="userinfo">
			<image class="userinfo-avatar" :src="userInfo.avatarUrl" background-size="cover"></image>
			<text class="userinfo-nickname">{{userInfo.nickName}}</text>
		</view>
		<view class="separate"></view>
		<view>
			<view>
				<view class="login-title">关联手机号</view>
				<form @submit="bindLoginMobilecode">
					<form @submit="bindGetPassCode">
						<view class="login">
							<view class="first-line"><input type="digit" name="mobile" @input="bindInputMobile" placeholder="输入手机号" v-model="mobile"
								 auto-focus /></view>
							<view class="second-line">
								<input type="digit" name="code" placeholder="四位验证码" />
								<button @tap="countDownPassCode" :disabled="disableGetMobileCode">{{getCodeButtonText}}</button>
							</view>
						</view>
					</form>
					<view class="third-line"><button formType="submit" :disabled="disableSubmitMobileCode">提交</button></view>
				</form>
			</view>
		</view>
	</view>
</template>

<script>
	const util = require("@/utils/util.js");
	const api = require('@/utils/api.js');
	const app = getApp();
	export default {
		data() {
			return {
				mobile: '',
				userInfo: app.globalData.userInfo,
				disableGetMobileCode: false,
				disableSubmitMobileCode: true,
				getCodeButtonText: '获取验证码'
			}
		},
		methods: {
			bindCheckMobile: function(mobile) {
				if (!mobile) {
					util.toast('请输入手机号码')
					return false
				}
				if (!mobile.match(/^1[3-9][0-9]\d{8}$/)) {
					util.toast('手机号格式不正确，仅支持国内手机号码')
					return false
				}
				return true
			},

			bindGetPassCode: function(e) {
				this.disableGetMobileCode = true
			},

			bindInputMobile: function(e) {
				this.mobile = e.detail.value
			},

			countDownPassCode: function() {
				var that = this
				if (!that.bindCheckMobile(that.mobile)) {
					return
				}
				util.request(api.SmsCode, {
					phone: that.mobile
				}, 'POST', 'application/json').then(function(res) {
					if (res.errno == 0) {
						util.toast('发送成功')
						var pages = getCurrentPages()
						var i = 60;
						var intervalId = setInterval(function() {
							i--
							if (i <= 0) {
								pages[pages.length - 1].disableGetMobileCode = false
								pages[pages.length - 1].disableSubmitMobileCode = false
								pages[pages.length - 1].getCodeButtonText = '获取验证码'
								clearInterval(intervalId)
							} else {
								pages[pages.length - 1].getCodeButtonText = i
								pages[pages.length - 1].disableGetMobileCode = true
								pages[pages.length - 1].disableSubmitMobileCode = false
							}
						}, 1000);
					} else {
						util.toast('发送失败')
					}
				});

			},

			bindLoginMobilecode: function(e) {
				var mobile = this.mobile;
				if (!this.bindCheckMobile(mobile)) {
					return
				}
				if (!(e.detail.value.code && e.detail.value.code.length === 4)) {
					return
				}
				util.request(api.BindMobile, {
					mobile_code: e.detail.value.code,
					mobile: mobile
				}, "POST", "application/json").then(function(res) {
					if (res.errno === 0) {
						util.toast('操作成功')
						uni.switchTab({
							url: '/pages/ucenter/index/index'
						});
					} else {
						util.toast('验证码错误')
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	.userinfo {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding-bottom: 25rpx;
		background: linear-gradient(to bottom, #FCD33D 0%, #FFF6D7 100%);
	}

	.userinfo-avatar {
		width: 100rpx;
		height: 100rpx;
		border-radius: 50%;
		margin-top: 20rpx;
		margin-bottom: 25rpx;
	}

	.userinfo-nickname {
		color: #272727;
		font-size: 28rpx;
		line-height: 40rpx;
	}

	.separate {
		height: 18rpx;
		background-color: #f2f2f2;
	}

	.zichan {
		display: flex;
		flex-direction: column;
	}

	.zichan .first-line {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		font-size: 27rpx;
		margin-left: 20rpx;
		margin-top: 20rpx;
		margin-bottom: 20rpx;
	}

	.zichan .second-line {
		padding-top: 15rpx;
		padding-bottom: 15rpx;
		border-top: 1rpx solid #F6F6F6;
		border-bottom: 1rpx solid #F6F6F6;
		height: max-content;
	}

	.long-view {
		display: flex;
		flex-direction: row;
		width: 1850rpx;
	}

	.zichan .second-line .item {
		width: 560rpx;
		height: 264rpx;
		margin-left: 20rpx;
	}

	.zichan .second-line .item .image {
		height: 264rpx;
	}

	.zichan .second-line .desc {
		position: relative;
		top: -262rpx;
		left: 0;
		width: 560rpx;
		height: 264rpx;
		background-color: rgba(0, 0, 0, 0.3);
		border-radius: 5px;
		text-align: center;
		color: #fff;
		display: flex;
		flex-direction: column;
		justify-content: center;
		font-size: 25rpx;
	}

	.desc-line {
		margin-top: 5rpx;
		margin-bottom: 5rpx;
	}

	.desc-line.asset-count {
		font-size: 32rpx;
		color: #FFC800;
	}

	.slide-img {
		border-radius: 5px;
		width: 560rpx;
		height: 264rpx;
	}

	/*.zichan .third-line {
	  display: flex;
	  flex-direction: row;
	  justify-content: space-between;
	  font-size: 27rpx;
	  margin-left: 20rpx;
	  margin-top: 20rpx;
	  margin-bottom: 20rpx;
	}*/

	/* 绑定手机号的两个form */
	.login-title {
		margin: 20rpx 0 35rpx;
		text-align: center;
		font-size: 30rpx;
	}

	.login {
		font-size: 32rpx;
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.login .first-line {
		height: 80rpx;
		border: 1rpx solid rgb(217, 217, 217);
		border-radius: 5px;
		width: 600rpx;
		margin-bottom: 20rpx;
	}

	.login .first-line input {
		padding-left: 20rpx;
		height: 80rpx;
	}

	.login .second-line {
		height: 80rpx;
		display: flex;
		width: 600rpx;
		margin-bottom: 50rpx;
	}

	.login .second-line input {
		height: 80rpx;
		width: 350rpx;
		margin-right: 20rpx;
		border: 1rpx solid rgb(217, 217, 217);
		padding-left: 20rpx;
		border-radius: 5px;
	}

	.login .second-line button {
		text-align: center;
		height: 84rpx;
		line-height: 84rpx;
		width: 250rpx;
		font-size: 30rpx;
		background-color: #FFC800;
	}

	.login .password-second-line {
		height: 80rpx;
		display: flex;
		width: 600rpx;
		margin-bottom: 50rpx;
	}

	.login .password-second-line input {
		height: 80rpx;
		width: 600rpx;
		border: 1rpx solid rgb(217, 217, 217);
		padding-left: 20rpx;
		border-radius: 5px;
	}

	.third-line {
		margin-left: auto;
		margin-right: auto;
		width: 600rpx;
	}

	.third-line button {
		height: 80rpx;
		font-size: 32rpx;
		background-color: #FFC800;
	}

	.login-type {
		width: 600rpx;
		margin: 60rpx auto 0 auto;
		padding: 20rpx 0 20rpx;
		font-size: 32rpx;
		text-align: right;
		text-decoration: underline;
	}

	/*************/

	.profile-button-container {
		display: flex;
		flex-wrap: wrap;
		margin-top: 10rpx;
		margin-bottom: 10rpx;
	}

	.profile-button {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		width: 188rpx;
		height: 130rpx;
	}

	.profile-button image {
		width: 36rpx;
		height: 36rpx;
	}

	.profile-button text {
		font-size: 25rpx;
	}
</style>
