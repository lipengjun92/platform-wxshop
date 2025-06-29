<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="关键字" prop="keyword">
        <el-input v-model="dataForm.keyword" :disabled="disabled" placeholder="关键字"></el-input>
      </el-form-item>
      <el-form-item label="热搜" prop="isHot">
        <el-radio-group v-model="dataForm.isHot" :disabled="disabled" class="width200">
          <el-radio-button :label="0">否</el-radio-button>
          <el-radio-button :label="1">是</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="默认" prop="isDefault">
        <el-radio-group v-model="dataForm.isDefault" :disabled="disabled" class="width200">
          <el-radio-button :label="0">否</el-radio-button>
          <el-radio-button :label="1">是</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="显示" prop="isShow">
        <el-radio-group v-model="dataForm.isShow" :disabled="disabled" class="width200">
          <el-radio-button :label="0">否</el-radio-button>
          <el-radio-button :label="1">是</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="排序" prop="sortOrder">
        <el-input-number v-model="dataForm.sortOrder" :disabled="disabled" controls-position="right" :min="0"
                         step-strictly></el-input-number>
      </el-form-item>
      <el-form-item label="跳转链接" prop="schemeUrl">
        <el-input v-model="dataForm.schemeUrl" :disabled="disabled" placeholder="关键词的跳转链接"></el-input>
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-radio-group v-model="dataForm.type" :disabled="disabled">
          <el-radio-button :label="0">小程序</el-radio-button>
          <el-radio-button :label="1">公众号</el-radio-button>
          <el-radio-button :label="2">APP</el-radio-button>
          <el-radio-button :label="3">PC</el-radio-button>
        </el-radio-group>
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
        keyword: '',
        isHot: 0,
        isDefault: 0,
        isShow: 1,
        sortOrder: 0,
        schemeUrl: '',
        type: 1
      },
      dataRule: {
        keyword: [
          {required: true, message: '关键字不能为空', trigger: 'blur'}
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
            url: `/mall/keywords/info/${this.dataForm.id}`,
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
              url: `/mall/keywords/${!this.dataForm.id ? 'save' : 'update'}`,
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
