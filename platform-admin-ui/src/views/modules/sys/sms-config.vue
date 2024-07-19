<template>
  <el-dialog
    title="短信配置"
    :lock-scroll="false"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="140px">
      <el-form-item label="短信类型" prop="type">
        <el-radio-group v-model="dataForm.type">
          <el-radio-button :label="1">腾讯云SMS</el-radio-button>
          <el-radio-button :label="2">阿里云SMS</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="appid" prop="appid" v-if="dataForm.type === 1">
        <el-input v-model="dataForm.appid" placeholder="appid"></el-input>
      </el-form-item>
      <el-form-item label="appKey" prop="appkey" v-if="dataForm.type === 1">
        <el-input v-model="dataForm.appkey" placeholder="appkey"></el-input>
      </el-form-item>
      <el-form-item label="templateId" prop="templateId" v-if="dataForm.type === 1">
        <el-input v-model="dataForm.templateId" placeholder="templateId"></el-input>
      </el-form-item>
      <el-form-item label="accessKeyId" prop="accessKeyId" v-if="dataForm.type === 2">
        <el-input v-model="dataForm.accessKeyId" placeholder="accessKeyId"></el-input>
      </el-form-item>
      <el-form-item label="accessSecret" prop="accessSecret" v-if="dataForm.type === 2">
        <el-input v-model="dataForm.accessSecret" placeholder="accessSecret"></el-input>
      </el-form-item>
      <el-form-item label="templateCode" prop="templateCode" v-if="dataForm.type === 2">
        <el-input v-model="dataForm.templateCode" placeholder="templateCode"></el-input>
      </el-form-item>
      <el-form-item label="签名" prop="sign">
        <el-input v-model="dataForm.sign" placeholder="公司简称"></el-input>
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
        type: 1,
        appid: '',
        appkey: '',
        templateId: '',
        accessKeyId: '',
        accessSecret: '',
        templateCode: '',
        sign: ''
      },
      dataRule: {
        sign: [
          {
            required: true,
            message: '签名不能为空',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    init () {
      this.visible = true
      this.$nextTick(() => {
        this.$http({
          url: `/sys/smslog/config`,
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataForm = data.data
          }
        })
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm']
        .validate((valid) => {
          if (valid) {
            let flag = true
            if (this.dataForm.type === 1) {
              if (!this.dataForm.appid) {
                flag = false
                this.$message({
                  message: 'appid不能为空',
                  type: 'error',
                  duration: 1500
                })
              }
              if (!this.dataForm.appkey) {
                flag = false
                this.$message({
                  message: 'appkey不能为空',
                  type: 'error',
                  duration: 1500
                })
              }
              if (!this.dataForm.templateId) {
                flag = false
                this.$message({
                  message: 'templateId不能为空',
                  type: 'error',
                  duration: 1500
                })
              }
            }
            if (this.dataForm.type === 2) {
              if (!this.dataForm.accessKeyId) {
                flag = false
                this.$message({
                  message: 'accessKeyId不能为空',
                  type: 'error',
                  duration: 1500
                })
              }
              if (!this.dataForm.accessSecret) {
                flag = false
                this.$message({
                  message: 'accessSecret不能为空',
                  type: 'error',
                  duration: 1500
                })
              }
              if (!this.dataForm.templateCode) {
                flag = false
                this.$message({
                  message: 'templateCode不能为空',
                  type: 'error',
                  duration: 1500
                })
              }
            }
            if (flag) {
              this.$http({
                url: `/sys/smslog/saveConfig`,
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
                }
              })
            }
          }
        })
    }
  }
}
</script>
