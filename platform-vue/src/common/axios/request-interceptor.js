import qs from 'qs'

export const requestInterceptor = (req) => {
	if (localStorage.getItem('token')) {
		req.headers.Token = localStorage.getItem('token');
	}
	if (req.method === 'post') {
		req.data = qs.stringify(req.data);
	}
	return req;
}