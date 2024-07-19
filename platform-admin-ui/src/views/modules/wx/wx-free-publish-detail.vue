<template>
  <el-dialog
      title="查看"
      :close-on-click-modal="false"
      :visible.sync="visible">
    <el-row v-if="dataForm.newsItem.length>0">
      <el-col tag="center" :span="6">
        <el-card shadow="hover" :style="{ width: '150px' }" v-for="(item, index) in dataForm.newsItem" :key="index">
          <div @click="tableName = index+''">
            <img :src="item.thumbUrl" style="width: 120px"/>
            <div>
              {{item.title}}
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18">
        <el-form :model="dataForm" ref="dataForm"
                 label-width="80px">
          <el-tabs v-model="tableName">
            <el-tab-pane :label="item.title" v-for="(item, index) in dataForm.newsItem" :key="index" :name="index+''">
              <el-form-item label="标题" prop="title">
                <el-input v-model="item.title" disabled placeholder="请在这里输入标题"></el-input>
              </el-form-item>
              <el-form-item label="作者" prop="author">
                <el-input v-model="item.author" disabled placeholder="请输入作者"></el-input>
              </el-form-item>
              <el-form-item label="内容" prop="content">
                <ueditor v-model="item.content" disabled placeholder="详情"></ueditor>
              </el-form-item>
              <el-form-item label="封面" prop="thumbUrl">
                <el-img v-model="item.thumbUrl" disabled disabled></el-img>
              </el-form-item>
              <el-form-item label="摘要" prop="digest">
                <el-input type="textarea" disabled :rows="5" v-model="item.digest" maxlength="120" show-word-limit
                          placeholder="选填，摘要会在订阅号消息、转发链接等文章外的场景显露，帮助读者快速了解内容，如不填写则默认抓取正文前54字"></el-input>
              </el-form-item>
              <el-form-item label="原文URL" prop="contentSourceUrl">
                <el-input v-model="item.contentSourceUrl" disabled placeholder="阅读原文URL"></el-input>
              </el-form-item>
              <el-form-item label="打开评论" prop="needOpenComment">
                <el-radio-group v-model="item.needOpenComment" class="width200" disabled>
                  <el-radio-button :label="1">是</el-radio-button>
                  <el-radio-button :label="0">否</el-radio-button>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="粉丝评论" prop="onlyFansCanComment">
                <el-radio-group v-model="item.onlyFansCanComment" class="width200" disabled>
                  <el-radio-button :label="1">是</el-radio-button>
                  <el-radio-button :label="0">否</el-radio-button>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="临时链接" prop="url">
                <el-input v-model="item.url" disabled placeholder="草稿的临时链接" disabled></el-input>
              </el-form-item>
            </el-tab-pane>
          </el-tabs>
        </el-form>
      </el-col>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      tableName: '0',
      visible: false,
      dataForm: {
        newsItem: [{
          title: '标题',
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
    init (articleId) {
      this.tableName = '0'
      this.visible = true
      this.dataForm = {
        newsItem: [{
          title: '标题',
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
      if (articleId) {
        this.$http({
          url: `/manage/freepublish/getArticleFromId`,
          method: 'get',
          params: {
            articleId: articleId
          }
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataForm.newsItem = data.data.newsItem
          }
        })
      }
    }
  }
}
</script>
