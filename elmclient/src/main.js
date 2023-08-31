import {
	createApp
} from 'vue'
import App from './App.vue'
import router from './router'

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

//设置axios的基础url部分
axios.defaults.baseURL = 'http://localhost:8081/elm/';

//原vue2代码
//将axios挂载到vue实例上，使用时就可以this.$axios这样使用了
// Vue.prototype.$axios = axios;
// Vue.prototype.$getCurDate = getCurDate;
// Vue.prototype.$setSessionStorage = setSessionStorage;
// Vue.prototype.$getSessionStorage = getSessionStorage;
// Vue.prototype.$removeSessionStorage = removeSessionStorage;
// Vue.prototype.$setLocalStorage = setLocalStorage;
// Vue.prototype.$getLocalStorage = getLocalStorage;
// Vue.prototype.$removeLocalStorage = removeLocalStorage;


// 注册全局属性和方法
const app = createApp(App);
app.config.globalProperties.$axios = axios;
app.config.globalProperties.$getCurDate = getCurDate;
app.config.globalProperties.$setSessionStorage = setSessionStorage;
app.config.globalProperties.$getSessionStorage = getSessionStorage;
app.config.globalProperties.$removeSessionStorage = removeSessionStorage;
app.config.globalProperties.$setLocalStorage = setLocalStorage;
app.config.globalProperties.$getLocalStorage = getLocalStorage;
app.config.globalProperties.$removeLocalStorage = removeLocalStorage;

//导航守卫
// router.beforeEach(function(to, from, next) {
// 	let user = getSessionStorage('user'); // 使用getSessionStorage获取用户信息
// 	// 除了登录、注册、首页、商家列表、商家信息之外，都需要判断是否登录
// 	if (!(to.path == '/' || to.path == '/index' || to.path == '/businessList' || to.path == '/businessInfo' || to.path ==
// 			'/login' || to.path == '/register')) {
// 		if (user == null) {
// 			next('/login'); // 重定向到登录页面
// 		} else {
// 			next(); // 用户已登录，继续路由跳转
// 		}
// 	} else {
// 		next(); // 其他页面不需要登录验证，直接跳转
// 	}
// });

createApp(App).use(router).mount('#app')
