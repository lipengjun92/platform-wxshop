<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form inline :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="分类名称" prop="name">
        <el-input v-model="dataForm.name" :disabled="disabled" placeholder="分类名称" class="width200"></el-input>
      </el-form-item>
      <el-form-item label="级别" prop="level">
        <el-radio-group v-model="dataForm.level" :disabled="disabled" class="width200">
          <el-radio-button label="L1">L1</el-radio-button>
          <el-radio-button label="L2">L2</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="是否显示" prop="isShow">
        <el-radio-group v-model="dataForm.isShow" :disabled="disabled" class="width200">
          <el-radio-button :label="1">是</el-radio-button>
          <el-radio-button :label="0">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="父节点" v-if="dataForm.level!==1" prop="parentId">
        <el-select v-model="dataForm.parentId" :disabled="disabled" clearable filterable placeholder="请选择">
          <el-option
            v-for="parent in parents"
            :key="parent.id"
            :label="parent.name"
            :value="parent.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="简介" prop="frontName">
        <el-input v-model="dataForm.frontName" :disabled="disabled" placeholder="简介" class="width200"></el-input>
      </el-form-item>
      <el-form-item label="顶部大图" v-if="dataForm.level===1" prop="imgUrl">
        <el-tooltip content="建议像素1080*320，大小不超过500KB">
          <el-img v-model="dataForm.imgUrl" :disabled="disabled">
          </el-img>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="icon链接" v-if="dataForm.level===2" prop="iconUrl">
        <el-tooltip content="建议像素200*200，大小不超过50KB">
          <el-img v-model="dataForm.iconUrl" :disabled="disabled">
          </el-img>
        </el-tooltip>
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
  data() {
    return {
      disabled: false,
      visible: false,
      dataForm: {
        id: '',
        name: '',
        level: 1,
        parentId: '',
        sortOrder: 1,
        isShow: 1,
        imgUrl: '',
        iconUrl: '',
        frontName: ''
      },
      parents: [],
      dataRule: {
        name: [
          {
            required: true,
            message: '名称不能为空',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    init(id, disabled) {
      this.disabled = disabled
      this.dataForm.id = id || ''
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: `/mall/category/info/${this.dataForm.id}`,
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm = data.data
            }
          })
        } else {
          this.dataForm = {
            id: '',
            name: '',
            level: 1,
            parentId: '',
            sort: 1,
            isShow: 1,
            imgUrl: '',
            iconUrl: '',
            frontName: ''
          }
        }
        this.$http({
          url: '/mall/category/queryAll',
          method: 'get',
          params: {
            parentId: 0
          }
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.parents = data.data
          }
        })
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm']
        .validate((valid) => {
          if (valid) {
            this.$http({
              url: `/mall/category/${!this.dataForm.id ? 'save' : 'update'}`,
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
