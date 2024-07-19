<template>
  <div class="mod-org">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="searchForm.orgName" placeholder="机构名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.parentName" placeholder="上级机构" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('sys:org:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      row-key="orgNo"
      style="width: 100%;">
      <el-table-column
        prop="orgNo"
        header-align="center"
        align="left"
        width="80"
        label="机构编码">
      </el-table-column>
      <table-tree-column
        prop="orgName"
        header-align="center"
        treeKey="orgNo"
        parentKey="parentNo"
        width="200"
        label="机构名称">
      </table-tree-column>
      <el-table-column
        width="200"
        prop="parentName"
        header-align="center"
        align="center"
        label="上级机构">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="small" type="danger">禁用</el-tag>
          <el-tag v-else-if="scope.row.status === 1" size="small" type="success">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="sort"
        header-align="center"
        align="center"
        label="排序号">
      </el-table-column>
      <el-table-column
        prop="createUserId"
        header-align="center"
        align="center"
        label="创建者">
        <template slot-scope="scope">
          <span>{{transUser(scope.row.createUserId)}}</span>
        </template>
      </el-table-column>
      <el-table-column
        width="250px"
        prop="createTime"
        header-align="center"
        align="center"
        label="创建时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('sys:org:update')" type="text" size="small"
                     @click="addOrUpdateHandle(scope.row.orgNo, scope.row.parentName)">
            修改
          </el-button>
          <el-button v-if="isAuth('sys:org:delete')" type="text" size="small" @click="deleteHandle(scope.row.orgNo)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import TableTreeColumn from '@/components/table-tree-column'
import AddOrUpdate from './org-add-or-update'

export default {
  data () {
    return {
      searchForm: {
        orgName: '',
        parentName: ''
      },
      dataList: [],
      addOrUpdateVisible: false
    }
  },
  components: {
    AddOrUpdate,
    TableTreeColumn
  },
  activated () {
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList () {
      this.$http({
        url: '/sys/org/queryAll',
        method: 'get',
        params: {
          'orgName': this.searchForm.orgName,
          'parentName': this.searchForm.parentName
        }
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = this.treeDataTranslate(data.data, 'orgNo', 'parentNo', 'childrens')
        } else {
          this.dataList = []
        }
      })
    },
    // 新增 / 修改
    addOrUpdateHandle (id, parentName) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id, parentName)
      })
    },
    // 删除
    deleteHandle (orgNo) {
      this.$confirm('确定对所选项进行[删除]操作?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: '/sys/org/delete',
          method: 'post',
          data: orgNo
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
