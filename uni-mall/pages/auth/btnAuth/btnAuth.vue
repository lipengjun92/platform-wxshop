<!-- 昵称（邮箱或者个人主页） -->
<template>
	<view class="page">
		<view class="hd">
			<image class="logo" src="/static/images/logo.png"></image>
			<view class="title">安徽微同科技有限公司欢迎您!</view>
		</view>
		<view class="bd">
			<view class="top_line"></view>
			<text class="m_name">安徽微同科技有限公司申请获得以下权限</text>
			<text class="s_name">· 获得你的公开信息（昵称、头像等）</text>
		</view>
		<view class="btn spacing">
			<!-- 需要使用 button 来授权登录 -->
			<button class="weui_btn weui_btn_primary" v-if="canIUseGetUserProfile" @tap="getUserProfile"> 微信登录 </button>
			<button class="weui_btn weui_btn_primary" v-else open-type="getUserInfo" @getuserinfo="bindGetUserInfo">
				微信登录
			</button>
		</view>
	</view>
</template>

<script>
	const util = require("@/utils/util.js")
	const api = require('@/utils/api.js');
	export default {
		data() {
			return {
				canIUseGetUserProfile: false,
				navUrl: '',
				code: ''
			}
		},
		methods: {
			getUserProfile() {
				let that = this;
				// 推荐使用uni.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认
				// 开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
				uni.getUserProfile({
					desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
					success: (resp) => {
						//登录远程服务器
						if (that.code) {
							//登录远程服务器
							that.loginByWeixin(resp)
						} else {
							uni.login({
								success: function(resp) {
									if (resp.code) {
										that.code = resp.code
										that.loginByWeixin(resp)
									}
								}
							});
						}
					}
				})
			},
			bindGetUserInfo: function(e) {
				//登录远程服务器
				this.loginByWeixin(e.detail)
			},
			loginByWeixin: function(userInfo) {
				let that = this;
				//登录远程服务器
				if (that.code) {
					util.request(api.AuthLoginByWeixin, {
						code: that.code,
						userInfo: userInfo
					}, 'POST', 'application/json').then(res => {
						if (res.errno === 0) {
							//存储用户信息
							uni.setStorageSync('userInfo', res.data.userInfo);
							uni.setStorageSync('X-Nideshop-Token', res.data.token);
							uni.setStorageSync('userId', res.data.userId);

						} else {
							// util.showErrorToast(res.errmsg)
							uni.showModal({
								title: '提示',
								content: res.errmsg,
								showCancel: false
							});
						}
					});
				}
				if (that.navUrl && that.navUrl == '/pages/index/index') {
					uni.switchTab({
						url: that.navUrl,
					})
				} else if (that.navUrl) {
					uni.redirectTo({
						url: that.navUrl,
					})
				}
			}
		},
		onLoad: function(options) {
			let that = this;
			if (uni.getStorageSync("navUrl")) {
				that.navUrl = uni.getStorageSync("navUrl")
			} else {
				that.navUrl = '/pages/index/index'
			}
			if (uni.getUserProfile) {
				that.canIUseGetUserProfile = true
			}
			uni.login({
				success: function(res) {
					if (res.code) {
						that.code = res.code
					}
				}
			});
		}
	}
</script>

<style lang="scss">
	view,
	text {
		font-family: PingFangSC-Light, helvetica, 'Heiti SC';
		font-size: 29rpx;
		color: #333;
	}

	.hd {
		display: flex;
		width: 100%;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.hd .logo {
		width: 260rpx;
		height: 260rpx;
		margin-top: 40rpx;
	}

	.hd .title {
		text-align: center;
		font-size: 36rpx;
		color: #000;
	}

	.bd {
		width: 100%;
		padding: 50rpx;
	}

	.bd .top_line {
		width: 100%;
		height: 1rpx;
		background: #ccc;
		margin-top: 20rpx;
		margin-bottom: 50rpx;
	}

	.bd .m_name {
		display: block;
		font-size: 36rpx;
		color: #000;
	}

	.bd .s_name {
		margin-top: 25rpx;
		display: block;
		font-size: 34rpx;
		color: #8a8a8a;
	}

	.btn {
		padding: 120rpx 50rpx 0;
	}

	.weui_btn_primary {
		background-color: #04be02;
	}

	.weui_btn {
		position: relative;
		display: block;
		margin-left: auto;
		margin-right: auto;
		padding-left: 14px;
		padding-right: 14px;
		box-sizing: border-box;
		font-size: 18px;
		text-align: center;
		text-decoration: none;
		color: #fff;
		line-height: 2.33333333;
		border-radius: 5px;
		-webkit-tap-highlight-color: rgba(0, 0, 0, 0);
		overflow: hidden;
	}

	.weui_btn:after {
		content: " ";
		width: 200%;
		height: 200%;
		position: absolute;
		top: 0;
		left: 0;
		border: 1px solid rgba(0, 0, 0, 0.2);
		-webkit-transform: scale(0.5);
		-ms-transform: scale(0.5);
		transform: scale(0.5);
		-webkit-transform-origin: 0 0;
		-ms-transform-origin: 0 0;
		transform-origin: 0 0;
		box-sizing: border-box;
		border-radius: 10px;
	}
</style>
