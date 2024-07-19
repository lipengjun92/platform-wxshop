<template>
  <div class="mod-config">
    <el-form>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('wx:draft:addDraft')" type="primary" @click="addHandle()">新增</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border v-loading="dataListLoading" style="width: 100%;">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-descriptions :style="{padding: '20px'}" :title="item.title"
                           v-for="(item, index) in props.row.content.newsItem"
                           :key="index" :column="2" border>
            <el-descriptions-item label="作者" v-if="item.author">{{item.author}}</el-descriptions-item>
            <el-descriptions-item label="摘要" v-if="item.digest">{{item.digest}}</el-descriptions-item>
            <el-descriptions-item label="是否打开评论">
              <el-tag v-if="item.needOpenComment === 1" size="small" type="success">是</el-tag>
              <el-tag v-else-if="item.needOpenComment === 0" size="small" type="danger">否</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="是否粉丝才可评论">
              <el-tag v-if="item.onlyFansCanComment === 1" size="small" type="success">是</el-tag>
              <el-tag v-else-if="item.onlyFansCanComment === 0" size="small" type="danger">否</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="临时链接" v-if="item.url">
              <a :href="item.url" target="_blank">临时链接</a>
            </el-descriptions-item>
            <el-descriptions-item label="阅读原文URL" v-if="item.contentSourceUrl">
              <a :href="item.contentSourceUrl" target="_blank">{{item.contentSourceUrl}}</a>
            </el-descriptions-item>
            <el-descriptions-item label="图文消息封面" v-if="item.thumbUrl">
              <img :style="{width: '150px'}" :src="item.thumbUrl"/>
            </el-descriptions-item>
          </el-descriptions>
        </template>
      </el-table-column>
      <el-table-column
          prop="content"
          header-align="center"
          label="文章">
        <template slot-scope="props">
          <img :style="{width: '150px'}" :src="props.row.content.newsItem[0].thumbUrl"/>
          <span v-if="props.row.content.newsItem.length>1">[{{props.row.content.newsItem.length}}篇]</span>
          {{props.row.content.newsItem[0].title}}
        </template>
      </el-table-column>
      <el-table-column
          prop="updateTime"
          header-align="center"
          align="center"
          label="更新时间"
          width="180">
        <template slot-scope="scope">
          {{transDate(scope.row.updateTime, 'yyyy-MM-dd hh:mm:ss')}}
        </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          header-align="center"
          align="center" width="150"
          label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" v-if="isAuth('wx:freepublish:submit')"
                     @click="submitHandle(scope.row.mediaId)">发布
          </el-button>
          <el-button type="text" size="small" v-if="isAuth('wx:draft:updateDraft')"
                     @click="updateHandle(scope.row.mediaId)">修改
          </el-button>
          <el-button type="text" size="small" v-if="isAuth('wx:draft:delDraft')"
                     @click="deleteHandle(scope.row.mediaId)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
                   :page-sizes="[10, 15, 20]" :page-size="pageSize" :total="totalCount"
                   layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <draft-add v-if="addVisible" ref="draftAdd" @refreshDataList="getDataList"></draft-add>
    <draft-update v-if="updateVisible" ref="draftUpdate" @refreshDataList="getDataList"></draft-update>
  </div>
</template>

<script>
import DraftAdd from './wx-draft-add'
import DraftUpdate from './wx-draft-update'

export default {
  components: {
    DraftAdd, DraftUpdate
  },
  data () {
    return {
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalCount: 0,
      dataListLoading: false,
      addVisible: false,
      updateVisible: false
    }
  },
  activated () {
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList () {
      this.dataListLoading = true
      this.$http({
        url: '/manage/draft/listDraft',
        method: 'get',
        params: {
          'page': this.pageIndex,
          'limit': this.pageSize
        }
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.data.items
          this.totalCount = data.data.totalCount
        } else {
          this.dataList = []
          this.totalCount = 0
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
    // 发布
    submitHandle (mediaId) {
      this.$confirm('你正在通过发布的方式发表内容。 发布不占用群发次数，一天可多次发布。已发布内容不会推送给用户，也不会展示在公众号主页中。 发布后，你可以前往发表记录获取链接，也可以将发布内容添加到自定义菜单、自动回复、合集和页面模板中。', '发布确认', {
        confirmButtonText: '发布',
        showCancelButton: false,
        type: 'warning'
      }).then(() => {
        this.$http({
          url: `/manage/freepublish/submit/${mediaId}`,
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$notify({
              title: '提示',
              type: 'success',
              message: '发布任务的id：' + data.publishId,
              duration: 0
            })
            this.getDataList()
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    },
    // 新增
    addHandle () {
      this.addVisible = true
      this.$nextTick(() => {
        this.$refs.draftAdd.init()
      })
    },
    // 修改
    updateHandle (mediaId) {
      this.updateVisible = true
      this.$nextTick(() => {
        this.$refs.draftUpdate.init(mediaId)
      })
    },
    // 删除
    deleteHandle (mediaId) {
      this.$confirm(`确定对[mediaId=${mediaId}]进行[删除]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: '/manage/draft/delDraft',
          method: 'post',
          data: {
            mediaId: mediaId
          }
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500
            })
            this.getDataList()
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    }
  }
}
</script>
