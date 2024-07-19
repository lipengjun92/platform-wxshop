<template>
  <div class="mod-schedule">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList(1)">
      <el-form-item>
        <el-input v-model="searchForm.beanName" placeholder="bean名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(1)">查询</el-button>
        <el-button v-if="isAuth('sys:schedule:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('sys:schedule:delete')" type="danger" @click="deleteHandle()"
                   :disabled="dataListSelections.length <= 0">批量删除
        </el-button>
        <el-button v-if="isAuth('sys:schedule:pause')" type="danger" @click="pauseHandle()"
                   :disabled="dataListSelections.length <= 0">批量暂停
        </el-button>
        <el-button v-if="isAuth('sys:schedule:resume')" type="danger" @click="resumeHandle()"
                   :disabled="dataListSelections.length <= 0">批量恢复
        </el-button>
        <el-button v-if="isAuth('sys:schedule:run')" type="danger" @click="runHandle()"
                   :disabled="dataListSelections.length <= 0">批量立即执行
        </el-button>
        <el-button v-if="isAuth('sys:schedule:log')" type="success" @click="logHandle()">日志列表</el-button>
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
        prop="beanName"
        header-align="center"
        align="center"
        label="bean名称">
      </el-table-column>
      <el-table-column
        prop="methodName"
        header-align="center"
        align="center"
        label="方法名称">
      </el-table-column>
      <el-table-column
        prop="params"
        header-align="center"
        align="center"
        label="参数">
      </el-table-column>
      <el-table-column
        prop="cronExpression"
        header-align="center"
        align="center"
        label="cron表达式">
      </el-table-column>
      <el-table-column
        show-tooltip-when-overflow
        prop="remark"
        header-align="center"
        align="center"
        label="备注">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="small">正常</el-tag>
          <el-tag v-else size="small" type="danger">暂停</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('sys:schedule:update')" type="text" size="small"
                     @click="addOrUpdateHandle(scope.row.jobId)">修改
          </el-button>
          <el-button v-if="isAuth('sys:schedule:delete')" type="text" size="small"
                     @click="deleteHandle(scope.row.jobId)">删除
          </el-button>
          <el-button v-if="isAuth('sys:schedule:pause')" type="text" size="small" @click="pauseHandle(scope.row.jobId)">
            暂停
          </el-button>
          <el-button v-if="isAuth('sys:schedule:resume')" type="text" size="small"
                     @click="resumeHandle(scope.row.jobId)">恢复
          </el-button>
          <el-button v-if="isAuth('sys:schedule:run')" type="text" size="small" @click="runHandle(scope.row.jobId)">
            立即执行
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
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <!-- 弹窗, 日志列表 -->
    <log v-if="logVisible" ref="log"></log>
  </div>
</template>

<script>
import AddOrUpdate from './schedule-add-or-update'
import Log from './schedule-log'

export default {
  data () {
    return {
      searchForm: {
        beanName: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListSelections: [],
      addOrUpdateVisible: false,
      logVisible: false
    }
  },
  components: {
    AddOrUpdate,
    Log
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
        url: '/sys/schedule/list',
        method: 'get',
        params: {
          'page': this.pageIndex,
          'limit': this.pageSize,
          'beanName': this.searchForm.beanName
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
        return item.jobId
      })
      this.$confirm('确定对所选项进行[删除]操作?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: '/sys/schedule/delete',
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
    },
    // 暂停
    pauseHandle (id) {
      let ids = id ? [id] : this.dataListSelections.map(item => {
        return item.jobId
      })
      this.$confirm('确定对所选项进行[暂停]操作?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: '/sys/schedule/pause',
          method: 'post',
          data: ids
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          }
        })
      }).catch(() => {
      })
    },
    // 恢复
    resumeHandle (id) {
      let ids = id ? [id] : this.dataListSelections.map(item => {
        return item.jobId
      })
      this.$confirm('确定对所选项进行[恢复]操作?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: '/sys/schedule/resume',
          method: 'post',
          data: ids
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          }
        })
      }).catch(() => {
      })
    },
    // 立即执行
    runHandle (id) {
      let ids = id ? [id] : this.dataListSelections.map(item => {
        return item.jobId
      })
      this.$confirm('确定对所选项进行[立即执行]操作?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: '/sys/schedule/run',
          method: 'post',
          data: ids
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          }
        })
      }).catch(() => {
      })
    },
    // 日志列表
    logHandle () {
      this.logVisible = true
      this.$nextTick(() => {
        this.$refs.log.init()
      })
    }
  }
}
</script>
