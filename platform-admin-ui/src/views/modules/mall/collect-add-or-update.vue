<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="用户Id" prop="userId">
        <el-input v-model="dataForm.userId" :disabled="disabled" placeholder="用户Id"></el-input>
      </el-form-item>
      <el-form-item label="产品Id" prop="valueId">
        <el-input v-model="dataForm.valueId" :disabled="disabled" placeholder="产品Id"></el-input>
      </el-form-item>
      <el-form-item label="添加时间" prop="addTime">
        <el-input v-model="dataForm.addTime" :disabled="disabled" placeholder="添加时间"></el-input>
      </el-form-item>
      <el-form-item label="是否提醒" prop="isAttention">
        <el-input v-model="dataForm.isAttention" :disabled="disabled" placeholder="是否提醒"></el-input>
      </el-form-item>
      <el-form-item label="" prop="typeId">
        <el-input v-model="dataForm.typeId" :disabled="disabled" placeholder=""></el-input>
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
          id: '',
          userId: '',
          valueId: '',
          addTime: '',
          isAttention: '',
          typeId: ''},
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
              url: `/mall/collect/info/${this.dataForm.id}`,
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
                url: `/mall/collect/${!this.dataForm.id ? 'save' : 'update'}`,
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
