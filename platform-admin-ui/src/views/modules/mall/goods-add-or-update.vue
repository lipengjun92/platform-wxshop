<template>
  <el-dialog
    :title="!form.goods.id ? '新增商品' : !disabled ? '编辑商品' : '查看商品'"
    :close-on-click-modal="false"
    :lock-scroll="false"
    :visible.sync="visible"
    width="90%">
    <el-tabs v-model="activeTab" type="border-card">
      <el-tab-pane label="基础信息" name="base">
        <el-form :model="form.goods" :rules="dataRule" ref="dataForm" label-width="120px">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="商品名称" prop="name">
                <el-input v-model="form.goods.name" :disabled="disabled" placeholder="商品名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="商品分类" prop="categoryId">
                <el-select v-model="form.goods.categoryId" :disabled="disabled" filterable placeholder="请选择">
                  <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name"
                             :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="品牌" prop="brandId">
                <el-select v-model="form.goods.brandId" :disabled="disabled" filterable clearable placeholder="请选择">
                  <el-option v-for="item in brandOptions" :key="item.id" :label="item.name"
                             :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="商品编码" prop="goodsSn">
                <el-input v-model="form.goods.goodsSn" :disabled="disabled" placeholder="商品编码"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="库存" prop="goodsNumber">
                <el-input-number v-model="form.goods.goodsNumber" :disabled="disabled" :min="0"
                                 controls-position="right"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="零售价格" prop="retailPrice">
                <el-input-number v-model="form.goods.retailPrice" :disabled="disabled" :min="0" :precision="2"
                                 controls-position="right"></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="市场价" prop="marketPrice">
                <el-input-number v-model="form.goods.marketPrice" :disabled="disabled" :min="0" :precision="2"
                                 controls-position="right"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="上架状态" prop="isOnSale">
                <el-radio-group v-model="form.goods.isOnSale" :disabled="disabled">
                  <el-radio-button :label="1">上架</el-radio-button>
                  <el-radio-button :label="0">下架</el-radio-button>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="新品" prop="isNew">
                <el-radio-group v-model="form.goods.isNew" :disabled="disabled">
                  <el-radio-button :label="1">是</el-radio-button>
                  <el-radio-button :label="0">否</el-radio-button>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="主图" prop="primaryPicUrl">
                <el-img v-model="form.goods.primaryPicUrl" :disabled="disabled"></el-img>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="列表图" prop="listPicUrl">
                <el-img v-model="form.goods.listPicUrl" :disabled="disabled"></el-img>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="商品简介" prop="goodsBrief">
            <el-input v-model="form.goods.goodsBrief" :disabled="disabled" placeholder="商品简介"></el-input>
          </el-form-item>
          <el-form-item label="商品详情" prop="goodsDesc">
            <ueditor v-model="form.goods.goodsDesc" :disabled="disabled" placeholder="商品详情"></ueditor>
            <el-button type="success" @click="openDetail">预览详情</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>

      <el-tab-pane label="商品参数" name="attr">
        <div class="mb10">
          <el-button v-if="!disabled" type="primary" size="mini" @click="addAttribute">新增参数</el-button>
        </div>
        <el-table :data="form.attributes" border>
          <el-table-column label="属性" width="240">
            <template slot-scope="scope">
              <el-select v-model="scope.row.attributeId" :disabled="disabled" filterable placeholder="请选择属性"
                         @change="onAttributeChange(scope.row)">
                <el-option v-for="item in attributeOptions" :key="item.id" :label="item.name"
                           :value="item.id"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="属性值" min-width="280">
            <template slot-scope="scope">
              <el-input v-model="scope.row.value" :disabled="disabled" placeholder="请输入属性值"></el-input>
            </template>
          </el-table-column>
          <el-table-column v-if="!disabled" label="操作" width="100" align="center">
            <template slot-scope="scope">
              <el-button type="text" @click="removeAttribute(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="商品相册" name="gallery">
        <div class="mb10">
          <el-button v-if="!disabled" type="primary" size="mini" @click="addGallery">新增图片</el-button>
        </div>
        <el-table :data="form.gallery" border>
          <el-table-column label="图片" width="260">
            <template slot-scope="scope">
              <el-img v-model="scope.row.imgUrl" :disabled="disabled"></el-img>
            </template>
          </el-table-column>
          <el-table-column label="描述">
            <template slot-scope="scope">
              <el-input v-model="scope.row.imgDesc" :disabled="disabled" placeholder="描述"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="排序" width="240" align="center">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.sortOrder" :disabled="disabled" :min="1"
                               controls-position="right"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column v-if="!disabled" label="操作" width="120" align="center">
            <template slot-scope="scope">
              <el-button type="text" @click="removeGallery(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="规格与规格值" name="spec">
        <div class="mb10">
          <el-button v-if="!disabled" type="primary" size="mini" @click="addSpecGroup">新增规格</el-button>
          <el-button v-if="!disabled" size="mini" @click="rebuildSkuList">重建SKU</el-button>
        </div>
        <el-table :data="form.specs" border>
          <el-table-column label="规格名" width="260">
            <template slot-scope="scope">
              <el-select v-model="scope.row.specificationId" :disabled="disabled" filterable placeholder="请选择规格"
                         @change="onSpecificationChange(scope.row)">
                <el-option v-for="item in specificationOptions" :key="item.id" :label="item.name"
                           :value="item.id"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="规格值">
            <template slot-scope="scope">
              <div v-for="(value, valueIndex) in scope.row.values" :key="valueIndex" class="spec-value-row">
                <el-input v-model="value.value" :disabled="disabled" placeholder="规格值"
                          class="spec-value-input"></el-input>
                <el-img v-model="value.picUrl" :disabled="disabled"></el-img>
                <el-button v-if="!disabled" type="text" @click="removeSpecValue(scope.row, valueIndex)">删除</el-button>
              </div>
              <el-button v-if="!disabled" type="text" @click="addSpecValue(scope.row)">+ 添加规格值</el-button>
            </template>
          </el-table-column>
          <el-table-column v-if="!disabled" label="操作" width="100" align="center">
            <template slot-scope="scope">
              <el-button type="text" @click="removeSpecGroup(scope.$index)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="SKU 列表" name="sku">
        <el-alert type="info" :closable="false" show-icon
                  title="规格变更后请点击“重建SKU”，系统会按规格组合生成 SKU。"></el-alert>
        <el-table :data="form.skuList" border class="mt10">
          <el-table-column label="规格组合" min-width="260">
            <template slot-scope="scope">
              <el-tag size="small">{{ scope.row.specLabel || '默认规格' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="SKU编码" width="180">
            <template slot-scope="scope">
              <el-input v-model="scope.row.goodsSn" :disabled="disabled" placeholder="SKU编码"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="库存" width="240" align="center">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.goodsNumber" :disabled="disabled" :min="0"
                               controls-position="right"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="零售价" width="240" align="center">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.retailPrice" :disabled="disabled" :min="0" :precision="2"
                               controls-position="right"></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="市场价" width="240" align="center">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.marketPrice" :disabled="disabled" :min="0" :precision="2"
                               controls-position="right"></el-input-number>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button v-if="!disabled" type="primary" @click="dataFormSubmit">保存</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      disabled: false,
      visible: false,
      activeTab: 'base',
      form: this.getDefaultForm(),
      brandOptions: [],
      categoryOptions: [],
      attributeOptions: [],
      specificationOptions: [],
      dataRule: {
        name: [{required: true, message: '商品名称不能为空', trigger: 'blur'}],
        categoryId: [{required: true, message: '商品分类不能为空', trigger: 'change'}],
        retailPrice: [{required: true, message: '零售价格不能为空', trigger: 'blur'}]
      }
    }
  },
  methods: {
    getDefaultForm () {
      return {
        goods: {
          id: '',
          categoryId: '',
          goodsSn: '',
          name: '',
          brandId: '',
          goodsNumber: 0,
          keywords: '',
          goodsBrief: '',
          goodsDesc: '',
          isOnSale: 1,
          sortOrder: 100,
          isDelete: 0,
          counterPrice: 0,
          extraPrice: 0,
          isNew: 0,
          goodsUnit: '件',
          primaryPicUrl: '',
          listPicUrl: '',
          retailPrice: 0,
          sellVolume: 0,
          unitPrice: 0,
          appExclusivePrice: 0,
          isAppExclusive: 0,
          isLimited: 0,
          isHot: 0,
          marketPrice: 0
        },
        attributes: [],
        gallery: [],
        specs: [],
        skuList: []
      }
    },
    init (id, disabled) {
      this.disabled = !!disabled
      this.visible = true
      this.activeTab = 'base'
      this.form = this.getDefaultForm()
      this.form.goods.id = id || ''

      this.$nextTick(() => {
        this.$refs.dataForm && this.$refs.dataForm.resetFields()
        this.loadOptions()
        if (id) {
          this.loadAggregate(id)
        }
      })
    },
    openDetail () {
      this.$alert(`<div style="width: 720px;height: 720px;overflow-y: auto;overflow-x: hidden"">${this.form.goods.goodsDesc}</div>`, this.form.goods.name, {
        dangerouslyUseHTMLString: true,
        closeOnClickModal: true,
        callback: action => {
        }
      })
    },
    loadOptions () {
      this.$http({url: '/mall/category/queryAll', method: 'get'}).then(({data}) => {
        if (data && data.code === 0) {
          this.categoryOptions = data.data || []
        }
      })
      this.$http({url: '/mall/brand/queryAll', method: 'get'}).then(({data}) => {
        if (data && data.code === 0) {
          this.brandOptions = data.data || []
        }
      })
      this.$http({url: '/mall/attribute/queryAll', method: 'get'}).then(({data}) => {
        if (data && data.code === 0) {
          this.attributeOptions = data.data || []
        }
      })
      this.$http({url: '/mall/specification/queryAll', method: 'get'}).then(({data}) => {
        if (data && data.code === 0) {
          this.specificationOptions = data.data || []
        }
      })
    },
    loadAggregate (id) {
      this.$http({url: `/mall/goods/aggregate/${id}`, method: 'get'}).then(({data}) => {
        if (data && data.code === 0 && data.data) {
          this.form = Object.assign(this.getDefaultForm(), data.data)
          this.form.goods = Object.assign(this.getDefaultForm().goods, data.data.goods || {})
          this.form.attributes = data.data.attributes || []
          this.form.gallery = (data.data.gallery || []).map((item, index) => Object.assign({sortOrder: index + 1}, item))
          this.form.specs = data.data.specs || []
          this.form.skuList = (data.data.skuList || []).map(item => ({
            ...item,
            specLabel: item.goodsSpecificationIds
          }))
        }
      })
    },
    addAttribute () {
      this.form.attributes.push({attributeId: '', attributeName: '', value: ''})
    },
    removeAttribute (index) {
      this.form.attributes.splice(index, 1)
    },
    onAttributeChange (row) {
      const hit = this.attributeOptions.find(item => item.id === row.attributeId)
      row.attributeName = hit ? hit.name : ''
    },
    addGallery () {
      this.form.gallery.push({imgUrl: '', imgDesc: '', sortOrder: this.form.gallery.length + 1})
    },
    removeGallery (index) {
      this.form.gallery.splice(index, 1)
    },
    addSpecGroup () {
      this.form.specs.push({specificationId: '', specificationName: '', values: [{value: '', picUrl: ''}]})
    },
    removeSpecGroup (index) {
      this.form.specs.splice(index, 1)
      this.rebuildSkuList()
    },
    onSpecificationChange (row) {
      const hit = this.specificationOptions.find(item => item.id === row.specificationId)
      row.specificationName = hit ? hit.name : ''
      this.rebuildSkuList()
    },
    addSpecValue (row) {
      row.values.push({value: '', picUrl: ''})
    },
    removeSpecValue (row, valueIndex) {
      row.values.splice(valueIndex, 1)
      this.rebuildSkuList()
    },
    getValidSpecs () {
      return this.form.specs
        .map(spec => ({
          specificationId: spec.specificationId,
          specificationName: spec.specificationName,
          values: (spec.values || []).filter(value => value.value)
        }))
        .filter(spec => spec.specificationId && spec.values.length > 0)
    },
    buildSpecCombinations (specs) {
      if (!specs.length) {
        return [{key: '', label: '默认规格'}]
      }
      let combos = [{key: '', label: ''}]
      specs.forEach(spec => {
        const next = []
        combos.forEach(combo => {
          spec.values.forEach(value => {
            const keyPart = `${spec.specificationId}:${value.value}`
            const labelPart = `${spec.specificationName || spec.specificationId}:${value.value}`
            next.push({
              key: combo.key ? `${combo.key}|${keyPart}` : keyPart,
              label: combo.label ? `${combo.label} / ${labelPart}` : labelPart
            })
          })
        })
        combos = next
      })
      return combos
    },
    rebuildSkuList () {
      const specs = this.getValidSpecs()
      const combinations = this.buildSpecCombinations(specs)
      const cache = (this.form.skuList || []).reduce((map, item) => {
        map[item.goodsSpecificationIds] = item
        return map
      }, {})
      this.form.skuList = combinations.map(combo => {
        const hit = cache[combo.key] || {}
        return {
          goodsSpecificationIds: combo.key,
          specLabel: combo.label,
          goodsSn: hit.goodsSn || this.form.goods.goodsSn || '',
          goodsNumber: hit.goodsNumber != null ? hit.goodsNumber : (this.form.goods.goodsNumber || 0),
          retailPrice: hit.retailPrice != null ? hit.retailPrice : (this.form.goods.retailPrice || 0),
          marketPrice: hit.marketPrice != null ? hit.marketPrice : (this.form.goods.marketPrice || 0)
        }
      })
    },
    sanitizePayload () {
      const payload = JSON.parse(JSON.stringify(this.form))
      payload.attributes = (payload.attributes || []).filter(item => item.attributeId && item.value)
      payload.gallery = (payload.gallery || []).filter(item => item.imgUrl)
      payload.specs = this.getValidSpecs()
      payload.skuList = (payload.skuList || []).map(item => ({
        goodsSpecificationIds: item.goodsSpecificationIds || '',
        goodsSn: item.goodsSn,
        goodsNumber: item.goodsNumber,
        retailPrice: item.retailPrice,
        marketPrice: item.marketPrice
      }))
      return payload
    },
    dataFormSubmit () {
      this.$refs.dataForm.validate(valid => {
        if (!valid) {
          return
        }
        const payload = this.sanitizePayload()
        const hasSpecs = payload.specs && payload.specs.length > 0
        if (hasSpecs && (!payload.skuList || payload.skuList.length === 0)) {
          this.$message.error('存在规格时必须生成SKU')
          this.activeTab = 'sku'
          return
        }
        this.$http({
          url: `/mall/goods/aggregate/${!payload.goods.id ? 'save' : 'update'}`,
          method: 'post',
          data: payload
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({message: '操作成功', type: 'success', duration: 1500})
            this.visible = false
            this.$emit('refreshDataList')
          }
        })
      })
    }
  }
}
</script>

<style scoped>
.mb10 {
  margin-bottom: 10px;
}

.mt10 {
  margin-top: 10px;
}

.spec-value-row {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}

.spec-value-input {
  width: 180px;
  margin-right: 8px;
}

.spec-pic-input {
  width: 260px;
  margin-right: 8px;
}
</style>
