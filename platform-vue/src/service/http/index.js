import { post } from "./axios";

export default {
    userLogin(val) {
        return post({
            url: '/api/user/login',
            params: val
        })
    }
}
