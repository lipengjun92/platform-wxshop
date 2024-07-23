<template>
  <div class="mod-config">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList(1)">
      <el-form-item>
        <el-select v-model="searchForm.tagid" filterable clearable placeholder="用户标签">
          <el-option v-for="item in wxUserTags" :key="item.id" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.nickname" placeholder="昵称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.city" placeholder="城市" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.qrSceneStr" placeholder="关注场景值" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchForm.subscribeScene" placeholder="关注渠道来源" @change="getDataList(1)" clearable>
          <el-option value="ADD_SCENE_SEARCH" label="公众号搜索"></el-option>
          <el-option value="ADD_SCENE_ACCOUNT_MIGRATION" label="公众号迁移"></el-option>
          <el-option value="ADD_SCENE_PROFILE_CARD" label="名片分享"></el-option>
          <el-option value="ADD_SCENE_QR_CODE" label="扫描二维码"></el-option>
          <el-option value="ADD_SCENE_PROFILE_LINK" label="图文页内名称点击"></el-option>
          <el-option value="ADD_SCENE_PROFILE_ITEM" label="图文页右上角菜单"></el-option>
          <el-option value="ADD_SCENE_PAID" label="支付后关注"></el-option>
          <el-option value="ADD_SCENE_WECHAT_ADVERTISEMENT" label="微信广告"></el-option>
          <el-option value="ADD_SCENE_OTHERS" label="其他"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(1)">查询</el-button>
        <el-button v-if="isAuth('wx:wxuser:save')" type="primary" @click="$refs.wxUserTagging.init('tagging')"
                   :disabled="dataListSelections.length <= 0">绑定标签
        </el-button>
        <el-button v-if="isAuth('wx:wxuser:save')" type="primary" @click="$refs.wxUserTagging.init('untagging')"
                   :disabled="dataListSelections.length <= 0">解绑标签
        </el-button>
      </el-form-item>
      <el-form-item class="fr">
        <el-button icon="el-icon-price-tag" type="success" @click="$refs.wxUserTagsEditor.show()">标签管理</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle"
              style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50">
      </el-table-column>
      <el-table-column prop="openid" header-align="center" align="center" label="openid">
      </el-table-column>
      <el-table-column prop="nickname" header-align="center" align="center" label="昵称">
      </el-table-column>
      <el-table-column prop="sex" header-align="center" align="center" label="性别" :formatter="sexFormat">
      </el-table-column>
      <el-table-column prop="city" header-align="center" align="center" label="城市">
      </el-table-column>
      <el-table-column prop="headImgUrl" header-align="center" align="center" label="头像">
        <template slot-scope="scope">
          <img class="headimg" @click="openImg(scope.row.headImgUrl)" v-if="scope.row.headImgUrl"
               :src="scope.row.headImgUrl"/>
        </template>
      </el-table-column>
      <el-table-column prop="tagidList" header-align="center" align="center" label="标签" show-overflow-tooltip>
        <template slot-scope="scope">
          <span v-for="tagid in scope.row.tagidList" :key="tagid">{{ getTagName(tagid) }} </span>
        </template>
      </el-table-column>
      <el-table-column prop="subscribeTime" header-align="center" align="center" label="订阅时间">
        <template slot-scope="scope">{{ scope.row.subscribeTime }}</template>
      </el-table-column>
      <el-table-column prop="qrSceneStr" header-align="center" align="center" label="场景值">
      </el-table-column>
      <el-table-column prop="subscribeScene" header-align="center" align="center" label="关注渠道来源">
        <template slot-scope="scope">
          <span>{{ getSubscribeScene(scope.row.subscribeScene) }} </span>
        </template>
      </el-table-column>
      <el-table-column prop="subscribe" header-align="center" align="center" label="是否关注">
        <span slot-scope="scope">{{ scope.row.subscribe ? '是' : '否' }}</span>
      </el-table-column>
      <el-table-column prop="integral" header-align="center" align="center" label="积分">
      </el-table-column>
    </el-table>
    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
                   :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                   layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <wx-user-tags-manager ref="wxUserTagsEditor" :visible="showWxUserTagsEditor"
                          @close="showWxUserTagsEditor=false"></wx-user-tags-manager>
    <wx-user-tagging ref="wxUserTagging" :wxUsers="dataListSelections"></wx-user-tagging>
  </div>
</template>

<script>
import WxUserTagsManager from '@/components/wx-user-tags-manager'
import WxUserTagging from './wx-user-tagging'
import {mapState} from 'vuex'

export default {
  data () {
    return {
      showWxUserTagsEditor: false,
      searchForm: {
        tagid: '',
        nickname: '',
        city: '',
        subscribeScene: '',
        qrSceneStr: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: []
    }
  },
  components: {
    WxUserTagsManager, WxUserTagging
  },
  activated () {
    this.getDataList()
  },
  computed: mapState({
    wxUserTags: state => state.wxUserTags.tags
  }),
  methods: {
    // 获取数据列表
    getDataList (page) {
      if (page) {
        this.pageIndex = page
      }
      this.dataListLoading = true
      this.$http({
        url: '/manage/wxUser/list',
        method: 'get',
        params: {
          'page': this.pageIndex,
          'limit': this.pageSize,
          'nickname': this.searchForm.nickname,
          'tagid': this.searchForm.tagid,
          'city': this.searchForm.city,
          'qrSceneStr': this.searchForm.qrSceneStr,
          'subscribeScene': this.searchForm.subscribeScene
        }
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.data.records
          this.totalPage = data.data.total
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle (val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandle (val) {
      this.dataListSelections = val
    },
    sexFormat (row, column, cellValue) {
      let sexType = {
        0: '未知',
        1: '男',
        2: '女'
      }
      return sexType[cellValue]
    },
    getTagName (tagid) {
      let tag = this.wxUserTags.find(item => item.id === tagid)
      return tag ? tag.name : '?'
    },
    getSubscribeScene (subscribeScene) {
      switch (subscribeScene) {
        case 'ADD_SCENE_SEARCH':
          return '公众号搜索'
        case 'ADD_SCENE_ACCOUNT_MIGRATION':
          return '公众号迁移'
        case 'ADD_SCENE_PROFILE_CARD':
          return '名片分享'
        case 'ADD_SCENE_QR_CODE':
          return '扫描二维码'
        case 'ADD_SCENE_PROFILE_LINK ':
          return '图文页内名称点击'
        case 'ADD_SCENE_PROFILE_ITEM ':
          return '图文页右上角菜单'
        case 'ADD_SCENE_PAID':
          return '支付后关注'
        case 'ADD_SCENE_WECHAT_ADVERTISEMENT':
          return '微信广告'
        case 'ADD_SCENE_OTHERS':
          return '其他'
        default:
          return '其他'
      }
    }
  }
}
</script>
<style scoped>
.headimg {
  width: 50px;
  height: 50px;
  border-radius: 8px;
}
</style>
