<template>
	<view class="container">
		<view class="header">
			<button v-if="canIUseGetUserProfile" class="profile-btn" @tap="getUserProfile">
				<image class="avatar" :src="avatarUrl" mode="aspectFill"></image>
				<view class="meta">
					<text class="nickname">{{ nicknameText }}</text>
					<text class="desc">{{ userInfo.userName || '点击完善资料并体验完整功能' }}</text>
				</view>
			</button>
			<button v-else class="profile-btn" open-type="getUserInfo" @getuserinfo="bindGetUserInfo">
				<image class="avatar" :src="avatarUrl" mode="aspectFill"></image>
				<view class="meta">
					<text class="nickname">{{ nicknameText }}</text>
					<text class="desc">{{ userInfo.userName || '点击完善资料并体验完整功能' }}</text>
				</view>
			</button>
		</view>

		<view class="menu-card">
			<navigator v-for="item in menuItems" :key="item.url" :url="item.url" class="menu-item">
				<view class="icon-badge">{{ item.badge }}</view>
				<text class="txt">{{ item.title }}</text>
			</navigator>

			<button class="menu-item contact-btn" open-type="contact">
				<view class="icon-badge">服</view>
				<text class="txt">联系客服</text>
			</button>

			<navigator v-if="!hasMobile" url="/pages/auth/mobile/mobile" class="menu-item">
				<view class="icon-badge">绑</view>
				<text class="txt">绑定手机</text>
			</navigator>
		</view>

		<view class="company">安徽微同科技有限公司提供技术支持 © fly2you.cn</view>
		<view class="logout" v-if="userInfo.userName != '点击去登录'" @tap="exitLogin">退出登录</view>
	</view>
</template>

<script>
const util = require('@/utils/util.js');
const api = require('@/utils/api.js');
const app = getApp();

const DEFAULT_AVATAR = 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/150547696d798c.png';

export default {
	data() {
		return {
			canIUseGetUserProfile: false,
			userInfo: {},
			hasMobile: '',
			menuItems: [{
					title: '我的订单',
					url: '/pages/ucenter/order/order',
					badge: '单'
				},
				{
					title: '优惠券',
					url: '/pages/ucenter/coupon/coupon',
					badge: '券'
				},
				{
					title: '我的收藏',
					url: '/pages/ucenter/collect/collect',
					badge: '藏'
				},
				{
					title: '我的足迹',
					url: '/pages/ucenter/footprint/footprint',
					badge: '迹'
				},
				{
					title: '地址管理',
					url: '/pages/ucenter/address/address',
					badge: '址'
				},
				{
					title: '帮助中心',
					url: '/pages/ucenter/help/help',
					badge: '助'
				},
				{
					title: '意见反馈',
					url: '/pages/ucenter/feedback/feedback',
					badge: '评'
				}
			]
		};
	},
	computed: {
		nicknameText() {
			return this.userInfo.nickname || 'Hi, 游客';
		},
		avatarUrl() {
			return this.userInfo.avatar || DEFAULT_AVATAR;
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
					util.request(api.AuthLoginByWeixin, {
						code: code,
						userInfo: userInfo
					}, 'POST', 'application/json').then(res => {
						if (res.code === 0) {
							uni.setStorageSync('userInfo', res.data.userInfo);
							uni.setStorageSync('token', res.data.token);
							resolve(res);
						} else {
							util.toast(res.msg);
							reject(res);
						}
					}).catch((err) => {
						reject(err);
					});
				}).catch((err) => {
					reject(err);
				});
			});
		},
		getUserProfile() {
			let that = this;
			wx.getUserProfile({
				desc: '用于完善会员资料',
				success: (resp) => {
					that.loginByWeixin(resp).then(res => {
						that.userInfo = res.data.userInfo;
						app.globalData.userInfo = res.data.userInfo;
						app.globalData.token = res.data.token;
					}).catch((err) => {
						console.log(err);
					});
				}
			});
		},
		bindGetUserInfo(e) {
			let that = this;
			that.loginByWeixin(e.detail).then(res => {
				that.userInfo = res.data.userInfo;
				app.globalData.userInfo = res.data.userInfo;
				app.globalData.token = res.data.token;
			}).catch((err) => {
				console.log(err);
			});
		},
		exitLogin: function() {
			uni.showModal({
				title: '',
				confirmColor: '#d1554f',
				content: '退出登录？',
				success: function(res) {
					if (res.confirm) {
						uni.removeStorageSync('token');
						uni.removeStorageSync('userInfo');
						app.globalData.userInfo = {
							nickname: 'Hi, 游客',
							userName: '点击去登录',
							avatar: DEFAULT_AVATAR
						};
						util.toast('退出成功');
						uni.switchTab({
							url: '/pages/index/index'
						});
					}
				}
			});
		}
	},
	onShow: function() {
		let that = this;
		let userInfo = uni.getStorageSync('userInfo');
		let token = uni.getStorageSync('token');
		if (userInfo && token) {
			app.globalData.userInfo = userInfo;
			app.globalData.token = token;
		} else {
			uni.login({
				success: function(res) {
					if (res.code) {
						that.code = res.code;
					}
				}
			});
		}
		that.userInfo = app.globalData.userInfo || {};
	},
	onLoad: function() {
		if (wx.getUserProfile) {
			this.canIUseGetUserProfile = true;
		}
	}
};
</script>

<style lang="scss">
page {
	height: 100%;
	background: #f2f5f9;
}

.container {
	min-height: 100%;
	padding: 0 24rpx 36rpx;
	box-sizing: border-box;
	background:
		radial-gradient(circle at 14% 12%, rgba(255, 214, 194, .95), transparent 42%),
		radial-gradient(circle at 82% 2%, rgba(159, 214, 255, .72), transparent 34%),
		linear-gradient(180deg, #10253f 0rpx, #173657 390rpx, #f2f5f9 700rpx);
}

.header {
	padding-top: 36rpx;
}

.profile-btn {
	display: flex;
	align-items: center;
	width: 100%;
	padding: 26rpx 28rpx;
	border: 0;
	border-radius: 28rpx;
	background: linear-gradient(130deg, rgba(16, 26, 42, .88), rgba(34, 59, 90, .88));
	box-shadow: 0 22rpx 54rpx rgba(10, 18, 33, .28);
	text-align: left;
}

.profile-btn::after {
	border: none;
}

.avatar {
	width: 128rpx;
	height: 128rpx;
	border-radius: 50%;
	box-shadow: 0 0 0 6rpx rgba(255, 255, 255, .22);
}

.meta {
	margin-left: 24rpx;
	display: flex;
	flex-direction: column;
	overflow: hidden;
}

.nickname {
	color: #fff;
	font-size: 36rpx;
	font-weight: 600;
	line-height: 1.25;
}

.desc {
	margin-top: 10rpx;
	color: rgba(245, 249, 255, .82);
	font-size: 24rpx;
	line-height: 1.45;
}

.menu-card {
	margin-top: 24rpx;
	background: rgba(255, 255, 255, .97);
	border-radius: 26rpx;
	padding: 8rpx 0;
	box-shadow: 0 18rpx 38rpx rgba(16, 34, 59, .09);
	display: flex;
	flex-wrap: wrap;
	overflow: hidden;
}

.menu-item {
	width: 33.3333%;
	height: 180rpx;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	text-decoration: none;
	position: relative;
}

.menu-item::before {
	content: '';
	position: absolute;
	left: 18rpx;
	right: 18rpx;
	bottom: 0;
	height: 1rpx;
	background: rgba(21, 50, 89, .08);
}

.menu-item::after {
	content: '';
	position: absolute;
	right: 0;
	top: 28rpx;
	bottom: 28rpx;
	width: 1rpx;
	background: rgba(21, 50, 89, .08);
}

.menu-item:nth-child(3n)::after {
	display: none;
}

.menu-item:nth-last-child(-n + 3)::before {
	display: none;
}

.icon-badge {
	width: 64rpx;
	height: 64rpx;
	border-radius: 18rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	background: linear-gradient(145deg, #1f6db3, #144d8f);
	color: #fff;
	font-size: 30rpx;
	font-weight: 600;
	box-shadow: 0 10rpx 18rpx rgba(26, 82, 143, .26);
}

.txt {
	margin-top: 14rpx;
	color: #203853;
	font-size: 24rpx;
	font-weight: 500;
}

.contact-btn {
	margin: 0;
	padding: 0;
	border: 0;
	background: transparent;
	border-radius: 0;
	line-height: normal;
}

.contact-btn::after {
	border: none;
}

.company {
	margin-top: 34rpx;
	font-size: 22rpx;
	color: #6f7c8f;
	text-align: center;
}

.logout {
	margin-top: 22rpx;
	height: 90rpx;
	line-height: 90rpx;
	text-align: center;
	background: #fff;
	border-radius: 20rpx;
	font-size: 28rpx;
	color: #ca4f49;
	box-shadow: 0 12rpx 24rpx rgba(16, 34, 59, .08);
}
</style>
