<template>
    <div class="center">
        <div class="center-account">
            <div class="account-view">
                <div class="view-avatar">
                    <img src="//api.neho.top/static/images/20180209082853253.jpeg">
                </div>
                <div class="view-outline">
                    <div class="outline-nikename">
                        <h3>邓海标</h3>
                    </div>
                    <div class="outline-bonus-points">
                        <div class="bonus-points-item">
                            <i class="iconfont">&#xe606;</i>
                            <span>普通会员</span>
                        </div>
                        <div class="bonus-points-item">
                            <i class="iconfont">&#xe604;</i>
                            <span>积分:1245</span>
                        </div>
                    </div>
                </div>
                <div class="view-setting">
                    <i class="iconfont">&#xe60a;</i>
                </div>
            </div>
        </div>
        <div class="center-navicon">
            <div class="navicon-shopping">
                <div class="shopping-icon">
                    <div class="icon-item" @click="goto('/order?status=1')">
                        <i class="iconfont">&#xe683;</i>
                        <span>待付款</span>
                    </div>
                    <div class="icon-item" @click="goto('/order?status=2')">
                        <i class="iconfont">&#xe60c;</i>
                        <span>待收货</span>
                    </div>
                    <div class="icon-item" @click="goto('/order?status=3')">
                        <i class="iconfont">&#xe665;</i>
                        <span>待评价</span>
                    </div>
                    <div class="icon-item">
                        <i class="iconfont">&#xe667;</i>
                        <span>售后/客服</span>
                    </div>
                </div>
                <div class="shopping-entry" @click="goto('/order?status=0')">
                    <i class="iconfont">&#xe671;</i>
                    <span>订单</span>
                </div>
            </div>
            <div class="navicon-wallet">
                <div class="wallet-icon">
                    <div class="icon-item">
                        <label>432</label>
                        <span>乐豆</span>
                    </div>
                    <div class="icon-item">
                        <label>34</label>
                        <span>优惠券</span>
                    </div>
                    <div class="icon-item">
                        <label>￥934</label>
                        <span>乐呗</span>
                    </div>
                    <div class="icon-item">
                        <label>￥7354</label>
                        <span>金库</span>
                    </div>
                </div>
                <div class="wallet-entry">
                    <i class="iconfont">&#xe603;</i>
                    <span>钱包</span>
                </div>
            </div>
        </div>
        <div class="center-promote">
            <category-box title="商城热卖">
                <goods-box
                :data="v"
                :key="k"
                v-for="(v,k) in hotGoods"
                ></goods-box>
            </category-box>
        </div>
    </div>
</template>

<script>
export default {
  data(){
      return{
          hotGoods:[
              {
                  id:1,
                  imgUrl:'//m.360buyimg.com/mobilecms/s220x220_jfs/t4063/252/2569617591/621169/2a1735b5/58d20d5eN4de2034c.jpg!q70.jpg',
                  brief:'Apple iPad 平板电脑 9.7英寸（32G WLAN版/A9 芯片/Retina显示屏/Touch ID技术 MPGT2CH/A）金色',
                  price:'2388',
                  evaluate:'92%'
              },
              {
                  id:2,
                  imgUrl:'//m.360buyimg.com/mobilecms/s220x220_jfs/t4063/252/2569617591/621169/2a1735b5/58d20d5eN4de2034c.jpg!q70.jpg',
                  brief:'Apple iPad 平板电脑 9.7英寸（32G WLAN版/A9 芯片/Retina显示屏/Touch ID技术 MPGT2CH/A）金色',
                  price:'2388',
                  evaluate:'92%'
              },
              {
                  id:3,
                  imgUrl:'//m.360buyimg.com/mobilecms/s220x220_jfs/t4063/252/2569617591/621169/2a1735b5/58d20d5eN4de2034c.jpg!q70.jpg',
                  brief:'Apple iPad 平板电脑 9.7英寸（32G WLAN版/A9 芯片/Retina显示屏/Touch ID技术 MPGT2CH/A）金色',
                  price:'2388',
                  evaluate:'92%'
              }
          ]
      }
  }
}
</script>


<style lang="less" scoped>
    @import '../../assets/css/var.less';

    .center {
        .center-account {
            display: flex;
            flex-direction: column;
            height: 115px;
            background: url(https://m.360buyimg.com/mobilecms/jfs/t15121/63/2237425096/226392/6edfe5f0/5a75436dN3b8b592d.gif) no-repeat center;
            background-size: 100% 100%;
            color: white;
            .account-view {
                flex-basis: 100px;
                padding: 0px 10px;
                display: flex;
                .view-avatar {
                    flex-basis: 90px;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    img {
                        display: inline-block;
                        width: 76px;
                        height: 76px;
                        border-radius: 76px;
                        border: 4px solid white;
                    }
                }
                .view-outline {
                    flex: 1;
                    display: flex;
                    flex-direction: column;
                    padding-left: 10px;
                    .outline-nikename {
                        flex-basis: 50%;
                        display: flex;
                        align-items: center;
                        h3 {
                            font-weight: 500;
                        }
                    }
                    .outline-bonus-points {
                        flex: 1;
                        display: flex;
                        align-items: center;
                        .bonus-points-item {
                            flex-grow: 0;
                            display: flex;
                            padding: 0px 5px;
                            border-radius: 20px;
                            background: @success-color;
                            i {
                                flex-basis: 20px;
                                line-height: 20px;
                            }
                            span {
                                flex-grow: 0;
                                line-height: 20px;
                                font-size: 12px;
                                white-space: nowrap;
                            }
                            &:not(:first-child) {
                                margin-left: 7px;
                            }
                        }
                    }
                }
                .view-setting {
                    flex-basis: 50px;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    i {
                        font-size: 25px;
                    }
                }
            }
        }
        .center-navicon {
            background: white;
            .navicon-shopping {
                position:relative;
                display: flex;
                padding: 3px 10px 7px 10px;
                height: 60px;
                .shopping-icon {
                    position:relative;
                    flex: 1;
                    display: flex;
                    padding-right:6px;
                    .icon-item {
                        flex: 1;
                        display: flex;
                        flex-direction: column;
                        align-items: center;
                        i {
                            flex-basis: 50%;
                            font-size: 20px;
                            line-height: 30px;
                            text-align: center;
                        }
                        span {
                            flex: 1;
                            font-size: 14px;
                            line-height: 30px;
                        }
                    }
                    &::after{
                      position: absolute;
                      content:'';
                      right:0px;
                      top:0px;
                      height:100%;
                      border-right:1px solid @border-color;
                      transform: scaleX(0.5);
                    }
                }
                .shopping-entry {
                    flex-basis: 60px;
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    i {
                        flex-basis: 50%;
                        font-size: 20px;
                        line-height: 30px;
                    }
                    span {
                        flex: 1;
                        font-size: 14px;
                        line-height: 30px;
                    }
                }
                &::after{
                  position: absolute;
                  content:'';
                  left:0px;
                  bottom:0px;
                  width:100%;
                  border-bottom:1px solid @border-color;
                  transform: scaleY(0.5);
                }
            }
            .navicon-wallet {
                display: flex;
                padding: 3px 10px 7px 10px;
                height: 60px;
                .wallet-icon {
                    position:relative;
                    flex: 1;
                    display: flex;
                    padding-right:6px;
                    .icon-item {
                        flex: 1;
                        display: flex;
                        flex-direction: column;
                        align-items: center;
                        label {
                            flex-basis: 50%;
                            font-size: 16px;
                            line-height: 30px;
                        }
                        span {
                            flex: 1;
                            font-size: 14px;
                            line-height: 30px;
                        }
                    }
                    &::after{
                      position: absolute;
                      content:'';
                      right:0px;
                      top:0px;
                      height:100%;
                      border-right:1px solid @border-color;
                      transform: scaleX(0.5);
                    }
                }
                .wallet-entry {
                    flex-basis: 60px;
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    i {
                        flex-basis: 50%;
                        font-size: 20px;
                        line-height: 30px;
                        text-align: center;
                    }
                    span {
                        flex: 1;
                        font-size: 14px;
                        line-height: 30px;
                    }
                }
            }
        }
        .center-promote{
          margin-top:10px;
        }
    }
</style>

