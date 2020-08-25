<template>
	<scroll-view class="container" style="height: 100%;">
		<view class="search-header">
			<view class="input-box">
				<image class="icon" src="http://nos.netease.com/mailpub/hxm/yanxuan-wap/p/20150730/style/img/icon-normal/search2-2fb94833aa.png"></image>
				<input name="input" class="keywrod" :focus="true" v-model="keyword" confirm-type="search" @input="inputChange"
				 @focus="inputFocus" @confirm="onKeywordConfirm" :placeholder="defaultKeyword.keyword" />
				<image class="del" v-if="keyword" @tap="clearKeyword" src="http://nos.netease.com/mailpub/hxm/yanxuan-wap/p/20150730/style/img/icon-normal/clearIpt-f71b83e3c2.png"></image>
			</view>
			<view class="right" @tap="closeSearch">取消</view>
		</view>
		<view class="no-search" v-if="!searchStatus">
			<view class="serach-keywords search-history" v-if="!keyword  && historyKeyword.length">
				<view class="h">
					<text class="title">历史记录</text>
					<image class="icon" @tap="clearHistory" src="http://nos.netease.com/mailpub/hxm/yanxuan-wap/p/20150730/style/img/icon-normal/del1-93f0a4add4.png"></image>
				</view>
				<view class="b">
					<view class="item" @tap="onKeywordTap" :data-keyword="item" v-for="(item, index) in historyKeyword" :key="index"
					 hover-class="navigator-hover">{{item}}</view>
				</view>
			</view>
			<view class="serach-keywords search-hot" v-if="!keyword">
				<view class="h">
					<text class="title">热门搜索</text>
				</view>
				<view class="b">
					<view :class="'item '+(item.is_hot === 1 ? 'active' : '')" hover-class="navigator-hover" @tap="onKeywordTap"
					 :data-keyword="item.keyword" v-for="(item, index) in hotKeyword" :key="index">{{item.keyword}}</view>
				</view>
			</view>
			<view class="shelper-list" v-if="keyword">
				<view class="item" hover-class="navigator-hover" v-for="(item, index) in helpKeyword" :key="index" @tap="onKeywordTap"
				 :data-keyword="item">{{item}}</view>
			</view>
		</view>

		<view class="search-result" v-if="searchStatus && goodsList.length">
			<view class="sort">
				<view class="sort-box">
					<view :class="'item ' + (currentSortType == 'default' ? 'active' : '')" @tap="openSortFilter" id="defaultSort">
						<text class="txt">综合</text>
					</view>
					<view :class="'item by-price ' + (currentSortType == 'price' ? 'active' : '') + (currentSortOrder == 'asc'  ? ' asc' : ' desc')"
					 @tap="openSortFilter" id="priceSort">
						<text class="txt">价格</text>
					</view>
					<view :class="'item '+(currentSortType == 'category' ? 'active' : '')" @tap="openSortFilter" id="categoryFilter">
						<text class="txt">分类</text>
					</view>
				</view>
				<view class="sort-box-category" v-if="categoryFilter">
					<view :class="'item '+(item.checked ? 'active' : '')" v-for="(item, index) in filterCategory" :key="item.id"
					 :data-category-index="index" @tap="selectCategory">{{item.name}}</view>
				</view>
			</view>
			<view class="cate-item">
				<view class="b">
					<navigator :class="'item ' + ((iindex + 1) % 2 == 0 ? 'item-b' : '')" :url="'/pages/goods/goods?id='+iitem.id"
					 v-for="(iitem, iindex) in goodsList" :key="iidex">
						<image class="img" :src="iitem.list_pic_url" background-size="cover"></image>
						<text class="name">{{iitem.name}}</text>
						<text class="price">￥{{iitem.retail_price}}</text>
					</navigator>
				</view>
			</view>
		</view>

		<view class="search-result-empty" v-if="!goodsList.length && searchStatus">
			<image class="icon" src="http://yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/icon-normal/noSearchResult-7572a94f32.png"></image>
			<text class="text">您寻找的商品还未上架</text>
		</view>
	</scroll-view>
</template>

<script>
	const util = require("@/utils/util.js");
	const api = require('@/utils/api.js');
	export default {
		data() {
			return {
				keywrod: '',
				searchStatus: false,
				goodsList: [],
				helpKeyword: [],
				historyKeyword: [],
				categoryFilter: false,
				currentSortType: 'default',
				currentSortOrder: '',
				filterCategory: [],
				defaultKeyword: {},
				hotKeyword: [],
				page: 1,
				size: 20,
				currentSortType: 'id',
				currentSortOrder: 'desc',
				categoryId: 0
			}
		},
		methods: {
			closeSearch: function() {
				uni.navigateBack()
			},
			clearKeyword: function() {
				this.keyword = ''
				this.searchStatus = false
			},
			getSearchKeyword() {
				let that = this;
				util.request(api.SearchIndex).then(function(res) {
					if (res.errno === 0) {
						that.historyKeyword = res.data.historyKeywordList
						that.defaultKeyword = res.data.defaultKeyword
						that.hotKeyword = res.data.hotKeywordList
					}
				});
			},

			inputChange: function(e) {
				this.keyword = e.detail.value
				this.searchStatus = false
				this.getHelpKeyword();
			},
			getHelpKeyword: function() {
				let that = this;
				util.request(api.SearchHelper, {
					keyword: that.keyword
				}).then(function(res) {
					if (res.errno === 0) {
						that.helpKeyword = res.data;
					}
				});
			},
			inputFocus: function() {
				this.searchStatus = false
				this.goodsList = []
				if (this.keyword) {
					this.getHelpKeyword();
				}
			},
			clearHistory: function() {
				this.historyKeyword = []

				util.request(api.SearchClearHistory)
			},
			getGoodsList: function() {
				let that = this;
				util.request(api.GoodsList, {
					keyword: that.keyword,
					page: that.page,
					size: that.size,
					sort: that.currentSortType,
					order: that.currentSortOrder,
					categoryId: that.categoryId
				}).then(function(res) {
					if (res.errno === 0) {
						that.searchStatus = true
						that.categoryFilter = false
						that.goodsList = res.data.data
						that.filterCategory = res.data.filterCategory
						that.page = res.data.currentPage
						that.size = res.data.numsPerPage
					}

					//重新获取关键词
					that.getSearchKeyword();
				});
			},
			onKeywordTap: function(event) {

				this.getSearchResult(event.target.dataset.keyword);

			},
			getSearchResult(keyword) {
				this.keyword = keyword
				this.page = 1
				this.categoryId = 0
				this.goodsList = []

				this.getGoodsList();
			},
			openSortFilter: function(event) {
				let that = this;
				let currentId = event.currentTarget.id;
				switch (currentId) {
					case 'categoryFilter':
						that.categoryFilter = !that.categoryFilter
						that.currentSortOrder = 'asc'
						break;
					case 'priceSort':
						let tmpSortOrder = 'asc';
						if (that.currentSortOrder == 'asc') {
							tmpSortOrder = 'desc';
						}
						that.currentSortType = 'price'
						that.currentSortOrder = tmpSortOrder
						that.categoryFilter = false

						that.getGoodsList();
						break;
					default:
						//综合排序
						that.currentSortType = 'default'
						that.currentSortOrder = 'desc'
						that.categoryFilter = false
						that.getGoodsList();
				}
			},
			selectCategory: function(event) {
				let currentIndex = event.target.dataset.categoryIndex;
				let filterCategory = this.filterCategory;
				let currentCategory = null;
				for (let key in filterCategory) {
					if (key == currentIndex) {
						filterCategory[key].selected = true;
						currentCategory = filterCategory[key];
					} else {
						filterCategory[key].selected = false;
					}
				}
				
				this.filterCategory = filterCategory
				this.categoryFilter = false
				this.categoryId = currentCategory.id
				this.page = 1
				this.goodsList = []
				this.getGoodsList();
			},
			onKeywordConfirm(event) {
				this.getSearchResult(event.detail.value);
			}
		},
		onLoad: function() {
			this.getSearchKeyword();
		}
	}
</script>

<style lang="scss">
	page {
		min-height: 100%;
		background-color: #f4f4f4;
	}

	.container {
		min-height: 100%;
		background-color: #f4f4f4;
	}

	.search-header {
		position: fixed;
		top: 0;
		width: 750rpx;
		height: 91rpx;
		display: flex;
		background: #fff;
		border-bottom: 1px solid rgba(0, 0, 0, .15);
		padding: 0 31.25rpx;
		font-size: 29rpx;
		color: #333;
	}

	.search-header .input-box {
		position: relative;
		margin-top: 16rpx;
		float: left;
		width: 0;
		flex: 1;
		height: 59rpx;
		line-height: 59rpx;
		padding: 0 20rpx;
		background: #f4f4f4;
	}

	.search-header .icon {
		position: absolute;
		top: 14rpx;
		left: 20rpx;
		width: 31rpx;
		height: 31rpx;
	}

	.search-header .del {
		position: absolute;
		top: 3rpx;
		right: 10rpx;
		width: 53rpx;
		height: 53rpx;
		z-index: 10;
	}

	.search-header .keywrod {
		position: absolute;
		top: 0;
		left: 40rpx;
		width: 506rpx;
		height: 59rpx;
		padding-left: 30rpx;
	}

	.search-header .right {
		margin-top: 24rpx;
		margin-left: 31rpx;
		margin-right: 6rpx;
		width: 58rpx;
		height: 43rpx;
		line-height: 43rpx;
		float: right;
	}

	.no-search {
		height: auto;
		overflow: hidden;
		margin-top: 91rpx;
	}

	.serach-keywords {
		background: #fff;
		width: 750rpx;
		height: auto;
		overflow: hidden;
		margin-bottom: 20rpx;
	}

	.serach-keywords .h {
		padding: 0 31.25rpx;
		height: 93rpx;
		line-height: 93rpx;
		width: 100%;
		color: #999;
		font-size: 29rpx;
	}

	.serach-keywords .title {
		display: block;
		width: 120rpx;
		float: left;
	}

	.serach-keywords .icon {
		margin-top: 19rpx;
		float: right;
		display: block;
		margin-left: 511rpx;
		height: 55rpx;
		width: 55rpx;
	}

	.serach-keywords .b {
		width: 750rpx;
		height: auto;
		overflow: hidden;
		padding-left: 31.25rpx;
	}

	.serach-keywords .item {
		display: inline-block;
		width: auto;
		height: 48rpx;
		line-height: 48rpx;
		padding: 0 15rpx;
		border: 1px solid #999;
		margin: 0 31.25rpx 31.25rpx 0;
		font-size: 24rpx;
		color: #333;
	}

	.serach-keywords .item.active {
		color: #b4282d;
		border: 1px solid #b4282d;
	}

	.shelper-list {
		width: 750rpx;
		height: auto;
		overflow: hidden;
		background: #fff;
		padding: 0 31.25rpx;
	}

	.shelper-list .item {
		height: 93rpx;
		width: 687.5rpx;
		line-height: 93rpx;
		font-size: 24rpx;
		color: #333;
		border-bottom: 1px solid #f4f4f4;
	}


	.sort {
		position: fixed;
		top: 91rpx;
		background: #fff;
		width: 100%;
		height: 78rpx;
	}

	.sort-box {
		background: #fff;
		width: 100%;
		height: 78rpx;
		overflow: hidden;
		padding: 0 30rpx;
		display: flex;
		border-bottom: 1px solid #d9d9d9;
	}

	.sort-box .item {
		height: 78rpx;
		line-height: 78rpx;
		text-align: center;
		flex: 1;
		color: #333;
		font-size: 30rpx;
	}

	.sort-box .item .txt {
		display: block;
		width: 100%;
		height: 100%;
		color: #333;
	}

	.sort-box .item.active .txt {
		color: #b4282d;
	}

	.sort-box .item.by-price {
		background: url(//yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/icon-normal/no-3127092a69.png) 155rpx center no-repeat;
		background-size: 15rpx 21rpx;
	}

	.sort-box .item.by-price.active.asc {
		background: url(http://yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/icon-normal/up-636b92c0a5.png) 155rpx center no-repeat;
		background-size: 15rpx 21rpx;
	}

	.sort-box .item.by-price.active.desc {
		background: url(http://yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/icon-normal/down-95e035f3e5.png) 155rpx center no-repeat;
		background-size: 15rpx 21rpx;
	}

	.sort-box-category {
		background: #fff;
		width: 100%;
		height: auto;
		overflow: hidden;
		padding: 40rpx 40rpx 0 0;
		border-bottom: 1px solid #d9d9d9;
	}

	.sort-box-category .item {
		height: 54rpx;
		line-height: 54rpx;
		text-align: center;
		float: left;
		padding: 0 16rpx;
		margin: 0 0 40rpx 40rpx;
		border: 1px solid #666;
		color: #333;
		font-size: 24rpx;
	}

	.sort-box-category .item.active {
		color: #b4282d;
		border: 1px solid #b4282d;
	}

	.cate-item {
		margin-top: 175rpx;
		height: auto;
		overflow: hidden;
	}

	.cate-item .h {
		height: 145rpx;
		width: 750rpx;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.cate-item .h .name {
		display: block;
		height: 35rpx;
		margin-bottom: 18rpx;
		font-size: 30rpx;
		color: #333;
	}

	.cate-item .h .desc {
		display: block;
		height: 24rpx;
		font-size: 24rpx;
		color: #999;
	}

	.cate-item .b {
		width: 750rpx;
		padding: 0 6.25rpx;
		height: auto;
		overflow: hidden;
	}

	.cate-item .list-filter {
		height: 80rpx;
		width: 100%;
		background: #fff;
		margin-bottom: 6.25rpx;
	}

	.cate-item .b .item {
		float: left;
		background: #fff;
		width: 365rpx;
		margin-bottom: 6.25rpx;
		padding-bottom: 33.333rpx;
		height: auto;
		overflow: hidden;
		text-align: center;
	}

	.cate-item .b .item-b {
		margin-left: 6.25rpx;
	}

	.cate-item .item .img {
		width: 302rpx;
		height: 302rpx;
	}

	.cate-item .item .name {
		display: block;
		width: 365.625rpx;
		height: 35rpx;
		margin: 11.5rpx 0 22rpx 0;
		text-align: center;
		overflow: hidden;
		padding: 0 20rpx;
		font-size: 30rpx;
		color: #333;
	}

	.cate-item .item .price {
		display: block;
		width: 365.625rpx;
		height: 30rpx;
		text-align: center;
		font-size: 30rpx;
		color: #b4282d;
	}

	.search-result-empty {
		width: 100%;
		height: 100%;
		padding-top: 300rpx;
	}

	.search-result-empty .icon {
		margin: 0 auto;
		display: block;
		width: 240rpx;
		height: 240rpx;
	}

	.search-result-empty .text {
		display: block;
		width: 100%;
		height: 40rpx;
		font-size: 28rpx;
		text-align: center;
		color: #999;
	}
</style>
