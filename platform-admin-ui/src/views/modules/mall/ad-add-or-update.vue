<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="120px">
      <el-form-item label="所属广告位" prop="adPositionId">
        <el-select v-model="dataForm.adPositionId" :disabled="disabled" clearable filterable placeholder="请选择">
          <el-option
            v-for="adPosition in adPositions"
            :key="adPosition.id"
            :label="adPosition.name"
            :value="adPosition.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="类型" prop="mediaType">
        <el-select v-model="dataForm.mediaType" :disabled="disabled" placeholder="类型">
          <el-option
            key="1"
            label="Banner广告"
            value="1">
          </el-option>
          <el-option
            key="2"
            label="激励广告"
            value="2">
          </el-option>
          <el-option
            key="3"
            label="插屏广告"
            value="3">
          </el-option>
          <el-option
            key="4"
            label="封面广告"
            value="4">
          </el-option>
          <el-option
            key="5"
            label="格子广告"
            value="5">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="dataForm.name" :disabled="disabled" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="链接" prop="link">
        <el-input v-model="dataForm.link" :disabled="disabled" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="图片" prop="imageUrl">
        <el-tooltip content="建议像素750*420，大小不超过50KB">
          <el-img v-model="dataForm.imageUrl" :disabled="disabled">
          </el-img>
        </el-tooltip>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker v-model="dataForm.endTime" :disabled="disabled"></el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="enabled">
        <el-radio-group v-model="dataForm.enabled" :disabled="disabled" class="width200">
          <el-radio-button :label="0">隐藏</el-radio-button>
          <el-radio-button :label="1">显示</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <el-input v-model="dataForm.content" type="textarea" :disabled="disabled" placeholder=""></el-input>
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
      adPositions: [],
      disabled: false,
      visible: false,
      dataForm: {
        id: '',
        adPositionId: '',
        mediaType: '',
        name: '',
        link: '',
        imageUrl: '',
        content: '',
        endTime: '',
        enabled: ''
      },
      dataRule: {
        adPositionId: [
          {required: true, message: '所属广告位不能为空', trigger: 'blur'}
        ],
        mediaType: [
          {required: true, message: '类型不能为空', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '名称不能为空', trigger: 'blur'}
        ],
        link: [
          {required: true, message: '链接不能为空', trigger: 'blur'}
        ],
        imageUrl: [
          {required: true, message: '图片不能为空', trigger: 'blur'}
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
            url: `/mall/ad/info/${this.dataForm.id}`,
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm = data.data
            }
          })
        }
        this.$http({
          url: '/mall/ad/queryAll',
          method: 'get',
          params: {
            parentId: 0
          }
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.adPositions = data.data
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
              url: `/mall/ad/${!this.dataForm.id ? 'save' : 'update'}`,
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
