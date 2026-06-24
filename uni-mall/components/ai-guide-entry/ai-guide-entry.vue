<template>
	<view v-if="supported" class="ai-guide-float" :style="containerStyle" @tap="openAgent">
		<view class="ai-guide-text">{{text}}</view>
	</view>
</template>

<script>
	export default {
		name: 'ai-guide-entry',
		props: {
			context: {
				type: String,
				default: ''
			},
			text: {
				type: String,
				default: 'AI 助手'
			},
			bottom: {
				type: String,
				default: '140rpx'
			},
			right: {
				type: String,
				default: '20rpx'
			},
			left: {
				type: String,
				default: ''
			},
			top: {
				type: String,
				default: ''
			},
			zIndex: {
				type: String,
				default: '99'
			}
		},
		data() {
			return {
				supported: false
			};
		},
		computed: {
			containerStyle() {
				const style = [];
				if (this.bottom) style.push('bottom:' + this.bottom);
				if (this.right) style.push('right:' + this.right);
				if (this.left) style.push('left:' + this.left);
				if (this.top) style.push('top:' + this.top);
				if (this.zIndex) style.push('z-index:' + this.zIndex);
				return style.join(';');
			}
		},
		mounted() {
			// 仅微信小程序原生 API，其他端没有 wx.openAgent
			// #ifdef MP-WEIXIN
			this.supported = !!(typeof wx !== 'undefined' && wx.openAgent);
			// #endif
		},
		methods: {
			openAgent() {
				// #ifdef MP-WEIXIN
				if (typeof wx === 'undefined' || !wx.openAgent) {
					uni.showToast({
						title: '当前微信版本暂不支持 AI 助手',
						icon: 'none',
						duration: 2000
					});
					return;
				}
				wx.openAgent({
					context: this.context || '',
					fail: (res) => {
						uni.showToast({
							title: (res && res.errMsg) || '打开 AI 助手失败',
							icon: 'none',
							duration: 2000
						});
					}
				});
				// #endif

				// #ifndef MP-WEIXIN
				uni.showToast({
					title: '当前平台暂不支持 AI 助手',
					icon: 'none',
					duration: 2000
				});
				// #endif
			}
		}
	};
</script>

<style scoped>
	.ai-guide-float {
		position: fixed;
		width: 80rpx;
		height: 80rpx;
		padding: 0 10rpx;
		box-sizing: border-box;
		border-radius: 50%;
		display: flex;
		align-items: center;
		justify-content: center;
		background: linear-gradient(135deg, #ff8a4d 0%, #eb5b2d 100%);
		box-shadow: 0 12rpx 26rpx rgba(235, 91, 45, 0.22);
	}

	.ai-guide-text {
		color: #fff;
		font-size: 18rpx;
		line-height: 1.25;
		font-weight: 600;
		text-align: center;
	}
</style>
