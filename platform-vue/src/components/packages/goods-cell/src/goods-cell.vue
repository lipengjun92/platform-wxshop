<template>
    <div class="goods-cell">
        <div class="goods-img">
            <img class="main-img" src="https://m.360buyimg.com/mobilecms/s357x357_jfs/t7936/179/2332511452/169563/e83620e7/59acea57N6ded222b.jpg!q50.jpg">
            <img class="hot-img" src="//st.360buyimg.com/so/images/search/hot-pic.png">
        </div>
        <div class="goods-desc">
            <div class="desc-name">
                <img src="//st.360buyimg.com/so/images/search/jd-send-icon.png">
                <p>名福 食用油 非转基因 核桃橄榄原香食用调和油5L</p>
            </div>
            <div class="desc-tag">
                <span class="tag-item">食用油</span>
                <span class="tag-item">调和油</span>
            </div>
            <div class="desc-price">
                <div class="price-value">
                    <label>￥</label>
                    <span class="value-big">149</span>
                    <span class="value-small">.90</span>
                </div>
            </div>
            <div class="desc-info">
                <span class="info-self">自营</span>
                <span class="info-evaluate">23345条评论</span>
            </div>
            <div class="cart-btn">
                <i class="iconfont">&#xe600;</i>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  name: "goods-cell"
};
</script>

<style lang="less" scoped>
@import "../../../../assets/css/var";
.goods-cell {
  display: flex;
  .goods-img {
    position: relative;
    flex-basis: 125px;
    height: 125px;
    .main-img {
      width: 100%;
      height: 100%;
    }
    .hot-img {
      position: absolute;
      top: 0;
      left: 0;
      width: 20px;
      height: 20px;
    }
  }
  .goods-desc {
    position: relative;
    display: flex;
    flex-direction: column;
    flex: 1;
    padding: 5px 0 5px 10px;
    .desc-name {
      color: #232326;
      font-size: 15px;
      line-height: 20px;
      flex-basis: 42px;
      overflow: hidden;
      text-overflow: ellipsis;
      display: box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      word-break: break-word;
      img {
        background-color: #09BB07;
        color: white;
        height: 15px;
        display: inline-block;
        line-height: 15px;
        border-radius: 2px;
        position: relative;
        top: -2px;
        margin-right: 2px;
      }
      p {
        display: inline;
        word-break: break-all;
      }
    }
    .desc-tag {
      display: flex;
      flex-basis: 29px;
      align-items: center;
      .tag-item {
        padding: 0 5px;
        font-size: 12px;
        border-radius: 5px;
        color: gray;
        background: @divider-color;
        &:not(:first-child) {
          margin-left: 10px;
        }
      }
    }
    .desc-price {
      flex-basis: 20px;
      display: flex;
      .price-value {
        display: flex;
        flex-grow: 0;
        align-items: flex-end;
        color: #09BB07;
        label {
          font-size: 14px;
        }
        .value-big {
          font-size: 16px;
        }
        .value-small {
          font-size: 12px;
        }
      }
    }
    .desc-info {
      display: flex;
      flex: 1;
      align-items: center;
      font-size: 12px;
      .info-self {
        padding: 0 3px;
        border: 1px solid #09BB07;
        border-radius: 2px;
        color: #09BB07;
        transform: scale(0.8);
      }
      .info-evaluate {
        margin-left: 10px;
        color: gray;
      }
    }
    .cart-btn {
      position: absolute;
      right: 0;
      bottom: 0;
      i {
        display: inline-block;
        width: 40px;
        height: 40px;
        text-align: center;
        line-height: 40px;
        font-size: 20px;
        color: #09BB07;
      }
    }
    &::after {
      position: absolute;
      bottom: 0;
      left: 0;
      width: 100%;
      height: 0;
      content: "";
      border-bottom: 1px solid @divider-color;
      transform: scaleY(0.5);
    }
  }
}
</style>