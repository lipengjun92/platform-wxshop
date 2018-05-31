import {get} from '../../common/axios'

export const home = {
	getHomeInfo (val) {
		return get({
			url: 'api/index/index',
			params: val
		})
	}
}