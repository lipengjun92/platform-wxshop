<template>
	<view>
		<scroll-view class="container">
			<scroll-view class="content">
				<uParse :content="topic.content" noData="" />
			</scroll-view>
			<view class="topic-goods">
			</view>
			<scroll-view class="comments">
				<view class="h">
					<text class="t">精选留言</text>
					<image @tap="postComment" class="i" src="http://nos.netease.com/mailpub/hxm/yanxuan-wap/p/20150730/style/img/icon-normal/comment-add-2aca147c3f.png"></image>
				</view>
				<view class="has-comments" v-if="commentList.length > 0">
					<view class="b">
						<view class="item" v-for="(item, index) in commentList" :key="item.id">
							<view class="info">
								<view class="user">
									<image class="avatar" :src="item.user_info.avatar"></image>
									<text class="nickname">{{item.user_info.nickname}}</text>
								</view>
								<view class="time">{{item.add_time}}</view>
							</view>
							<view class="comment">
								{{item.content}}
							</view>
						</view>
					</view>
					<view class="load" v-if="commentCount > 5">
						<navigator :url="'/pages/topicComment/topicComment?valueId='+topic.id+'&typeId=1'">查看更多</navigator>
					</view>
				</view>
				<view class="no-comments" v-if="commentList.length <= 0">
					<view class="b">
						<image class="icon" src="http://yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/icon-normal/no-comment-560f87660a.png"></image>
						<text class="txt">等你来留言</text>
					</view>
				</view>
			</scroll-view>
			<scroll-view class="rec-box">
				<view class="h">
					<text class="txt">专题推荐</text>
				</view>
				<view class="b">
					<navigator class="item" v-for="(item, index) in topicList" :key="index" :url="'../topicDetail/topicDetail?id='+item.id">
						<image class="img" :src="item.scene_pic_url"></image>
						<text class="title">{{item.title}}</text>
					</navigator>
				</view>
			</scroll-view>
		</scroll-view>
	</view>
</template>

<script>
	const util = require("@/utils/util.js")
	const api = require('@/utils/api.js');
	import uParse from '@/components/uParse/src/wxParse'
	export default {
		components: {
			uParse
		},
		data() {
			return {
				id: 0,
				topic: {},
				topicList: [],
				commentCount: 0,
				commentList: []
			}
		},
		methods: {
			getCommentList() {
				let that = this;
				util.request(api.CommentList, {
					valueId: that.id,
					typeId: 1,
					size: 5
				}).then(function(res) {
					if (res.errno === 0) {
						that.commentList = res.data.data
						that.commentCount = res.data.count
					}
				});
			},
			postComment() {
				wx.navigateTo({
					url: '/pages/commentPost/commentPost?valueId=' + this.id + '&typeId=1',
				})
			},
		},
		onShow: function() {
			// 页面显示
			this.getCommentList();
		},
		onLoad: function(options) {
			// 页面初始化 options为页面跳转所带来的参数
			var that = this;
			that.id = parseInt(options.id)

			util.request(api.TopicDetail, {
				id: that.id
			}).then(function(res) {
				if (res.errno === 0) {
					that.topic = res.data;
				}
			});

			util.request(api.TopicRelated, {
				id: that.id
			}).then(function(res) {
				if (res.errno === 0) {
					that.topicList = res.data
				}
			});
		}
	}
</script>

<style lang="scss">
	.content {
		width: 100%;
		height: auto;
		font-size: 0;
	}

	.content image {
		display: inline-block;
		width: 100%;
	}

	.comments {
		width: 100%;
		height: auto;
		padding-left: 30rpx;
		background: #fff;
		margin-top: 20rpx;
	}

	.comments .h {
		height: 93rpx;
		line-height: 93rpx;
		width: 720rpx;
		padding-right: 30rpx;
		border-bottom: 1px solid #d9d9d9;
	}

	.comments .h .t {
		display: block;
		float: left;
		width: 50%;
		font-size: 29rpx;
		color: #333;
	}

	.comments .h .i {
		display: block;
		float: right;
		margin-top: 30rpx;
		width: 33rpx;
		height: 33rpx;
	}

	.comments .b {
		height: auto;
		width: 720rpx;
	}

	.comments .item {
		height: auto;
		width: 720rpx;
		overflow: hidden;
		border-bottom: 1px solid #d9d9d9;
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

	.comments .user .avatar {
		display: block;
		float: left;
		width: 67rpx;
		height: 67rpx;
		margin-right: 17rpx;
		border-radius: 50%;
	}

	.comments .user .nickname {
		display: block;
		width: auto;
		float: left;
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
		margin-bottom: 30rpx;
		font-size: 29rpx;
		color: #333;
	}

	.comments .load {
		width: 720rpx;
		height: 108rpx;
		line-height: 108rpx;
		text-align: center;
		font-size: 38.5rpx;
	}

	.no-comments {
		height: 297rpx;
	}

	.no-comments .txt {
		height: 43rpx;
		line-height: 43rpx;
		display: block;
		width: 100%;
		text-align: center;
		font-size: 29rpx;
		color: #7f7f7f;
	}

	.no-comments .icon {
		margin: 48rpx auto 18rpx auto;
		height: 130rpx;
		display: block;
		width: 115rpx;
	}


	.rec-box {
		width: 690rpx;
		height: auto;
		margin: 0 30rpx;
	}

	.rec-box .h {
		position: relative;
		width: 690rpx;
		height: 96rpx;
		/*border-bottom: 1px solid #d0d0d0;*/
		margin-bottom: 32rpx;
	}

	.rec-box .h .txt {
		display: inline-block;
		position: absolute;
		background: #f4f4f4;
		top: 59rpx;
		left: 200rpx;
		width: 290rpx;
		height: 45rpx;
		line-height: 45rpx;
		font-size: 30rpx;
		color: #999;
		text-align: center;
	}

	.rec-box .b .item {
		width: 690rpx;
		height: 397rpx;
		padding: 24rpx 24rpx 30rpx 24rpx;
		background: #fff;
		margin-bottom: 30rpx;
	}

	.rec-box .b .item .img {
		height: 278rpx;
		width: 642rpx;
	}

	.rec-box .b .item .title {
		display: block;
		margin-top: 30rpx;
		height: 30rpx;
		width: 642rpx;
		font-size: 28rpx;
	}
</style>
