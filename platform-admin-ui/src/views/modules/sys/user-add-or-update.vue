<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form inline :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="用户名" prop="userName">
        <el-input v-model="dataForm.userName" placeholder="登录帐号" class="width200"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="dataForm.status" class="width200">
          <el-radio-button :label="0">禁用</el-radio-button>
          <el-radio-button :label="1">正常</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-dict code="SEX" v-model="dataForm.sex"></el-dict>
      </el-form-item>
      <el-form-item label="真实姓名" prop="realName">
        <el-input v-model="dataForm.realName" placeholder="真实姓名" class="width200"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="dataForm.email" placeholder="邮箱" class="width200"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="dataForm.mobile" placeholder="手机号" class="width200"></el-input>
      </el-form-item>
      <el-form-item label="所属机构" prop="orgNo">
        <el-dialog
          width="30%"
          append-to-body
          title="选择区域"
          :lock-scroll="false"
          :visible.sync="visiblePopover">
          <el-tree
            :data="orgNoOptions"
            :props="orgListTreeProps"
            node-key="orgNo"
            ref="orgListTree"
            @current-change="orgListTreeCurrentChangeHandle"
            default-expand-all
            :expand-on-click-node="false">
          </el-tree>
        </el-dialog>
        <el-button @click="visiblePopover = true" class="width200">{{dataForm.orgName || '点击选择所属机构'}}</el-button>
      </el-form-item>
      <el-form-item label="角色" prop="roleIdList">
        <el-select v-model="dataForm.roleIdList" multiple clearable filterable placeholder="请选择">
          <el-option
            v-for="role in roleList"
            :key="role.roleId"
            :label="role.roleName"
            :value="role.roleId">
          </el-option>
        </el-select>
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
      orgNoOptions: this.treeDataTranslate(JSON.parse(sessionStorage.getItem('orgList') || '[]'), 'orgNo', 'parentNo'),
      orgListTreeProps: {
        label: 'orgName',
        children: 'children'
      },
      visible: false,
      visiblePopover: false,
      roleList: [],
      dataForm: {
        orgName: '',
        id: '',
        userName: '',
        sex: '',
        realName: '',
        salt: '',
        email: '',
        mobile: '',
        orgNo: '',
        roleIdList: [],
        status: 1
      },
      dataRule: {
        userName: [{
          required: true,
          message: '用户名不能为空',
          trigger: 'blur'
        }],
        sex: [{
          required: true,
          message: '性别不能为空',
          trigger: 'blur'
        }],
        realName: [{
          required: true,
          message: '真实姓名不能为空',
          trigger: 'blur'
        }]
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.id = id || 0
      this.$http({
        url: '/sys/role/select',
        method: 'get'
      }).then(({data}) => {
        this.roleList = data && data.code === 0 ? data.data : []
      }).then(() => {
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
        })
      }).then(() => {
        if (this.dataForm.id) {
          this.$http({
            url: `/sys/user/info/${this.dataForm.id}`,
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.userName = data.data.userName
              this.dataForm.sex = data.data.sex.toString()
              this.dataForm.realName = data.data.realName
              this.dataForm.salt = data.data.salt
              this.dataForm.email = data.data.email
              this.dataForm.mobile = data.data.mobile
              this.dataForm.orgNo = data.data.orgNo
              this.dataForm.orgName = this.transOrg(data.data.orgNo)
              this.dataForm.roleIdList = data.data.roleIdList
              this.dataForm.status = data.data.status
            }
          })
        } else {
          this.dataForm.orgName = ''
        }
      })
    },
    // 机构树选中
    orgListTreeCurrentChangeHandle (data) {
      this.dataForm.orgNo = data.orgNo
      this.dataForm.orgName = data.orgName
      this.visiblePopover = false
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: `/sys/user/${!this.dataForm.id ? 'save' : 'update'}`,
            method: 'post',
            data: {
              'userId': this.dataForm.id || undefined,
              'userName': this.dataForm.userName,
              'sex': this.dataForm.sex,
              'realName': this.dataForm.realName,
              'salt': this.dataForm.salt,
              'email': this.dataForm.email,
              'mobile': this.dataForm.mobile,
              'orgNo': this.dataForm.orgNo,
              'status': this.dataForm.status,
              'roleIdList': this.dataForm.roleIdList
            }
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.visible = false
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500
              })
              this.$emit('refreshDataList')
            }
          })
        }
      })
    }
  }
}
</script>
