<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="" prop="couponId">
        <el-input v-model="dataForm.couponId" :disabled="disabled" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="" prop="couponNumber">
        <el-input v-model="dataForm.couponNumber" :disabled="disabled" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="" prop="userId">
        <el-input v-model="dataForm.userId" :disabled="disabled" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="" prop="usedTime">
        <el-input v-model="dataForm.usedTime" :disabled="disabled" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="" prop="addTime">
        <el-input v-model="dataForm.addTime" :disabled="disabled" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="" prop="orderId">
        <el-input v-model="dataForm.orderId" :disabled="disabled" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="状态 1. 可用 2. 已用 3. 过期" prop="couponStatus">
        <el-input v-model="dataForm.couponStatus" :disabled="disabled" placeholder="状态 1. 可用 2. 已用 3. 过期"></el-input>
      </el-form-item>
      <el-form-item label="来源key" prop="sourceKey">
        <el-input v-model="dataForm.sourceKey" :disabled="disabled" placeholder="来源key"></el-input>
      </el-form-item>
      <el-form-item label="发券人" prop="referrer">
        <el-input v-model="dataForm.referrer" :disabled="disabled" placeholder="发券人"></el-input>
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
          couponId: '',
          couponNumber: '',
          userId: '',
          usedTime: '',
          addTime: '',
          orderId: '',
          couponStatus: '',
          sourceKey: '',
          referrer: ''},
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
              url: `/mall/usercoupon/info/${this.dataForm.id}`,
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
                url: `/mall/usercoupon/${!this.dataForm.id ? 'save' : 'update'}`,
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
