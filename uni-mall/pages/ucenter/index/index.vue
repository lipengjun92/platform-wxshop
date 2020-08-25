<template>
	<view class="container">
		<button class="userinfo" open-type="getUserInfo" @getuserinfo="bindGetUserInfo">
			<image class="userinfo-avatar" :src="userInfo.avatarUrl" background-size="cover"></image>
			<text class="userinfo-nickname">{{ userInfo.nickName}}</text>
		</button>
		<view style="height:20rpx;background: #eee;width:100%;"></view>
		<view class="my-item" style='background:none;display:flex;flex-direction:column;height:auto;'></view>

		<view class="user-menu">
			<view class="item">
				<navigator url="/pages/ucenter/order/order" class="a">
					<text class="icon order"></text>
					<text class="txt">我的订单</text>
				</navigator>
			</view>
			<view class="item">
				<navigator url="/pages/ucenter/coupon/coupon" class="a">
					<text class="icon coupon"></text>
					<text class="txt">优惠券</text>
				</navigator>
			</view>
			<view class="item">
				<navigator url="/pages/ucenter/collect/collect" class="a">
					<text class="icon address"></text>
					<text class="txt">我的收藏</text>
				</navigator>
			</view>
			<view class="item">
				<navigator url="/pages/ucenter/footprint/footprint" class="a">
					<text class="icon security"></text>
					<text class="txt">我的足迹</text>
				</navigator>
			</view>
			<view class="item">
				<navigator url="../address/address" class="a">
					<text class="icon address"></text>
					<text class="txt">地址管理</text>
				</navigator>
			</view>
			<button class='service' open-type="contact">
				<view class="item no-border">
					<navigator url="url" class="a">
						<text class="icon kefu"></text>
						<text class="txt">联系客服</text>
					</navigator>
				</view>
			</button>
			<view class="item item-bottom">
				<navigator url="/pages/ucenter/help/help" class="a">
					<text class="icon help"></text>
					<text class="txt">帮助中心</text>
				</navigator>
			</view>
			<view class="item item-bottom">
				<navigator url="/pages/ucenter/feedback/feedback" class="a">
					<text class="icon feedback"></text>
					<text class="txt">意见反馈</text>
				</navigator>
			</view>
			<view class="item item-bottom" v-if="!hasMobile">
				<navigator url="/pages/auth/mobile/mobile" class="a">
					<text class="icon phone"></text>
					<text class="txt">绑定手机</text>
				</navigator>
			</view>
		</view>
		<view class='company'>安徽微同科技有限公司提供技术支持 © fly2you.cn</view>
		<view class="logout" v-if="userInfo.userName!='点击去登录'" @tap="exitLogin">退出登录</view>
	</view>
</template>

<script>
	const util = require("@/utils/util.js")
	const api = require('@/utils/api.js');
	const app = getApp();
	export default {
		data() {
			return {
				userInfo: {},
				hasMobile: ''
			}
		},
		methods: {
			loginByWeixin: function(userInfo) {
				let code = null;
				return new Promise(function(resolve, reject) {
					return util.login().then((res) => {
						code = res.code;
						return userInfo;
					}).then((userInfo) => {
						//登录远程服务器
						util.request(api.AuthLoginByWeixin, {
							code: code,
							userInfo: userInfo
						}, 'POST', 'application/json').then(res => {
							if (res.errno === 0) {
								//存储用户信息
								uni.setStorageSync('userInfo', res.data.userInfo);
								uni.setStorageSync('X-Nideshop-Token', res.data.token);

								resolve(res);
							} else {
								util.toast(res.errmsg)
								reject(res);
							}
						}).catch((err) => {
							reject(err);
						});
					}).catch((err) => {
						reject(err);
					})
				});
			},
			bindGetUserInfo(e) {
				let userInfo = uni.getStorageSync('userInfo');
				let token = uni.getStorageSync('X-Nideshop-Token');
				let that = this;
				if (userInfo && token) {
					return;
				}
				if (e.detail.userInfo) {
					//用户按了允许授权按钮
					that.loginByWeixin(e.detail).then(res => {
						that.userInfo = res.data.userInfo
						app.globalData.userInfo = res.data.userInfo;
						app.globalData.token = res.data.token;
						util.setUserInfo('', res.data.token)
					}).catch((err) => {
						console.log(err)
					});
				} else {
					//用户按了拒绝按钮
					uni.showModal({
						title: '警告通知',
						content: '您点击了拒绝授权,将无法正常显示个人信息,点击确定重新获取授权。',
						success: function(res) {
							if (res.confirm) {
								uni.openSetting({
									success: (res) => {
										if (res.authSetting["scope.userInfo"]) { ////如果用户重新同意了授权登录
											that.loginByWeixin(e.detail).then(res => {
												that.userInfo = res.data.userInfo
												app.globalData.userInfo = res.data.userInfo;
												app.globalData.token = res.data.token;
											}).catch((err) => {
												console.log(err)
											});
										}
									}
								})
							}
						}
					});
				}
			},
			exitLogin: function() {
				uni.showModal({
					title: '',
					confirmColor: '#b4282d',
					content: '退出登录？',
					success: function(res) {
						if (res.confirm) {
							uni.removeStorageSync('X-Nideshop-Token');
							uni.removeStorageSync('userInfo');
							app.globalData.userInfo = {
								nickName: 'Hi,游客',
								userName: '点击去登录',
								avatarUrl: 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/150547696d798c.png'
							}
							util.toast('退出成功');
							uni.switchTab({
								url: '/pages/index/index'
							});
						}
					}
				})
			}
		},
		onShow: function() {
			let that = this;
			let userInfo = uni.getStorageSync('userInfo');
			let token = uni.getStorageSync('X-Nideshop-Token');

			// 页面显示
			if (userInfo && token) {
				app.globalData.userInfo = userInfo;
				app.globalData.token = token;
			} else {
				uni.login({
					success: function(res) {
						if (res.code) {
							that.code = res.code
						}
					}
				});
			}
			that.userInfo = app.globalData.userInfo
		},
		onLoad: function() {}
	}
</script>

<style lang="scss">
	page {
		height: 100%;
		width: 100%;
		background: #f4f4f4;
	}

	.container {
		background: #f4f4f4;
		height: auto;
		overflow: hidden;
		width: 100%;
	}

	.userinfo {
		display: flex;
		flex-direction: column;
		padding: 50rpx 0;
		align-items: center;
		background: #333;
		width: 750rpx;
	}

	.userinfo-avatar {
		width: 160rpx;
		height: 160rpx;
		margin: 20rpx;
		border-radius: 50%;
	}

	.userinfo-nickname {
		margin-top: 20rpx;
		color: #FFF;
	}

	.profile-info {
		width: 100%;
		height: 280rpx;
		display: flex;
		flex-wrap: wrap;
		align-items: center;
		justify-content: flex-start;
		padding: 0 30.25rpx;
		background: #333;
	}

	.profile-info .avatar {
		height: 148rpx;
		width: 148rpx;
		border-radius: 50%;
	}

	.profile-info .info {
		flex: 1;
		height: 85rpx;
		padding-left: 31.25rpx;
	}

	.profile-info .name {
		display: block;
		height: 45rpx;
		line-height: 45rpx;
		color: #fff;
		font-size: 37.5rpx;
		margin-bottom: 10rpx;
	}

	.profile-info .level {
		display: block;
		height: 30rpx;
		line-height: 30rpx;
		margin-bottom: 10rpx;
		color: #7f7f7f;
		font-size: 30rpx;
	}

	.user-menu {
		width: 100%;
		height: auto;
		overflow: hidden;
		background: #fff;
	}

	.user-menu .item {
		float: left;
		width: 33.33333%;
		height: 187.5rpx;
		border-right: 1px solid rgba(0, 0, 0, .15);
		border-bottom: 1px solid rgba(0, 0, 0, .15);
		text-align: center;
	}

	.user-menu .item .a {
		display: flex;
		width: 100%;
		height: 100%;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.user-menu .item.no-border {
		border-right: 0;
	}

	.user-menu .item.item-bottom {
		border-bottom: none;
	}

	.user-menu .icon {
		margin: 0 auto;
		display: block;
		height: 52.803rpx;
		width: 52.803rpx;
		margin-bottom: 16rpx;
	}

	.user-menu .icon.order {
		background: url(http://yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/sprites/ucenter-sdf6a55ee56-f2c2b9c2f0.png) 0 -437.5rpx no-repeat;
		background-size: 52.803rpx;
	}

	.user-menu .icon.coupon {
		background: url(http://yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/sprites/ucenter-sdf6a55ee56-f2c2b9c2f0.png) 0 -62.4997rpx no-repeat;
		background-size: 52.803rpx;
	}

	.user-menu .icon.gift {
		background: url(http://yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/sprites/ucenter-sdf6a55ee56-f2c2b9c2f0.png) 0 -187.5rpx no-repeat;
		background-size: 52.803rpx;
	}

	.user-menu .icon.address {
		background: url(http://yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/sprites/ucenter-sdf6a55ee56-f2c2b9c2f0.png) 0 0 no-repeat;
		background-size: 52.803rpx;
	}

	.user-menu .icon.security {
		background: url(http://yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/sprites/ucenter-sdf6a55ee56-f2c2b9c2f0.png) 0 -500rpx no-repeat;
		background-size: 52.803rpx;
	}

	.user-menu .icon.kefu {
		background: url(http://yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/sprites/ucenter-sdf6a55ee56-f2c2b9c2f0.png) 0 -312.5rpx no-repeat;
		background-size: 52.803rpx;
	}

	.user-menu .icon.help {
		background: url(http://yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/sprites/ucenter-sdf6a55ee56-f2c2b9c2f0.png) 0 -250rpx no-repeat;
		background-size: 52.803rpx;
	}

	.user-menu .icon.feedback {
		background: url(http://yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/sprites/ucenter-sdf6a55ee56-f2c2b9c2f0.png) 0 -125rpx no-repeat;
		background-size: 52.803rpx;
	}

	.user-menu .icon.phone {
		background: url(https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/15011540ebe21.png) no-repeat;
		background-size: 52.803rpx;
	}


	.user-menu .txt {
		display: block;
		height: 24rpx;
		width: 100%;
		font-size: 24rpx;
		color: #333;
	}

	.logout {
		margin-top: 50rpx;
		height: 101rpx;
		width: 100%;
		line-height: 101rpx;
		text-align: center;
		background: #fff;
		color: #333;
		font-size: 30rpx;
	}

	.service {
		position: static;
		background-color: transparent;
		color: transparent;
		margin: 0;
		padding: 0;
		border: none;
		text-align: left;
		line-height: normal;
		display: inline;
	}

	.company {
		font-size: 20rpx;
		text-align: center;
		margin-top: 50px;
	}
</style>
