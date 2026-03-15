<template>
  <el-dialog
    title="查看"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" label-width="80px">
      <el-form-item label="" prop="goodsId">
        <el-input v-model="dataForm.goodsId" :disabled="disabled" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="" prop="specificationId">
        <el-input v-model="dataForm.specificationId" :disabled="disabled" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="" prop="value">
        <el-input v-model="dataForm.value" :disabled="disabled" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="" prop="picUrl">
        <el-input v-model="dataForm.picUrl" :disabled="disabled" placeholder=""></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关闭</el-button>
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
          goodsId: '',
          specificationId: '',
          value: '',
          picUrl: ''},
        dataRule: {
          name: [
            {required: true, message: '名称不能为空', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      init (id, disabled) {
        this.disabled = true
        this.dataForm.id = id || ''
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: `/mall/goodsspecification/info/${this.dataForm.id}`,
              method: 'get'
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm = data.data
              }
            })
          }
        })
      },
      dataFormSubmit () {}
    }
  }
</script>
