<template>
  <el-dialog
    title="修改"
    :close-on-click-modal="false"
    :before-close="handleClose"
    :visible.sync="visible">
    <el-row v-if="dataForm.newsItem.length>0">
      <el-col tag="center" :span="6">
        <el-card shadow="hover" :style="{ width: '150px' }" v-for="(item, index) in dataForm.newsItem" :key="index">
          <div @click="tableName = index+''">
            <img :src="item.thumbUrl" style="width: 120px"/>
            <div>
              {{ item.title }}
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18">
        <el-form :model="dataForm" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
                 label-width="80px">
          <el-tabs v-model="tableName">
            <el-tab-pane :label="item.title" v-for="(item, index) in dataForm.newsItem" :key="index" :name="index+''">
              <el-input hidden>{{ item.index = index }}</el-input>
              <el-form-item label="标题" prop="title">
                <el-input v-model="item.title" placeholder="请在这里输入标题"></el-input>
              </el-form-item>
              <el-form-item label="作者" prop="author">
                <el-input v-model="item.author" placeholder="请输入作者"></el-input>
              </el-form-item>
              <el-form-item label="内容" prop="content">
                <ueditor v-model="item.content" placeholder="详情"></ueditor>
              </el-form-item>
              <el-form-item label="封面" prop="thumbUrl">
                <el-img v-model="item.thumbUrl" disabled></el-img>
                <el-button type="text" @click="assetsSelectorVisible=true">
                  从素材库中选择
                </el-button>
              </el-form-item>
              <el-form-item label="摘要" prop="digest">
                <el-input type="textarea" :rows="5" v-model="item.digest" maxlength="120" show-word-limit
                          placeholder="选填，摘要会在订阅号消息、转发链接等文章外的场景显露，帮助读者快速了解内容，如不填写则默认抓取正文前54字"></el-input>
              </el-form-item>
              <el-form-item label="原文URL" prop="contentSourceUrl">
                <el-input v-model="item.contentSourceUrl" placeholder="阅读原文URL"></el-input>
              </el-form-item>
              <el-form-item label="打开评论" prop="needOpenComment">
                <el-radio-group v-model="item.needOpenComment" class="width200">
                  <el-radio-button :label="1">是</el-radio-button>
                  <el-radio-button :label="0">否</el-radio-button>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="粉丝评论" prop="onlyFansCanComment">
                <el-radio-group v-model="item.onlyFansCanComment" class="width200">
                  <el-radio-button :label="1">是</el-radio-button>
                  <el-radio-button :label="0">否</el-radio-button>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="临时链接" prop="url">
                <el-input v-model="item.url" disabled placeholder="草稿的临时链接"></el-input>
              </el-form-item>
            </el-tab-pane>
          </el-tabs>
        </el-form>
      </el-col>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
    <assets-selector v-if="assetsSelectorVisible" :visible="assetsSelectorVisible"
                     selectType="image" @selected="onAssetsSelect"
                     @onClose="assetsSelectorVisible=false"></assets-selector>
  </el-dialog>
</template>

<script>
export default {
  components: {
    AssetsSelector: () => import('./assets/assets-selector')
  },
  data () {
    return {
      tableName: '0',
      visible: false,
      assetsSelectorVisible: false,
      dataForm: {
        mediaId: '',
        newsItem: [{
          index: '',
          title: '',
          author: '',
          digest: '',
          content: '',
          contentSourceUrl: '',
          thumbMediaId: '',
          needOpenComment: '1',
          onlyFansCanComment: '0',
          url: '',
          thumbUrl: ''
        }]
      }
    }
  },
  methods: {
    init (mediaId) {
      this.dataForm.mediaId = mediaId
      this.tableName = '0'
      this.visible = true
      this.$nextTick(() => {
        if (this.dataForm.mediaId) {
          this.$http({
            url: `/manage/draft/getDraft/${this.dataForm.mediaId}`,
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.newsItem = data.data.newsItem
            }
          })
        }
      })
    },
    handleClose (done) {
      let _this = this
      this.$confirm('系统可能不会保存您所做的更改。', '关闭此窗口？', {
        distinguishCancelAndClose: true,
        confirmButtonText: '离开',
        cancelButtonText: '取消'
      }).then(() => {
        _this.visible = false
        done()
      })
    },
    onAssetsSelect (assetsInfo) {
      this.dataForm.newsItem[this.tableName].thumbUrl = assetsInfo.url
      this.dataForm.newsItem[this.tableName].thumbMediaId = assetsInfo.mediaId
      this.assetsSelectorVisible = false
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: `/manage/draft/updateDraft`,
            method: 'post',
            data: this.dataForm
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500
              })
              this.visible = false
              this.$emit('refreshDataList')
            }
          })
        }
      })
    }
  }
}
</script>
