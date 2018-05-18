<template>
  <div class="number">
      <div class="number-value">
          <span>{{value}}</span>
      </div>
      <div class="number-indicator">
          <div class="indicator-minus" @click="minusNum">
              <i class="iconfont">&#xe68e;</i>
          </div>
          <div class="indicator-num">
              <span>{{currentNum}}</span>
          </div>
          <div class="indicator-add" @click="addNum">
              <i class="iconfont">&#xe623;</i>
          </div>
      </div>
  </div>
</template>

<script>
export default {
  name:'number',
  data(){
      return{
          currentNum:this.num
      }
  },
  props:{
      value:{
          type:String
      },
      num:{
          type:Number,
          default:1
      },
      minNum:{
          type:Number,
          default:1
      }
  },
  methods:{
      addNum(){
          this.currentNum += 1;
      },
      minusNum(){
          if(this.currentNum > this.minNum){
            this.currentNum -= 1;
          }else{
            this.$vux.toast.show({
            text: '最小数量不能少于' + this.minNum
            })
          }
      }
  }
}
</script>


<style lang="less" scoped>
@import '../../../../assets/css/var.less';
.number{
    display: flex;
    justify-content: space-between;
    .number-value{
        display: flex;
        flex-grow:0; 
        align-items: center;
        span{
            font-size: 16px;
            height: 30px;
            line-height: 30px;
            color:@success-color;
        }
    }
    .number-indicator{
        display: flex;
        flex-basis: 110px;
        flex-shrink: 0;
        height: 30px;
        .indicator-minus{
            display: flex;
            flex-basis: 30px;
            justify-content: center;
            align-items: center;
            background: @divider-color;
            i{
                font-size: 20px;
            }
        }
        .indicator-num{
            display: flex;
            flex:1;
            justify-content: center;
            align-items: center;
            margin:0px 1px;
            background: @divider-color;
            span{
                font-size: 16px;
            }
        }
        .indicator-add{
            display: flex;
            flex-basis: 30px;
            justify-content: center;
            align-items: center;
            background: @divider-color;
            i{
                font-size: 20px;
            }
        }
    }
}
</style>
