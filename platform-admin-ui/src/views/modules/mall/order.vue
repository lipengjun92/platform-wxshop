<template>
  <div class="mod-order">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getDataList(1)">
      <el-form-item>
        <el-input v-model="searchForm.orderSn" placeholder="订单编号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.nickname" placeholder="用户昵称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchForm.orderStatus" placeholder="订单状态" clearable>
          <el-option label="待付款" :value="0"></el-option>
          <el-option label="待发货" :value="1"></el-option>
          <el-option label="待收货" :value="2"></el-option>
          <el-option label="已完成" :value="3"></el-option>
          <el-option label="已取消" :value="4"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList(1)">查询</el-button>
        <el-button v-if="isAuth('mall:order:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('mall:order:delete')" type="danger" :disabled="dataListSelections.length <= 0"
                   @click="deleteHandle()">批量删除
        </el-button>
      </el-form-item>
    </el-form>

    <el-table :data="dataList" border @selection-change="selectionChangeHandle" style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="id" header-align="center" align="center" label="ID" width="80"></el-table-column>
      <el-table-column prop="orderSn" header-align="center" align="center" label="订单编号"
                       min-width="180"></el-table-column>
      <el-table-column prop="userNickname" header-align="center" align="center" label="用户昵称"
                       width="120"></el-table-column>
      <el-table-column prop="consignee" header-align="center" align="center" label="收货人"
                       width="100"></el-table-column>
      <el-table-column prop="mobile" header-align="center" align="center" label="手机号" width="130"></el-table-column>
      <el-table-column prop="actualPrice" header-align="center" align="center" label="实付金额"
                       width="100"></el-table-column>
      <el-table-column header-align="center" align="center" label="订单状态" width="100">
        <template slot-scope="scope">
          <el-tag size="small" :type="orderStatusType(scope.row.orderStatus)">{{
              orderStatusText(scope.row.orderStatus)
            }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column header-align="center" align="center" label="支付状态" width="100">
        <template slot-scope="scope">
          <el-tag size="small" :type="payStatusType(scope.row.payStatus)">{{
              payStatusText(scope.row.payStatus)
            }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column header-align="center" align="center" label="发货状态" width="100">
        <template slot-scope="scope">
          <el-tag size="small" :type="shippingStatusType(scope.row.shippingStatus)">
            {{ shippingStatusText(scope.row.shippingStatus) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="addTime" header-align="center" align="center" label="下单时间"
                       min-width="160"></el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="220" label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('mall:order:info')" type="text" size="small" @click="showDetails(scope.row.id)">查看
          </el-button>
          <el-button
            v-if="isAuth('mall:order:update') && canAdjustPrice(scope.row)"
            type="text"
            size="small"
            @click="adjustPrice(scope.row)">改价
          </el-button>
          <el-button
            v-if="isAuth('mall:order:update') && canShip(scope.row)"
            type="text"
            size="small"
            @click="shipOrder(scope.row)">发货
          </el-button>
          <el-button v-if="isAuth('mall:order:delete')" type="text" size="small" @click="deleteHandle(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>

    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>

    <el-dialog
      title="订单发货"
      :visible.sync="shipDialogVisible"
      width="420px"
      :close-on-click-modal="false">
      <el-form :model="shipForm" :rules="shipRule" ref="shipForm" label-width="90px">
        <el-form-item label="快递公司" prop="shippingName">
          <el-select v-model="shipForm.shippingName" placeholder="请选择快递公司" filterable style="width: 100%;">
            <el-option
              v-for="item in shippingCompanyOptions"
              :key="item.id"
              :label="item.name"
              :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="快递单号" prop="shippingNo">
          <el-input v-model.trim="shipForm.shippingNo" placeholder="请输入快递单号"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="shipDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitShip()">确认发货</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import AddOrUpdate from './order-add-or-update'

export default {
  data () {
    return {
      searchForm: {
        orderSn: '',
        nickname: '',
        orderStatus: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListSelections: [],
      addOrUpdateVisible: false,
      shipDialogVisible: false,
      shipForm: {
        id: '',
        shippingName: '',
        shippingNo: ''
      },
      shippingCompanyOptions: [],
      shipRule: {
        shippingName: [{required: true, message: '请选择快递公司', trigger: 'change'}],
        shippingNo: [{required: true, message: '请输入快递单号', trigger: 'blur'}]
      }
    }
  },
  components: {AddOrUpdate},
  activated () {
    this.loadShippingCompanies()
    this.getDataList()
  },
  methods: {
    loadShippingCompanies () {
      this.$http({
        url: '/mall/shipping/queryAll',
        method: 'get'
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.shippingCompanyOptions = data.data || []
        } else {
          this.shippingCompanyOptions = []
        }
      }).catch(() => {
        this.shippingCompanyOptions = []
      })
    },
    getDataList (page) {
      if (page) {
        this.pageIndex = page
      }
      this.$http({
        url: '/mall/order/list',
        method: 'get',
        params: {
          page: this.pageIndex,
          limit: this.pageSize,
          orderSn: this.searchForm.orderSn,
          nickname: this.searchForm.nickname,
          orderStatus: this.searchForm.orderStatus
        }
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.data.records
          this.totalPage = data.data.total
        } else {
          this.dataList = []
          this.totalPage = 0
        }
      })
    },
    sizeChangeHandle (val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getDataList()
    },
    selectionChangeHandle (val) {
      this.dataListSelections = val
    },
    showDetails (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id, true)
      })
    },
    addOrUpdateHandle (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    orderStatusText (status) {
      const map = {
        0: '待付款',
        1: '待发货',
        2: '待收货',
        3: '已完成',
        4: '已取消',
        101: '已取消',
        102: '已删除',
        200: '已支付',
        201: '待发货',
        300: '待收货',
        301: '已完成',
        401: '已退款',
        402: '已退货'
      }
      return map[status] || `状态${status}`
    },
    orderStatusType (status) {
      const map = {
        0: 'warning',
        1: '',
        2: 'success',
        3: 'success',
        4: 'info',
        101: 'info',
        102: 'info',
        200: '',
        201: '',
        300: 'success',
        301: 'success',
        401: 'danger',
        402: 'danger'
      }
      return map[status] || 'info'
    },
    payStatusText (status) {
      const map = {0: '未支付', 1: '已支付', 2: '已支付', 3: '退款中', 4: '已退款'}
      return map[status] || `状态${status}`
    },
    payStatusType (status) {
      const map = {0: 'warning', 1: 'success', 2: 'success', 3: 'danger', 4: 'danger'}
      return map[status] || 'info'
    },
    shippingStatusText (status) {
      const map = {0: '未发货', 1: '已发货', 2: '已收货'}
      return map[status] || `状态${status}`
    },
    shippingStatusType (status) {
      const map = {0: 'info', 1: 'success', 2: 'success'}
      return map[status] || 'info'
    },
    canAdjustPrice (row) {
      return Number(row.payStatus) === 0
    },
    canShip (row) {
      return Number(row.payStatus) === 1 && Number(row.shippingStatus) === 0
    },
    adjustPrice (row) {
      this.$prompt('请输入新的实付金额', '支付前改价', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: row.actualPrice,
        inputValidator: (value) => {
          if (value === '' || value === null || value === undefined) {
            return '金额不能为空'
          }
          const n = Number(value)
          if (Number.isNaN(n) || n < 0) {
            return '金额必须是大于等于0的数字'
          }
          return true
        }
      }).then(({value}) => {
        this.$http({
          url: '/mall/order/adjustPrice',
          method: 'post',
          data: {
            id: row.id,
            actualPrice: value
          }
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({message: data.msg || '改价成功', type: 'success', duration: 1500})
            this.getDataList()
          } else if (data) {
            this.$message.error(data.msg || '改价失败')
          }
        })
      }).catch(() => {
      })
    },
    shipOrder (row) {
      this.shipForm.id = row.id
      this.shipForm.shippingName = row.shippingName || ''
      this.shipForm.shippingNo = row.shippingNo || ''
      this.shipDialogVisible = true
      this.$nextTick(() => {
        if (this.$refs.shipForm) {
          this.$refs.shipForm.clearValidate()
        }
      })
    },
    submitShip () {
      this.$refs.shipForm.validate((valid) => {
        if (!valid) {
          return
        }
        this.$http({
          url: '/mall/order/ship',
          method: 'post',
          data: {
            id: this.shipForm.id,
            shippingName: this.shipForm.shippingName,
            shippingNo: this.shipForm.shippingNo
          }
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({message: data.msg || '发货成功', type: 'success', duration: 1500})
            this.shipDialogVisible = false
            this.getDataList()
          } else if (data) {
            this.$message.error(data.msg || '发货失败')
          }
        })
      })
    },
    deleteHandle (id) {
      let ids = id ? [id] : this.dataListSelections.map(item => item.id)
      this.$confirm('确定对所选项进行[删除]操作?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: '/mall/order/delete',
          method: 'post',
          data: ids
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({message: '操作成功', type: 'success', duration: 1500})
            this.getDataList()
          }
        })
      }).catch(() => {
      })
    }
  }
}
</script>
