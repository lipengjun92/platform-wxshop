<template>
  <div class="mod-comment">
    <el-table :data="dataList" border @selection-change="selectionChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="id" header-align="center" align="center" label="ID" width="80"></el-table-column>
      <el-table-column header-align="center" align="left" label="商品" min-width="220">
        <template slot-scope="scope">
          <el-link type="primary" :underline="false" @click="showGoodsDetails(scope.row.valueId)">
            {{ scope.row.goodsName || ('商品ID: ' + scope.row.valueId) }}
          </el-link>
        </template>
      </el-table-column>
      <el-table-column prop="content" header-align="center" align="left" label="评论内容"
                       show-overflow-tooltip></el-table-column>
      <el-table-column prop="status" header-align="center" align="center" label="状态" width="90">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="small">待审核</el-tag>
          <el-tag v-if="scope.row.status === 1" size="small" type="success">通过</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="userNickname" header-align="center" align="center" label="评论用户"
                       width="110"></el-table-column>
      <el-table-column prop="addTime" header-align="center" align="center" label="评论时间" width="260">
        <template slot-scope="scope">
          <el-date-picker
            v-model="scope.row.addTime"
            type="datetime"
            disabled
            value-format="timestamp"
            placeholder="评论时间">
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('mall:comment:info')" type="text" size="small" @click="showDetails(scope.row.id)">
            查看
          </el-button>
          <el-button v-if="isAuth('mall:comment:update')" type="text" size="small"
                     @click="addOrUpdateHandle(scope.row.id)">修改
          </el-button>
          <el-button v-if="isAuth('mall:comment:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>

    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <goods-detail v-if="goodsDetailVisible" ref="goodsDetail"></goods-detail>
  </div>
</template>

<script>
import AddOrUpdate from './comment-add-or-update'
import GoodsDetail from './goods-add-or-update'

export default {
  data () {
    return {
      searchForm: {
        valueId: '',
        userId: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListSelections: [],
      addOrUpdateVisible: false,
      goodsDetailVisible: false
    }
  },
  components: {AddOrUpdate, GoodsDetail},
  activated () {
    this.getDataList()
  },
  methods: {
    getDataList (page) {
      if (page) {
        this.pageIndex = page
      }
      this.$http({
        url: '/mall/comment/list',
        method: 'get',
        params: {
          page: this.pageIndex,
          limit: this.pageSize,
          valueId: this.searchForm.valueId,
          userId: this.searchForm.userId
        }
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.data.records
          this.totalPage = data.data.total
        } else {
          this.dataList = []
          this.totalPage = 0
        }
      })
    },
    sizeChangeHandle (val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getDataList()
    },
    selectionChangeHandle (val) {
      this.dataListSelections = val
    },
    showDetails (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id, true)
      })
    },
    addOrUpdateHandle (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    showGoodsDetails (goodsId) {
      if (!goodsId) {
        this.$message.warning('未关联商品')
        return
      }
      this.goodsDetailVisible = true
      this.$nextTick(() => {
        this.$refs.goodsDetail.init(goodsId, true)
      })
    },
    deleteHandle (id) {
      let ids = id ? [id] : this.dataListSelections.map(item => item.id)
      this.$confirm('确定对所选项进行[删除]操作?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: '/mall/comment/delete',
          method: 'post',
          data: ids
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({message: '操作成功', type: 'success', duration: 1500})
            this.getDataList()
          }
        })
      }).catch(() => {
      })
    }
  }
}
</script>
