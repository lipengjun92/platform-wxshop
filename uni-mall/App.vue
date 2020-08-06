<script>
	const util = require("@/utils/util.js")
	export default {
		globalData: {
			userInfo: {
				nickName: 'Hi,游客',
				userName: '点击去登录',
				avatarUrl: 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/upload/20180727/150547696d798c.png'
			},
			token: ''
		},
		onLaunch: function() {
			//获取小程序更新机制兼容
			if (uni.canIUse('getUpdateManager')) {
				const updateManager = uni.getUpdateManager()
				updateManager.onCheckForUpdate(function(res) {
					// 请求完新版本信息的回调
					if (res.hasUpdate) {
						updateManager.onUpdateReady(function() {
							uni.showModal({
								title: '更新提示',
								content: '新版本已经准备好，是否重启应用？',
								success: function(res) {
									if (res.confirm) {
										// 新的版本已经下载好，调用 applyUpdate 应用新版本并重启
										updateManager.applyUpdate()
									}
								}
							})
						})
						updateManager.onUpdateFailed(function() {
							// 新的版本下载失败
							uni.showModal({
								title: '已经有新版本了哟~',
								content: '新版本已经上线啦~，请您删除当前小程序，重新搜索打开哟~',
							})
						})
					}
				})
			} else {
				// 如果希望用户在最新版本的客户端上体验您的小程序，可以这样子提示
				uni.showModal({
					title: '提示',
					content: '当前微信版本过低，无法更好体验程序，请升级到最新微信版本后重试。'
				})
			}
		},
		onShow: function() {

		},
		onHide: function() {},
		onError: function(err) {
			//全局错误监听
			// #ifdef APP-PLUS
			plus.runtime.getProperty(plus.runtime.appid, widgetInfo => {
				const res = uni.getSystemInfoSync();
				let errMsg = `手机品牌：${res.brand}；手机型号：${res.model}；操作系统版本：${res.system}；客户端平台：${res.platform}；错误描述：${err}`;
				console.log('发生错误：' + errMsg);
			});
			// #endif
		}
	};
</script>

<style>
	/*每个页面公共css uParse为优化版本*/
	@import './common/app.css';
	/* #ifndef APP-NVUE */
	@import './components/uParse/src/wxParse.css';
	/* #endif */
</style>
