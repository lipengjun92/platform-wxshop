<template>
  <div class="app-shell">
      <header v-if="this.$route.meta.level !== 0">
          <app-shell-header></app-shell-header>
      </header>
      <section :class="sectionClass">
          <router-view></router-view>
      </section>
      <footer v-if="this.$route.meta.level === 2">
          <app-shell-footer></app-shell-footer>
      </footer>
  </div>
</template>

<script>
import appShellHeader from './app-shell-header.vue'
import appShellFooter from './app-shell-footer.vue'
export default {
  name:'app-shell',
  components:{
      appShellHeader,
      appShellFooter
  },
  computed:{
      sectionClass(){
          const arrClass = [
              '',
              'app-shell-with-header',
              'app-shell-with-header app-shell-with-footer'
          ];
          const routeIndex = this.$route.meta.level;
          return arrClass[routeIndex];
      }
  }
}
</script>

<style lang="less" scoped>
@import '../../../../assets/css/var.less';
.app-shell{
    position: absolute;
    top:0px;
    right:0px;
    bottom:0px;
    left:0px;
    display: flex;
    flex-direction: column;
    header{
        position: absolute;
        top:0px;
        left:0px;
        right:0px;
        z-index:9;
        &::after{
            position: absolute;
            content: '';
            bottom:0px;
            left:0px;
            width:100%;
            border-bottom:1px solid @divider-color;
            transform: scaleY(0.5);
            z-index:9;
        }
    }
    section{
        position: relative;
        flex:1;
        overflow-x: hidden;
        overflow-y: auto;
        background: @background-color;
    }
    .app-shell-with-header{
        padding-top:46px;
    }
    .app-shell-with-footer{
        padding-bottom:53px;
    }
    footer{
        position:absolute;
        bottom:0px;
        left:0px;
        right:0px;
        z-index:9
    }
}
</style>
