<template>
  <el-dialog
    title="查看"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-descriptions :column="3" border>
      <el-descriptions-item>
        <template slot="label">
          手机
        </template>
        {{ dataForm.mobile }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          反馈时间
        </template>
        {{ dataForm.addTime }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          反馈类型
        </template>
        <el-dict code="FEED_TYPE" disabled v-model="dataForm.feedType"></el-dict>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          详细内容
        </template>
        {{ dataForm.content }}
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
        mobile: '',
        feedType: '',
        content: '',
        status: '',
        addTime: ''
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
            url: `/mall/feedback/info/${this.dataForm.id}`,
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
