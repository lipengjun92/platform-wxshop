<template>
	<view class="container">
		<swiper class="banner" indicator-dots="true" autoplay="true" interval="3000" duration="1000">
			<swiper-item v-for="(item, index) in banner" :key="index">
				<navigator v-if="item.link" :url="item.link">
					<image :src="item.image_url" background-size="cover"></image>
				</navigator>
				<image v-else :src="item.image_url" background-size="cover"></image>
			</swiper-item>
		</swiper>
		<view class="a-section a-topic" v-if="channel.length > 0">
			<view class="m-menu">
				<navigator class="item" v-for="(item, index) in channel" :key="index" :url="item.url">
					<image :src="item.icon_url" background-size="cover"></image>
					<text>{{item.name}}</text>
				</navigator>
			</view>
		</view>

		<view class="a-section a-topic" v-if="brands.length > 0">
			<view class="a-section a-brand">
				<view class="h">
					<navigator url="/pages/brand/brand">
						<text class="txt">品牌制造商直供</text>
					</navigator>
				</view>
			</view>
			<view class="b">
				<scroll-view scroll-x="true" class="list">
					<view class="item" v-for="(item, index) in brands" :key="index">
						<navigator :url="'/pages/brandDetail/brandDetail?id='+item.id">
							<image class="img" :src="item.new_pic_url" background-size="cover"></image>
							<view class="np">
								<text class="name">{{item.name}}</text>
								<text class="price">￥{{item.floor_price}}元起</text>
							</view>
							<text class="desc">{{item.simple_desc}}</text>
						</navigator>
					</view>
				</scroll-view>
			</view>
		</view>

		<view class="a-section a-topic" v-if="topics.length > 0">
			<view class="a-section a-brand">
				<view class="h">
					<navigator open-type="switchTab" url="/pages/topic/topic">
						<text class="txt">专题精选</text>
					</navigator>
				</view>
			</view>
			<view class="b">
				<scroll-view scroll-x="true" class="list">
					<view class="item" v-for="(item, index) in topics" :key="index">
						<navigator :url="'/pages/topicDetail/topicDetail?id='+item.id">
							<image class="img" :src="item.scene_pic_url" background-size="cover"></image>
							<view class="np">
								<text class="name">{{item.title}}</text>
								<text class="price">￥{{item.price_info}}元起</text>
							</view>
							<text class="desc">{{item.subtitle}}</text>
						</navigator>
					</view>
				</scroll-view>
			</view>
		</view>

		<view class="a-section a-new" v-if="newGoods.length > 0">
			<view class="h">
				<view>
					<navigator url="../newGoods/newGoods">
						<text class="txt">周一周四 · 新品首发</text>
					</navigator>
				</view>
			</view>
			<view class="b">
				<view class="item" v-for="(item, index) in newGoods" :key="index">
					<navigator :url="'/pages/goods/goods?id='+item.id">
						<image class="img" :src="item.list_pic_url" background-size="cover"></image>
						<text class="name">{{item.name}}</text>
						<text class="price">￥{{item.retail_price}}</text>
					</navigator>
				</view>
			</view>
		</view>

		<view class="a-section a-popular" v-if="hotGoods.length > 0">
			<view class="h">
				<view>
					<navigator url="../hotGoods/hotGoods">
						<text class="txt">人气推荐</text>
					</navigator>
				</view>
			</view>
			<view class="b">
				<view class="item" v-for="(item, index) in hotGoods" :key="index">
					<navigator :url="'/pages/goods/goods?id='+item.id">
						<image class="img" :src="item.list_pic_url" background-size="cover"></image>
						<view class="right">
							<view class="text">
								<text class="name">{{item.name}}</text>
								<text class="desc">{{item.goods_brief}}</text>
								<text class="price">￥{{item.retail_price}}</text>
							</view>
						</view>
					</navigator>
				</view>
			</view>
		</view>

		<view class="good-grid" v-for="(item, index) in floorGoods" :key="index">
			<view class="h">
				<view>
					<text>{{item.name}}</text>
				</view>
			</view>
			<view class="b">
				<view class="item" v-for="(iitem, iindex) in item.goodsList" :key="iindex">
					<view :class="'item ' + iindex % 2 == 0 ? '' : 'item-b'">
						<navigator :url="'/pages/goods/goods?id='+item.id">
							<image class="img" :src="iitem.list_pic_url" background-size="cover"></image>
							<text class="name">{{iitem.name}}</text>
							<text class="price">￥{{iitem.retail_price}}</text>
						</navigator>
					</view>
				</view>
				<view class="item item-b item-more">
					<navigator :url="'/pages/category/category?id='+item.id" class="more-a">
						<view class="txt">{{'更多'+item.name+'好物'}}</view>
						<image class="icon" src="../../static/images/icon_go_more.png" background-size="cover"></image>
					</navigator>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	const api = require('@/utils/api.js');
	const util = require("@/utils/util.js")
	export default {
		data() {
			return {
				newGoods: [],
				hotGoods: [],
				topics: [],
				brands: [],
				floorGoods: [],
				banner: [],
				channel: []
			}
		},
		methods: {
			getIndexData: function() {
				let that = this;
				util.request(api.IndexUrlNewGoods).then(function(res) {
					if (res.errno === 0) {
						that.newGoods = res.data.newGoodsList
					}
				});
				util.request(api.IndexUrlHotGoods).then(function(res) {
					if (res.errno === 0) {
						that.hotGoods = res.data.hotGoodsList
					}
				});
				util.request(api.IndexUrlTopic).then(function(res) {
					if (res.errno === 0) {
						that.topics = res.data.topicList
					}
				});
				util.request(api.IndexUrlBrand).then(function(res) {
					if (res.errno === 0) {
						that.brands = res.data.brandList
					}
				});
				util.request(api.IndexUrlCategory).then(function(res) {
					if (res.errno === 0) {
						that.floorGoods = res.data.categoryList
					}
				});
				util.request(api.IndexUrlBanner).then(function(res) {
					if (res.errno === 0) {
						that.banner = res.data.banner
					}
				});
				util.request(api.IndexUrlChannel).then(function(res) {
					if (res.errno === 0) {
						that.channel = res.data.channel
					}
				});

			},
		},
		// 增加下拉刷新数据的功能
		onPullDownRefresh() {
			this.getIndexData();
		},
		onShareAppMessage: function() {
			return {
				title: '微同开源商城',
				path: '/pages/index/index'
			}
		},
		onLoad: function() {
			this.getIndexData();
		}
	}
</script>

<style lang="scss">
	.banner {
		width: 750rpx;
		height: 417rpx;
	}

	.banner image {
		width: 100%;
		height: 417rpx;
	}

	.m-menu {
		display: flex;
		height: 181rpx;
		width: 750rpx;
		flex-flow: row nowrap;
		align-items: center;
		justify-content: space-between;
		background-color: #fff;
	}

	.m-menu .item {
		flex: 1;
		display: block;
		padding: 20rpx 0;
	}

	.m-menu image {
		display: block;
		width: 58rpx;
		height: 58rpx;
		margin: 0 auto;
		margin-bottom: 12rpx;
	}

	.m-menu text {
		display: block;
		font-size: 24rpx;
		text-align: center;
		margin: 0 auto;
		line-height: 1;
		color: #333;
	}

	.a-section {
		width: 750rpx;
		height: auto;
		overflow: hidden;
		background: #fff;
		color: #333;
		margin-top: 20rpx;
	}

	.a-section .h {
		display: flex;
		flex-flow: row nowrap;
		align-items: center;
		justify-content: center;
		height: 130rpx;
	}

	.a-section .h .txt {
		padding-right: 30rpx;
		background-size: 16.656rpx 27rpx;
		display: inline-block;
		height: 36rpx;
		font-size: 33rpx;
		line-height: 36rpx;
	}

	.a-brand .b {
		width: 750rpx;
		height: auto;
		overflow: hidden;
		position: relative;
	}

	.a-brand .wrap {
		position: relative;
	}

	.a-brand .img {
		position: absolute;
		left: 0;
		top: 0;
	}

	.a-brand .mt {
		position: absolute;
		z-index: 2;
		padding: 27rpx 31rpx;
		left: 0;
		top: 0;
	}

	.a-brand .mt .brand {
		display: block;
		font-size: 33rpx;
		height: 43rpx;
		color: #333;
	}

	.a-brand .mt .price,
	.a-brand .mt .unit {
		font-size: 25rpx;
		color: #999;
	}

	.a-brand .item-1 {
		float: left;
		width: 375rpx;
		height: 252rpx;
		overflow: hidden;
		border-top: 1rpx solid #fff;
		margin-left: 1rpx;
	}

	.a-brand .item-1:nth-child(2n+1) {
		margin-left: 0;
		width: 374rpx;
	}

	.a-brand .item-1 .img {
		width: 375rpx;
		height: 253rpx;
	}

	.a-new .b {
		width: 750rpx;
		height: auto;
		overflow: hidden;
		padding: 0 31rpx 45rpx 31rpx;
	}

	.a-new .b .item {
		float: left;
		width: 302rpx;
		margin-top: 10rpx;
		margin-left: 21rpx;
		margin-right: 21rpx;
	}

	.a-new .b .item-b {
		margin-left: 42rpx;
	}

	.a-new .b .img {
		width: 302rpx;
		height: 302rpx;
	}

	.a-new .b .name {
		text-align: center;
		display: block;
		width: 302rpx;
		height: 35rpx;
		margin-bottom: 14rpx;
		overflow: hidden;
		font-size: 30rpx;
		color: #333;
	}

	.a-new .b .price {
		display: block;
		text-align: center;
		line-height: 30rpx;
		font-size: 30rpx;
		color: #b4282d;
	}

	.a-popular {
		width: 750rpx;
		height: auto;
		overflow: hidden;
	}

	.a-popular .b .item {
		border-top: 1px solid #d9d9d9;
		margin: 0 20rpx;
		height: 264rpx;
		width: 710rpx;
	}

	.a-popular .b .img {
		margin-top: 12rpx;
		margin-right: 12rpx;
		float: left;
		width: 240rpx;
		height: 240rpx;
	}

	.a-popular .b .right {
		float: left;
		height: 264rpx;
		width: 456rpx;
		display: flex;
		flex-flow: row nowrap;
	}

	.a-popular .b .text {
		display: flex;
		flex-wrap: nowrap;
		flex-direction: column;
		justify-content: center;
		overflow: hidden;
		height: 264rpx;
		width: 456rpx;
	}

	.a-popular .b .name {
		width: 456rpx;
		display: block;
		color: #333;
		line-height: 50rpx;
		font-size: 30rpx;
	}

	.a-popular .b .desc {
		width: 456rpx;
		display: block;
		color: #999;
		line-height: 50rpx;
		font-size: 25rpx;
	}

	.a-popular .b .price {
		width: 456rpx;
		display: block;
		color: #b4282d;
		line-height: 50rpx;
		font-size: 33rpx;
	}

	.a-topic .b {
		height: 533rpx;
		width: 750rpx;
		padding: 0 0 48rpx 0;
	}

	.a-topic .b .list {
		height: 533rpx;
		width: 750rpx;
		white-space: nowrap;
	}

	.a-topic .b .item {
		display: inline-block;
		height: 533rpx;
		width: 680.5rpx;
		margin-left: 30rpx;
		overflow: hidden;
	}

	.a-topic .b .item:last-child {
		margin-right: 30rpx;
	}

	.a-topic .b .img {
		height: 387.5rpx;
		width: 680.5rpx;
		margin-bottom: 30rpx;
	}

	.a-topic .b .np {
		height: 35rpx;
		margin-bottom: 13.5rpx;
		color: #333;
		font-size: 30rpx;
	}

	.a-topic .b .np .price {
		margin-left: 20.8rpx;
		color: #b4282d;
	}

	.a-topic .b .desc {
		display: block;
		height: 30rpx;
		color: #999;
		font-size: 24rpx;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.good-grid {
		width: 750rpx;
		height: auto;
		overflow: hidden;
	}

	.good-grid .h {
		display: flex;
		flex-flow: row nowrap;
		align-items: center;
		justify-content: center;
		height: 130rpx;
		font-size: 33rpx;
		color: #333;
	}

	.good-grid .b {
		width: 750rpx;
		padding: 0 6.25rpx;
		height: auto;
		overflow: hidden;
	}

	.good-grid .b .item {
		float: left;
		background: #fff;
		width: 365rpx;
		margin-bottom: 6.25rpx;
		height: 452rpx;
		overflow: hidden;
		text-align: center;
	}

	.good-grid .b .item .a {
		height: 452rpx;
		width: 100%;
	}

	.good-grid .b .item-b {
		margin-left: 6.25rpx;
	}

	.good-grid .item .img {
		margin-top: 20rpx;
		width: 302rpx;
		height: 302rpx;
	}

	.good-grid .item .name {
		display: block;
		width: 365.625rpx;
		padding: 0 20rpx;
		overflow: hidden;
		height: 35rpx;
		margin: 11.5rpx 0 22rpx 0;
		text-align: center;
		font-size: 30rpx;
		color: #333;
	}

	.good-grid .item .price {
		display: block;
		width: 365.625rpx;
		height: 30rpx;
		text-align: center;
		font-size: 30rpx;
		color: #b4282d;
	}

	.good-grid .more-item {
		height: 100%;
		width: 100%;
	}

	.more-a {
		height: 100%;
		width: 100%;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.good-grid .more-a .txt {
		height: 33rpx;
		width: 100%;
		line-height: 33rpx;
		color: #333;
		font-size: 33rpx;
	}

	.good-grid .more-a .icon {
		margin: 60rpx auto 0 auto;
		width: 70rpx;
		height: 70rpx;
	}
</style>
