<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : !disabled ? '修改' : '查看'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="100px">
      <el-form-item label="商品名称">
        <el-input v-model="dataForm.goodsName" disabled placeholder="商品名称"></el-input>
      </el-form-item>
      <el-form-item label="评论内容" prop="content">
        <el-input type="textarea" :rows="4" v-model="dataForm.content" :disabled="disabled"
                  placeholder="评论内容"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="dataForm.status" :disabled="disabled">
          <el-radio-button :label="0">待审核</el-radio-button>
          <el-radio-button :label="1">通过</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="用户昵称">
        <el-input v-model="dataForm.userNickname" disabled placeholder="自动展示用户昵称"></el-input>
      </el-form-item>
      <el-form-item label="评论时间">
        <el-date-picker
          v-model="addTimeDate"
          type="datetime"
          disabled
          value-format="timestamp"
          placeholder="评论时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item v-if="dataForm.id" label="评论图片">
        <div v-if="commentPictures.length" class="picture-wrap">
          <el-image
            v-for="item in commentPictures"
            :key="item.id"
            :src="item.picUrl"
            :preview-src-list="commentPictures.map(p => p.picUrl)"
            fit="cover"
            class="picture-item">
          </el-image>
        </div>
        <span v-else>暂无图片</span>
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
        typeId: 0,
        valueId: 0,
        content: '',
        addTime: 0,
        status: 0,
        userId: 0,
        goodsName: '',
        userNickname: ''
      },
      addTimeDate: null,
      commentPictures: [],
      dataRule: {
        valueId: [{required: true, message: '商品ID不能为空', trigger: 'blur'}],
        content: [{required: true, message: '评论内容不能为空', trigger: 'blur'}]
      }
    }
  },
  methods: {
    init (id, disabled) {
      this.disabled = disabled
      this.dataForm.id = id || ''
      this.visible = true
      this.commentPictures = []
      this.addTimeDate = null
      this.$nextTick(() => {
        this.$refs.dataForm.resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: `/mall/comment/info/${this.dataForm.id}`,
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm = data.data
              this.addTimeDate = this.toTimestamp(this.dataForm.addTime)
              this.loadPictures(this.dataForm.id)
            }
          })
        }
      })
    },
    loadPictures (commentId) {
      this.$http({
        url: '/mall/commentpicture/queryAll',
        method: 'get',
        params: {
          commentId
        }
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.commentPictures = data.data || []
        } else {
          this.commentPictures = []
        }
      })
    },
    dataFormSubmit () {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          if (!this.dataForm.id && !this.dataForm.addTime) {
            this.dataForm.addTime = Math.floor(Date.now() / 1000)
          }
          this.$http({
            url: `/mall/comment/${!this.dataForm.id ? 'save' : 'update'}`,
            method: 'post',
            data: this.dataForm
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({message: '操作成功', type: 'success', duration: 1500})
              this.visible = false
              this.$emit('refreshDataList')
            }
          })
        }
      })
    },
    toTimestamp (value) {
      if (!value) {
        return null
      }
      const num = Number(value)
      if (Number.isNaN(num)) {
        return null
      }
      return num < 1000000000000 ? num * 1000 : num
    }
  }
}
</script>

<style scoped>
.picture-wrap {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.picture-item {
  width: 96px;
  height: 96px;
  border-radius: 4px;
  border: 1px solid #ebeef5;
}
</style>
