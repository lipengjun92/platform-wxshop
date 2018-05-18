<template>
    <div class="order-confirm">
        <div class="order-confirm-address">
            <div class="address-view">
                <div class="view-info">
                    <p class="info-receiver">邓海标 136****5830</p>
                    <p class="info-address">广东韶关市仁化县城区新城路2号新政府大楼110室</p>
                </div>
                <div class="view-icon">
                    <i class="iconfont">&#xe639;</i>
                </div>
            </div>
        </div>
        <cell-group>
            <div class="order-confirm-thumbnail">
                <div class="thumbnail-goods">
                    <div class="goods-img">
                        <img :src="require('../../assets/logo.png')">
                    </div>
                    <div class="goods-num">
                        <span>共一件</span>
                        <i class="iconfont">&#xe639;</i>
                    </div>
                </div>
                <div class="thumbnail-pay-express">
                    <div class="pay-express-title">
                        <span>支付配送</span>
                    </div>
                    <div class="pay-express-details">
                        <div class="express-details">
                            <span>微信支付</span>
                            <span>顺丰快递</span>
                        </div>
                        <i class="iconfont">&#xe639;</i>
                    </div>
                </div>
            </div>
        </cell-group>
        <cell-group>
            <cell title="优惠券" value="无可用" is-link></cell>
            <cell title="礼品卡" value="无可用" is-link></cell>
        </cell-group>
        <cell-group>
            <cell title="商品金额" value="￥3280.00" valueStyle="color:#04BE02"></cell>
            <cell title="立减" value="￥0.00" valueStyle="color:#04BE02"></cell>
            <cell title="运费" value="￥0.00" valueStyle="color:#04BE02"></cell>
        </cell-group>
        <div class="order-confirm-submit">
            <div class="submit-price">
                <span>实付款：￥3453.00</span>
            </div>
            <div class="submit-btn" @click="goto('/order/pay')">
                <span>提交订单</span>
            </div>
        </div>
    </div>
</template>

<style lang="less" scoped>
    @import '../../assets/css/var.less';

    .order-confirm {
        position: absolute;
        top: 46px;
        bottom: 52px;
        width: 100%;
        overflow-x: hidden;
        overflow-y: auto;
        .order-confirm-address {
            padding-bottom: 5px;
            background: white url(../../assets/images/order_confirm_bg.png) -7px bottom repeat-x;
            background-size: 64px 5px;
            .address-view {
                display: flex;
                padding: 10px;
                .view-icon {
                    display: flex;
                    flex-basis: 35px;
                    justify-content: flex-end;
                    align-items: center;
                    i {
                        font-size: 20px;
                    }
                }
                .view-info {
                    display: flex;
                    flex-direction: column;
                    flex: 1;
                    .info-receiver {
                        flex-basis: 25px;
                        line-height: 25px;
                        font-size: 16px;
                        font-weight: 600;
                    }
                    .info-address {
                        flex: 1;
                        font-size: 14px;
                    }
                }
            }
        }
        .order-confirm-thumbnail {
            .thumbnail-goods {
                position: relative;
                display: flex;
                align-items: center;
                justify-content: space-between;
                height: 60px;
                .goods-img {
                    display: flex;
                    img {
                        flex-basis: 40px;
                        height: 40px;
                        &:not(:first-child) {
                            margin-left: 10px;
                        }
                    }
                }
                .goods-num {
                    display: flex;
                    padding-right: 10px;
                    align-items: center;
                    span {
                        font-size: 14px;
                        color:gray;
                    }
                }
                &:after {
                    position: absolute;
                    bottom: 0px;
                    left: 0px;
                    width: 100%;
                    content: '';
                    height: 0px;
                    border-top: 1px solid @border-color;
                    transform: scaleY(0.5);
                }
            }
            .thumbnail-pay-express {
                display: flex;
                align-items: center;
                justify-content: space-between;
                height: 60px;
                .pay-express-title {
                    display: flex;
                    align-items: center;
                    span {
                        font-size: 16px;
                    }
                }
                .pay-express-details {
                    display: flex;
                    align-items: center;
                    padding-right: 10px;
                    .express-details {
                        display: flex;
                        flex-direction: column;
                        span {
                            font-size: 14px;
                            color:gray;
                        }
                    }
                }
            }
        }
        .order-confirm-submit {
            position: fixed;
            bottom: 0px;
            left: 0px;
            width: 100%;
            background: white;
            height: 52px;
            display: flex;
            justify-content: flex-end;
            .submit-btn {
                flex-basis: 110px;
                display: flex;
                background: #04BE02;
                align-items: center;
                justify-content: center;
                span {
                    font-size: 16px;
                    color: white;
                }
            }
            .submit-price {
                flex: 1;
                display: flex;
                align-items: center;
                justify-content: flex-end;
                padding-right: 10px;
                span {
                    font-size: 14px;
                    color: #04BE02;
                }
            }
            &:after {
                position: absolute;
                content: '';
                top: 0px;
                left: 0px;
                width: 100%;
                height: 0px;
                border-top: 1px solid @border-color;
                transform: scaleY(0.5);
            }
        }
    }
</style>

