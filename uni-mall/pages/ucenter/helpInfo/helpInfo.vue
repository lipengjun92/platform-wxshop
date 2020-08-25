<template>
	<view class="container">
		<view class="help-container" v-for="(item, index) in helpList" :key="index">
			<view class="help-box">
				<view class="help-title">{{item.question||''}}</view>
				<view class="help-content">{{item.answer||''}}</view>
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
				helpList: []
			}
		},
		methods: {
			getHelpList(id) {
				let that = this;
				util.request(api.HelpIssueList, {
					type_id: id
				}).then(function(res) {
					if (res.errno === 0) {
						that.helpList = res.data
					}
				});
			}
		},
		onLoad: function(options) {
			this.getHelpList(options.id)

		}
	}
</script>

<style lang="scss">
	.container {
		width: 750rpx;
		height: 100%;
		overflow-x: hidden;
		overflow-y: auto;
		background: #f4f4f4;
		position: relative;
	}

	.help-container {
		background-color: #fff;
		padding: 0 30rpx;
	}

	.help-box {
		position: relative;
	}

	.help-box::after {
		content: " ";
		position: absolute;
		left: 0;
		bottom: 0;
		right: 0;
		height: 1px;
		border-bottom: 1px solid #d9d9d9;
		color: #d9d9d9;
		-webkit-transform-origin: 0 100%;
		transform-origin: 0 100%;
		-webkit-transform: scaleY(0.5);
		transform: scaleY(0.5);
	}

	.help-box .help-title {
		padding-top: 30rpx;
		font-weight: bold;
	}

	.help-box .help-content {
		padding: 30rpx 0;
	}
</style>
