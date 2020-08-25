<template>
	<view class="add-address">
		<view class="add-form">
			<view class="form-item">
				<input class="input" placeholder="姓名" v-model="address.userName" auto-focus />
			</view>
			<view class="form-item">
				<input class="input" v-model="address.telNumber" maxlength="15" type="number" placeholder="手机号码" />
			</view>
			<view class="form-item">
				<input class="input" v-model="address.full_region" disabled="true" @tap="chooseRegion" placeholder="省份、城市、区县" />
			</view>
			<view class="form-item">
				<input class="input" v-model="address.detailInfo" placeholder="详细地址, 如街道、楼盘号等" />
			</view>
			<view class="form-default">
				<text @tap="bindIsDefault" :class="'default-input '+(address.is_default == 1 ? 'selected' : '')">设为默认地址</text>
			</view>
		</view>

		<view class="btns">
			<button class="cannel" @tap="cancelAddress">取消</button>
			<button class="save" @tap="saveAddress">保存</button>
		</view>

		<view class="region-select" v-if="openSelectRegion">
			<view class="hd">
				<view class="region-selected">
					<view :class="'item '+(item.id == 0 ? 'disabled ' : ' ') + ((regionType -1) === index ? 'selected' : '')" @tap="selectRegionType"
					 :data-region-type-index="index" v-for="(item, index) in selectRegionList" :key="item.id">{{item.name}}</view>
				</view>
				<view :class="'done '+(selectRegionDone ? '' : 'disabled')" @tap="doneSelectRegion">确定</view>
			</view>
			<view class="bd">
				<scroll-view scroll-y="true" class="region-list">
					<view :class="'item ' + (item.selected ? 'selected' : '')" @tap="selectRegion" :data-region-index="index" v-for="(item, index) in regionList"
					 :key="item.id">{{item.name}}</view>
				</scroll-view>
			</view>
		</view>
		<view class="bg-mask" @tap="cancelSelectRegion" v-if="openSelectRegion"></view>
	</view>
</template>

<script>
	const util = require("@/utils/util.js");
	const api = require('@/utils/api.js');
	export default {
		data() {
			return {
				address: {
					id: 0,
					province_id: 0,
					city_id: 0,
					district_id: 0,
					address: '',
					full_region: '',
					userName: '',
					telNumber: '',
					is_default: 0
				},
				addressId: 0,
				openSelectRegion: false,
				selectRegionList: [{
						id: 0,
						name: '省份',
						parent_id: 1,
						type: 1
					},
					{
						id: 0,
						name: '城市',
						parent_id: 1,
						type: 2
					},
					{
						id: 0,
						name: '区县',
						parent_id: 1,
						type: 3
					}
				],
				regionType: 1,
				regionList: [],
				selectRegionDone: false
			}
		},
		methods: {
			bindIsDefault() {
				let address = this.address;
				address.is_default = !address.is_default;
				this.address = address;
			},
			getAddressDetail() {
				let that = this;
				util.request(api.AddressDetail, {
					id: that.addressId
				}).then(function(res) {
					if (res.errno === 0) {
						if (res.data) {
							that.address = res.data
						}
					}
				});
			},
			setRegionDoneStatus() {
				let that = this;
				let doneStatus = that.selectRegionList.every(item => {
					return item.id != 0;
				});

				that.selectRegionDone = doneStatus

			},
			chooseRegion() {
				let that = this;
				that.openSelectRegion = !that.openSelectRegion;

				//设置区域选择数据
				let address = that.address;
				if (address.province_id > 0 && address.city_id > 0 && address.district_id > 0) {
					let selectRegionList = that.selectRegionList;
					selectRegionList[0].id = address.province_id;
					selectRegionList[0].name = address.province_name;
					selectRegionList[0].parent_id = 1;

					selectRegionList[1].id = address.city_id;
					selectRegionList[1].name = address.city_name;
					selectRegionList[1].parent_id = address.province_id;

					selectRegionList[2].id = address.district_id;
					selectRegionList[2].name = address.district_name;
					selectRegionList[2].parent_id = address.city_id;

					that.selectRegionList = selectRegionList
					that.regionType = 3

					that.getRegionList(address.city_id);
				} else {
					that.selectRegionList = [{
						id: 0,
						name: '省份',
						parent_id: 1,
						type: 1
					}, {
						id: 0,
						name: '城市',
						parent_id: 1,
						type: 2
					}, {
						id: 0,
						name: '区县',
						parent_id: 1,
						type: 3
					}]
					that.regionType = 1
					that.getRegionList(1);
				}

				that.setRegionDoneStatus();

			},
			selectRegionType(event) {
				let that = this;
				let regionTypeIndex = event.target.dataset.regionTypeIndex;
				let selectRegionList = that.selectRegionList;

				//判断是否可点击
				if (regionTypeIndex + 1 == that.regionType || (regionTypeIndex - 1 >= 0 && selectRegionList[regionTypeIndex - 1].id <=
						0)) {
					return false;
				}

				that.regionType = regionTypeIndex + 1

				let selectRegionItem = selectRegionList[regionTypeIndex];

				this.getRegionList(selectRegionItem.parent_id);

				this.setRegionDoneStatus();

			},
			selectRegion(event) {
				let that = this;
				let regionIndex = event.target.dataset.regionIndex;
				let regionItem = that.regionList[regionIndex];
				let regionType = regionItem.type;
				let selectRegionList = that.selectRegionList;
				selectRegionList[regionType - 1] = regionItem;


				that.selectRegionList = selectRegionList
				if (regionType != 3) {
					that.regionType = regionType + 1
					that.getRegionList(regionItem.id);
				}

				//重置下级区域为空
				selectRegionList.map((item, index) => {
					if (index > regionType - 1) {
						item.id = 0;
						item.name = index == 1 ? '城市' : '区县';
						item.parent_id = 0;
					}
					return item;
				});

				that.selectRegionList = selectRegionList


				that.regionList = that.regionList.map(item => {

					//标记已选择的
					if (that.regionType == item.type && that.selectRegionList[that.regionType - 1].id == item.id) {
						item.selected = true;
					} else {
						item.selected = false;
					}
					return item;
				})

				this.setRegionDoneStatus();

			},
			doneSelectRegion() {
				if (this.selectRegionDone === false) {
					return false;
				}

				let address = this.address;
				let selectRegionList = this.selectRegionList;
				address.province_id = selectRegionList[0].id;
				address.city_id = selectRegionList[1].id;
				address.district_id = selectRegionList[2].id;
				address.province_name = selectRegionList[0].name;
				address.city_name = selectRegionList[1].name;
				address.district_name = selectRegionList[2].name;
				address.full_region = selectRegionList.map(item => {
					return item.name;
				}).join('');

				this.address = address
				this.openSelectRegion = false

			},
			cancelSelectRegion() {
				this.regionType = this.regionDoneStatus ? 3 : 1
				this.openSelectRegion = false

			},
			getRegionList(regionId) {
				let that = this;
				let regionType = that.regionType;
				util.request(api.RegionList, {
					parentId: regionId
				}).then(function(res) {
					if (res.errno === 0) {
						that.regionList = res.data.map(item => {
							//标记已选择的
							if (regionType == item.type && that.selectRegionList[regionType - 1].id == item.id) {
								item.selected = true;
							} else {
								item.selected = false;
							}

							return item;
						})
					}
				});
			},
			cancelAddress() {
				uni.navigateBack({
					url: '/pages/shopping/address/address',
				})
			},
			saveAddress() {
				let address = this.address;

				if (address.userName == '') {
					util.toast('请输入姓名');

					return false;
				}

				if (address.telNumber == '') {
					util.toast('请输入手机号码');
					return false;
				}


				if (address.district_id == 0) {
					util.toast('请输入省市区');
					return false;
				}

				if (address.detailInfo == '') {
					util.toast('请输入详细地址');
					return false;
				}


				let that = this;
				util.request(api.AddressSave, {
					id: address.id,
					userName: address.userName,
					telNumber: address.telNumber,
					province_id: address.province_id,
					city_id: address.city_id,
					district_id: address.district_id,
					is_default: address.is_default,
					provinceName: address.province_name,
					cityName: address.city_name,
					countyName: address.district_name,
					detailInfo: address.detailInfo,
				}, 'POST', 'application/json').then(function(res) {
					if (res.errno === 0) {
						uni.navigateBack({
							url: '/pages/shopping/address/address',
						})
					}
				});

			}
		},
		onLoad: function(options) {
			// 页面初始化 options为页面跳转所带来的参数
			if (options.id != '' && options.id != 0) {
				this.addressId = options.id;
				this.getAddressDetail();
			}

			this.getRegionList(1);

		}
	}
</script>

<style lang="scss">
	page {
		height: 100%;
		background: #f4f4f4;
	}

	.add-address .add-form {
		background: #fff;
		width: 100%;
		height: auto;
		overflow: hidden;
	}

	.add-address .form-item {
		height: 116rpx;
		padding-left: 31.25rpx;
		border-bottom: 1px solid #d9d9d9;
		display: flex;
		align-items: center;
		padding-right: 31.25rpx;
	}

	.add-address .input {
		flex: 1;
		height: 44rpx;
		line-height: 44rpx;
		overflow: hidden;
	}

	.add-address .form-default {
		border-bottom: 1px solid #d9d9d9;
		height: 96rpx;
		background: #fafafa;
		padding-top: 28rpx;
		font-size: 28rpx;
	}

	.default-input {
		margin: 0 auto;
		display: block;
		width: 240rpx;
		height: 40rpx;
		padding-left: 50rpx;
		line-height: 40rpx;
		background: url(http://yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/sprites/checkbox-sed825af9d3-a6b8540d42.png) 1rpx -448rpx no-repeat;
		background-size: 38rpx 486rpx;
		font-size: 28rpx;
	}

	.default-input.selected {
		background: url(http://yanxuan.nosdn.127.net/hxm/yanxuan-wap/p/20161201/style/img/sprites/checkbox-sed825af9d3-a6b8540d42.png) 0 -192rpx no-repeat;
		background-size: 38rpx 486rpx;
	}

	.add-address .btns {
		position: fixed;
		bottom: 0;
		left: 0;
		overflow: hidden;
		display: flex;
		height: 100rpx;
		width: 100%;
	}

	.add-address .cannel,
	.add-address .save {
		flex: 1;
		height: 100rpx;
		text-align: center;
		line-height: 100rpx;
		font-size: 28rpx;
		color: #fff;
		border: none;
		border-radius: 0;
	}

	.add-address .cannel {
		background: #333;
	}

	.add-address .save {
		background: #b4282d;
	}


	.region-select {
		width: 100%;
		height: 600rpx;
		background: #fff;
		position: fixed;
		z-index: 10;
		left: 0;
		bottom: 0;
	}

	.region-select .hd {
		height: 108rpx;
		width: 100%;
		border-bottom: 1px solid #f4f4f4;
		padding: 46rpx 30rpx 0 30rpx;
	}

	.region-select .region-selected {
		float: left;
		height: 60rpx;
		display: flex;
	}

	.region-select .region-selected .item {
		max-width: 140rpx;
		margin-right: 30rpx;
		text-align: left;
		line-height: 60rpx;
		height: 100%;
		color: #333;
		font-size: 28rpx;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.region-select .region-selected .item.disabled {
		color: #999;
	}

	.region-select .region-selected .item.selected {
		color: #b4282d;
	}

	.region-select .done {
		float: right;
		height: 60rpx;
		width: 60rpx;
		border: none;
		background: #fff;
		line-height: 60rpx;
		text-align: center;
		color: #333;
		font-size: 28rpx;
	}

	.region-select .done.disabled {
		color: #999;
	}



	.region-select .bd {
		height: 492rpx;
		width: 100%;
		padding: 0 30rpx;
	}

	.region-select {
		height: auto;
		overflow: scroll;

	}

	.region-list {
		width: 100%;
		height: 100%;
		line-height: 104rpx;
		text-align: left;
		color: #333;
		font-size: 28rpx;
	}

	.region-select .item {
		width: 100%;
		height: 104rpx;
		line-height: 104rpx;
		text-align: left;
		color: #333;
		font-size: 28rpx;
	}

	.region-select .region-list .item.selected {
		color: #b4282d;
	}


	.bg-mask {
		height: 100%;
		width: 100%;
		background: rgba(0, 0, 0, 0.4);
		position: fixed;
		top: 0;
		left: 0;
		z-index: 8;
	}
</style>
