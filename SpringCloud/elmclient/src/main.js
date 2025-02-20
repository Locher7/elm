import {
	createApp
} from 'vue'
import App from './App.vue'
import router from './router'
import { API_URL } from './config';

import 'font-awesome/css/font-awesome.min.css'
import axios from 'axios'
import qs from 'qs'
import {
	getCurDate,
	setSessionStorage,
	getSessionStorage,
	removeSessionStorage,
	setLocalStorage,
	getLocalStorage,
	removeLocalStorage
} from './common.js'

const app = createApp(App);
axios.defaults.baseURL = 'http://localhost:14000/';
// 设置响应拦截器
axios.interceptors.response.use(function(response){
	if(response.data.code == 403) {
		//location.href = '/error403';
		router.push('/error403')
		console.log(response);
	}
	return response;
},function(error) {
	console.error();
	return Promise.reject(error);
});

// 注册全局属性和方法
app.config.globalProperties.$axios = axios;
app.config.globalProperties.$qs = qs;
app.config.globalProperties.$getCurDate = getCurDate;
app.config.globalProperties.$setSessionStorage = setSessionStorage;
app.config.globalProperties.$getSessionStorage = getSessionStorage;
app.config.globalProperties.$removeSessionStorage = removeSessionStorage;
app.config.globalProperties.$setLocalStorage = setLocalStorage;
app.config.globalProperties.$getLocalStorage = getLocalStorage;
app.config.globalProperties.$removeLocalStorage = removeLocalStorage;

//导航守卫
router.beforeEach((to, from, next) => {
	let user = sessionStorage.getItem('user');
	if (
		![
			'/',
			'/index',
			'/businessList',
			'/businessInfo',
			'/login',
			'/register',
			'/error403',
		].includes(to.path)
	) {
		if (user == null) {
			next('/login');
		} else {
			next();
		}
	} else {
		next();
	}
});
app.use(router).mount('#app');

// console.log('API URL:', API_URL);
