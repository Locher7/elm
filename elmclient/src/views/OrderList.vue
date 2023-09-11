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
						<p>&#165;{{ item.orderTotal }}</p>
						<div class="order-info-right-icon" @click="toPayment(item.id)">去支付</div>
					</div>
				</div>
				<ul class="order-detailed" v-show="item.isShowDetailet">
					<li v-for="odItem in item.list" :key="odItem.id">
						<p>{{ odItem.food.foodName }} x {{ odItem.quantity }}</p>
						<p>&#165;{{ odItem.food.foodPrice*odItem.quantity }}</p>
					</li>
					<li>
						<p>配送费</p>
						<p>&#165;{{ item.business.deliveryPrice }}</p>
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
						<p>&#165;{{ item.orderTotal }}</p>
					</div>
				</div>
				<ul class="order-detailed" v-show="item.isShowDetailet">
					<li v-for="odItem in item.list" :key="odItem.id">
						<p>{{ odItem.food.foodName }} x {{ odItem.quantity }}</p>
						<p>&#165;{{ odItem.food.foodPrice*odItem.quantity }}</p>
					</li>
					<li>
						<p>配送费</p>
						<p>&#165;{{ item.business.deliveryPrice }}</p>
					</li>
				</ul>
			</li>
		</ul>

		<!-- 底部菜单 -->
		<Footer></Footer>

	</div>
</template>

<script>
	import Footer from '../components/Footer.vue';
	export default {
		name: 'OrderList',
		data() {
			return {
				orderArr: [],
				user: {},
				userId: null
			}
		},

		created() {
			this.user = this.$getSessionStorage('user');
			console.log('User:', this.user);
			//根据businessId查询商家信息
			this.$axios.post('OrdersController/listOrdersByUserId', this.$qs.stringify({
				userId: this.user.userId
			})).then(response => {
				let result = response.data
				for (let orders of result) {
					orders.isShowDetailet = false;
				}
				this.orderArr = result;
				console.log(this.orderArr)
			}).catch(error => {
				console.error(error);
			});
		},

		computed: {
			unpaidOrders() {
				return this.orderArr.filter(item => item.orderState === 0);
			},
			paidOrders() {
				return this.orderArr.filter(item => item.orderState === 1);
			}
		},

		methods: {
			detailetShow(orders) {
				orders.isShowDetailet = !orders.isShowDetailet
			},
			toPayment(orderId) {
				this.$router.push({
					path: '/payment',
					query: {
						orderId: orderId
					}
				});
			}
		},

		components: {
			Footer
		},
		mounted() {
			console.log('Component is mounted');
			document.onscroll = () => {
				// 这里添加滚动事件的处理逻辑
				console.log('Scroll event triggered');
			}
		},
	}
</script>

<style scoped>
	/****************** 总容器 ****************/
	.wrapper {
		width: 100%;
		height: 100%;
		padding-bottom:140vw;
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
