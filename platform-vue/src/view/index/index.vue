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
      <index-floor title="人气推荐">
        <special-cell
        :data="item"
        :key="key"
        v-for="(item,key) in hotGoodsList"
        ></special-cell>
      </index-floor>
  </div>
</template>

<script>
import { Swiper } from "vux"
import {mapActions, mapGetters} from 'vuex'
export default {
  data() {
    return {
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
    }
  },
  computed: {
    ...mapGetters('home', [
      'swiperList',
      'hotGoodsList'
    ])
  },
  components: {
    Swiper
  },
  methods: {
    ...mapActions('home', [
      'getHomeInfo'
    ])
  },
  created () {
    this.getHomeInfo()
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

