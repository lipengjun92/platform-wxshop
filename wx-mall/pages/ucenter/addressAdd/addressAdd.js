var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();
Page({
  data: {
    address: {
      id:0,
      provinceId: 0,
      cityId: 0,
      districtId: 0,
      address: '',
      fullRegion: '',
      userName: '',
      telNumber: '',
      isDefault: 0
    },
    addressId: 0,
    openSelectRegion: false,
    selectRegionList: [
      { id: 0, name: '省份', parentId: 1, type: 1 },
      { id: 0, name: '城市', parentId: 1, type: 2 },
      { id: 0, name: '区县', parentId: 1, type: 3 }
    ],
    regionType: 1,
    regionList: [],
    selectRegionDone: false
  },
  bindinputMobile(event) {
    let address = this.data.address;
    address.telNumber = event.detail.value;
    this.setData({
      address: address
    });
  },
  bindinputName(event) {
    let address = this.data.address;
    address.userName = event.detail.value;
    this.setData({
      address: address
    });
  },
  bindinputAddress (event){
    let address = this.data.address;
    address.detailInfo = event.detail.value;
    this.setData({
      address: address
    });
  },
  bindIsDefault(){
    let address = this.data.address;
    address.isDefault = !address.isDefault;
    this.setData({
      address: address
    });
  },
  getAddressDetail() {
    let that = this;
    util.request(api.AddressDetail, { id: that.data.addressId }).then(function (res) {
      if (res.errno === 0) {
        if(res.data){
            that.setData({
                address: res.data
            });
        }
      }
    });
  },
  setRegionDoneStatus() {
    let that = this;
    let doneStatus = that.data.selectRegionList.every(item => {
      return item.id != 0;
    });

    that.setData({
      selectRegionDone: doneStatus
    })

  },
  chooseRegion() {
    let that = this;
    this.setData({
      openSelectRegion: !this.data.openSelectRegion
    });

    //设置区域选择数据
    let address = this.data.address;
    if (address.provinceId > 0 && address.cityId > 0 && address.districtId > 0) {
      let selectRegionList = this.data.selectRegionList;
      selectRegionList[0].id = address.provinceId;
      selectRegionList[0].name = address.province_name;
      selectRegionList[0].parentId = 1;

      selectRegionList[1].id = address.cityId;
      selectRegionList[1].name = address.city_name;
      selectRegionList[1].parentId = address.provinceId;

      selectRegionList[2].id = address.districtId;
      selectRegionList[2].name = address.district_name;
      selectRegionList[2].parentId = address.cityId;

      this.setData({
        selectRegionList: selectRegionList,
        regionType: 3
      });

      this.getRegionList(address.cityId);
    } else {
      this.setData({
        selectRegionList: [
          { id: 0, name: '省份', parentId: 1, type: 1 },
          { id: 0, name: '城市', parentId: 1, type: 2 },
          { id: 0, name: '区县', parentId: 1, type: 3 }
        ],
        regionType: 1
      })
      this.getRegionList(1);
    }

    this.setRegionDoneStatus();

  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    if (options.id) {
      this.setData({
        addressId: options.id
      });
      this.getAddressDetail();
    }

    this.getRegionList(1);

  },
  onReady: function () {

  },
  selectRegionType(event) {
    let that = this;
    let regionTypeIndex = event.target.dataset.regionTypeIndex;
    let selectRegionList = that.data.selectRegionList;

    //判断是否可点击
    if (regionTypeIndex + 1 == this.data.regionType || (regionTypeIndex - 1 >= 0 && selectRegionList[regionTypeIndex-1].id <= 0)) {
      return false;
    }

    this.setData({
      regionType: regionTypeIndex + 1
    })

    let selectRegionItem = selectRegionList[regionTypeIndex];

    this.getRegionList(selectRegionItem.parentId);

    this.setRegionDoneStatus();

  },
  selectRegion(event) {
    let that = this;
    let regionIndex = event.target.dataset.regionIndex;
    let regionItem = this.data.regionList[regionIndex];
    let regionType = regionItem.type;
    let selectRegionList = this.data.selectRegionList;
    selectRegionList[regionType - 1] = regionItem;


    if (regionType != 3) {
      this.setData({
        selectRegionList: selectRegionList,
        regionType: regionType + 1
      })
      this.getRegionList(regionItem.id);
    } else {
      this.setData({
        selectRegionList: selectRegionList
      })
    }

    //重置下级区域为空
    selectRegionList.map((item, index) => {
      if (index > regionType - 1) {
        item.id = 0;
        item.name = index == 1 ? '城市' : '区县';
        item.parentId = 0;
      }
      return item;
    });

    this.setData({
      selectRegionList: selectRegionList
    })


    that.setData({
      regionList: that.data.regionList.map(item => {

        //标记已选择的
        if (that.data.regionType == item.type && that.data.selectRegionList[that.data.regionType - 1].id == item.id) {
          item.selected = true;
        } else {
          item.selected = false;
        }

        return item;
      })
    });

    this.setRegionDoneStatus();

  },
  doneSelectRegion() {
    if (this.data.selectRegionDone === false) {
      return false;
    }

    let address = this.data.address;
    let selectRegionList = this.data.selectRegionList;
    address.provinceId = selectRegionList[0].id;
    address.cityId = selectRegionList[1].id;
    address.districtId = selectRegionList[2].id;
    address.province_name = selectRegionList[0].name;
    address.city_name = selectRegionList[1].name;
    address.district_name = selectRegionList[2].name;
    address.fullRegion = selectRegionList.map(item => {
      return item.name;
    }).join('');

    this.setData({
      address: address,
      openSelectRegion: false
    });

  },
  cancelSelectRegion() {
    this.setData({
      openSelectRegion: false,
      regionType: this.data.regionDoneStatus ? 3 : 1
    });

  },
  getRegionList(regionId) {
    let that = this;
    let regionType = that.data.regionType;
    util.request(api.RegionList, { parentId: regionId }).then(function (res) {
      if (res.errno === 0) {
        that.setData({
          regionList: res.data.map(item => {

            //标记已选择的
            if (regionType == item.type && that.data.selectRegionList[regionType - 1].id == item.id) {
              item.selected = true;
            } else {
              item.selected = false;
            }

            return item;
          })
        });
      }
    });
  },
  cancelAddress(){
    wx.navigateBack({
      url: '/pages/ucenter/address/address',
    })
  },
  saveAddress(){
    let address = this.data.address;

    if (address.userName == '') {
      util.showErrorToast('请输入姓名');

      return false;
    }

    if (address.telNumber == '') {
      util.showErrorToast('请输入手机号码');
      return false;
    }

    if (address.districtId == 0) {
      util.showErrorToast('请输入省市区');
      return false;
    }

    if (address.detailInfo == '') {
      util.showErrorToast('请输入详细地址');
      return false;
    }

    let that = this;
    util.request(api.AddressSave, {
      id: address.id,
      userName: address.userName,
      telNumber: address.telNumber,
      provinceId: address.provinceId,
      cityId: address.cityId,
      districtId: address.districtId,
      isDefault: address.isDefault,
      provinceName: address.province_name,
      cityName: address.city_name,
      countyName: address.district_name,
      detailInfo: address.detailInfo,
    }, 'POST', 'application/json').then(function (res) {
      if (res.errno === 0) {
        wx.navigateBack({
          url: '/pages/ucenter/address/address',
        })
      }
    });

  },
  onShow: function () {
    // 页面显示

  },
  onHide: function () {
    // 页面隐藏

  },
  onUnload: function () {
    // 页面关闭

  }
})
