<template>
  <div class="mod-config">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList(1)">
      <el-form-item>
        <el-input v-model="searchForm.paramKey" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(1)">查询</el-button>
        <el-button v-if="isAuth('sys:config:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('sys:config:delete')" type="danger" @click="deleteHandle()"
                   :disabled="dataListSelections.length <= 0">批量删除
        </el-button>
        <el-button v-if="isAuth('sys:config:update')" type="info" disabled round plain>双击单元格进行编辑</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      @selection-change="selectionChangeHandle"
      @cell-dblclick="cellclick"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="paramKey"
        header-align="center"
        align="center"
        label="参数名">
        <template slot-scope="scope">
          <el-input ref="gain" size="mini" v-if="isAuth('sys:config:update')&&scope.row.isOK"
                    @keyup.native.enter="blurClick(scope)" @blur="blurClick(scope)"
                    v-model="scope.row.paramKey"></el-input>
          <span v-else>{{ scope.row.paramKey }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="paramValue"
        header-align="center"
        align="center"
        label="参数值">
        <template slot-scope="scope">
          <el-input ref="gain" size="mini" v-if="isAuth('sys:config:update')&&scope.row.isOK1"
                    @keyup.native.enter="blurClick(scope)" @blur="blurClick(scope)"
                    v-model="scope.row.paramValue"></el-input>
          <span v-else>{{ scope.row.paramValue }}</span>
        </template>
      </el-table-column>
      <el-table-column
        show-tooltip-when-overflow
        prop="remark"
        header-align="center"
        align="center"
        label="备注">
        <template slot-scope="scope">
          <el-input ref="gain" size="mini" v-if="isAuth('sys:config:update')&&scope.row.isOK2"
                    @keyup.native.enter="blurClick(scope)" @blur="blurClick(scope)"
                    v-model="scope.row.remark"></el-input>
          <span v-else>{{ scope.row.remark }}</span>
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
import AddOrUpdate from './config-add-or-update'

export default {
  data () {
    return {
      searchForm: {
        paramKey: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListSelections: [],
      addOrUpdateVisible: false,
      updateValue: ''
    }
  },
  components: {
    AddOrUpdate
  },
  activated () {
    this.getDataList()
  },
  methods: {
    cellclick (row, column) {
      if (this.isAuth('sys:config:update')) {
        if (column.label === '参数名') {
          this.$set(row, 'isOK', true)
        }
        if (column.label === '参数值') {
          this.$set(row, 'isOK1', true)
        }
        if (column.label === '备注') {
          this.$set(row, 'isOK2', true)
        }
        this.updateValue = row[column.property]
        this.$nextTick(() => {
          this.$refs.gain.focus()
        })
      }
    },
    blurClick ({row, column}) {
      if (column.label === '参数名') {
        this.$set(row, 'isOK', false)
      }
      if (column.label === '参数值') {
        this.$set(row, 'isOK1', false)
      }
      if (column.label === '备注') {
        this.$set(row, 'isOK2', false)
      }
      if (this.updateValue !== row[column.property]) {
        this.$http({
          url: '/sys/config/update',
          method: 'post',
          data: {
            'id': row.id || undefined,
            'paramKey': row.paramKey,
            'paramValue': row.paramValue,
            'remark': row.remark
          }
        }).then(({data}) => {
          this.getDataList()
          if (data && data.code === 0) {
            this.$message({
              message: '更新成功',
              type: 'success',
              duration: 1500
            })
          }
        })
      }
    },
    // 获取数据列表
    getDataList (page) {
      if (page) {
        this.pageIndex = page
      }
      this.$http({
        url: '/sys/config/list',
        method: 'get',
        params: {
          'page': this.pageIndex,
          'limit': this.pageSize,
          'paramKey': this.searchForm.paramKey
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
          url: '/sys/config/delete',
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
