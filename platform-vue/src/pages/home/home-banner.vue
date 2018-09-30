<template>
  <div data-tag="recommend" class="home-banner">
    <cube-slide :data="list">
      <cube-slide-item v-for="(item, index) in list" :key="index">
        <div style="height: 5.3333rem">
          <img :src="item.list_pic_url" alt="">
        </div>
      </cube-slide-item>
    </cube-slide>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    data() {
      return {
        list: []
      }
    },
    methods: {
      getBanner() {
        this.request(this.api.IndexUrlBrand).then(res => {
          if (res.errno === 0) {
            this.list = res.data.brandList
          }
        })
      }
    },
    created() {
      this.getBanner()
    }
  }
</script>

<style lang="less" scoped>
  .home-banner {
    margin-bottom: 20px;
    background: white;
  }
</style>
