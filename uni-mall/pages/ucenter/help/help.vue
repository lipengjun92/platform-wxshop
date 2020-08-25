<template>
	<view class="container">
		<view class="help-head">常见问题</view>
		<view class="list-group" style="margin-top: 0;">
			<navigator :url="'../helpInfo/helpInfo?id='+item.id" class="list-cell" v-for="(item,index) in helpList" :key="index">
				<view class="list-cell-bd">
					<view class="list-label">{{item.typeName||''}}</view>
				</view>
				<view class="list-cell-ft router"></view>
			</navigator>
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
			getHelpList() {
				let that = this;
				util.request(api.HelpTypeList, {}).then(function(res) {
					if (res.errno === 0) {
						that.helpList = res.data
					}
				});
			}
		},
		onLoad: function() {
			this.getHelpList()
		}
	}
</script>

<style lang="scss">
	.container {
		background-color: #fff;
		width: 750rpx;
		height: 100%;
		overflow-x: hidden;
		overflow-y: auto;
		position: relative;
	}

	.help-head {
		background-color: #fff;
		padding: 30rpx;
		font-size: 35rpx;
		color: #009944;
	}

	.list-cell {
		border-bottom: thin dotted #f4f4f4;
		font-size: 25rpx;
		margin: 30rpx;
	}
</style>
