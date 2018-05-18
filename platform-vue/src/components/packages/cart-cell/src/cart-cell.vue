<template>
  <div class="cart-cell">
      <div class="cart-cell-mall">
          <div class="mall-select">
                <selector></selector>
          </div>
          <div class="mall-info">
              <i class="iconfont icon-mall">&#xe633;</i>
              <span>京东自营</span>
              <i class="iconfont icon-more">&#xe639;</i>
          </div>
      </div>
      <div class="cart-cell-view">
          <div class="view-goods">
              <div class="goods-select">
                  <selector></selector>
              </div>
              <div class="goods-thumbnail">
                  <img src="//img10.360buyimg.com/n4/jfs/t16870/115/435634371/205861/2f965046/5a7adfdbNd9b17fce.jpg">
              </div>
              <div class="goods-details">
                  <div class="detials-sketch">
                       <p>HONGU红谷女包2017新款品牌蝙蝠翅膀包单肩包女包购物袋手提包牛皮女大包 深蓝精巧</p>
                  </div>
                  <div class="detials-spec">
                        <div class="spec-box">
                                <p>深蓝精巧</p>
                                <i class="iconfont">&#xe619;</i>
                        </div>
                  </div>
                    <div class="detials-num">
                        <number value="￥235.00"></number>
                    </div>
                  <div class="detials-menu">
                      <span class="menu-follow">移入关注</span>
                      <span class="menu-delete">删除</span>
                  </div>
              </div>
          </div>
      </div>
  </div>
</template>

<script>
import { XNumber } from 'vux'
export default {
  name:'cart-cell',
  components:{
      XNumber
  }
}
</script>

<style lang="less" scoped>
@import '../../../../assets/css/var.less';
.cart-cell{
    background: white;
    .cart-cell-mall{
        position: relative;
        display: flex;
        padding:0px 10px;
        height: 40px;
        .mall-select{
            flex-basis:35px;
        }
        .mall-info{
            display: flex;
            flex:1;
            align-items: center;
            .icon-mall{
                font-size: 20px;
                line-height: 40px;
            }
            span{
                padding-left:5px;
                font-size: 16px;
                line-height: 40px;
            }
            .icon-more{
                font-size: 14px;
                line-height: 40px;
            }
        }
        &::before{
            position: absolute;
            content: '';
            top:0px;
            left:0px;
            width:100%;
            border-top:1px solid @divider-color;
            transform: scaleY(0.5);
        }
        &::after{
            position: absolute;
            content: '';
            bottom:0px;
            left:0px;
            width:100%;
            border-top:1px solid @divider-color;
            transform: scaleY(0.5);
        }
    }
    .cart-cell-view{
        padding:10px;
        .view-goods{
            display: flex;
            .goods-select{
                display: flex;
                flex-basis: 35px;
                align-items: center;
            }
            .goods-thumbnail{
                display: flex;
                flex-basis: 75px;
                flex-shrink: 0;
                img{
                    width:75px;
                    height: 75px;
                }
            }
            .goods-details{
                flex:1;
                padding-left:10px;
                .detials-sketch{
                    p{
                        font-weight: 700;
                        font-size: 14px;
                        overflow: hidden;
                        text-overflow: ellipsis;
                        display: -webkit-box;
                        -webkit-line-clamp: 2;
                        -webkit-box-orient: vertical;
                    }
                }
                .detials-spec{
                    padding-top:6px;
                    .spec-box{
                        display: flex;
                        border:1px solid @divider-color;
                        p{
                            flex:1;
                            height: 25px;
                            padding-left:10px;
                            line-height: 25px;
                            font-size: 14px;
                        }
                        i{
                            flex-basis: 20px;
                            flex-shrink: 0;
                            line-height: 25px;
                            font-size: 14px;
                        }
                    }
                }
                .detials-num{
                    padding:10px 0px;
                }
                .detials-menu{
                    display: flex;
                    justify-content: flex-end;
                    font-size: 14px;
                    color:@content-color;
                    .menu-follow{
                        flex-grow: 0;
                        padding-right:10px;
                    }
                    .menu-delete{
                        flex-grow: 0;
                        padding-left:10px;
                        border-left:1px solid @divider-color;
                    }
                }
            }
        }
    }
}
</style>


