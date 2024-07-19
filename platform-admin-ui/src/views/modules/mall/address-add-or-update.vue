<template>
  <el-dialog
    title="查看"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-descriptions :column="2" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          收货人
        </template>
        {{dataForm.userName}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          手机
        </template>
        {{dataForm.mobile}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          邮编
        </template>
        {{dataForm.postalCode}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          省
        </template>
        {{dataForm.provinceName}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          市
        </template>
        {{dataForm.cityName}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          区
        </template>
        {{dataForm.countyName}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-map-location"></i>
          详细地址
        </template>
        {{dataForm.detailInfo}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-star-off"></i>
          默认地址
        </template>
        <el-tag v-if="dataForm.isDefault === 0" size="small" type="danger">否</el-tag>
        <el-tag v-else-if="dataForm.isDefault === 1" size="small" type="success">是</el-tag>
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
        userId: '',
        userName: '',
        mobile: '',
        postalCode: '',
        nationalCode: '',
        provinceName: '',
        cityName: '',
        countyName: '',
        detailInfo: '',
        isDefault: ''
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
            url: `/mall/address/info/${this.dataForm.id}`,
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
