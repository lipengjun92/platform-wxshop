<template>
  <nav class="site-navbar" :class="'site-navbar--' + navbarLayoutType">
    <div class="site-navbar__header">
      <h1 class="site-navbar__brand" @click="$router.push({ name: 'home' })">
        <a class="site-navbar__brand-lg" href="javascript:;">微同软件管理平台</a>
        <a class="site-navbar__brand-mini" href="javascript:;">微同</a>
      </h1>
    </div>
    <div class="site-navbar__body clearfix">
      <el-menu
        class="site-navbar__menu"
        mode="horizontal">
        <el-menu-item class="site-navbar__switch" index="0" @click="sidebarFold = !sidebarFold">
          <icon-svg name="zhedie"></icon-svg>
        </el-menu-item>
      </el-menu>
      <el-menu
        class="site-navbar__menu site-navbar__menu--right"
        mode="horizontal">
        <el-menu-item index="1" @click="showTheme()">
          <template slot="title">
            <el-badge is-dot value="new">
              <icon-svg name="xitongpeizhi" class="el-icon-setting"></icon-svg>
            </el-badge>
          </template>
        </el-menu-item>
        <el-menu-item class="site-navbar__avatar" index="2">
          <el-dropdown :show-timeout="0" placement="bottom">
            <span class="el-dropdown-link">
              <icon-svg name="admin" class="el-icon-setting"></icon-svg>{{ userName }}
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="updatePasswordHandle()">修改密码</el-dropdown-item>
              <el-dropdown-item @click.native="logoutHandle()">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-menu-item>
      </el-menu>
    </div>
    <!-- 弹窗, 修改密码 -->
    <update-password v-if="updatePassowrdVisible" ref="updatePassowrd"></update-password>
    <theme v-if="themeVisible" ref="theme"></theme>
  </nav>
</template>

<script>
import Theme from './common/theme'
import UpdatePassword from './main-navbar-update-password'
import {clearLoginInfo} from '@/utils'

export default {
  data () {
    return {
      updatePassowrdVisible: false,
      themeVisible: false
    }
  },
  components: {
    UpdatePassword, Theme
  },
  computed: {
    navbarLayoutType: {
      get () {
        return this.$store.state.common.navbarLayoutType
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
    mainTabs: {
      get () {
        return this.$store.state.common.mainTabs
      },
      set (val) {
        this.$store.commit('common/updateMainTabs', val)
      }
    },
    userName: {
      get () {
        return this.$store.state.user.name
      }
    }
  },
  methods: {
    showTheme () {
      this.themeVisible = true
      this.$nextTick(() => {
        this.$refs.theme.init()
      })
    },
    // 修改密码
    updatePasswordHandle () {
      this.updatePassowrdVisible = true
      this.$nextTick(() => {
        this.$refs.updatePassowrd.init()
      })
    },
    // 退出
    logoutHandle () {
      this.$confirm(`确定进行[退出]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: '/sys/logout',
          method: 'post'
        }).then(({data}) => {
          if (data && data.code === 0) {
            clearLoginInfo()
            this.$router.push({path: '/login'})
            location.reload()
          }
        })
      }).catch(() => {
      })
    }
  }
}
</script>
