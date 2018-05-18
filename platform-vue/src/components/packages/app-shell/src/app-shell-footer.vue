<template>
  <div class="app-shell-footer">
        <tabbar>
            <tabbar-item link="/index" :selected="this.$route.path === '/index'">
                <i class="iconfont" slot="icon">&#xe601;</i>
                <span slot="label">首页</span>
            </tabbar-item>
            <tabbar-item link="/special" :selected="this.$route.path === '/special'">
                <i class="iconfont" slot="icon">&#xe88d;</i>
                <span slot="label">专题</span>
            </tabbar-item>
            <tabbar-item link="/category" :selected="this.$route.path === '/category'">
                <i class="iconfont" slot="icon">&#xe609;</i>
                <span slot="label">分类</span>
            </tabbar-item>
            <tabbar-item link="/cart" :selected="this.$route.path === '/cart'">
                <i class="iconfont" slot="icon">&#xe600;</i>
                <span slot="label">购物车</span>
            </tabbar-item>
            <tabbar-item link="/center" :selected="this.$route.path === '/center'">
                <i class="iconfont" slot="icon">&#xe698;</i>
                <span slot="label">购物车</span>
            </tabbar-item>
        </tabbar>
  </div>
</template>

<script>
import { Tabbar, TabbarItem} from 'vux'

export default {
  components: {
    Tabbar,
    TabbarItem
  }
}
</script>

<style lang="less" scoped>
.app-shell-footer{
    .weui-tabbar{
        background: white;
        .weui-tabbar__item{
          text-decoration: none;
        }
    }
}
</style>


