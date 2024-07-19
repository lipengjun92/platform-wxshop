<template>
  <div class="site-wrapper" :class="{ 'site-sidebar--fold': sidebarFold }" v-loading.fullscreen.lock="loading"
       element-loading-text="拼命加载中">
    <template v-if="!loading">
      <main-navbar/>
      <main-sidebar/>
      <div class="site-content__wrapper">
        <main-content v-if="!$store.state.common.contentIsNeedRefresh"/>
      </div>
    </template>
  </div>
</template>

<script>
import MainNavbar from './main-navbar'
import MainSidebar from './main-sidebar'
import MainContent from './main-content'

export default {
  provide () {
    return {
      // 刷新
      refresh () {
        this.$store.commit('common/updateContentIsNeedRefresh', true)
        this.$nextTick(() => {
          this.$store.commit('common/updateContentIsNeedRefresh', false)
        })
      }
    }
  },
  data () {
    return {
      loading: true
    }
  },
  components: {
    MainNavbar,
    MainSidebar,
    MainContent
  },
  computed: {
    documentClientHeight: {
      get () {
        return this.$store.state.common.documentClientHeight
      },
      set (val) {
        this.$store.commit('common/updateDocumentClientHeight', val)
      }
    },
    sidebarFold: {
      get () {
        return this.$store.state.common.sidebarFold
      },
      set (val) {
        this.$store.commit('common/updateSidebarFold', val)
      }
    },
    userId: {
      get () {
        return this.$store.state.user.id
      },
      set (val) {
        this.$store.commit('user/updateId', val)
      }
    },
    userName: {
      get () {
        return this.$store.state.user.name
      },
      set (val) {
        this.$store.commit('user/updateName', val)
      }
    }
  },
  created () {
    this.getUserInfo()
  },
  mounted () {
    this.resetDocumentClientHeight()
  },
  methods: {
    // 重置窗口可视高度
    resetDocumentClientHeight () {
      this.sidebarFold = document.documentElement['clientWidth'] <= 1200 || false
      this.documentClientHeight = document.documentElement['clientHeight']
      window.onresize = () => {
        this.sidebarFold = document.documentElement['clientWidth'] <= 1200 || false
        this.documentClientHeight = document.documentElement['clientHeight']
      }
    },
    // 获取当前管理员信息
    getUserInfo () {
      this.$http({
        url: '/sys/user/info',
        method: 'get'
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.loading = false
          this.userId = data.data.userId
          this.userName = data.data.userName
        }
      })
    }
  }
}
</script>
