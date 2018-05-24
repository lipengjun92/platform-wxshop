import {get} from '../../common/axios'

export const home = {
	getHomeInfo (val) {
		return get({
			url: '/api/user/login',
			params: val
		})
	}
}