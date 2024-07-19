<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="会员Id" prop="userId">
        <el-input v-model="dataForm.userId" :disabled="disabled" placeholder="会员Id"></el-input>
      </el-form-item>
      <el-form-item label="会员会员名称" prop="userName">
        <el-input v-model="dataForm.userName" :disabled="disabled" placeholder="会员会员名称"></el-input>
      </el-form-item>
      <el-form-item label="手机" prop="mobile">
        <el-input v-model="dataForm.mobile" :disabled="disabled" placeholder="手机"></el-input>
      </el-form-item>
      <el-form-item label="反馈类型" prop="feedType">
        <el-input v-model="dataForm.feedType" :disabled="disabled" placeholder="反馈类型"></el-input>
      </el-form-item>
      <el-form-item label="详细内容" prop="content">
        <el-input v-model="dataForm.content" :disabled="disabled" placeholder="详细内容"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-input v-model="dataForm.status" :disabled="disabled" placeholder="状态"></el-input>
      </el-form-item>
      <el-form-item label="反馈时间" prop="addTime">
        <el-input v-model="dataForm.addTime" :disabled="disabled" placeholder="反馈时间"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button v-if="!disabled" type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        disabled: false,
        visible: false,
        dataForm: {
          msgId: '',
          userId: '',
          userName: '',
          mobile: '',
          feedType: '',
          content: '',
          status: '',
          addTime: ''},
        dataRule: {
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id, disabled) {
        this.disabled = disabled
        this.dataForm.id = id || ''
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: `/mall/feedback/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.data
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm']
          .validate((valid) => {
            if (valid) {
              this.$http({
                url: `/mall/feedback/${!this.dataForm.id ? 'save' : 'update'}`,
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
