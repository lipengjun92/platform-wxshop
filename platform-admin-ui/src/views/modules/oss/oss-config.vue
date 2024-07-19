<template>
  <el-dialog
    title="云存储配置"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="140px">
      <el-form-item size="mini" label="存储类型">
        <el-radio-group v-model="dataForm.type">
          <el-radio :label="1">七牛</el-radio>
          <el-radio :label="2">阿里云</el-radio>
          <el-radio :label="3">腾讯云</el-radio>
          <el-radio :label="4">服务器</el-radio>
          <el-radio :label="5">MinIO</el-radio>
          <el-radio :label="6">华为云</el-radio>
        </el-radio-group>
      </el-form-item>
      <template v-if="dataForm.type === 1">
        <el-form-item label="域名">
          <el-input v-model="dataForm.qiniuDomain" placeholder="七牛绑定的域名"></el-input>
        </el-form-item>
        <el-form-item size="mini" label="存储区域">
          <el-radio-group v-model="dataForm.qiniuRegion">
            <el-radio :label="1">华东-浙江</el-radio>
            <el-radio :label="2">华北-河北</el-radio>
            <el-radio :label="3">华南-广东</el-radio>
            <el-radio :label="4">北美-洛杉矶</el-radio>
            <el-radio :label="5">亚太-新加坡（原东南亚）</el-radio>
            <el-radio :label="6">华东-浙江2</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="路径前缀">
          <el-input v-model="dataForm.qiniuPrefix" placeholder="不设置默认为空"></el-input>
        </el-form-item>
        <el-form-item label="AccessKey">
          <el-input v-model="dataForm.qiniuAccessKey" placeholder="七牛AccessKey"></el-input>
        </el-form-item>
        <el-form-item label="SecretKey">
          <el-input v-model="dataForm.qiniuSecretKey" placeholder="七牛SecretKey"></el-input>
        </el-form-item>
        <el-form-item label="空间名">
          <el-input v-model="dataForm.qiniuBucketName" placeholder="七牛存储空间名"></el-input>
        </el-form-item>
      </template>
      <template v-else-if="dataForm.type === 2">
        <el-form-item label="域名">
          <el-input v-model="dataForm.aliyunDomain" placeholder="阿里云绑定的域名"></el-input>
        </el-form-item>
        <el-form-item label="路径前缀">
          <el-input v-model="dataForm.aliyunPrefix" placeholder="不设置默认为空"></el-input>
        </el-form-item>
        <el-form-item label="EndPoint">
          <el-input v-model="dataForm.aliyunEndPoint" placeholder="阿里云EndPoint"></el-input>
        </el-form-item>
        <el-form-item label="AccessKeyId">
          <el-input v-model="dataForm.aliyunAccessKeyId" placeholder="阿里云AccessKeyId"></el-input>
        </el-form-item>
        <el-form-item label="AccessKeySecret">
          <el-input v-model="dataForm.aliyunAccessKeySecret" placeholder="阿里云AccessKeySecret"></el-input>
        </el-form-item>
        <el-form-item label="BucketName">
          <el-input v-model="dataForm.aliyunBucketName" placeholder="阿里云BucketName"></el-input>
        </el-form-item>
      </template>
      <template v-else-if="dataForm.type === 3">
        <el-form-item label="域名">
          <el-input v-model="dataForm.qcloudDomain" placeholder="腾讯云绑定的域名"></el-input>
        </el-form-item>
        <el-form-item label="路径前缀">
          <el-input v-model="dataForm.qcloudPrefix" placeholder="不设置默认为空"></el-input>
        </el-form-item>
        <el-form-item label="AppId">
          <el-input v-model="dataForm.qcloudAppId" placeholder="腾讯云AppId"></el-input>
        </el-form-item>
        <el-form-item label="SecretId">
          <el-input v-model="dataForm.qcloudSecretId" placeholder="腾讯云SecretId"></el-input>
        </el-form-item>
        <el-form-item label="SecretKey">
          <el-input v-model="dataForm.qcloudSecretKey" placeholder="腾讯云SecretKey"></el-input>
        </el-form-item>
        <el-form-item label="BucketName">
          <el-input v-model="dataForm.qcloudBucketName" placeholder="腾讯云BucketName"></el-input>
        </el-form-item>
        <el-form-item label="Bucket所属地区">
          <el-input v-model="dataForm.qcloudRegion" placeholder="如：sh（可选值 ，华南：gz 华北：tj 华东：sh）"></el-input>
        </el-form-item>
      </template>
      <template v-if="dataForm.type === 4">
        <el-form-item label="路径">
          <el-input v-model="dataForm.diskPath" placeholder="本地存储路径"></el-input>
        </el-form-item>
        <el-form-item label="代理服务器">
          <el-input v-model="dataForm.proxyServer" placeholder="本地存储代理服务器"></el-input>
        </el-form-item>
      </template>
      <template v-if="dataForm.type === 5">
        <el-form-item label="AccessKey">
          <el-input v-model="dataForm.minioAccessKey" placeholder="AccessKey"></el-input>
        </el-form-item>
        <el-form-item label="SecretKey">
          <el-input v-model="dataForm.minioSecretKey" placeholder="SecretKey"></el-input>
        </el-form-item>
        <el-form-item label="Url">
          <el-input v-model="dataForm.minioUrl" placeholder="Url"></el-input>
        </el-form-item>
        <el-form-item label="BucketName">
          <el-input v-model="dataForm.minioBucketName" placeholder="BucketName"></el-input>
        </el-form-item>
      </template>
      <template v-if="dataForm.type === 6">
        <el-form-item label="AccessKey">
          <el-input v-model="dataForm.huaweiAccessKey" placeholder="AccessKey"></el-input>
        </el-form-item>
        <el-form-item label="SecretKey">
          <el-input v-model="dataForm.huaweiSecretKey" placeholder="SecretKey"></el-input>
        </el-form-item>
        <el-form-item label="EndPoint">
          <el-input v-model="dataForm.huaweiEndPoint" placeholder="华为云EndPoint"></el-input>
        </el-form-item>
        <el-form-item label="BucketName">
          <el-input v-model="dataForm.huaweiBucketName" placeholder="华为云BucketName"></el-input>
        </el-form-item>
        <el-form-item label="路径前缀">
          <el-input v-model="dataForm.huaweiPrefix" placeholder="不设置默认为空"></el-input>
        </el-form-item>
      </template>
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
      dataForm: {},
      dataRule: {}
    }
  },
  methods: {
    init (id) {
      this.visible = true
      this.$http({
        url: '/sys/oss/config',
        method: 'get'
      }).then(({data}) => {
        this.dataForm = data && data.code === 0 ? data.data : []
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: '/sys/oss/saveConfig',
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
      })
    }
  }
}
</script>

