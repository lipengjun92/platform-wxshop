<template>
  <div class="mod-category">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="searchForm.pName" placeholder="上级分类" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.name" placeholder="分类名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('mall:category:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      row-key="id"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="name"
        header-align="center"
        width="200"
        label="分类名称">
      </el-table-column>
      <el-table-column
        prop="parentName"
        header-align="center"
        align="center"
        label="上级分类">
      </el-table-column>
      <el-table-column
        width="130"
        show-tooltip-when-overflow
        prop="goodsSn"
        header-align="center"
        align="center"
        label="小程序内链接">
        <template slot-scope="scope">
          <el-button type="text" size="small" v-clipboard:copy="'/pages/category/category?categoryId=' + scope.row.id"
                     v-clipboard:success="copySuccess">
            /pages/category/category?categoryId={{scope.row.id}}(点击复制)
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="level"
        header-align="center"
        align="center"
        label="级别">
      </el-table-column>
      <el-table-column
        prop="sortOrder"
        header-align="center"
        align="center"
        label="排序">
      </el-table-column>
      <el-table-column
        prop="isShow"
        header-align="center"
        align="center"
        label="是否显示">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isShow === 0" size="small" type="danger">否</el-tag>
          <el-tag v-else-if="scope.row.isShow === 1" size="small" type="success">是</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="imgUrl"
        header-align="center"
        align="center"
        label="顶部大图">
        <template slot-scope="scope">
          <img style="height: 50%;width: 50%" v-if="scope.row.imgUrl" @click="openImg(scope.row.imgUrl)"
               :src="scope.row.imgUrl"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="iconUrl"
        header-align="center"
        align="center"
        label="icon链接">
        <template slot-scope="scope">
          <img style="height: 50%;width: 50%" v-if="scope.row.iconUrl" @click="openImg(scope.row.iconUrl)"
               :src="scope.row.iconUrl"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="frontName"
        header-align="center"
        align="center"
        label="简介">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('mall:category:info')" type="text" size="small" @click="showDetails(scope.row.id)">
            查看
          </el-button>
          <el-button v-if="isAuth('mall:category:update')" type="text" size="small"
                     @click="addOrUpdateHandle(scope.row.id)">修改
          </el-button>
          <el-button v-if="isAuth('mall:category:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from './category-add-or-update'

export default {
  data () {
    return {
      searchForm: {
        name: '',
        pName: ''
      },
      dataList: [],
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
    copySuccess () {
      this.$message({
        message: '复制成功',
        type: 'success'
      })
    },
    // 获取数据列表
    getDataList () {
      this.$http({
        url: '/mall/category/queryAll',
        method: 'get',
        params: {
          'name': this.searchForm.name,
          'pName': this.searchForm.pName
        }
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = this.treeDataTranslate(data.data, 'id', 'parentId')
        } else {
          this.dataList = []
        }
      })
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
      this.$confirm('确定对所选项进行[删除]操作?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: '/mall/category/delete',
          method: 'post',
          data: [id]
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
