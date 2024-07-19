<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="参数名" prop="paramKey">
        <el-input v-model="dataForm.paramKey" :disabled="disabled" placeholder="参数名"></el-input>
      </el-form-item>
      <el-form-item label="参数值" prop="paramValue">
        <el-input v-model="dataForm.paramValue" :disabled="disabled" placeholder="参数值"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="dataForm.remark" type="textarea" :disabled="disabled" placeholder="备注"></el-input>
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
        paramKey: '',
        paramValue: '',
        remark: ''
      },
      dataRule: {
        paramKey: [
          {
            required: true,
            message: '参数名不能为空',
            trigger: 'blur'
          }
        ],
        paramValue: [
          {
            required: true,
            message: '参数值不能为空',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    init (id, disabled) {
      this.disabled = disabled
      this.dataForm.id = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: `/sys/config/info/${this.dataForm.id}`,
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.paramKey = data.data.paramKey
              this.dataForm.paramValue = data.data.paramValue
              this.dataForm.remark = data.data.remark
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
            url: `/sys/config/${!this.dataForm.id ? 'save' : 'update'}`,
            method: 'post',
            data: {
              'id': this.dataForm.id || undefined,
              'paramKey': this.dataForm.paramKey,
              'paramValue': this.dataForm.paramValue,
              'remark': this.dataForm.remark
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
