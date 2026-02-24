<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="120px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="dataForm.name" :disabled="disabled" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="优惠金额" prop="typeMoney">
        <el-input-number v-model="dataForm.typeMoney" :disabled="disabled" controls-position="right" :min="0"
                         :precision="2" step-strictly></el-input-number>
      </el-form-item>
      <el-form-item label="发放类型" prop="sendType">
        <el-select v-model="dataForm.sendType" :disabled="disabled" placeholder="类型">
          <el-option
            :key="0"
            label="满减券"
            :value="0">
          </el-option>
          <el-option
            :key="1"
            label="红包"
            :value="1">
          </el-option>
          <el-option
            :key="4"
            label="注册赠送"
            :value="4">
          </el-option>
          <el-option
            :key="7"
            label="包邮"
            :value="7">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="最小金额" prop="minAmount">
        <el-input-number v-model="dataForm.minAmount" :disabled="disabled" controls-position="right" :min="0"
                         :precision="2" step-strictly></el-input-number>
      </el-form-item>
      <el-form-item label="最大金额" prop="maxAmount">
        <el-input-number v-model="dataForm.maxAmount" :disabled="disabled" controls-position="right" :min="0"
                         :precision="2" step-strictly></el-input-number>
      </el-form-item>
      <el-form-item label="开始发送日期" prop="sendStartDate">
        <el-date-picker v-model="dataForm.sendStartDate" type="datetime" :disabled="disabled"></el-date-picker>
      </el-form-item>
      <el-form-item label="结束发送日期" prop="sendEndDate">
        <el-date-picker v-model="dataForm.sendEndDate" type="datetime" :disabled="disabled"></el-date-picker>
      </el-form-item>
      <el-form-item label="开始使用日期" prop="useStartDate">
        <el-date-picker v-model="dataForm.useStartDate" type="datetime" :disabled="disabled"></el-date-picker>
      </el-form-item>
      <el-form-item label="结束使用日期" prop="useEndDate">
        <el-date-picker v-model="dataForm.useEndDate" type="datetime" :disabled="disabled"></el-date-picker>
      </el-form-item>
      <el-form-item label="最小商品金额" prop="minGoodsAmount">
        <el-input-number v-model="dataForm.minGoodsAmount" :disabled="disabled" controls-position="right" :min="0"
                         :precision="2" step-strictly></el-input-number>
      </el-form-item>
      <el-form-item label="转发次数" prop="minTransmitNum">
        <el-input-number v-model="dataForm.minTransmitNum" :disabled="disabled" controls-position="right" :min="0"
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
      dataForm: {
        id: '',
        name: '',
        typeMoney: '',
        sendType: '',
        minAmount: '',
        maxAmount: '',
        sendStartDate: '',
        sendEndDate: '',
        useStartDate: '',
        useEndDate: '',
        minGoodsAmount: '',
        minTransmitNum: ''
      },
      dataRule: {
        name: [
          {required: true, message: '名称不能为空', trigger: 'blur'}
        ],
        sendType: [
          {required: true, message: '发放类型不能为空', trigger: 'blur'}
        ],
        sendStartDate: [
          {required: true, message: '开始发送日期不能为空', trigger: 'blur'}
        ],
        sendEndDate: [
          {required: true, message: '结束发送日期不能为空', trigger: 'blur'}
        ],
        useStartDate: [
          {required: true, message: '开始使用日期不能为空', trigger: 'blur'}
        ],
        useEndDate: [
          {required: true, message: '结束使用日期不能为空', trigger: 'blur'}
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
            url: `/mall/coupon/info/${this.dataForm.id}`,
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
              url: `/mall/coupon/${!this.dataForm.id ? 'save' : 'update'}`,
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
