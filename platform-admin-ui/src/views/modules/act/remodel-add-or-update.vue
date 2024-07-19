<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="模型名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="模型名称"></el-input>
      </el-form-item>
      <el-form-item label="模型标识" prop="key">
        <el-input v-model="dataForm.key" placeholder="模型标识"></el-input>
      </el-form-item>
      <el-form-item label="模型描述" prop="description">
        <el-input v-model="dataForm.description" placeholder="模型描述"></el-input>
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
      dataForm: {
        name: '',
        key: '',
        description: ''
      },
      dataRule: {
        name: [
          {
            required: true,
            message: '模型名称不能为空',
            trigger: 'blur'
          }
        ],
        key: [
          {
            required: true,
            message: '模型标识不能为空',
            trigger: 'blur'
          }
        ],
        description: [
          {
            required: true,
            message: '模型描述不能为空',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.id = id || ''
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm']
        .validate((valid) => {
          if (valid) {
            this.$http({
              url: `/act/remodel/${!this.dataForm.id ? 'save' : 'update'}`,
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
