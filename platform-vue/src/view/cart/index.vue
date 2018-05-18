<template>
    <div class="cart cart-with-fixbar">
        <div class="cart-view">
            <div class="cart-view-menu">
                <div class="menu-location">
                    <i class="iconfont">&#xe60d;</i>
                    <p>韶关市仁化县</p>
                </div>
                <div class="menu-edit" @click="toggleEditCart">
                    <span>{{isEditCart ? '完成' : '编辑商品'}}</span>
                </div>
            </div>
            <div class="cart-view-view">
                <cart-cell></cart-cell>
            </div>
            <div class="cart-view-fixbar">
                <div class="fixbar-select-all">
                    <selector></selector>
                    <span class="fixbar-select-all-text">全部</span>
                </div>
                <div class="fixbar-menu">
                    <div class="menu-buy" v-if="!isEditCart">
                        <div class="buy-info">
                            <div class="info-total-price">
                                <label>总计：</label>
                                <span>￥547.00</span>
                            </div>
                            <div class="info-coupon">
                                <p>总额¥937.00 立减¥0.00</p>
                            </div>
                        </div>
                        <div class="buy-btn" @click="goto('/order/confirm')">
                            <span>去结算（1件）</span>
                        </div>
                    </div>
                    <div class="menu-edit" v-if="isEditCart">
                        <span  class="menu-edit-follow">移入关注</span>
                        <span  class="menu-edit-delete">删除</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="cart-promote">
            <category-box title="为您推荐"></category-box>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                isEditCart:false
            }
        },
        methods: {
            toggleEditCart(){
                this.isEditCart = !this.isEditCart;
            }
        }
    }
</script>

<style lang="less" scoped>
@import '../../assets/css/var.less';
.cart{
    .cart-view{
        .cart-view-menu{
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding:0px 10px;
            height: 45px;
            background: white;
            .menu-location{
                display: flex;
                align-items: center;
                i{
                    font-size: 20px;
                    color:@sub-color;
                }
                p{
                    font-size: 14px;
                    color:@sub-color;
                }
            }
            .menu-edit{
                flex-grow: 0;
                span{
                    display: inline-block;
                    height: 45px;
                    line-height: 45px;
                    font-size: 14px;
                    color:#04BE02;
                }
            }
        }
        .cart-view-fixbar{
            position: fixed;
            bottom:53px;
            left:0px;
            width:100%;
            box-sizing: border-box;
            height: 50px;
            z-index: 11;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding-left:10px;
            background: white;
            .fixbar-select-all{
                display: flex;
                align-items: center;
                flex-basis:70px;
                .fixbar-select-all-text{
                    font-size: 14px;
                }
            }
            .fixbar-menu{
                flex:1;
                .menu-buy{
                    display: flex;
                    justify-content: flex-end;
                     .buy-info{
                        display: flex;
                        flex-direction: column;
                        padding-right:10px;
                         .info-total-price{
                             display: flex;
                             text-align: right;
                             label{
                                 flex: 1;
                                 font-size: 16px;
                                 font-weight: 600;
                                 color:#04BE02;
                             }
                             span{
                                 flex-grow: 0;
                                 font-size: 14px;
                             }
                         }
                         .info-coupon{
                             flex:1;
                             p{
                                 font-size: 12px;
                                 color:@sub-color;
                             }
                         }
                     }
                    .buy-btn{
                        flex-basis: 110px;
                        flex-shrink: 0;
                        height: 50px;
                        text-align: center;
                        background: #04BE02;
                        span{
                            line-height: 50px;
                            font-size: 14px;
                            color:white;
                        }
                    }
                }
                .menu-edit{
                    display: flex;
                    justify-content: flex-end;
                    font-size: 16px;
                    padding-right:10px;
                    .menu-edit-follow{
                        flex-grow: 0;
                        padding-right:10px;
                        border-right:1px solid @divider-color;
                    }
                    .menu-edit-delete{
                        flex-grow: 0;
                         padding-left:10px;
                    }
                }
            }
        }
    }
    .cart-promote{
      margin-top:10px;
    }
}
.cart-with-fixbar{
    padding-bottom: 50px;
}
</style>
