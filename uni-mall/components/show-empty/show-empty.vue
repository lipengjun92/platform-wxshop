<template>
	<view class='no-course'>
		<image class='img' src='../../static/images/my_course_empty.png'></image>
		<text class='desc' v-if="text">{{text}}~</text>
	</view>
</template>

<script>
	export default {
		name: "tuiShowEmpty",
		props: {
			text: {
				type: String,
				value: ''
			}
		}
	}
</script>

<style scoped>
	.no-course {
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 100%;
		height: 100%;
	}

	.no-course .img {
		margin-top: 116rpx;
		width: 597rpx;
		height: 467rpx;
		margin-bottom: 60rpx;
	}

	.no-course .desc {
		font-size: 30rpx;
		line-height: 40rpx;
		color: #333;
		font-weight: bold;
	}
</style>
