<template>
	<view class="container">
		<view class="orders" v-if="orderList.length>0">
			<navigator :url="'../orderDetail/orderDetail?id='+item.id" class="order" v-for="(item, index) in orderList" :key="item.id">
				<view class="h">
					<view class="l">订单编号：{{item.order_sn||''}}</view>
					<view class="r">{{item.order_status_text||''}}</view>
				</view>
				<view class="goods" v-for="(gitem, gindex) in item.goodsList" :key="gitem.id">
					<view class="img">
						<image :src="gitem.list_pic_url"></image>
					</view>
					<view class="info">
						<text class="name">{{gitem.goods_name||''}}</text>
						<text class="number">共{{gitem.number||0}}件商品</text>
					</view>
					<view class="status"></view>
				</view>
				<view class="b">
					<view class="l">实付：￥{{item.actual_price||''}}</view>
					<view class="r">
						<button class="btn" :data-order-index="index" @click.stop.prevent="payOrder" v-if="item.handleOption.pay">立即支付</button>
					</view>
				</view>
			</navigator>
		</view>
		<tui-show-empty v-else text="暂无订单"></tui-show-empty>
	</view>
</template>

<script>
	const util = require("@/utils/util.js");
	const api = require('@/utils/api.js');
	export default {
		data() {
			return {
				orderList: [],
				page: 1,
				size: 10,
				totalPages: 1
			}
		},
		methods: {
			getOrderList() {
				let that = this;

				util.request(api.OrderList, {
					page: that.page,
					size: that.size
				}).then(function(res) {
					if (res.errno === 0) {
						that.orderList = that.orderList.concat(res.data.data)
						that.page = res.data.currentPage + 1
						that.totalPages = res.data.totalPages
					}
				});
			},
			payOrder(event) {
				let that = this;
				let orderIndex = event.currentTarget.dataset.orderIndex;
				let order = that.orderList[orderIndex];
				util.payOrder(parseInt(order.id)).then(res => {
					that.getOrderList();
				}).catch(res => {
					util.toast('支付失败');
				});
			}
		},
		/**
		 * 页面上拉触底事件的处理函数
		 */
		onReachBottom: function() {
			this.getOrderList()
		},
		onShow: function(options) {
			this.getOrderList();
		}
	}
</script>

<style lang="scss">
	page {
		height: 100%;
		width: 100%;
		background: #f4f4f4;
	}

	.orders {
		height: auto;
		width: 100%;
		overflow: hidden;
	}

	.order {
		margin-top: 20rpx;
		background: #fff;
	}

	.order .h {
		height: 83.3rpx;
		line-height: 83.3rpx;
		margin-left: 31.25rpx;
		padding-right: 31.25rpx;
		border-bottom: 1px solid #f4f4f4;
		font-size: 30rpx;
		color: #333;
	}

	.order .h .l {
		float: left;
	}

	.order .h .r {
		float: right;
		color: #b4282d;
		font-size: 24rpx;
	}

	.order .goods {
		display: flex;
		align-items: center;
		height: 199rpx;
		margin-left: 31.25rpx;
	}

	.order .goods .img {
		height: 145.83rpx;
		width: 145.83rpx;
		background: #f4f4f4;
	}

	.order .goods .img image {
		height: 145.83rpx;
		width: 145.83rpx;
	}

	.order .goods .info {
		height: 145.83rpx;
		flex: 1;
		padding-left: 20rpx;
	}

	.order .goods .name {
		margin-top: 30rpx;
		display: block;
		height: 44rpx;
		line-height: 44rpx;
		color: #333;
		font-size: 30rpx;
	}

	.order .goods .number {
		display: block;
		height: 37rpx;
		line-height: 37rpx;
		color: #666;
		font-size: 25rpx;
	}

	.order .goods .status {
		width: 105rpx;
		color: #b4282d;
		font-size: 25rpx;
	}

	.order .b {
		height: 103rpx;
		line-height: 103rpx;
		margin-left: 31.25rpx;
		padding-right: 31.25rpx;
		border-top: 1px solid #f4f4f4;
		font-size: 30rpx;
		color: #333;
	}

	.order .b .l {
		float: left;
	}

	.order .b .r {
		float: right;
	}

	.order .b .btn {
		margin-top: 19rpx;
		height: 64.5rpx;
		line-height: 64.5rpx;
		text-align: center;
		padding: 0 20rpx;
		border-radius: 5rpx;
		font-size: 26rpx;
		color: #fff;
		background: #b4282d;
	}
</style>
