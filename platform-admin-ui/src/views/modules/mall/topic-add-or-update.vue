<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="专题主题" prop="title">
        <el-input v-model="dataForm.title" :disabled="disabled" placeholder="专题主题"></el-input>
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <el-img v-model="dataForm.avatar" :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="专题图片" prop="itemPicUrl">
        <el-img v-model="dataForm.itemPicUrl" :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="子标题" prop="subtitle">
        <el-input v-model="dataForm.subtitle" :disabled="disabled" placeholder="子标题"></el-input>
      </el-form-item>
      <el-form-item label="专题类别" prop="topicCategoryId">
        <el-select v-model="dataForm.topicCategoryId" :disabled="disabled" clearable filterable placeholder="请选择">
          <el-option
            v-for="topicCategory in topicCategorys"
            :key="topicCategory.id"
            :label="topicCategory.title"
            :value="topicCategory.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="专题价格" prop="priceInfo">
        <el-input v-model="dataForm.priceInfo" :disabled="disabled" placeholder="专题价格"></el-input>
      </el-form-item>
      <el-form-item label="阅读数" prop="readCount">
        <el-input v-model="dataForm.readCount" :disabled="disabled" placeholder="阅读数"></el-input>
      </el-form-item>
      <el-form-item label="场景图片" prop="scenePicUrl">
        <el-img v-model="dataForm.scenePicUrl" :disabled="disabled"/>
      </el-form-item>
      <el-form-item label="专题内容" prop="content">
        <ueditor v-model="dataForm.content" :disabled="disabled" placeholder="专题内容"></ueditor>
        <el-button type="success" @click="openDetail">预览详情</el-button>
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
      topicCategorys: '',
      dataForm: {
        id: '',
        title: '',
        content: '',
        avatar: '',
        itemPicUrl: '',
        subtitle: '',
        topicCategoryId: '',
        priceInfo: '',
        readCount: '',
        scenePicUrl: '',
        topicTemplateId: '',
        topicTagId: ''
      },
      dataRule: {
        title: [
          {required: true, message: '专题主题不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    openDetail () {
      this.$alert(`<div style="width: 720px;height: 720px;overflow-y: auto;overflow-x: hidden"">${this.dataForm.content}</div>`, this.dataForm.title, {
        dangerouslyUseHTMLString: true,
        closeOnClickModal: true,
        callback: action => {
        }
      })
    },
    init (id, disabled) {
      this.disabled = disabled
      this.dataForm.id = id || ''
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: `/mall/topic/info/${this.dataForm.id}`,
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm = data.data
            }
          })
        }
        this.$http({
          url: '/mall/topiccategory/queryAll',
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.topicCategorys = data.data
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
              url: `/mall/topic/${!this.dataForm.id ? 'save' : 'update'}`,
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
