import * as types from './mutation-types'

export const mutations = {
  [types.INIT_HOME_PAGE] (state, o) {
    state.banner = o.banner
    state.channel = o.channel
    state.hotGoodsList = o.hotGoodsList
  }
}
