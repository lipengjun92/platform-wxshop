import Vue from 'vue'
import * as types from './mutation-types'
import {api} from '../../service/api'
export const actions = {
  getHomeInfo ({commit, state}) {
    return new Promise((resolve, reject) => {
      if (state.banner.length === 0) {
        api.home.getHomeInfo().then(res => {
          if (!res.errno) {
            commit(types.INIT_HOME_PAGE, res.data)
          }
        })
      }
    })
  }
}
