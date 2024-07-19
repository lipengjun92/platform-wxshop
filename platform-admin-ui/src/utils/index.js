import Vue from 'vue'
import router from '@/router'
import store from '@/store'

/**
 * 获取uuid
 */
export function getUUID () {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
    return (c === 'x' ? (Math.random() * 16 | 0) : ('r&0x3' | '0x8')).toString(16)
  })
}

/**
 * 是否有权限
 * @param {*} key
 */
export function isAuth (key) {
  return JSON.parse(sessionStorage.getItem('permissions') || '[]').indexOf(key) !== -1 || false
}

/**
 * 树形数据转换
 * @param {*} data list数据
 * @param {*} id 主键ID
 * @param {*} pid 上级ID
 * @param childrenKey 子list数据的key
 */
export function treeDataTranslate (data, id = 'id', pid = 'parentId', childrenKey = 'children') {
  const res = []
  const temp = {}
  for (let i = 0; i < data.length; i++) {
    temp[data[i][id]] = data[i]
  }
  for (let k = 0; k < data.length; k++) {
    if (temp[data[k][pid]] && data[k][id] !== data[k][pid]) {
      if (!temp[data[k][pid]][childrenKey]) {
        temp[data[k][pid]][childrenKey] = []
      }
      if (!temp[data[k][pid]]._level) {
        temp[data[k][pid]]._level = 1
      }
      data[k]._level = temp[data[k][pid]]._level + 1
      temp[data[k][pid]][childrenKey].push(data[k])
    } else {
      res.push(data[k])
    }
  }
  return res
}

/**
 * @param date
 */
export function transDate (date, fmt) {
  if (!date) {
    return '--'
  }
  let _this = new Date(date * 1000)
  var o = {
    'M+': _this.getMonth() + 1,
    'd+': _this.getDate(),
    'h+': _this.getHours(),
    'm+': _this.getMinutes(),
    's+': _this.getSeconds(),
    'q+': Math.floor((_this.getMonth() + 3) / 3),
    'S': _this.getMilliseconds()
  }
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (_this.getFullYear() + '').substr(4 - RegExp.$1.length))
  }
  for (var k in o) {
    if (new RegExp('(' + k + ')').test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
    }
  }
  return fmt
}

function getFormatDate (arg) {
  if (arg === undefined || arg === '') {
    return ''
  }
  var re = arg + ''
  if (re.length < 2) {
    re = '0' + re
  }
  return re
}

/**
 * 减几天
 * @param num
 * @returns {string}
 */
export function subtractDate (date, num) {
  date.setDate(date.getDate() - num)
  let month = date.getMonth() + 1
  let day = date.getDate()
  let hours = date.getHours()
  let minutes = date.getMinutes()
  let seconds = date.getSeconds()
  return date.getFullYear() + '-' + getFormatDate(month) + '-' + getFormatDate(day) + ' ' + getFormatDate(hours) + ':' + getFormatDate(minutes) + ':' + getFormatDate(seconds)
}

/**
 * 根据orgNo翻译为orgName
 * @param orgNo
 */
export function transOrg (orgNo) {
  const orgList = JSON.parse(sessionStorage.getItem('orgList') || '[]')
  if (orgList.length > 0) {
    for (let i = 0; i < orgList.length; i++) {
      if (orgList[i].orgNo === orgNo) {
        return orgList[i].orgName
      }
    }
  }
  return orgNo
}

/**
 * 根据userId翻译为realName
 * @param userId
 */
export function transUser (userId) {
  const userList = JSON.parse(sessionStorage.getItem('userList') || '[]')
  if (userList.length > 0) {
    for (let i = 0; i < userList.length; i++) {
      if (userList[i].userId === userId) {
        return userList[i].realName
      }
    }
  }
  return userId
}

export function transDict (code, value) {
  if (!value && value !== 0) {
    return '--'
  }
  const dictList = JSON.parse(sessionStorage.getItem('dictList') || '[]')
  if (dictList.length > 0) {
    for (let i = 0; i < dictList.length; i++) {
      if (dictList[i].code === code && dictList[i].value.toString() === value.toString()) {
        return dictList[i].name
      }
    }
  }
  return value
}

/**
 * 预览图片
 * @param imageUrl
 */
export function openImg (imageUrl) {
  this.$alert('<img style="width: 100%;height: 100%;" src="' + imageUrl + '"/>', '预览', {
    dangerouslyUseHTMLString: true,
    callback: function () {
    }
  })
}

/**
 * 清除登录信息
 */
export function clearLoginInfo () {
  Vue.cookie.delete('token')
  store.commit('resetStore')
  router.options.isAddDynamicMenuRoutes = false
}
