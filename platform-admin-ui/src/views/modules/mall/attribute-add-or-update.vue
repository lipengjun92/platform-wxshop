<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="属性名称" prop="name">
        <el-input v-model="dataForm.name" :disabled="disabled" placeholder="属性名称"></el-input>
      </el-form-item>
      <el-form-item label="类型" prop="attributeCategoryId">
        <el-select v-model="dataForm.attributeCategoryId" :disabled="disabled" clearable filterable
                   placeholder="请选择">
          <el-option
            v-for="topicCategory in categorys"
            :key="topicCategory.id"
            :label="topicCategory.name"
            :value="topicCategory.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="排序" prop="sortOrder">
        <el-input-number v-model="dataForm.sortOrder" :disabled="disabled" controls-position="right" :min="0"
                         step-strictly></el-input-number>
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
      categorys: [],
      dataForm: {
        id: '',
        attributeCategoryId: '',
        name: '',
        inputType: '',
        value: '',
        sortOrder: ''
      },
      dataRule: {
        attributeCategoryId: [
          {required: true, message: '类型不能为空', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '属性名称不能为空', trigger: 'blur'}
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
            url: `/mall/attribute/info/${this.dataForm.id}`,
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm = data.data
            }
          })
        }
        this.$http({
          url: '/mall/attributecategory/queryAll',
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.categorys = data.data
          }
        })
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm']
        .validate((valid) => {
          if (valid) {
            this.$http({
              url: `/mall/attribute/${!this.dataForm.id ? 'save' : 'update'}`,
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
