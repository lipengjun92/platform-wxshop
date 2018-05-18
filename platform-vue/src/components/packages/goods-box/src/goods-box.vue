<template>
  <div class="goods-box" @click="goto('/goods/' + data.id)">
      <div class="goods-box-wrap">
        <div class="goods-box-img">
            <img :src="data.imgUrl">
        </div>
        <div class="goods-box-thumbnail">
            <p class="thumbnail-brief">{{data.brief}}</p>
            <div class="thumbnail-price">
                <span>￥{{data.price}}</span>
            </div>
            <div class="thumbnail-evaluate">
                <span>{{data.evaluate}}</span>
            </div>
            <div class="thumbnail-similarity">
                <span>看相似</span>
            </div>
        </div>
      </div>
  </div>
</template>

<script>
export default {
  name: "goods-box",
  props: {
    data: {
      type: Object,
      required: true
    }
  }
};
</script>

<style lang="less" scoped>
@import "../../../../assets/css/var.less";
.goods-box {
  display: flex;
  justify-content: center;
  flex-basis: 50%;
  padding: 7px 0px;
  .goods-box-wrap {
    position: relative;
    flex-basis: 96%;
    .goods-box-img {
      width: 100%;
      height: 160px;
      img {
        width: 100%;
        height: 100%;
      }
    }
    .goods-box-thumbnail {
      margin-top: 10px;
      .thumbnail-brief {
        height: 33px;
        font-size: 13px;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        word-break: break-word;
        color: #232326;
        margin-top: 5px;
        line-height: 17px;
        margin-bottom: 3px;
        padding: 0 4px;
      }
      .thumbnail-price {
        height: 20px;
        padding: 7px 0px 4px 0px;
        span {
          line-height: 20px;
          font-size: 16px;
          color: @error-color;
        }
      }
      .thumbnail-evaluate {
        height: 20px;
        span {
          line-height: 20px;
          font-size: 16px;
          color: @content-color;
        }
      }
      .thumbnail-similarity {
        position: absolute;
        right: 1px;
        bottom: 1px;
        span {
          display: block;
          height: 25px;
          line-height: 25px;
          padding: 0px 10px;
          font-size: 16px;
          border: 1px solid @sub-color;
          color: @sub-color;
        }
      }
    }
  }
}
</style>


