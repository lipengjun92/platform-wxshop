<template>
	<view class="container">
		<scroll-view v-if="topicList.length >0" class="topic-list" :scroll-y="true" :scroll-top="scrollTop">
			<navigator class="item" v-for="(item, index) in topicList" :key="index" :url="'../topicDetail/topicDetail?id='+item.id">
				<image class="img" :src="item.scene_pic_url"></image>
				<view class="info">
					<text class="title">{{item.title}}</text>
					<text class="desc">{{item.subtitle}}</text>
					<text class="price">{{item.price_info}}元起</text>
				</view>
			</navigator>
			<view class="page" v-if="showPage">
				<view :class="'prev ' + (page <= 1 ? 'disabled' : '')" @tap="prevPage">上一页</view>
				<view :class="'next ' + ((count / size) < page +1 ? 'disabled' : '')" @tap="nextPage">下一页</view>
			</view>
		</scroll-view>
		<tui-show-empty v-else text="暂无专题"></tui-show-empty>
	</view>
</template>

<script>
	const util = require("@/utils/util.js")
	const api = require('@/utils/api.js');
	export default {
		data() {
			return {
				// text:"这是一个页面"
				topicList: [],
				page: 1,
				size: 10,
				count: 0,
				scrollTop: 0,
				showPage: false
			}
		},
		methods: {
			nextPage: function(event) {
				let that = this;
				if (this.page + 1 > that.count / that.size) {
					return true;
				}
				that.page = parseInt(that.page) + 1
				this.getTopic();
			},
			getTopic: function() {

				let that = this;
				that.scrollTop = 0
				that.showPage = false
				that.topicList = []
				// 页面渲染完成
				uni.showToast({
					title: '加载中...',
					icon: 'loading',
					duration: 2000
				});

				util.request(api.TopicList, {
					page: that.page,
					size: that.size
				}).then(function(res) {
					if (res.errno === 0) {
						that.scrollTop = 0
						that.topicList = res.data.data
						that.showPage = true
						that.count = res.data.count
					}
					uni.hideToast();
				});

			},
			prevPage: function(event) {
				if (this.page <= 1) {
					return false;
				}

				var that = this;
				that.page = parseInt(that.page) - 1
				this.getTopic();
			}
		},
		onLoad: function(options) {
			// 页面初始化 options为页面跳转所带来的参数
			this.getTopic();

		}
	}
</script>

<style lang="scss">
	page,
	.container {
		width: 750rpx;
		height: 100%;
		overflow: hidden;
		background: #f4f4f4;
	}

	.topic-list {
		width: 750rpx;
		height: 100%;
		overflow: hidden;
		background: #f4f4f4;
	}

	.topic-list .item {
		width: 100%;
		height: 625rpx;
		overflow: hidden;
		background: #fff;
		margin-bottom: 20rpx;
	}

	.topic-list .img {
		width: 100%;
		height: 415rpx;
	}

	.topic-list .info {
		width: 100%;
		height: 210rpx;
		overflow: hidden;
	}

	.topic-list .title {
		display: block;
		text-align: center;
		width: 100%;
		height: 33rpx;
		line-height: 35rpx;
		color: #333;
		overflow: hidden;
		font-size: 35rpx;
		margin-top: 30rpx;
	}

	.topic-list .desc {
		display: block;
		text-align: center;
		position: relative;
		width: auto;
		height: 24rpx;
		line-height: 24rpx;
		overflow: hidden;
		color: #999;
		font-size: 24rpx;
		margin-top: 16rpx;
		margin-bottom: 30rpx;
	}

	.topic-list .price {
		display: block;
		text-align: center;
		width: 100%;
		height: 27rpx;
		line-height: 27rpx;
		overflow: hidden;
		color: #b4282d;
		font-size: 27rpx;
	}


	.page {
		width: 750rpx;
		height: 108rpx;
		background: #fff;
		margin-bottom: 20rpx;
	}

	.page view {
		height: 108rpx;
		width: 50%;
		float: left;
		font-size: 29rpx;
		color: #333;
		text-align: center;
		line-height: 108rpx;
	}

	.page .prev {
		border-right: 1px solid #D9D9D9;
	}

	.page .disabled {
		color: #ccc;
	}
</style>
