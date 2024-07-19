<template>
  <div class="site-wrapper site-page--login min300">
    <div class="site-content__wrapper">
      <div class="site-content">
        <div class="brand-info">
          <h2 class="brand-info__text">微同基础架构</h2>
          <p class="brand-info__intro">微同基础架构后台管理系统</p>
        </div>
        <div class="login-main">
          <h3 class="login-title">管理员登录</h3>
          <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
                   status-icon>
            <el-form-item prop="userName">
              <el-input v-model="dataForm.userName" placeholder="帐号" autofocus></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="dataForm.password" type="password" placeholder="密码"></el-input>
            </el-form-item>
            <el-form-item prop="captcha">
              <el-row :gutter="20">
                <el-col :span="14">
                  <el-input v-model="dataForm.captcha" placeholder="验证码">
                  </el-input>
                </el-col>
                <el-col :span="10" class="login-captcha">
                  <img :src="captchaPath" @click="getCaptcha()" alt=""
                       style="height: 32px; width: 96px; border-radius: 4px;">
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="dataFormSubmit()" style="width: 100%">登录</el-button>
            </el-form-item>
          </el-form>
        </div>

        <div class="brand-info">
          <p class="brand-info__intro">
            <el-link type="success" target="_blank" href="http://www.beian.miit.gov.cn">安徽微同科技有限公司 © 2019 |
              皖ICP备18002832号-1
            </el-link>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {getUUID} from '@/utils'
import {Encrypt} from '@/utils/AESUtils'

export default {
  data () {
    return {
      dataForm: {
        userName: '',
        password: '',
        uuid: '',
        captcha: ''
      },
      dataRule: {
        userName: [
          {
            required: true,
            message: '帐号不能为空',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '密码不能为空',
            trigger: 'blur'
          }
        ],
        captcha: [
          {
            required: true,
            message: '验证码不能为空',
            trigger: 'blur'
          }
        ]
      },
      captchaPath: ''
    }
  },
  created () {
    this.getCaptcha()
  },
  methods: {
    // 提交表单
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: '/sys/login',
            method: 'post',
            data: {
              'userName': this.dataForm.userName,
              'password': Encrypt(this.dataForm.password),
              'uuid': this.dataForm.uuid,
              'captcha': this.dataForm.captcha
            }
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$cookie.set('token', data.data)
              this.$router.push({path: '/home'})
            } else {
              this.getCaptcha()
            }
          })
        }
      })
    },
    // 获取验证码
    getCaptcha () {
      this.dataForm.uuid = getUUID()
      this.captchaPath = this.$http.BASE_URL + `/captcha.jpg?uuid=${this.dataForm.uuid}`
    }
  }
}
</script>

<style lang="scss">
body {
  height: auto;
  background: url(~@/assets/img/login_bg.jpg) no-repeat center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}

.site-wrapper.site-page--login {
  text-align: center;
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  overflow: hidden;

  .site-content__wrapper {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    padding: 0;
    margin: 0;
    overflow-x: hidden;
    overflow-y: auto;
    background-color: transparent;
  }

  .brand-info {
    margin: 10% 100px 0 90px;
    color: #fff;
  }

  .brand-info__text {
    margin: 0 0 22px 0;
    font-size: 28px;
    font-weight: 400;
    text-transform: uppercase;
  }

  .brand-info__intro {
    margin: 10px 0;
    font-size: 16px;
    line-height: 1.58;
    opacity: .6;
  }

  .login-main {
    border: 0px solid;
    border-radius: 20px;
    margin: 0 auto;
    max-width: 400px;
    min-width: 300px;
    top: 0;
    right: 0;
    padding: 10px 60px 10px;
    color: #fff;
    background: rgba(109, 109, 109, 0.23);
  }

  .login-title {
    font-size: 16px;
  }

  .login-captcha {
    overflow: hidden;

    > img {
      width: 100%;
      cursor: pointer;
    }

  }

  .login-btn-submit {
    width: 100%;
    margin-top: 38px;
  }

}
</style>
