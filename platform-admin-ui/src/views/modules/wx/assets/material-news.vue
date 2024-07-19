<template>
  <div class="panel">
    <div class="flex justify-start" v-loading="dataListLoading">
      <div v-for="n in rows" :key="n">
        <template v-for="(item,i) in dataList">
          <div class="card" :key="item.mediaId" v-if="i%rows==n-1" @click="onSelect(item)">
            <div class="card-preview">
              <a v-for="(article,k) in item.content.articles" :key="k" :href="article.url" class="article-item"
                 target="_blank">
                <div class="article-title">{{article.title}}</div>
                <el-image class="article-thumb" :src="article.thumbUrl"></el-image>
              </a>
            </div>
            <div class="card-footer">
              <div>{{item.updateTime}}</div>
              <div class="flex justify-between align-center" v-show="!selectMode">
                <el-button size="mini" type="text" icon="el-icon-copy-document" v-clipboard:copy="item.mediaId"
                           v-clipboard:success="onCopySuccess" v-clipboard:error="onCopyError">复制media_id
                </el-button>
                <el-button size="mini" type="text" icon="el-icon-delete" @click="deleteHandle(item.mediaId)">删除
                </el-button>
              </div>
            </div>
          </div>
        </template>
      </div>
    </div>
    <el-pagination @current-change="currentChangeHandle" :current-page="pageIndex" :page-size="pageSize"
                   :total="totalCount" layout="total, prev,pager, next, jumper">
    </el-pagination>
  </div>
</template>
<script>

export default {
  name: 'material-news',
  props: {
    selectMode: {
      // 是否选择模式，选择模式下点击素材选中，不可新增和删除
      type: Boolean,
      default: false
    },
    rows: {
      type: Number,
      default: 4
    }
  },
  data () {
    return {
      dataList: [],
      pageIndex: 1,
      pageSize: 20,
      totalCount: 0,
      dataListLoading: false
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      if (!this.dataList.length) {
        this.getDataList()
      }
    },
    getDataList () {
      if (this.dataListLoading) {
        return
      }
      this.dataListLoading = true
      this.$http({
        url: '/manage/wxAssets/materialNewsBatchGet',
        params: {
          'page': this.pageIndex
        }
      }).then(({data}) => {
        if (data.code === 0) {
          this.dataList = data.data.items
          this.totalCount = data.data.totalCount
        }
        this.dataListLoading = false
      })
    },
    onSelect (itemInfo) {
      if (!this.selectMode) {
        return
      }
      this.$emit('selected', itemInfo)
    },
    // 删除
    deleteHandle (id) {
      this.$confirm(`确定对[mediaId=${id}]进行删除操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: '/manage/wxAssets/materialDelete',
          method: 'post',
          data: {
            mediaId: id
          }
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500
            })
            this.onChange()
          }
        })
      })
    },
    // 当前页
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getDataList()
    },
    onCopySuccess () {
      this.$message.success('已复制')
    },
    onCopyError () {
      this.$message.error('复制失败,可能是此浏览器不支持复制')
    },
    onChange () {
      this.pageIndex = 1
      this.getDataList()
      this.$emit('change')
    }
  }
}
</script>
<style scoped>
.card {
  width: 240px;
  min-height: 120px;
  display: inline-block;
  background: #FFFFFF;
  border: 1px solid #EBEEF5;
  box-shadow: 1px 1px 20px 0 rgba(0, 0, 0, 0.1);
  margin: 0 10px 10px 0;
  border-radius: 5px;
  vertical-align: top;
  height: fit-content;
}

.card:hover {
  border: 2px solid #66b1ff;
  margin-bottom: 6px;
}

.card-preview {
  color: #d9d9d9;
  padding-left: 10px;
  padding-top: 15px;
}

.article-item {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding: 10px 0;
  cursor: pointer;
}

.article-item::after {
  width: 168px;
  border-bottom: 1px solid #eee;
}

.article-title {
  display: -webkit-box;
  word-wrap: break-word;
  word-break: break-all;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
  flex: 1;
  color: #333333;
  padding-right: 10px;
  line-height: 20px;
  font-size: 13px;

}

.article-thumb {
  width: 50px;
  height: 50px;
  display: inline-block;
}

.card-footer {
  font-size: 12px;
  color: #ccc;
  padding: 15px 10px;
}
</style>
