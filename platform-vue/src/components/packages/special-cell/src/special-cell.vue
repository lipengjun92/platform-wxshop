<template>
  <div class="special-cell">
      <div class="special-cell-img">
          <img :src="data.imageUrl">
      </div>
      <div class="special-cell-thumbnail">
          <h3>{{data.title}}</h3>
          <p>{{data.desc}}</p>
          <span>{{data.price + '￥'}}</span>
      </div>
  </div>
</template>

<script>
export default {
  name:'special-cell',
  props:{
      data:{
          type:Object,
          required:true
      }
  }
}
</script>

<style lang="less" scoped>
@import '../../../../assets/css/var.less';
.special-cell{
    padding-bottom:10px;
    background: white;
    .special-cell-img{
        width:100%;
        height: 180px;
        img{
            width:100%;
            height: 100%;
        }
    }
    .special-cell-thumbnail{
        text-align: center;
        padding:10px 0px;
        h3{
            height: 30px;
            line-height: 30px;
            font-weight: 500;
            font-size: 20px;
        }
        p{
            height: 30px;
            line-height: 30px;
            font-size: 13px;
            color:@sub-color;
        }
        span{
            display: block;
            height: 30px;
            line-height: 30px;
            font-size: 16px;
            color:@error-color;
        }
    }
    &:not(:first-child){
        margin-top:10px;
    }
}
</style>


