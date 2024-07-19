<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="角色名称" prop="roleName">
        <el-input v-model="dataForm.roleName" placeholder="角色名称"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input type="textarea" v-model="dataForm.remark" placeholder="备注"></el-input>
      </el-form-item>
      <el-form-item size="mini" label="功能权限">
        <el-tree
          check-strictly
          :data="menuList"
          :props="menuListTreeProps"
          node-key="menuId"
          ref="menuListTree"
          :default-expand-all="true"
          show-checkbox>
        </el-tree>
      </el-form-item>
      <el-form-item size="mini" label="数据权限">
        <el-tree
          check-strictly
          :data="orgList"
          :props="orgListTreeProps"
          node-key="orgNo"
          ref="orgListTree"
          :default-expand-all="true"
          show-checkbox>
        </el-tree>
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
      visible: false,
      menuList: [],
      orgList: this.treeDataTranslate(JSON.parse(sessionStorage.getItem('orgList') || '[]'), 'orgNo', 'parentNo'),
      menuListTreeProps: {
        label: 'name',
        children: 'children'
      },
      orgListTreeProps: {
        label: 'orgName',
        children: 'children'
      },
      dataForm: {
        id: '',
        roleName: '',
        remark: ''
      },
      dataRule: {
        roleName: [
          {
            required: true,
            message: '角色名称不能为空',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.id = id || 0

      this.$http({
        url: '/sys/menu/list',
        method: 'get'
      }).then(({data}) => {
        this.menuList = this.treeDataTranslate(data.data, 'menuId')
      }).then(() => {
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.$refs.menuListTree.setCheckedKeys([])
        })
      }).then(() => {
        if (this.dataForm.id) {
          this.$http({
            url: `/sys/role/info/${this.dataForm.id}`,
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.roleName = data.data.roleName
              this.dataForm.remark = data.data.remark
              this.$refs.menuListTree.setCheckedKeys(data.data.menuIdList)
              this.$refs.orgListTree.setCheckedKeys(data.data.orgNoList)
            }
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: `/sys/role/${!this.dataForm.id ? 'save' : 'update'}`,
            method: 'post',
            data: {
              'roleId': this.dataForm.id || undefined,
              'roleName': this.dataForm.roleName,
              'remark': this.dataForm.remark,
              'menuIdList': [].concat(this.$refs.menuListTree.getCheckedKeys(), this.$refs.menuListTree.getHalfCheckedKeys()),
              'orgNoList': [].concat(this.$refs.orgListTree.getCheckedKeys(), this.$refs.orgListTree.getHalfCheckedKeys())
            }
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
