<template>
	<view>
		<scroll-view class="container" :style="'height: ' + winHeight + 'rpx'" :scroll-y="true">
			<view v-if="!openAttr">
				<swiper class="goodsimgs" indicator-dots="true" autoplay="true" interval="3000" duration="1000">
					<swiper-item v-for="(item, index) in gallery" :key="item.id">
						<image :src="item.img_url" background-size="cover"></image>
					</swiper-item>
				</swiper>
				<view class="service-policy">
					<view class="item">30天无忧退货</view>
					<view class="item">48小时快速退款</view>
					<view class="item">满88元免邮费</view>
				</view>
				<view class="goods-info">
					<view class="c">
						<text class="name">{{goods.name||''}}</text>
						<text class="desc">{{goods.goods_brief||''}}</text>
						<text class="price">￥{{goods.retail_price||'0'}}</text>
						<view class="brand" v-if="brand.name">
							<navigator :url="'../brandDetail/brandDetail?id='+ brand.id">
								<text>{{brand.name}}</text>
							</navigator>
						</view>
					</view>
				</view>
				<view class="section-nav section-attr" @tap="switchAttrPop">
					<view class="t">请选择规格数量</view>
					<image class="i" src="../../static/images/address_right.png" background-size="cover"></image>
				</view>
				<view class="comments" v-if="comment.count > 0">
					<view class="h">
						<navigator :url="'../comment/comment?valueId='+goods.id+'&typeId=0'">
							<text class="t">评价({{comment.count > 999 ? '999+' : comment.count}})</text>
							<text class="i">查看全部</text>
						</navigator>
					</view>
					<view class="b">
						<view class="item">
							<view class="info">
								<view class="user">
									<image :src="comment.data.avatar"></image>
									<text>{{comment.data.nickname}}</text>
								</view>
								<view class="time">{{comment.data.add_time}}</view>
							</view>
							<view class="content">
								{{comment.data.content}}
							</view>
							<view class="imgs" v-if="comment.data.pic_list.length > 0">
								<image class="img" v-for="(item, index) in comment.data.pic_list" :key="index" :src="item.pic_url"></image>
							</view>
							<!-- <view class="spec">白色 2件</view> -->
						</view>
					</view>
				</view>
				<view class="goods-attr">
					<view class="t">商品参数</view>
					<view class="l">
						<view class="item" v-for="(item, index) in attribute" :key="item.name">
							<text class="left">{{item.name}}</text>
							<text class="right">{{item.value}}</text>
						</view>
					</view>
				</view>

				<view class="detail">
					<uParse :content="goods.goods_desc" noData="" />
				</view>

				<view class="common-problem">
					<view class="h">
						<view class="line"></view>
						<text class="title">常见问题</text>
					</view>
					<view class="b">
						<view class="item" v-for="(item, index) in issueList" :key="item.id">
							<view class="question-box">
								<text class="spot"></text>
								<text class="question">{{item.question}}</text>
							</view>
							<view class="answer">
								{{item.answer}}
							</view>
						</view>
					</view>
				</view>

				<view class="related-goods" v-if="relatedGoods.length > 0">
					<view class="h">
						<view class="line"></view>
						<text class="title">大家都在看</text>
					</view>
					<view class="b">
						<view class="item" v-for="(item, index) in relatedGoods" :key="item.id">
							<navigator :url="'/pages/goods/goods?id='+item.id">
								<image class="img" :src="item.list_pic_url" background-size="cover"></image>
								<text class="name">{{item.name}}</text>
								<text class="price">￥{{item.retail_price}}</text>
							</navigator>
						</view>
					</view>
				</view>
			</view>

			<view v-if="openAttr" class="attr-pop">
				<view class="img-info">
					<image class="img" :src="goods.list_pic_url"></image>
					<view class="info">
						<view class="c">
							<view class="p">价格：￥{{goods.retail_price}}</view>
							<view class="a" v-if="productList.length>0">已选择：{{checkedSpecText}}</view>
						</view>
					</view>
				</view>
				<view class="spec-con">
					<view class="spec-item" v-for="(item, index) in specificationList" :key="item.specification_id">
						<view class="name">{{item.name}}</view>
						<view class="values">
							<view :class="'value ' + (vitem.checked ? 'selected' : '')" @tap="clickSkuValue" v-for="(vitem, vindex) in item.valueList"
							 :key="vitem.id" :data-value-id="vitem.id" :data-name-id="vitem.specification_id">{{vitem.value}}</view>
						</view>
					</view>

					<view class="number-item">
						<view class="name">数量</view>
						<view class="selnum">
							<view class="cut" @tap="cutNumber">-</view>
							<input :value="number" class="number" :disabled="true" type="number" />
							<view class="add" @tap="addNumber">+</view>
						</view>
					</view>
				</view>
			</view>
		</scroll-view>

		<view class="bottom-btn">
			<view :class="'l l-collect '+ (openAttr ? 'back' : '')" @tap="closeAttrOrCollect">
				<image class="icon" :src="collectBackImage"></image>
			</view>
			<view class="l l-cart">
				<view class="box">
					<text class="cart-count">{{cartGoodsCount}}</text>
					<image @tap="openCartPage" class="icon" src="/static/images/ic_menu_shoping_nor.png"></image>

				</view>
			</view>
			<view class="c" @tap='buyGoods'>立即购买</view>
			<view class="r" @tap="addToCart">加入购物车</view>
		</view>
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
				winHeight: "",
				id: 0,
				goods: {},
				gallery: [],
				attribute: [],
				issueList: [],
				comment: [],
				brand: {},
				specificationList: [],
				productList: [],
				relatedGoods: [],
				cartGoodsCount: 0,
				userHasCollect: 0,
				number: 1,
				checkedSpecText: '请选择规格数量',
				openAttr: false,
				noCollectImage: "/static/images/icon_collect.png",
				hasCollectImage: "/static/images/icon_collect_checked.png",
				collectBackImage: "/static/images/icon_collect.png"
			}
		},
		methods: {
			getGoodsInfo: function() {
				let that = this;
				util.request(api.GoodsDetail, {
					id: that.id
				}).then(function(res) {
					if (res.errno === 0) {
						that.goods = res.data.info
						that.gallery = res.data.gallery
						that.attribute = res.data.attribute
						that.issueList = res.data.issue
						that.comment = res.data.comment
						that.brand = res.data.brand
						that.specificationList = res.data.specificationList
						that.productList = res.data.productList
						that.userHasCollect = res.data.userHasCollect
						//设置默认值
						that.setDefSpecInfo(that.specificationList);
						if (res.data.userHasCollect == 1) {
							that.collectBackImage = that.hasCollectImage
						} else {
							that.collectBackImage = that.noCollectImage
						}
						that.getGoodsRelated();
					}
				});

			},
			getGoodsRelated: function() {
				let that = this;
				util.request(api.GoodsRelated, {
					id: that.id
				}).then(function(res) {
					if (res.errno === 0) {
						that.relatedGoods = res.data.goodsList
					}
				});

			},
			clickSkuValue: function(event) {
				let that = this;
				let specNameId = event.currentTarget.dataset.nameId;
				let specValueId = event.currentTarget.dataset.valueId;

				//判断是否可以点击

				let _specificationList = this.specificationList;
				for (let i = 0; i < _specificationList.length; i++) {
					if (_specificationList[i].specification_id == specNameId) {
						for (let j = 0; j < _specificationList[i].valueList.length; j++) {
							if (_specificationList[i].valueList[j].id == specValueId) {
								//如果已经选中，则反选
								if (_specificationList[i].valueList[j].checked) {
									_specificationList[i].valueList[j].checked = false;
								} else {
									_specificationList[i].valueList[j].checked = true;
								}
							} else {
								_specificationList[i].valueList[j].checked = false;
							}
						}
					}
				}
				this.specificationList = _specificationList;
				//重新计算spec改变后的信息
				this.changeSpecInfo();

				//重新计算哪些值不可以点击
			},

			//获取选中的规格信息
			getCheckedSpecValue: function() {
				let checkedValues = [];
				let _specificationList = this.specificationList;
				for (let i = 0; i < _specificationList.length; i++) {
					let _checkedObj = {
						nameId: _specificationList[i].specification_id,
						valueId: 0,
						valueText: ''
					};
					for (let j = 0; j < _specificationList[i].valueList.length; j++) {
						if (_specificationList[i].valueList[j].checked) {
							_checkedObj.valueId = _specificationList[i].valueList[j].id;
							_checkedObj.valueText = _specificationList[i].valueList[j].value;
						}
					}
					checkedValues.push(_checkedObj);
				}

				return checkedValues;

			},
			//根据已选的值，计算其它值的状态
			setSpecValueStatus: function() {

			},
			//判断规格是否选择完整
			isCheckedAllSpec: function() {
				return !this.getCheckedSpecValue().some(function(v) {
					if (v.valueId == 0) {
						return true;
					}
				});
			},
			getCheckedSpecKey: function() {
				let checkedValue = this.getCheckedSpecValue().map(function(v) {
					return v.valueId;
				});

				return checkedValue.join('_');
			},
			changeSpecInfo: function() {
				let checkedNameValue = this.getCheckedSpecValue();

				//设置选择的信息
				let checkedValue = checkedNameValue.filter(function(v) {
					if (v.valueId != 0) {
						return true;
					} else {
						return false;
					}
				}).map(function(v) {
					return v.valueText;
				});
				if (checkedValue.length > 0) {
					this.checkedSpecText = checkedValue.join('　');
				} else {
					this.checkedSpecText = '请选择规格数量'
				}

			},
			getCheckedProductItem: function(key) {
				return this.productList.filter(function(v) {
					if (v.goods_specification_ids.indexOf(key) > -1) {
						return true;
					} else {
						return false;
					}
				});
			},
			switchAttrPop: function() {
				if (this.openAttr == false) {
					this.openAttr = !this.openAttr
					this.collectBackImage = "/static/images/detail_back.png"
				}
			},
			closeAttrOrCollect: function() {
				let that = this;
				if (that.openAttr) {
					that.openAttr = false
					if (that.userHasCollect == 1) {
						that.collectBackImage = that.hasCollectImage
					} else {
						that.collectBackImage = that.noCollectImage
					}
				} else {
					//添加或是取消收藏
					util.request(api.CollectAddOrDelete, {
							typeId: 0,
							valueId: that.id
						}, "POST", "application/json")
						.then(function(res) {
							let _res = res;
							if (_res.errno == 0) {
								if (_res.data.type == 'add') {
									that.collectBackImage = that.hasCollectImage
								} else {
									that.collectBackImage = that.noCollectImage
								}

							} else {
								uni.showToast({
									image: '/static/images/icon_error.png',
									title: _res.errmsg,
									mask: true
								});
							}
						});
				}
			},
			openCartPage: function() {
				uni.switchTab({
					url: '/pages/cart/cart',
				});
			},

			/**
			 * 直接购买
			 */
			buyGoods: function() {
				var that = this;
				if (that.openAttr == false) {
					//打开规格选择窗口
					that.openAttr = !that.openAttr
					that.collectBackImage = "/static/images/detail_back.png"
				} else {

					//提示选择完整规格
					if (!that.isCheckedAllSpec()) {
						return false;
					}

					//根据选中的规格，判断是否有对应的sku信息
					let checkedProduct = that.getCheckedProductItem(that.getCheckedSpecKey());
					if (!checkedProduct || checkedProduct.length <= 0) {
						//找不到对应的product信息，提示没有库存
						return false;
					}

					//验证库存
					if (checkedProduct.goods_number < that.number) {
						//找不到对应的product信息，提示没有库存
						return false;
					}

					// 直接购买商品
					util.request(api.BuyAdd, {
							goodsId: that.goods.id,
							number: that.number,
							productId: checkedProduct[0].id
						}, "POST", 'application/json')
						.then(function(res) {
							let _res = res;
							if (_res.errno == 0) {
								that.openAttr = !that.openAttr
								uni.navigateTo({
									url: '/pages/shopping/checkout/checkout?isBuy=true',
								})
							} else {
								uni.showToast({
									image: '/static/images/icon_error.png',
									title: _res.errmsg,
									mask: true
								});
							}

						});
				}
			},

			/**
			 * 添加到购物车
			 */
			addToCart: function() {
				var that = this;
				if (that.openAttr == false) {
					//打开规格选择窗口
					that.openAttr = !that.openAttr
					that.collectBackImage = "/static/images/detail_back.png"
				} else {

					//提示选择完整规格
					if (!that.isCheckedAllSpec()) {
						uni.showToast({
							title: '请选择完整规格'
						});
						return false;
					}

					//根据选中的规格，判断是否有对应的sku信息
					let checkedProduct = that.getCheckedProductItem(that.getCheckedSpecKey());
					if (!checkedProduct || checkedProduct.length <= 0) {
						//找不到对应的product信息，提示没有库存
						return false;
					}

					//验证库存
					if (checkedProduct.goods_number < that.number) {
						//找不到对应的product信息，提示没有库存
						return false;
					}

					//添加到购物车
					util.request(api.CartAdd, {
						goodsId: that.goods.id,
						number: that.number,
						productId: checkedProduct[0].id
					}, 'POST', 'application/json').then(function(res) {
						let _res = res;
						if (_res.errno == 0) {
							uni.showToast({
								title: '添加成功'
							});
							that.openAttr = !that.openAttr
							that.cartGoodsCount = _res.data.cartTotal.goodsCount
							if (that.userHasCollect == 1) {
								that.collectBackImage = that.hasCollectImage
							} else {
								that.collectBackImage = that.noCollectImage
							}
						} else {
							uni.showToast({
								image: '/static/images/icon_error.png',
								title: _res.errmsg,
								mask: true
							});
						}

					});
				}

			},
			cutNumber: function() {
				this.number = (this.number - 1 > 1) ? this.number - 1 : 1
			},
			addNumber: function() {
				this.number = this.number + 1
			},
			setDefSpecInfo: function(specificationList) {
				//未考虑规格联动情况
				let that = this;
				if (!specificationList) return;
				for (let i = 0; i < specificationList.length; i++) {
					let specification = specificationList[i];
					let specNameId = specification.specification_id;
					//规格只有一个时自动选择规格
					if (specification.valueList && specification.valueList.length == 1) {
						let specValueId = specification.valueList[0].id;
						that.clickSkuValue({
							currentTarget: {
								dataset: {
									"nameId": specNameId,
									"valueId": specValueId
								}
							}
						});
					}
				}
				specificationList.map(function(item) {

				});

			}
		},
		onLoad: function(options) {
			// 页面初始化 options为页面跳转所带来的参数
			this.id = parseInt(options.id)
			var that = this;
			this.getGoodsInfo();
			util.request(api.CartGoodsCount).then(function(res) {
				if (res.errno === 0) {
					that.cartGoodsCount = res.data.cartTotal.goodsCount
				}
			});

			var that = this
			//  高度自适应
			uni.getSystemInfo({
				success: function(res) {
					var clientHeight = res.windowHeight,
						clientWidth = res.windowWidth,
						rpxR = 750 / clientWidth;
					var calc = clientHeight * rpxR - 100;
					that.winHeight = calc
				}
			});
		}
	}
</script>

<style lang="scss">
	.container {
		/* margin-bottom: 100rpx; */
	}

	.goodsimgs {
		width: 750rpx;
		height: 750rpx;
	}

	.goodsimgs image {
		width: 750rpx;
		height: 750rpx;
	}

	.service-policy {
		width: 750rpx;
		height: 73rpx;
		background: #f4f4f4;
		padding: 0 31.25rpx;
		display: flex;
		flex-flow: row nowrap;
		align-items: center;
		justify-content: space-between;
	}

	.service-policy .item {
		background: url(http://nos.netease.com/mailpub/hxm/yanxuan-wap/p/20150730/style/img/icon-normal/servicePolicyRed-518d32d74b.png) 0 center no-repeat;
		background-size: 10rpx;
		padding-left: 15rpx;
		display: flex;
		align-items: center;
		font-size: 25rpx;
		color: #666;
	}

	.goods-info {
		width: 750rpx;
		height: 306rpx;
		overflow: hidden;
		background: #fff;
	}

	.goods-info .c {
		display: block;
		width: 718.75rpx;
		height: 100%;
		margin-left: 31.25rpx;
		padding: 38rpx 31.25rpx 38rpx 0;
		border-bottom: 1px solid #f4f4f4;
	}

	.goods-info .c text {
		display: block;
		width: 687.5rpx;
		text-align: center;
	}

	.goods-info .name {
		height: 41rpx;
		margin-bottom: 5.208rpx;
		font-size: 41rpx;
		line-height: 41rpx;
	}

	.goods-info .desc {
		height: 43rpx;
		margin-bottom: 41rpx;
		font-size: 24rpx;
		line-height: 36rpx;
		color: #999;
	}


	.goods-info .price {
		height: 35rpx;
		font-size: 35rpx;
		line-height: 35rpx;
		color: #b4282d;
	}

	.goods-info .brand {
		margin-top: 23rpx;
		min-height: 40rpx;
		text-align: center;
	}

	.goods-info .brand text {
		display: inline-block;
		width: auto;
		padding: 2px 30rpx 2px 10.5rpx;
		line-height: 35.5rpx;
		border: 1px solid #f48f18;
		font-size: 25rpx;
		color: #f48f18;
		border-radius: 4px;
		background: url(http://nos.netease.com/mailpub/hxm/yanxuan-wap/p/20150730/style/img/icon-normal/detailTagArrow-18bee52dab.png) 95% center no-repeat;
		background-size: 10.75rpx 18.75rpx;
	}

	.section-nav {
		width: 750rpx;
		height: 108rpx;
		background: #fff;
		margin-bottom: 20rpx;
	}

	.section-nav .t {
		float: left;
		width: 600rpx;
		height: 108rpx;
		line-height: 108rpx;
		font-size: 29rpx;
		color: #333;
		margin-left: 31.25rpx;
	}

	.section-nav .i {
		float: right;
		width: 52rpx;
		height: 52rpx;
		margin-right: 16rpx;
		margin-top: 28rpx;
	}

	.section-act .t {
		float: left;
		display: flex;
		align-items: center;
		width: 600rpx;
		height: 108rpx;
		overflow: hidden;
		line-height: 108rpx;
		font-size: 29rpx;
		color: #999;
		margin-left: 31.25rpx;
	}

	.section-act .label {
		color: #999;
	}

	.section-act .tag {
		display: flex;
		align-items: center;
		padding: 0 10rpx;
		border-radius: 3px;
		height: 37rpx;
		width: auto;
		color: #f48f18;
		overflow: hidden;
		border: 1px solid #f48f18;
		font-size: 25rpx;
		margin: 0 10rpx;
	}

	.section-act .text {
		display: flex;
		align-items: center;
		height: 37rpx;
		width: auto;
		overflow: hidden;
		color: #f48f18;
		font-size: 29rpx;
	}

	.comments {
		width: 100%;
		height: auto;
		padding-left: 30rpx;
		background: #fff;
		margin: 20rpx 0;
	}

	.comments .h {
		height: 102.5rpx;
		line-height: 100.5rpx;
		width: 718.75rpx;
		padding-right: 16rpx;
		border-bottom: 1px solid #d9d9d9;
	}

	.comments .h .t {
		display: block;
		float: left;
		width: 50%;
		font-size: 38.5rpx;
		color: #333;
	}

	.comments .h .i {
		display: block;
		float: right;
		width: 164rpx;
		height: 100.5rpx;
		line-height: 100.5rpx;
		background: url(http://nos.netease.com/mailpub/hxm/yanxuan-wap/p/20150730/style/img/icon-normal/address-right-990628faa7.png) right center no-repeat;
		background-size: 52rpx;
	}

	.comments .b {
		height: auto;
		width: 720rpx;
	}

	.comments .item {
		height: auto;
		width: 720rpx;
		overflow: hidden;
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

	.comments .content {
		width: 720rpx;
		padding-right: 30rpx;
		line-height: 45.8rpx;
		font-size: 29rpx;
		margin-bottom: 24rpx;
	}

	.comments .imgs {
		width: 720rpx;
		height: auto;
		margin-bottom: 25rpx;
	}

	.comments .imgs .img {
		height: 150rpx;
		width: 150rpx;
		margin-right: 28rpx;
	}


	.comments .spec {
		width: 720rpx;
		padding-right: 30rpx;
		line-height: 30rpx;
		font-size: 24rpx;
		color: #999;
		margin-bottom: 30rpx;
	}


	.goods-attr {
		width: 750rpx;
		height: auto;
		overflow: hidden;
		padding: 0 31.25rpx 25rpx 31.25rpx;
		background: #fff;
	}

	.goods-attr .t {
		width: 687.5rpx;
		height: 104rpx;
		line-height: 104rpx;
		font-size: 38.5rpx;
	}

	.goods-attr .item {
		width: 687.5rpx;
		height: 68rpx;
		padding: 11rpx 20rpx;
		margin-bottom: 11rpx;
		background: #f7f7f7;
		font-size: 38.5rpx;
	}

	.goods-attr .left {
		float: left;
		font-size: 25rpx;
		width: 134rpx;
		height: 45rpx;
		line-height: 45rpx;
		overflow: hidden;
		color: #999;
	}

	.goods-attr .right {
		float: left;
		font-size: 36.5rpx;
		margin-left: 20rpx;
		width: 480rpx;
		height: 45rpx;
		line-height: 45rpx;
		overflow: hidden;
		color: #333;
	}

	.detail {
		width: 750rpx;
		height: auto;
		overflow: hidden;
	}

	.detail image {
		width: 750rpx;
		display: block;
	}


	.common-problem {
		width: 750rpx;
		height: auto;
		overflow: hidden;
	}

	.common-problem .h {
		position: relative;
		height: 145.5rpx;
		width: 750rpx;
		padding: 56.25rpx 0;
		background: #fff;
		text-align: center;
	}

	.common-problem .h .line {
		display: inline-block;
		position: absolute;
		top: 72rpx;
		left: 0;
		z-index: 2;
		height: 1px;
		margin-left: 225rpx;
		width: 300rpx;
		background: #ccc;
	}

	.common-problem .h .title {
		display: inline-block;
		position: absolute;
		top: 56.125rpx;
		left: 0;
		z-index: 3;
		height: 33rpx;
		margin-left: 285rpx;
		width: 180rpx;
		background: #fff;
	}

	.common-problem .b {
		width: 750rpx;
		height: auto;
		overflow: hidden;
		padding: 0rpx 30rpx;
		background: #fff;

	}

	.common-problem .item {
		height: auto;
		overflow: hidden;
		padding-bottom: 25rpx;
	}

	.common-problem .question-box .spot {
		float: left;
		display: block;
		height: 8rpx;
		width: 8rpx;
		background: #b4282d;
		border-radius: 50%;
		margin-top: 11rpx;
	}

	.common-problem .question-box .question {
		float: left;
		line-height: 30rpx;
		padding-left: 8rpx;
		display: block;
		font-size: 26rpx;
		padding-bottom: 15rpx;
		color: #303030;
	}

	.common-problem .answer {
		line-height: 36rpx;
		padding-left: 16rpx;
		font-size: 26rpx;
		color: #787878;
	}


	.related-goods {
		width: 750rpx;
		height: auto;
		overflow: hidden;
	}

	.related-goods .h {
		position: relative;
		height: 145.5rpx;
		width: 750rpx;
		padding: 56.25rpx 0;
		background: #fff;
		text-align: center;
		border-bottom: 1px solid #f4f4f4;
	}

	.related-goods .h .line {
		display: inline-block;
		position: absolute;
		top: 72rpx;
		left: 0;
		z-index: 2;
		height: 1px;
		margin-left: 225rpx;
		width: 300rpx;
		background: #ccc;
	}

	.related-goods .h .title {
		display: inline-block;
		position: absolute;
		top: 56.125rpx;
		left: 0;
		z-index: 3;
		height: 33rpx;
		margin-left: 285rpx;
		width: 180rpx;
		background: #fff;
	}

	.related-goods .b {
		width: 750rpx;
		height: auto;
		overflow: hidden;
	}

	.related-goods .b .item {
		float: left;
		background: #fff;
		width: 375rpx;
		height: auto;
		overflow: hidden;
		text-align: center;
		padding: 15rpx 31.25rpx;
		border-right: 1px solid #f4f4f4;
		border-bottom: 1px solid #f4f4f4;
	}

	.related-goods .item .img {
		width: 311.45rpx;
		height: 311.45rpx;
	}

	.related-goods .item .name {
		display: block;
		width: 311.45rpx;
		height: 35rpx;
		margin: 11.5rpx 0 15rpx 0;
		text-align: center;
		overflow: hidden;
		font-size: 30rpx;
		color: #333;
	}

	.related-goods .item .price {
		display: block;
		width: 311.45rpx;
		height: 30rpx;
		text-align: center;
		font-size: 30rpx;
		color: #b4282d;
	}

	.bottom-btn {
		position: fixed;
		left: 0;
		bottom: 0;
		z-index: 10;
		width: 750rpx;
		height: 100rpx;
		display: flex;
		background: #fff;
	}

	.bottom-btn .l {
		float: left;
		height: 100rpx;
		width: 162rpx;
		border: 1px solid #f4f4f4;
		display: flex;
		align-items: center;
		justify-content: center;

	}

	.bottom-btn .l.l-collect {
		border-right: none;
		border-left: none;
		text-align: center;
	}


	.bottom-btn .l.l-cart .box {
		position: relative;
		height: 60rpx;
		width: 60rpx;
	}

	.bottom-btn .l.l-cart .cart-count {
		height: 28rpx;
		width: 28rpx;
		z-index: 10;
		position: absolute;
		top: 0;
		right: 0;
		background: #b4282d;
		text-align: center;
		font-size: 18rpx;
		color: #fff;
		line-height: 28rpx;
		border-radius: 50%;

	}

	.bottom-btn .l.l-cart .icon {

		position: absolute;
		top: 10rpx;
		left: 0;

	}


	.bottom-btn .l .icon {
		display: block;
		height: 44rpx;
		width: 44rpx;
	}


	.bottom-btn .c {
		float: left;
		height: 100rpx;
		line-height: 96rpx;
		flex: 1;
		text-align: center;
		color: #333;
		border-top: 1px solid #f4f4f4;
		border-bottom: 1px solid #f4f4f4;
	}

	.bottom-btn .r {
		border: 1px solid #b4282d;
		background: #b4282d;
		float: left;
		height: 100rpx;
		line-height: 96rpx;
		flex: 1;
		text-align: center;
		color: #fff;
	}

	.attr-pop {
		width: 100%;
		height: 100%;
		padding: 31.25rpx;
		background: #fff;
	}

	.attr-pop .img-info {
		width: 687.5rpx;
		height: 177rpx;
		overflow: hidden;
		margin-bottom: 41.5rpx;
	}

	.attr-pop .img {
		float: left;
		height: 177rpx;
		width: 177rpx;
		background: #f4f4f4;
		margin-right: 31.25rpx;
	}

	.attr-pop .info {
		float: left;
		height: 177rpx;
		display: flex;
		align-items: center;
	}

	.attr-pop .p {
		font-size: 33rpx;
		color: #333;
		height: 33rpx;
		line-height: 33rpx;
		margin-bottom: 10rpx;
		overflow: hidden;
	}

	.attr-pop .a {
		font-size: 29rpx;
		color: #333;
		height: 40rpx;
		line-height: 40rpx;
	}

	.spec-con {
		width: 100%;
		height: auto;
		overflow: hidden;
	}

	.spec-con .name {
		margin-bottom: 22rpx;
		font-size: 29rpx;
		color: #333;
	}

	.spec-con .values {
		height: auto;
		margin-bottom: 31.25rpx;
		font-size: 0;
	}

	.spec-con .value {
		display: inline-block;
		height: 62rpx;
		padding: 0 35rpx;
		line-height: 56rpx;
		text-align: center;
		margin-right: 25rpx;
		margin-bottom: 16.5rpx;
		border: 1px solid #333;
		font-size: 25rpx;
		color: #333;
	}

	.spec-con .value.disable {
		border: 1px solid #ccc;
		color: #ccc;
	}

	.spec-con .value.selected {
		border: 1px solid #b4282d;
		color: #b4282d;
	}

	.number-item .selnum {
		width: 322rpx;
		height: 71rpx;
		border: 1px solid #ccc;
		display: flex;
	}

	.number-item .cut {
		width: 93.75rpx;
		height: 100%;
		text-align: center;
		line-height: 65rpx;
	}

	.number-item .number {
		flex: 1;
		height: 100%;
		text-align: center;
		line-height: 68.75rpx;
		border-left: 1px solid #ccc;
		border-right: 1px solid #ccc;
		float: left;
	}

	.number-item .add {
		width: 93.75rpx;
		height: 100%;
		text-align: center;
		line-height: 65rpx;
	}
</style>
