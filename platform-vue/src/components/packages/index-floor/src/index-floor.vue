<template>
  <div class="index-floor">
      <div class="floor-title">
          <div class="title-text">
              <span>{{title}}</span>
              <i class="iconfont">&#xe74e;</i>
          </div>
      </div>
      <div class="floor-container">
          <slot></slot>
      </div>
  </div>
</template>

<script>
export default {
  name:'index-floor',
  props:{
      title:{
          required:true,
          type:String
      }
  }
}
</script>

<style lang="less" scoped>
@import '../../../../assets/css/var.less';
.index-floor{
    margin-top:10px;
    background: white;
    .floor-title{
        position: relative;
        display: flex;
        height:40px;
        color:@title-color;
        font-size:14px;
        .title-text{
            position: absolute;
            top:50%;
            left:50%;
            transform: translate(-50%,-50%)
        }
    }
    .floor-container{
        padding:10px;
    }
}
</style>

