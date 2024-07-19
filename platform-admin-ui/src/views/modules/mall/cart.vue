<template>
  <div class="mod-cart">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList(1)">
      <el-form-item>
        <el-input v-model="searchForm.name" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(1)">查询</el-button>
        <el-button v-if="isAuth('mall:cart:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('mall:cart:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="userId"
        header-align="center"
        align="center"
        label="会员Id">
      </el-table-column>
      <el-table-column
        prop="sessionId"
        header-align="center"
        align="center"
        label="sessionId">
      </el-table-column>
      <el-table-column
        prop="goodsId"
        header-align="center"
        align="center"
        label="商品Id">
      </el-table-column>
      <el-table-column
        prop="goodsSn"
        header-align="center"
        align="center"
        label="商品序列号">
      </el-table-column>
      <el-table-column
        prop="productId"
        header-align="center"
        align="center"
        label="产品Id">
      </el-table-column>
      <el-table-column
        prop="goodsName"
        header-align="center"
        align="center"
        label="产品名称">
      </el-table-column>
      <el-table-column
        prop="marketPrice"
        header-align="center"
        align="center"
        label="市场价">
      </el-table-column>
      <el-table-column
        prop="retailPrice"
        header-align="center"
        align="center"
        label="零售价格">
      </el-table-column>
      <el-table-column
        prop="number"
        header-align="center"
        align="center"
        label="数量">
      </el-table-column>
      <el-table-column
        prop="goodsSpecifitionNameValue"
        header-align="center"
        align="center"
        label="规格属性组成的字符串，用来显示用">
      </el-table-column>
      <el-table-column
        prop="goodsSpecifitionIds"
        header-align="center"
        align="center"
        label="product表对应的goods_specifition_ids">
      </el-table-column>
      <el-table-column
        prop="checked"
        header-align="center"
        align="center"
        label="">
      </el-table-column>
      <el-table-column
        prop="listPicUrl"
        header-align="center"
        align="center"
        label="商品图片">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('mall:cart:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看</el-button>
          <el-button v-if="isAuth('mall:cart:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button v-if="isAuth('mall:cart:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
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
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './cart-add-or-update'

  export default {
    data () {
      return {
        searchForm: {
          name: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表

      getDataList (page) {
        if (page) {
          this.pageIndex = page
        }
        this.$http({
          url: '/mall/cart/list',
          method: 'get',
          params: {
            'page': this.pageIndex,
            'limit': this.pageSize,
            'name': this.searchForm.name
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
      // 查看详情
      showDetails (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id, true)
        })
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        let ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm('确定对所选项进行[删除]操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: '/mall/cart/delete',
            method: 'post',
            data: ids
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500
              })
              this.getDataList()
            }
          })
        }).catch(() => {
        })
      }
    }
  }
</script>
