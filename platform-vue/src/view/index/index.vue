<template>
  <div class="index">
      <div class="index-slider">
           <swiper :list="swiperList" dots-position="center"></swiper>
      </div>
      <div class="index-fast-nav">
        <div class="nav-item" :key="i" v-for="(v,i) in fastNavList">
          <i class="iconfont" v-html="v.icon"></i>
          <p>{{v.name}}</p>
        </div>
      </div>
      <index-floor title="活动精选">
        <div class="index-floor-slot-img">
          <img src="https://m.360buyimg.com/mobilecms/jfs/t15121/63/2237425096/226392/6edfe5f0/5a75436dN3b8b592d.gif">
        </div>
      </index-floor>
  </div>
</template>

<script>
import { Swiper } from "vux";
export default {
  data() {
    return {
      swiperList: [
        {
          url: "javascript:",
          img: "https://m.360buyimg.com/mobilecms/s720x352_jfs/t19276/304/389513185/183977/f36d6d4d/5a741970N23f736f7.jpg!q70.jpg.webp"
        },
        {
          url: "javascript:",
          img: "https://m.360buyimg.com/mobilecms/s1125x549_jfs/t17608/231/399707608/159479/4a4b1a18/5a741e56N7ce895e3.jpg!q70.jpg"
        },
        {
          url: "javascript:",
          img: "https://img1.360buyimg.com/da/jfs/t15598/303/1938712613/164594/35949b50/5a72d79aN022baba9.jpg"
        }
      ],
      fastNavList:[
        {
          name:'家居',
          icon:'&#xe62b;'
        },
        {
          name:'餐厨',
          icon:'&#xe602;'
        },
        {
          name:'配件',
          icon:'&#xe659;'
        },
        {
          name:'服装',
          icon:'&#xe666;'
        },
        {
          name:'志趣',
          icon:'&#xe605;'
        }
      ]
    };
  },
  components: {
    Swiper
  }
};
</script>

<style lang="less" scoped>
@import '../../assets/css/var.less';
.index{
  .index-fast-nav{
    display: flex;
    background: white;
    color:@content-color;
    .nav-item{
      display: flex;
      flex-direction: column;
      flex:1;
      text-align: center;
      i{
        flex-basis:30px;
        line-height: 30px;
        font-size: 20px;
      }
      p{
        flex-basis: 30px;
        line-height: 30px;
      }
    }
  }
  .index-floor-slot-img{
    img{
      width:100%;
    }
  }
}
</style>

