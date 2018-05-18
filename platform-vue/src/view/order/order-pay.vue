<template>
    <div class="order-pay">
        <div class="pay-info">
            <div class="pay-info-wrap">
                <div class="info-item">
                    <span>支付单号</span>
                    <p>EZM201803059346SD</p>
                </div>
                <div class="info-item">
                    <span>创建时间</span>
                    <p>2018-03-05 15：30</p>
                </div>
                <div class="info-item">
                    <span>支付金额</span>
                    <p style="color:#04BE02;font-size: 16px;font-weight: 600">￥3476.00</p>
                </div>
            </div>
        </div>
        <div class="pay-type">
            <div class="pay-type-item">
                <div class="item-icon">
                    <i class="iconfont" style="color:#19be6b">&#xe616;</i>
                </div>
                <div class="item-desc">
                    <span>微信支付</span>
                    <p>使用微信支付将默认接受微信支付相关协议</p>
                </div>
                <div class="item-select">
                    <selector
                            :select="payType === 1"
                            @handler="(e) => {e ? selectPayType(1) : cancelPayType()}"
                    ></selector>
                </div>
            </div>
            <div class="pay-type-item">
                <div class="item-icon">
                    <i class="iconfont" style="color:#ed3f14">&#xe62a;</i>
                </div>
                <div class="item-desc">
                    <span>余额支付</span>
                    <p>可用余额￥2376.00</p>
                </div>
                <div class="item-select">
                    <selector
                            :select="payType === 2"
                            @handler="(e) => {e ? selectPayType(2) : cancelPayType()}"
                    ></selector>
                </div>
            </div>
            <div class="pay-type-item">
                <div class="item-icon">
                    <i class="iconfont" style="color:#2d8cf0">&#xe65f;</i>
                </div>
                <div class="item-desc">
                    <span>支付宝支付</span>
                    <p>使用支付宝支付将默认接受支付宝相关协议</p>
                </div>
                <div class="item-select">
                    <selector
                            :select="payType === 3"
                            @handler="(e) => {e ? selectPayType(3) : cancelPayType()}"
                    ></selector>
                </div>
            </div>
        </div>
        <div class="pay-submit" v-if="payType">
            <div class="submit-price">
                <span>实付款：￥3453.00</span>
            </div>
            <div class="submit-btn" @click="goto('/order/pay/status')">
                <span>{{payText}}</span>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                payType: 2
            }
        },
        computed: {
            payText() {
                return this.payType === 1 && '微信支付' ||
                    this.payType === 2 && '账户支付' ||
                    this.payType === 3 && '支付宝支付'
            }
        },
        methods: {
            selectPayType(val) {
                this.payType = val;
            },
            cancelPayType() {
                this.payType = null;
            }
        }
    }
</script>
<style lang="less" scoped>
    @import '../../assets/css/var.less';

    .order-pay {
        .pay-info {
            padding-bottom: 5px;
            background: white url(../../assets/images/order_confirm_bg.png) -7px bottom repeat-x;
            background-size: 64px 5px;
            .pay-info-wrap {
                padding: 7px 10px;
                .info-item {
                    display: flex;
                    height: 35px;
                    align-items: center;
                    span {
                        flex-basis: 90px;
                        font-size: 16px;
                    }
                    p {
                        flex: 1;
                        font-size: 14px;
                        text-align: right;
                        color: gray;
                    }
                }
            }
        }
        .pay-type {
            position: relative;
            margin-top: 10px;
            padding-left: 10px;
            background: white;
            .pay-type-item {
                position: relative;
                display: flex;
                .item-icon {
                    flex-basis: 50px;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    i {
                        font-size: 25px;
                    }
                }
                .item-desc {
                    display: flex;
                    flex-direction: column;
                    flex: 1;
                    height: 50px;
                    span {
                        flex: 1;
                        font-size: 16px;
                    }
                    p {
                        flex: 1;
                        font-size: 12px;
                        color: gray;
                    }
                }
                .item-select {
                    flex-basis: 30px;
                }
            }
            &::before {
                position: absolute;
                content: '';
                top: -1px;
                left: 0px;
                width: 100%;
                height: 0px;
                border-top: 1px solid @border-color;
                transform: scaleY(0.5);
            }
            &::after {
                position: absolute;
                content: '';
                bottom: -1px;
                left: 0px;
                width: 100%;
                height: 0px;
                border-top: 1px solid @border-color;
                transform: scaleY(0.5);
            }
        }
        .pay-submit {
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