<template>
  <div class="mod-dictgroup">
    <el-row :gutter="24">
      <el-col :span="14">
        <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList(1)">
          <el-form-item>
            <el-input v-model="searchForm.code" placeholder="分组编码" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="searchForm.name" placeholder="分组名称" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="getDataList(1)">查询</el-button>
            <el-button v-if="isAuth('sys:dictgroup:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
            <el-button v-if="isAuth('sys:dictgroup:delete')" type="danger" @click="deleteHandle()"
                       :disabled="dataListSelections.length <= 0">批量删除
            </el-button>
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
            prop="code"
            header-align="center"
            align="center"
            label="分组编码">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="showDict(scope.row.id,scope.row.code)">{{scope.row.code}}
              </el-button>
            </template>
          </el-table-column>
          <el-table-column
            prop="name"
            header-align="center"
            align="center"
            label="分组名称">
          </el-table-column>
          <el-table-column
            prop="remark"
            header-align="center"
            align="center"
            show-tooltip-when-overflow
            label="备注">
          </el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            width="150"
            label="操作">
            <template slot-scope="scope">
              <el-button v-if="isAuth('sys:dictgroup:update')" type="text" size="small"
                         @click="addOrUpdateHandle(scope.row.id)">修改
              </el-button>
              <el-button v-if="isAuth('sys:dictgroup:delete')" type="text" size="small"
                         @click="deleteHandle(scope.row.id)">删除
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
      </el-col>
      <el-col :span="10">
        <dict ref="dict"></dict>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import AddOrUpdate from './dictgroup-add-or-update'
import Dict from './dict'

export default {
  data () {
    return {
      searchForm: {
        code: '',
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
    AddOrUpdate,
    Dict
  },
  activated () {
    this.getDataList()
  },
  methods: {
    showDict (groupId, code) {
      this.$nextTick(() => {
        this.$refs.dict.getDictDataList(groupId, code)
      })
    },
    // 获取数据列表
    getDataList (page) {
      if (page) {
        this.pageIndex = page
      }
      this.$http({
        url: '/sys/dictgroup/list',
        method: 'get',
        params: {
          'page': this.pageIndex,
          'limit': this.pageSize,
          'code': this.searchForm.code,
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
          url: '/sys/dictgroup/delete',
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
