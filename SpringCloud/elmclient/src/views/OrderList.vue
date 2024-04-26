<template>
	<div class="wrapper">

		<!-- header -->
		<header>
			<p>我的订单</p>
		</header>

		<!-- 订单列表 -->
		<h3>未支付订单信息 : </h3>
		<ul class="order">
			<li v-for="item in unpaidOrders" :key="item.id">
				<div class="order-info">
					<p>
						{{ item.business.businessName }}
						<i class="fa fa-caret-down" @click="detailetShow(item)"></i>
					</p>
					<div class="order-info-right">
						<p>&#165;{{ item.orderTotal/100 }}</p>
						<div class="order-info-right-icon" @click="toPayment(item.id)">去支付</div>
					</div>
				</div>
				<ul class="order-detailed" v-show="item.isShowDetailet">
					<li v-for="odItem in item.list" :key="odItem.id">
						<p>{{ odItem.food.foodName }} x {{ odItem.quantity }}</p>
						<p>&#165;{{ odItem.food.foodPrice/100*odItem.quantity }}</p>
					</li>
					<li>
						<p>配送费</p>
						<p>&#165;{{ item.business.deliveryPrice/100 }}</p>
					</li>
				</ul>
			</li>
		</ul>

		<h3>已支付订单信息 : </h3>
		<ul class="order">
			<li v-for="item in paidOrders" :key="item.id">
				<div class="order-info">
					<p>
						{{ item.business.businessName }}
						<i class="fa fa-caret-down" @click="detailetShow(item)"></i>
					</p>
					<div class="order-info-right">
						<p>&#165;{{ item.orderTotal/100 }}</p>
					</div>
				</div>
				<ul class="order-detailed" v-show="item.isShowDetailet">
					<li v-for="odItem in item.list" :key="odItem.id">
						<p>{{ odItem.food.foodName }} x {{ odItem.quantity }}</p>
						<p>&#165;{{ odItem.food.foodPrice/100*odItem.quantity }}</p>
					</li>
					<li>
						<p>配送费</p>
						<p>&#165;{{ item.business.deliveryPrice/100 }}</p>
					</li>
				</ul>
			</li>
		</ul>

		<!-- 底部菜单 -->
		<Footer></Footer>

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
		useRouter
	} from 'vue-router';
	import axios from 'axios';
	import qs from 'qs';
	import Footer from '../components/Footer.vue';
	export default {
		name: 'OrderList',
		components: {
			Footer
		},
		setup() {
			const router = useRouter();
			const orderArr = ref([]);
			const user = ref(JSON.parse(sessionStorage.getItem('user')));
			const userId = ref(null);

			onMounted(() => {
				// 查询历史订单
				let url = `OrdersController/listOrdersByUserId/${user.value.userId}`;
				axios.get(url).then(response => {
					let result = response.data.result;
					for (let orders of result) {
						orders.isShowDetailet = false;
					}
					orderArr.value = result;
					// console.log(orderArr.value)
				}).catch(error => {
					console.error(error);
				});
				document.onscroll = () => {
					console.log('Scroll event triggered');
				}
			});

			const unpaidOrders = computed(() => {
				return orderArr.value.filter(item => item.orderState === 0);
			});

			const paidOrders = computed(() => {
				return orderArr.value.filter(item => item.orderState === 1);
			});

			// 是否显示订单明细
			const detailetShow = (orders) => {
				orders.isShowDetailet = !orders.isShowDetailet;
			};
			return {
				orderArr,
				user,
				userId,
				unpaidOrders,
				paidOrders,
				detailetShow,
			}

		}
	}
</script>

<style scoped>
	/****************** 总容器 ****************/
	.wrapper {
		width: 100%;
		height: 100%;
		padding-bottom: 140vw;
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

	/****************** 订单列表 ****************/
	.wrapper h3 {
		margin-top: 12vw;
		box-sizing: border-box;
		padding: 4vw;
		font-size: 4vw;
		font-weight: 300;
		color: #999;
	}

	.wrapper .order {
		width: 100%;
	}

	.wrapper .order li {
		width: 100%;
	}

	.wrapper .order li .order-info {
		box-sizing: border-box;
		padding: 2vw 4vw;
		font-size: 4vw;
		color: #666;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .order li .order-info .order-info-right {
		display: flex;
	}

	.wrapper .order li .order-info .order-info-right .order-info-right-icon {
		background-color: #f90;
		color: #fff;
		border-radius: 3px;
		margin-left: 2vw;
		user-select: none;
		cursor: pointer;
	}

	.wrapper .order li .order-detailed {
		width: 100%;
	}

	.wrapper .order li .order-detailed li {
		width: 100%;
		box-sizing: border-box;
		padding: 1vw 4vw;
		color: #666;
		font-size: 3vw;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}
</style>
