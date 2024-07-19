<template>
  <el-dialog
    :title="!dataForm.orgNo ? '新增' : '修改'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form inline :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="机构名称" prop="orgName">
        <el-input v-model="dataForm.orgName" placeholder="机构名称" class="width200"></el-input>
      </el-form-item>
      <el-form-item label="上级机构" prop="parentName">
        <el-dialog
          width="30%"
          append-to-body
          title="选择区域"
          :lock-scroll="false"
          :visible.sync="visiblePopover">
          <el-tree
            :data="parentOptions"
            :props="orgListTreeProps"
            node-key="orgNo"
            ref="orgListTree"
            @current-change="orgListTreeCurrentChangeHandle"
            default-expand-all
            :expand-on-click-node="false">
          </el-tree>
        </el-dialog>
        <el-button @click="visiblePopover = true" class="width200">{{dataForm.parentName || '点击选择上级机构'}}</el-button>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="dataForm.status" class="width200">
          <el-radio-button :label="1" border>正常</el-radio-button>
          <el-radio-button :label="0" border>禁用</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="排序号" prop="sort">
        <el-input-number v-model="dataForm.sort" controls-position="right" :min="0" step-strictly></el-input-number>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      testTime: '',
      parentOptions: [],
      orgListTreeProps: {
        label: 'orgName',
        children: 'children'
      },
      visible: false,
      visiblePopover: false,
      dataForm: {
        orgNo: 0,
        orgName: '',
        parentNo: '',
        parentName: '',
        orgType: '',
        status: 1,
        sort: ''
      },
      dataRule: {
        orgName: [
          {
            required: true,
            message: '名称不能为空',
            trigger: 'blur'
          }
        ],
        status: [
          {
            required: true,
            message: '状态不能为空',
            trigger: 'blur'
          }
        ],
        sort: [
          {
            required: true,
            message: '排序不能为空',
            trigger: 'blur'
          }
        ],
        parentName: [
          {
            required: true,
            message: '上级机构不能为空',
            trigger: 'change'
          }
        ]
      }
    }
  },
  methods: {
    init (orgNo, parentName) {
      this.dataForm.orgNo = orgNo || 0
      this.$http({
        url: '/sys/org/queryAll',
        method: 'get'
      }).then(({data}) => {
        data.data.push({
          'orgNo': '0',
          'orgName': '顶级'
        })
        this.parentOptions = this.treeDataTranslate(data.data, 'orgNo', 'parentNo')
      }).then(() => {
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
        })
      }).then(() => {
        if (this.dataForm.orgNo) {
          // 修改
          this.$http({
            url: `/sys/org/info/${this.dataForm.orgNo}`,
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm = data.data
              this.dataForm.parentName = parentName || ''
            }
          })
        }
      })
    },
    // 机构树选中
    orgListTreeCurrentChangeHandle (data) {
      if (data.orgNo === this.dataForm.orgNo) {
        this.$message({
          message: '不可设置自己为自己的上级机构！',
          type: 'error',
          duration: 1500
        })
        return
      }
      if (data.parentNo === this.dataForm.orgNo) {
        this.$message({
          message: '不可设置自己的下级为上级机构！',
          type: 'error',
          duration: 1500
        })
        return
      }
      this.dataForm.parentNo = data.orgNo
      this.dataForm.parentName = data.orgName
      this.visiblePopover = false
    },
    // 表单提交
    dataFormSubmit () {
      if (!this.dataForm.parentNo) {
        this.dataForm.parentNo = 0
      }
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: `/sys/org/${!this.dataForm.orgNo ? 'save' : 'update'}`,
            method: 'post',
            data: this.dataForm
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500
              })
              this.visible = false
              this.$emit('refreshDataList')
            }
          })
        }
      })
    }
  }
}
</script>
