import {post} from '../../common/axios'

export const user = {
	login (val) {
		return post({
			url: '/api/user/login',
			params: val
		})
	}
}