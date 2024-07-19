<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible">
    <el-form inline :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="类型" prop="type">
        <el-radio-group v-model="dataForm.type">
          <el-radio-button v-for="(type, index) in dataForm.typeList" :label="index" :key="index">{{ type }}</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item :label="dataForm.typeList[dataForm.type] + '名称'" prop="name">
        <el-input v-model="dataForm.name" :placeholder="dataForm.typeList[dataForm.type] + '名称'"
                  class="width200"></el-input>
      </el-form-item>
      <el-form-item label="上级菜单" prop="parentName">
        <el-dialog
          width="30%"
          append-to-body
          title="选择上级菜单"
          :lock-scroll="false"
          :visible.sync="visiblePopover">
          <el-tree
            :data="menuList"
            :props="menuListTreeProps"
            node-key="menuId"
            @current-change="menuListTreeCurrentChangeHandle"
            default-expand-all
            :expand-on-click-node="false">
          </el-tree>
        </el-dialog>
        <el-button @click="visiblePopover = true" class="width200">{{dataForm.parentName || '点击选择上级菜单'}}</el-button>
      </el-form-item>
      <el-form-item v-if="dataForm.type === 1" label="菜单路由" prop="url">
        <el-input v-model="dataForm.url" placeholder="菜单路由" class="width200"></el-input>
      </el-form-item>
      <el-form-item v-if="dataForm.type !== 0" label="授权标识" prop="perms">
        <el-input v-model="dataForm.perms" placeholder="多个用逗号分隔, 如: user:list,user:create" class="width200"></el-input>
      </el-form-item>
      <el-form-item v-if="dataForm.type !== 2" label="排序号" prop="orderNum">
        <el-input-number v-model="dataForm.orderNum" controls-position="right" :min="0" label="排序号"></el-input-number>
      </el-form-item>
      <el-form-item v-if="dataForm.type !== 2" label="菜单图标" prop="icon">
        <el-row>
          <el-col>
            <el-popover
              ref="iconListPopover"
              placement="bottom-start"
              trigger="click"
              popper-class="mod-menu__icon-popover">
              <div class="mod-menu__icon-list">
                <el-button
                  v-for="(item, index) in iconList"
                  :key="index"
                  @click="iconActiveHandle(item)"
                  :class="{ 'is-active': item === dataForm.icon }">
                  <icon-svg :name="item"></icon-svg>
                </el-button>
              </div>
            </el-popover>
            <el-input v-model="dataForm.icon" v-popover:iconListPopover :readonly="true" placeholder="菜单图标名称"
                      class="icon-list__input width200"></el-input>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import Icon from '@/icons'

export default {
  data () {
    const validateUrl = (rule, value, callback) => {
      if (this.dataForm.type === 1 && !/\S/.test(value)) {
        callback(new Error('菜单URL不能为空'))
      } else {
        callback()
      }
    }
    return {
      visible: false,
      visiblePopover: false,
      dataForm: {
        id: '',
        type: 1,
        typeList: ['目录', '菜单', '按钮'],
        name: '',
        parentId: 0,
        parentName: '',
        url: '',
        perms: '',
        orderNum: 0,
        icon: '',
        iconList: []
      },
      dataRule: {
        name: [
          {
            required: true,
            message: '菜单名称不能为空',
            trigger: 'blur'
          }
        ],
        parentName: [
          {
            required: true,
            message: '上级菜单不能为空',
            trigger: 'change'
          }
        ],
        url: [
          {
            validator: validateUrl,
            trigger: 'blur'
          }
        ]
      },
      menuList: [],
      menuListTreeProps: {
        label: 'name',
        children: 'children'
      }
    }
  },
  created () {
    this.iconList = Icon.getNameList()
  },
  methods: {
    init (id) {
      this.dataForm.id = id || 0
      this.$http({
        url: '/sys/menu/select',
        method: 'get'
      }).then(({data}) => {
        this.menuList = this.treeDataTranslate(data.data, 'menuId')
      }).then(() => {
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
        })
      }).then(() => {
        if (this.dataForm.id) {
          this.$http({
            url: `/sys/menu/info/${this.dataForm.id}`,
            method: 'get'
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.id = data.data.menuId
              this.dataForm.type = data.data.type
              this.dataForm.name = data.data.name
              this.dataForm.parentId = data.data.parentId
              this.dataForm.parentName = data.data.parentName
              this.dataForm.url = data.data.url
              this.dataForm.perms = data.data.perms
              this.dataForm.orderNum = data.data.orderNum
              this.dataForm.icon = data.data.icon
            }
          })
        } else {
          this.dataForm.parentName = ''
        }
      })
    },
    // 菜单树选中
    menuListTreeCurrentChangeHandle (data, node) {
      this.dataForm.parentId = data.menuId
      this.dataForm.parentName = data.name
      this.visiblePopover = false
    },
    // 图标选中
    iconActiveHandle (iconName) {
      this.dataForm.icon = iconName
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: `/sys/menu/${!this.dataForm.id ? 'save' : 'update'}`,
            method: 'post',
            data: {
              'menuId': this.dataForm.id || undefined,
              'type': this.dataForm.type,
              'name': this.dataForm.name,
              'parentId': this.dataForm.parentId,
              'url': this.dataForm.url,
              'perms': this.dataForm.perms,
              'orderNum': this.dataForm.orderNum,
              'icon': this.dataForm.icon
            }
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

<style lang="scss">

.menu-list__input,
.icon-list__input {

  > .el-input__inner {
    cursor: pointer;
  }

}

.mod-menu__icon-popover {
  max-width: 350px;
}

.mod-menu__icon-list {
  max-height: 380px;
  padding: 0;
  margin: -8px 0 0 -8px;

  > .el-button {
    padding: 8px;
    margin: 8px 0 0 8px;

    > span {
      display: inline-block;
      vertical-align: middle;
      width: 18px;
      height: 18px;
      font-size: 18px;
    }

  }
}

.icon-list__tips {
  font-size: 18px;
  text-align: center;
  color: #e6a23c;
  cursor: pointer;
}
</style>
