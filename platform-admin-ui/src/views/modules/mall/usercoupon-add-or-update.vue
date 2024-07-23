<template>
  <el-dialog
    title="查看"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-descriptions :column="3" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          会员昵称
        </template>
        {{ dataForm.nickname }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          优惠券
        </template>
        {{ dataForm.title }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-date"></i>
          领用时间
        </template>
        {{ dataForm.addTime }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-star-off"></i>
          领取类型
        </template>
        <el-tag v-if="dataForm.type === 0" size="small" type="success">平台发放</el-tag>
        <el-tag v-else-if="dataForm.type === 1" size="small" type="danger">自动发放</el-tag>
        <el-tag v-else-if="dataForm.type === 2" size="small" type="danger">领券中心领取</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-star-off"></i>
          状态
        </template>
        <el-tag v-if="dataForm.status === 0" size="small" type="success">未使用</el-tag>
        <el-tag v-else-if="dataForm.status === 1" size="small" type="danger">已使用</el-tag>
        <el-tag v-else-if="dataForm.status === 2" size="small" type="info">过期</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-alarm-clock"></i>
          使用时间
        </template>
        {{ dataForm.usedTime }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-wallet"></i>
          订单编号
        </template>
        {{ dataForm.orderSn }}
      </el-descriptions-item>
    </el-descriptions>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      dataForm: {
        id: '',
        userName: '',
        title: '',
        addTime: '',
        type: '',
        status: '',
        usedTime: '',
        orderSn: ''
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.id = id || ''
      this.visible = true
      this.$nextTick(() => {
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
    }
  }
}
</script>
