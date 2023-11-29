<template>
	<div class="wrapper">
		<!-- header -->
		<header>
			<p>商家信息</p>
		</header>

		<!-- 商家logo -->
		<div class="business-logo">
			<img :src="business.businessImg">
		</div>

		<!-- 商家信息 -->
		<div class="business-info">
			<h1>{{ business.businessName }}</h1>
			<p>&#165;{{ business.starPrice/100 }}起送 {{ business.deliveryPrice/100 }}配送&#165;3</p>
			<p>{{ business.businessExplain }}</p>
		</div>

		<!-- 食品列表 -->
		<ul class="food">
			<li v-for="(item,index) in foodArr" :key="item.id">
				<div class="food-left">
					<img :src="item.foodImg">
					<div class="food-left-info">
						<h3>{{ item.foodName }}</h3>
						<p>{{ item.foodExplain }}</p>
						<p>&#165;{{ item.foodPrice/100 }}</p>
					</div>
				</div>
				<div class="food-right">
					<div>
						<i class="fa fa-minus-circle" @click="minus(index)" v-show="item.quantity!=0"></i>
					</div>
					<p><span v-show="item.quantity!=0">{{ item.quantity }}</span></p>
					<div>
						<i class="fa fa-plus-circle" @click="add(index)"></i>
					</div>
				</div>
			</li>
		</ul>

		<!-- 底部购物车 -->
		<div class="cart">
			<div class="cart-left">
				<div class="cart-left-icon" :style="totalQuantity==0?'background-color:#505051;':'background-color:#3190E8;'">
					<i class="fa fa-shopping-cart"></i>
					<div class="cart-left-icon-quantity" v-show="totalQuantity!=0">{{totalQuantity}}</div>
				</div>
				<div class="cart-left-info">
					<p>&#165;{{totalPrice/100}}</p>
					<p>另需配送费{{business.deliveryPrice/100}}元</p>
				</div>
				<div class="cart-right">
					<!--不够配送费-->
					<div class="cart-right-item" v-show="totalSettle<business.starPrice" style="background-color: #535356;cursor: default;">&#165;{{business.starPrice/100}}起送</div>
					<!--达到配送费-->
					<div class="cart-right-item" @click="toOrder" v-show="totalSettle>=business.starPrice">去结算</div>
				</div>
			</div>
		</div>

	</div>
</template>

<script>
	import {
		ref,
		inject,
		onMounted,
		watch,
		computed,
		onUnmounted
	} from 'vue';
	import {
		useRoute
	} from 'vue-router';
	import {
		useRouter
	} from 'vue-router';
	import axios from 'axios';
	import qs from 'qs';


	export default {
		name: 'BusinessInfo',
		setup() {
			const route = useRoute();
			const router = useRouter();
			const businessId = ref(route.query.businessId);
			const business = ref({});
			const foodArr = ref([]);
			const user = ref(JSON.parse(sessionStorage.getItem('user')));


			// const business = ref({
			// 	businessImg: '',
			// 	businessName: '测试商家',
			// 	starPrice: 5000,
			// 	deliveryPrice: 200,
			// 	businessExplain: '这是一个测试商家说明'
			// });

			// const foodArr = ref([{
			// 		id: 1,
			// 		foodImg: '',
			// 		foodName: '测试食物1',
			// 		foodExplain: '这是一个测试食物1的说明',
			// 		foodPrice: 1500,
			// 		quantity: 0
			// 	},
			// 	{
			// 		id: 2,
			// 		foodImg: '',
			// 		foodName: '测试食物2',
			// 		foodExplain: '这是一个测试食物2的说明',
			// 		foodPrice: 2500,
			// 		quantity: 0
			// 	}

			// ]);


			onMounted(() => {

				// 请求商家信息
				axios.post('BusinessController/getBusinessById', qs.stringify({
						businessId: businessId.value,
					}))
					.then(response => {
						business.value = response.data;
						console.log('商家信息：', business.value);
					})
					.catch(error => {
						console.error(error);
					});

				//请求食品列表信息
				axios.post('FoodController/listFoodByBusinessId', qs.stringify({
						businessId: businessId.value,
					}))
					.then(response => {
						foodArr.value = response.data;
						for (let i = 0; i < foodArr.value.length; i++) {
							foodArr.value[i].quantity = 0;
						}

						if (user.value != null) {
							listCart();
						}
						console.log('食品列表：', foodArr.value);
					})
					.catch(error => {
						console.error(error);
					});
			});

			//跳转到订单界面
			const toOrder = () => {
				router.push({
					path: '/orders',
					query: {
						businessId: business.value.businessId
					}
				});
			};


			// 请求购物车信息
			const listCart = () => {
				axios.post('CartController/listCart', qs.stringify({
					businessId: businessId.value,
					userId: user.value.userId,
				})).then(response => {
					let cartArr = response.data;
					//遍历所有食品列表
					for (let foodItem of foodArr.value) {
						foodItem.quantity = 0;
						for (let cartItem of cartArr) {
							if (cartItem.foodId == foodItem.foodId) {
								foodItem.quantity = cartItem.quantity;
							}
						}
					}
					foodArr.value.sort();
				}).catch(error => {
					console.error(error);
				});
			};

			// 增加按钮
			const add = (index) => {
				//首先做登录验证
				if (user.value == null) {
					router.push('/login');
					return
				}

				if (foodArr.value[index].quantity == 0) {
					//做insert
					saveCart(index);
				} else {
					//做update
					updateCart(index, 1);
				}
			};

			// 减少按钮
			const minus = (index) => {
				if (user.value == null) {
					router.push('/login');
					return
				}
				if (foodArr.value[index].quantity > 1) {
					//做update
					updateCart(index, -1);
				} else {
					//做delete
					removeCart(index);
				}
			};

			//封装函数
			//增加一个食品
			const saveCart = (index) => {
				axios.post('CartController/saveCart', qs.stringify({
					businessId: businessId.value,
					userId: user.value.userId,
					foodId: foodArr.value[index].foodId
				})).then(response => {
					if (response.data == 1) {
						//此食品数量要更新为1
						foodArr.value[index].quantity = 1;
						foodArr.value.sort();
					} else {
						alert('向购物车中添加食品失败！');
					}
				}).catch(error => {
					console.error(error);
				});
			};

			//更新食品数量
			const updateCart = (index, num) => {
				axios.post('CartController/updateCart', qs.stringify({
					businessId: businessId.value,
					userId: user.value.userId,
					foodId: foodArr.value[index].foodId,
					quantity: foodArr.value[index].quantity + num
				})).then(response => {
					if (response.data == 1) {
						//此食品数量要更新为1或-1；
						foodArr.value[index].quantity += num;
						//让vue监听数量变化,视图层发生变化
						foodArr.value.sort();
					} else {
						alert('向购物车中更新食品失败!')
					}
				}).catch(error => {
					console.error(error);
				});
			};

			//删除食品数量
			const removeCart = (index) => {
				axios.post('CartController/removeCart', qs.stringify({
					businessId: businessId.value,
					userId: user.value.userId,
					foodId: foodArr.value[index].foodId,
				})).then(response => {
					if (response.data == 1) {
						//此食品数量要更新为0；视图的减号和数量要消失
						foodArr.value[index].quantity = 0;
						//让vue监听数量变化,视图层发生变化
						foodArr.value.sort();
					} else {
						alert('从购物车中删除食品失败!')
					}
				}).catch(error => {
					console.error(error);
				});
			};

			// 计算食物总价格
			const totalPrice = computed(() => {
				let total = 0;
				for (let item of foodArr.value) {
					total += item.foodPrice * item.quantity;
				}
				// console.log('totalPrice:', total);
				return total;
			});

			// 计算食物总数量
			const totalQuantity = computed(() => {
				let quantity = 0;
				for (let item of foodArr.value) {
					quantity += item.quantity;
				}
				// console.log('totalQuantity:', quantity);
				return quantity;
			});

			// 计算总费用
			const totalSettle = computed(() => {
				return totalPrice.value + business.value.deliveryPrice;
			});

			return {
				foodArr,
				business,
				totalPrice,
				totalQuantity,
				totalSettle,
				add,
				minus,
				toOrder
			};

		}
	}
</script>

<style scoped>
	/****************** 总容器 ****************/
	.wrapper {
		width: 100%;
		height: 100%;
	}

	/****************** header ****************/
	.wrapper header {
		width: 100%;
		height: 12vw;
		background-color: #0097ff;
		color: #fff;
		font-size: 4.8vw;

		position: fixed;
		left: 0;
		top: 0;
		z-index: 1000;

		display: flex;
		justify-content: center;
		align-items: center;
	}

	/****************** 商家logo ****************/
	.wrapper .business-logo {
		width: 100%;
		height: 35vw;
		margin-top: 12vw;

		display: flex;
		justify-content: center;
		align-items: center;
	}

	.wrapper .business-logo img {
		width: 40vw;
		height: 30vw;
		border-radius: 5px;
	}

	/****************** 商家信息 ****************/
	.wrapper .business-info {
		width: 100%;
		height: 20vw;

		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}

	.wrapper .business-info h1 {
		font-size: 5vw;
	}

	.wrapper .business-info p {
		font-size: 3vw;
		color: #666;
		margin-top: 1vw;
	}

	/****************** 食品列表 ****************/
	.wrapper .food {
		width: 100%;
	}

	.wrapper .food::after {
		content: "";
		display: block;
		height: 14vw;
	}

	.wrapper .food li {
		width: 100%;
		box-sizing: border-box;
		padding: 2.5vw;
		user-select: none;
		border-bottom: solid 1px #ddd;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .food li .food-left {
		display: flex;
		align-items: center;
	}

	.wrapper .food li .food-left img {
		width: 20vw;
		height: 20vw;
	}

	.wrapper .food li .food-left .food-left-info {
		margin-left: 3vw;
	}

	.wrapper .food li .food-left .food-left-info h3 {
		font-size: 3, 8vw;
		color: #555;
	}

	.wrapper .food li .food-left .food-left-info p {
		font-size: 3vw;
		color: #888;
		margin-top: 2vw;
	}

	.wrapper .food li .food-right {
		width: 16vw;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .food li .food-right .fa-minus-circle {
		font-size: 5.5vw;
		color: #999;
		cursor: pointer;
	}

	.wrapper .food li .food-right p {
		font-size: 3.6vw;
		color: #333;
	}

	.wrapper .food li .food-right .fa-plus-circle {
		font-size: 5.5vw;
		color: #0097ef;
		cursor: pointer;
	}

	/****************** 购物车 ****************/
	.wrapper .cart {
		width: 100%;
		height: 14vw;

		position: fixed;
		left: 0;
		bottom: 0;

		display: flex;
	}

	.wrapper .cart .cart-left {
		flex: 2;
		background-color: #505051;

		display: flex;
	}

	.wrapper .cart .cart-left .cart-left-icon {
		width: 16vw;
		height: 16vw;
		box-sizing: border-box;
		border: solid 1.6vw #444;
		border-radius: 8vw;
		background-color: #3190e8;
		font-size: 7vw;
		color: #fff;

		display: flex;
		justify-content: center;
		align-items: center;

		margin-top: -4vw;
		margin-left: 3vw;

		position: relative;
	}

	.wrapper .cart .cart-left .cart-left-icon-quantity {
		width: 5vw;
		height: 5vw;
		border-radius: 2.5vw;
		background-color: red;
		color: #fff;
		font-size: 3.6vw;

		display: flex;
		justify-content: center;
		align-items: center;

		position: absolute;
		right: -1.5vw;
		top: -1.5vw;
	}

	.wrapper .cart .cart-left .cart-left-info p:first-child {
		font-size: 4.5vw;
		color: #fff;
		margin-top: 1vw;
	}

	.wrapper .cart .cart-left .cart-left-info p:last-child {
		font-size: 2.8vw;
		color: #aaa;
	}

	.wrapper .cart .cart-right {
		flex: 1;
	}

	/****************** 达到起送费时的样式 ****************/
	.wrapper .cart .cart-right .cart-right-item {
		width: 50%;
		height: 100%;
		background-color: #38CA73;
		color: #fff;
		font-size: 4.5vw;
		font-weight: 700;
		user-select: none;
		cursor: pointer;
		margin-left: 50%;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	/****************** 不够起送费时的样式 ****************/
	/*.wrapper .cart .cart-right .cart-right-item{
	width: 100%;
	height: 100%;
	background-color: #535356;
	color: #fff;
	font-size: 4.5vw;
	font-weight: 700;
	user-select: none;
	
	display: flex;
	justify-content: center;
	align-items: center;
}*/
</style>
