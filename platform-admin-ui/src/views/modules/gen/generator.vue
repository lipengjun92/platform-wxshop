<template>
  <div class="mod-generator">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList(1)">
      <el-form-item>
        <el-input v-model="searchForm.tableName" placeholder="表名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(1)">查询</el-button>
      </el-form-item>
    </el-form>
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="generator()">
      <el-form-item>
        <el-input v-model="searchForm.projectName" placeholder="项目名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input style="width: 260px;" v-model="searchForm.packageName" placeholder="包名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.author" placeholder="作者" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button v-if="isAuth('sys:generator:code')" type="primary" @click="generator()">生成代码</el-button>
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
        prop="tableName"
        header-align="center"
        align="left"
        width="200"
        label="表名">
      </el-table-column>
      <el-table-column
        prop="engine"
        header-align="center"
        align="left"
        width="120"
        label="engine">
      </el-table-column>
      <el-table-column
        prop="tableComment"
        header-align="center"
        align="left"
        label="表备注">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="left"
        label="创建时间">
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
  </div>
</template>

<script>
export default {
  data () {
    return {
      searchForm: {
        tableName: '',
        projectName: 'platform',
        packageName: 'com.platform.modules',
        author: '李鹏军'
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListSelections: []
    }
  },
  activated () {
    this.getDataList()
  },
  methods: {
    generator: function () {
      if (!this.dataListSelections.length) {
        this.$message({
          message: '请选择要生成的表',
          type: 'error',
          duration: 1500,
          onClose: () => {
            this.visible = false
            this.$emit('refreshDataList')
          }
        })
        return
      }
      let tableNames = this.dataListSelections.map(item => {
        return item.tableName
      })
      location.href = this.$http.BASE_URL + `/sys/generator/code?tables=${tableNames}&projectName=${this.searchForm.projectName}&packageName=${this.searchForm.packageName}&author=${this.searchForm.author}&token=${this.$cookie.get('token')}`
    },
    // 获取数据列表

    getDataList (page) {
      if (page) {
        this.pageIndex = page
      }
      this.$http({
        url: '/sys/generator/list',
        method: 'get',
        params: {
          'page': this.pageIndex,
          'limit': this.pageSize,
          'tableName': this.searchForm.tableName
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
    selectionChangeHandle (val) {
      this.dataListSelections = val
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
    }
  }
}
</script>
