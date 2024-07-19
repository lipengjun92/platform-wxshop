<template>
  <div class="mod-reprocdef">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList(1)">
      <el-form-item>
        <el-input v-model="searchForm.key" placeholder="流程编号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.category" placeholder="流程命名空间" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(1)">查询</el-button>
        <el-button v-if="isAuth('act:reprocdef:deploy')" type="primary" @click="deployHandle()">部署流程文件</el-button>
        <el-button v-if="isAuth('act:reprocdef:delete')" type="danger" @click="deleteHandle()"
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
        prop="deploymentId"
        header-align="center"
        align="center"
        label="部署编号">
      </el-table-column>
      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        label="流程名称">
      </el-table-column>
      <el-table-column
        prop="key"
        header-align="center"
        align="center"
        label="流程编号">
      </el-table-column>
      <el-table-column
        prop="version"
        header-align="center"
        align="center"
        label="流程版本号">
      </el-table-column>
      <el-table-column
        prop="category"
        header-align="center"
        align="center"
        label="流程命名空间">
      </el-table-column>
      <el-table-column
        prop="resourceName"
        header-align="center"
        align="center"
        label="资源文件">
        <template slot-scope="scope">
          <a href="javascript:;" @click="read(scope.row.id,'xml')">{{scope.row.resourceName}}</a>
        </template>
      </el-table-column>
      <el-table-column
        prop="dgrmResourceName"
        header-align="center"
        align="center"
        label="图片资源文件">
        <template slot-scope="scope">
          <a href="javascript:;" @click="read(scope.row.id,'image')">{{scope.row.dgrmResourceName}}</a>
        </template>
      </el-table-column>
      <el-table-column
        prop="description"
        header-align="center"
        align="center"
        label="描述信息">
      </el-table-column>
      <el-table-column
        prop="suspensionState"
        header-align="center"
        align="center"
        label="是否挂起">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.suspensionState === 1" size="small" type="success">激活</el-tag>
          <el-tag v-else-if="scope.row.suspensionState === 2" size="small" type="danger">挂起</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        width="250px"
        prop="deployTime"
        header-align="center"
        align="center"
        label="部署时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="200"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('act:reprocdef:update')&&scope.row.suspensionState===2" type="text" size="small"
                     @click="updateHandle(scope.row.id,1)">激活
          </el-button>
          <el-button v-if="isAuth('act:reprocdef:update')&&scope.row.suspensionState===1" type="text" size="small"
                     @click="updateHandle(scope.row.id,2)">挂起
          </el-button>
          <el-button v-if="isAuth('act:reprocdef:update')" type="text" size="small"
                     @click="convertToModel(scope.row.id)">转为模型
          </el-button>
          <el-button v-if="isAuth('act:reprocdef:delete')" type="text" size="small"
                     @click="deleteHandle(scope.row.deploymentId)">
            删除
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
    <el-dialog
      title="上传文件"
      :lock-scroll="false"
      :close-on-click-modal="false"
      @close="closeHandle"
      :visible.sync="visible">
      <el-upload
        drag
        :action="url"
        :before-upload="beforeUploadHandle"
        :on-success="successHandle"
        :file-list="fileList"
        style="text-align: center;">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">支持文件格式：zip、bar、bpmn、bpmn20.xml</div>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      searchForm: {
        category: '',
        key: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListSelections: [],
      visible: false,
      url: '',
      fileList: []
    }
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
        url: '/act/reprocdef/list',
        method: 'get',
        params: {
          'page': this.pageIndex,
          'limit': this.pageSize,
          'category': this.searchForm.category,
          'key': this.searchForm.key
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
    // 激活 / 挂起
    updateHandle (id, state) {
      this.$http({
        url: '/act/reprocdef/update?id=' + id + '&state=' + state,
        method: 'post'
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$message({
            message: data.msg,
            type: 'success',
            duration: 2000
          })
          this.getDataList()
        }
      })
    },
    // 转为模型
    convertToModel (id) {
      this.$http({
        url: '/act/reprocdef/convertToModel?id=' + id,
        method: 'post'
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$message({
            message: '操作成功',
            type: 'success',
            duration: 2000
          })
          this.getDataList()
        }
      })
    },
    // 读取xml、png资源
    read (id, resType) {
      let url = this.$http.BASE_URL + `/act/reprocdef/read?id=${id}&resType=${resType}&token=${this.$cookie.get('token')}`
      window.open(url)
    },
    // 部署
    deployHandle () {
      this.visible = true
      this.url = this.$http.BASE_URL + `/act/reprocdef/deploy?token=${this.$cookie.get('token')}`
    },
    // 删除
    deleteHandle (deploymentId) {
      let deploymentIds = deploymentId ? [deploymentId] : this.dataListSelections.map(item => {
        return item.deploymentId
      })
      this.$confirm('确定对所选项进行[删除]操作?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: '/act/reprocdef/delete',
          method: 'post',
          data: deploymentIds
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
    // 上传之前
    beforeUploadHandle (file) {
      if (!file.name.endsWith('zip') && !file.name.endsWith('bar') && !file.name.endsWith('bpmn') && !file.name.endsWith('bpmn20.xml')) {
        this.$message.error('只支持zip、bar、bpmn、bpmn20.xml格式的图片！')
        return false
      }
    },
    // 上传成功
    successHandle (response, file, fileList) {
      this.fileList = fileList
      if (response && response.code === 0) {
        this.$message({
          message: response.msg,
          type: 'success',
          duration: 2000
        })
        this.getDataList()
      } else {
        this.$message.error(response.msg)
      }
      this.visible = false
    },
    // 弹窗关闭时
    closeHandle () {
      this.fileList = []
      this.$emit('refreshDataList')
    }
  }
}
</script>
