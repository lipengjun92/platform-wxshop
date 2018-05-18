import Mock from 'mockjs'
import config from '../../config/index'
import response from './response'
Mock.mock(config.mock.api + '/mobile/user/login', (option) => {
    return response.userLogin();
});