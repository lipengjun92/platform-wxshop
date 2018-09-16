const root = '/platform/api/'

export const IS_MOCK = true
export const API_BASE_URL = IS_MOCK ? '/mock/' : (process.env.NODE_ENV === 'development' ? 'http://127.0.0.1:8080' + root : 'http://fly2you.cn' + root)
export const TOKEN_KEY = 'X-Nideshop-Token'
export const HISTORY_KEY = 'History-Key'
