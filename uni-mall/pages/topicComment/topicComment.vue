<template>
	<view class="comments">
		<view class="b">
			<view class="item" v-for="(item, index) in comments" :key="item.id">
				<view class="info">
					<view class="user">
						<image :src="item.user_info.avatar"></image>
						<text>{{item.user_info.nickname||''}}</text>
					</view>
					<view class="time">{{item.add_time||''}}</view>
				</view>
				<view class="comment">{{item.content||''}}</view>
				<view class="imgs" v-if="item.pic_list.length > 0">
					<image class="img" v-for="(pitem, index) in item.pic_list" :key="pitem.id" :src="pitem.pic_url"></image>
				</view>
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
				comments: [],
				allCommentList: [],
				picCommentList: [],
				typeId: 0,
				valueId: 0,
				showType: 0,
				allCount: 0,
				hasPicCount: 0,
				allPage: 1,
				picPage: 1,
				size: 20
			}
		},
		methods: {
			getCommentCount: function() {
				let that = this;
				util.request(api.CommentCount, {
					valueId: that.valueId,
					typeId: that.typeId
				}).then(function(res) {
					if (res.errno === 0) {
						that.allCount = res.data.allCount
						that.hasPicCount = res.data.hasPicCount
					}
				});
			},
			getCommentList: function() {
				let that = this;
				util.request(api.CommentList, {
					valueId: that.valueId,
					typeId: that.typeId,
					size: that.size,
					page: (that.showType == 0 ? that.allPage : that.picPage),
					showType: that.showType
				}).then(function(res) {
					if (res.errno === 0) {
						if (that.showType == 0) {
							that.allCommentList = that.allCommentList.concat(res.data.data)
							that.allPage = res.data.currentPage
							that.comments = that.allCommentList.concat(res.data.data)
						} else {
							that.picCommentList = that.picCommentList.concat(res.data.data)
							that.picPage = res.data.currentPage
							that.comments = that.picCommentList.concat(res.data.data)
						}
					}
				});
			},
			switchTab: function() {
				this.showType = this.showType == 1 ? 0 : 1

				this.getCommentList();
			},
		},
		onLoad: function(options) {
			// 页面初始化 options为页面跳转所带来的参数
			this.typeId = options.typeId
			this.valueId = options.valueId
			this.getCommentCount();
			this.getCommentList();
		},
		onReachBottom: function() {
			if (this.showType == 0) {

				if (this.allCount / this.size < this.allPage) {
					return false;
				}
				this.setData({
					'allPage': this.allPage + 1
				});
			} else {
				if (this.hasPicCount / this.size < this.picPage) {
					return false;
				}

				this.picPage = this.picPage + 1
			}
			this.getCommentList();
		}
	}
</script>

<style lang="scss">
	.comments {
		width: 100%;
		height: auto;
		padding-left: 30rpx;
		background: #fff;
		margin: 20rpx 0;
	}

	.comments .b {
		height: auto;
		width: 720rpx;
	}

	.comments .b.no-h {
		margin-top: 0;
	}

	.comments .item {
		height: auto;
		width: 720rpx;
		overflow: hidden;
		border-bottom: 1px solid #d9d9d9;
		padding-bottom: 25rpx;
	}

	.comments .info {
		height: 127rpx;
		width: 100%;
		padding: 33rpx 0 27rpx 0;
	}

	.comments .user {
		float: left;
		width: auto;
		height: 67rpx;
		line-height: 67rpx;
		font-size: 0;
	}

	.comments .user image {
		float: left;
		width: 67rpx;
		height: 67rpx;
		margin-right: 17rpx;
		border-radius: 50%;
	}

	.comments .user text {
		display: inline-block;
		width: auto;
		height: 66rpx;
		overflow: hidden;
		font-size: 29rpx;
		line-height: 66rpx;
	}

	.comments .time {
		display: block;
		float: right;
		width: auto;
		height: 67rpx;
		line-height: 67rpx;
		color: #7f7f7f;
		font-size: 25rpx;
		margin-right: 30rpx;
	}

	.comments .comment {
		width: 720rpx;
		padding-right: 30rpx;
		line-height: 45.8rpx;
		font-size: 29rpx;
		margin-bottom: 16rpx;
	}

	.comments .imgs {
		width: 720rpx;
		height: 150rpx;
		margin-bottom: 25rpx;
	}

	.comments .imgs .img {
		height: 150rpx;
		width: 150rpx;
		margin-right: 28rpx;
	}

	.comments .customer-service {
		width: 690rpx;
		height: auto;
		overflow: hidden;
		margin-top: 23rpx;
		background: rgba(0, 0, 0, .03);
		padding: 21rpx;
	}


	.comments .customer-service .u {
		font-size: 24rpx;
		color: #333;
		line-height: 37.5rpx;
	}

	.comments .customer-service .c {
		font-size: 24rpx;
		color: #999;
		line-height: 37.5rpx;
	}
</style>
