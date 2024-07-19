<template>
  <div>
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList(1)">
      <el-form-item>
        <el-select v-model="searchForm.type" placeholder="请选择" @change="getDataList(1)">
          <el-option
            key="1"
            label="请选择"
            value="1">
          </el-option>
          <el-option
            key="2"
            label="@Cacheable缓存"
            value="2">
          </el-option>
          <el-option
            key="3"
            label="系统缓存"
            value="3">
          </el-option>
          <el-option
            key="4"
            label="业务缓存"
            value="4">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(1)">查询</el-button>
        <el-button v-if="isAuth('sys:cache:deleteCache')" type="danger" @click="deleteHandle()"
                   :disabled="dataListSelections.length <= 0">批量删除
        </el-button>
      </el-form-item>
    </el-form>
    <el-table
      border
      :data="dataList"
      @selection-change="selectionChangeHandle"
      style="width: 100%">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="cacheKey"
        label="缓存KEY"
        show-tooltip-when-overflow
        width="180">
      </el-table-column>
      <el-table-column
        prop="value"
        label="缓存VALUE"
        show-tooltip-when-overflow
        flex="1">
      </el-table-column>
      <el-table-column
        prop="seconds"
        label="过期剩余时间(秒)"
        width="180">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('sys:cache:deleteCache')" type="text" size="small"
                     @click="deleteHandle(scope.row.cacheKey)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data () {
    return {
      dataListSelections: [],
      dataList: [],
      searchForm: {
        type: '1'
      }
    }
  },
  activated () {
    this.getDataList()
  },
  methods: {
    // 多选
    selectionChangeHandle (val) {
      this.dataListSelections = val
    },
    getDataList (page) {
      if (page) {
        this.pageIndex = page
      }
      this.$http({
        url: `/sys/cache/queryAll`,
        method: 'get',
        params: {
          type: this.searchForm.type
        }
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.data
        }
      })
    },
    deleteHandle (id) {
      console.log(id)
      let ids = id ? [id] : this.dataListSelections.map(item => {
        return item.cacheKey
      })
      this.$confirm('确定对所选项进行[删除]操作?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: '/sys/cache/deleteCache',
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
