<template>
  <el-dialog
    title="网络检测"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-alert
      style="margin: 0 0 30px 0"
      title="为了帮助开发者排查回调连接失败的问题，提供这个网络检测的API。它可以对开发者URL做域名解析，然后对所有IP进行一次ping操作，得到丢包率和耗时。(请勿重复调用)"
      type="warning"
      :closable="false"
      show-icon>
    </el-alert>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>DNS</span>
      </div>
      <el-table :data="dataForm.dnsInfos">
        <el-table-column property="realOperator" label="运营商">
          <template slot-scope="scope">
            <span>{{getOperator(scope.row.realOperator)}} </span>
          </template>
        </el-table-column>
        <el-table-column property="ip" label="ip"></el-table-column>
      </el-table>
    </el-card>
    <el-divider></el-divider>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>PING</span>
      </div>
      <el-table :data="dataForm.pingInfos">
        <el-table-column property="fromOperator" label="运营商">
          <template slot-scope="scope">
            <span>{{getOperator(scope.row.fromOperator)}} </span>
          </template>
        </el-table-column>
        <el-table-column property="ip" label="ip"></el-table-column>
        <el-table-column property="packageLoss" label="丢包率"></el-table-column>
        <el-table-column property="time" label="耗时"></el-table-column>
      </el-table>
    </el-card>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      dataForm: {dnsInfos: [], pingInfos: []}
    }
  },
  methods: {
    init () {
      this.visible = true
      this.$nextTick(() => {
        this.$http({
          url: `/manage/wxMenu/netCheck`,
          method: 'get',
          params: {
            'action': 'all'
          }
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataForm = data.data
          }
        })
      })
    },
    getOperator (operator) {
      switch (operator) {
        case 'CHINANET':
          return '电信'
        case 'UNICOM':
          return '联通'
        case 'CAP':
          return '腾讯自建'
        default:
          return ''
      }
    }
  }
}
</script>
