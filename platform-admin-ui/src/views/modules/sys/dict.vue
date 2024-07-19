<template>
  <div class="mod-dict">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDictDataList()">
      <el-form-item>
        <el-input v-model="searchForm.name" placeholder="字典名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDictDataList()">查询</el-button>
        <el-button v-if="isAuth('sys:dict:save')" type="primary" @click="addOrUpdateDictHandle()">新增</el-button>
        <el-button v-if="isAuth('sys:dict:delete')" type="danger" @click="deleteDictHandle()"
                   :disabled="dictDataListSelections.length <= 0">批量删除
        </el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dictDataList"
      border
      @selection-change="selectionChangeDictHandle"
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
      </el-table-column>
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        label="字典名称">
      </el-table-column>
      <el-table-column
        prop="value"
        header-align="center"
        align="center"
        label="字典值">
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
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('sys:dict:update')" type="text" size="small"
                     @click="addOrUpdateDictHandle(scope.row.id)">修改
          </el-button>
          <el-button v-if="isAuth('sys:dict:delete')" type="text" size="small" @click="deleteDictHandle(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeDictHandle"
      @current-change="currentChangeDictHandle"
      :current-page="dictPageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="dictPageSize"
      :total="dictTotalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <dict-add-or-update v-if="dictVisible" ref="dictAddOrUpdate"
                        @refreshDataList="getDictDataList"></dict-add-or-update>
  </div>
</template>

<script>
import DictAddOrUpdate from './dict-add-or-update'

export default {
  data () {
    return {
      searchForm: {
        name: ''
      },
      dictDataList: [],
      dictPageIndex: 1,
      dictPageSize: 10,
      dictTotalPage: 0,
      dictDataListSelections: [],
      dictVisible: false,
      groupId: '',
      code: ''
    }
  },
  components: {
    DictAddOrUpdate
  },
  methods: {
    // 获取数据列表
    getDictDataList (groupId, code) {
      if (groupId) {
        this.groupId = groupId
      }
      if (code) {
        this.code = code
      }
      this.$http({
        url: '/sys/dict/list',
        method: 'get',
        params: {
          'page': this.dictPageIndex,
          'limit': this.dictPageSize,
          'name': this.searchForm.name,
          'code': this.code
        }
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dictDataList = data.data.records
          this.dictTotalPage = data.data.total
        } else {
          this.dictDataList = []
          this.dictTotalPage = 0
        }
      })
    },
    // 每页数
    sizeChangeDictHandle (val) {
      this.dictPageSize = val
      this.dictPageIndex = 1
      this.getDictDataList()
    },
    // 当前页
    currentChangeDictHandle (val) {
      this.dictPageIndex = val
      this.getDictDataList()
    },
    // 多选
    selectionChangeDictHandle (val) {
      this.dictDataListSelections = val
    },
    // 新增 / 修改
    addOrUpdateDictHandle (id) {
      this.dictVisible = true
      this.$nextTick(() => {
        this.$refs.dictAddOrUpdate.init(id)
      })
    },
    // 删除
    deleteDictHandle (id) {
      let ids = id ? [id] : this.dictDataListSelections.map(item => {
        return item.id
      })
      this.$confirm('确定对所选项进行[删除]操作?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: '/sys/dict/delete',
          method: 'post',
          data: ids
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500
            })
            this.getDictDataList()
          }
        })
      }).catch(() => {
      })
    }
  }
}
</script>
