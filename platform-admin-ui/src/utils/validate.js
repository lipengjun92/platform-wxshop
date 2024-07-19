/**
 * 邮箱
 * @param {*} s
 */
export function isEmail (s) {
  return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(s)
}

/**
 * 手机号码
 * @param {*} s
 */
export function isMobile (s) {
  return /^1[0-9]{10}$/.test(s)
}

/**
 * 电话号码
 * @param {*} s
 */
export function isPhone (s) {
  return /^([0-9]{3,4}-)?[0-9]{7,8}$/.test(s)
}

/**
 * URL地址
 * @param {*} s
 */
export function isURL (s) {
  return /^http[s]?:\/\/.*/.test(s)
}

/* 小写字母 */
export function isLowerCase (str) {
  const reg = /^[a-z]+$/
  return reg.test(str)
}

/* 大写字母 */
export function isUpperCase (str) {
  const reg = /^[A-Z]+$/
  return reg.test(str)
}

/* 大小写字母 */
export function isAlphabets (str) {
  const reg = /^[A-Za-z]+$/
  return reg.test(str)
}

/* 验证pad还是pc */
export const isPc = function () {
  const userAgentInfo = navigator.userAgent
  const Agents = ['Android', 'iPhone',
    'SymbianOS', 'Windows Phone',
    'iPad', 'iPod']
  let flag = true
  for (let v = 0; v < Agents.length; v++) {
    if (userAgentInfo.indexOf(Agents[v]) > 0) {
      flag = false
      break
    }
  }
  return flag
}

/**
 * 判断姓名是否正确
 */
export function isName (name) {
  const regName = /^[\u4e00-\u9fa5]{2,4}$/
  if (!regName.test(name)) {
    return false
  }
  return true
}

/**
 * 判断是否为整数
 */
export function isNum (num, type) {
  let regName = /[^\d.]/g
  if (type === 1) {
    if (!regName.test(num)) {
      return false
    }
  } else if (type === 2) {
    regName = /[^\d]/g
    if (!regName.test(num)) {
      return false
    }
  }
  return true
}

/**
 * 判断是否为小数
 */
export function isNumOrd (num, type) {
  let regName = /[^\d.]/g
  if (type === 1) {
    if (!regName.test(num)) {
      return false
    }
  } else if (type === 2) {
    regName = /[^\d.]/g
    if (!regName.test(num)) {
      return false
    }
  }
  return true
}

/**
 * 判断是否为空
 */
export function isNull (val) {
  if (val instanceof Array) {
    if (val.length === 0) {
      return true
    }
  } else if (val instanceof Object) {
    if (JSON.stringify(val) === '{}') {
      return true
    }
  } else {
    return val === 'null' || val == null || val === 'undefined' || val === undefined || val === ''
  }
  return false
}

/**
 * 判断身份证号码
 */
export function isCardId (code) {
  let msg = ''
  const city = {
    11: '北京',
    12: '天津',
    13: '河北',
    14: '山西',
    15: '内蒙古',
    21: '辽宁',
    22: '吉林',
    23: '黑龙江 ',
    31: '上海',
    32: '江苏',
    33: '浙江',
    34: '安徽',
    35: '福建',
    36: '江西',
    37: '山东',
    41: '河南',
    42: '湖北 ',
    43: '湖南',
    44: '广东',
    45: '广西',
    46: '海南',
    50: '重庆',
    51: '四川',
    52: '贵州',
    53: '云南',
    54: '西藏 ',
    61: '陕西',
    62: '甘肃',
    63: '青海',
    64: '宁夏',
    65: '新疆',
    71: '台湾',
    81: '香港',
    82: '澳门',
    91: '国外 '
  }
  if (!isNull(code)) {
    if (code.length === 18) {
      if (!code || !/(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(code)) {
        msg = '证件号码格式错误'
        return false
      } else if (!city[code.substr(0, 2)]) {
        msg = '地址编码错误'
        return false
      } else {
        // 18位身份证需要验证最后一位校验位
        code = code.split('')
        // ∑(ai×Wi)(mod 11)
        // 加权因子
        const factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]
        // 校验位
        const parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2, 'x']
        let sum = 0
        let ai = 0
        let wi = 0
        for (let i = 0; i < 17; i++) {
          ai = code[i]
          wi = factor[i]
          sum += ai * wi
        }
        const last = parity[sum % 11]
        if (last !== code[17]) {
          msg = '证件号码校验位错误'
          return false
        }
      }
    } else {
      msg = '证件号码长度不为18位'
      return false
    }
  } else {
    msg = '证件号码不能为空'
    return false
  }
  if (msg) {
    console.log(msg)
  }
  return true
}
