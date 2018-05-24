export const responseInterceptor = (res) => {
	if (res.headers.Token) {
		localStorage.setItem('token', res.headers.Token);
	}

	if (res.data.success === false && res.data.error_code === '20000') {
		localStorage.clear();
	}

	return Promise.resolve(res.data)
}