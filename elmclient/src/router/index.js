import {
	createRouter,
	createWebHistory
} from 'vue-router';
import Index from '../views/Index.vue';
import BusinessList from '../views/BusinessList.vue';
import BusinessInfo from '../views/BusinessInfo.vue';
import Orders from '../views/Orders.vue';
import Login from '../views/Login.vue';
import Register from '../views/Register.vue';
import Payment from '../views/Payment.vue';
import OrderList from '../views/OrderList.vue';
import UserAddress from '../views/UserAddress.vue';

const routes = [{
		path: '/',
		name: 'Home',
		component: Index
	},
	{
		path: '/index',
		name: 'Index',
		component: Index
	},
	{
		path: '/businessList',
		name: 'BusinessList',
		component: BusinessList
	},
	{
		path: '/businessInfo',
		name: 'BusinessInfo',
		component: BusinessInfo
	},
	{
		path: '/orders',
		name: 'Orders',
		component: Orders
	},
	{
		path: '/login',
		name: 'Login',
		component: Login
	},
	{
		path: '/register',
		name: 'Register',
		component: Register
	},
	{
		path: '/payment',
		name: 'Payment',
		component: Payment
	},
	{
		path: '/orderList',
		name: 'OrderList',
		component: OrderList
	},
	{
		path: '/userAddress',
		name: 'UserAddress',
		component: UserAddress
	}
];


const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes
});

// 避免重复点击一个页面报错
const originalPush = router.push;
router.push = function push(location) {
	return originalPush.call(this, location).catch(err => err);
};


export default router;
