<template>
  <el-dialog
    title="日志列表"
    :close-on-click-modal="false"
    :visible.sync="visible"
    :lock-scroll="false"
    width="75%">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList(1)">
      <el-form-item>
        <el-input v-model="searchForm.beanName" placeholder="bean名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.methodName" placeholder="方法名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(1)">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      height="460"
      style="width: 100%;">
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
        prop="status"
        header-align="center"
        align="center"
        label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="small">成功</el-tag>
          <el-tag v-else @click.native="showErrorInfo(scope.row.logId)" size="small" type="danger"
                  style="cursor: pointer;">失败
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="times"
        header-align="center"
        align="center"
        label="耗时(单位: 毫秒)">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        width="180"
        label="执行时间">
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
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      searchForm: {
        beanName: '',
        methodName: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0
    }
  },
  methods: {
    init () {
      this.visible = true
      this.getDataList()
    },
    // 获取数据列表

    getDataList (page) {
      if (page) {
        this.pageIndex = page
      }
      this.$http({
        url: '/sys/scheduleLog/list',
        method: 'get',
        params: {
          'page': this.pageIndex,
          'limit': this.pageSize,
          'beanName': this.searchForm.beanName,
          'methodName': this.searchForm.methodName
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
    // 失败信息
    showErrorInfo (id) {
      this.$http({
        url: `/sys/scheduleLog/info/${id}`,
        method: 'get'
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$alert(data.log.error)
        }
      })
    }
  }
}
</script>
