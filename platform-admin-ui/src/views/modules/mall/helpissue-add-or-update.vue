<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="问题分类" prop="typeId">
        <el-select v-model="dataForm.typeId" :disabled="disabled" clearable filterable
                   placeholder="请选择">
          <el-option
            v-for="helpType in helpTypes"
            :key="helpType.id"
            :label="helpType.typeName"
            :value="helpType.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="问题" prop="question">
        <el-input v-model="dataForm.question" :disabled="disabled" placeholder="问题"></el-input>
      </el-form-item>
      <el-form-item label="回答" prop="answer">
        <el-input type="textarea" :rows="4" v-model="dataForm.answer" :disabled="disabled" placeholder="回答"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number v-model="dataForm.sort" :disabled="disabled" controls-position="right" :min="0"
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
      helpTypes: [],
      dataForm: {
        id: '',
        typeId: '',
        question: '',
        answer: '',
        sort: ''
      },
      dataRule: {
        typeId: [
          {required: true, message: '问题分类不能为空', trigger: 'blur'}
        ],
        question: [
          {required: true, message: '问题不能为空', trigger: 'blur'}
        ],
        answer: [
          {required: true, message: '回答不能为空', trigger: 'blur'}
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
            url: `/mall/helpissue/info/${this.dataForm.id}`,
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm = data.data
            }
          })
        }
        this.$http({
          url: '/mall/helptype/queryAll',
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.helpTypes = data.data
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
              url: `/mall/helpissue/${!this.dataForm.id ? 'save' : 'update'}`,
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
