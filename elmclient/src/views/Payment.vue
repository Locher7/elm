<template>
	<div class="wrapper">
		<!-- header -->
		<header>
			<p>在线支付</p>
		</header>

		<!-- 订单信息 -->
		<h3>订单信息: </h3>
		<div class="order-info">
			<p>
				{{ orders.business.businessName }}
				<i class="fa fa-caret-down" @click="detailetShow"></i>
			</p>
			<p>&#165;{{ orders.orderTotal }}</p>
		</div>

		<!-- 订单明细 -->
		<ul class="order-detailed" v-show="isShowDetailet">
			<li v-for="item in orders.list" :key="item.id">
				<p>{{ item.food.foodName }} x {{ item.quantity }}</p>
				<p>&#165;{{ item.food.foodPrice*item.quantity }}</p>
			</li>

			<li>
				<p>配送费</p>
				<p>&#165;{{ orders.business.deliveryPrice }}</p>
			</li>
		</ul>

		<!-- 支付方式 -->
		<ul class="payment-type">
			<li>
				<img src="../assets/alipay.png">
				<i class="fa fa-check-circle"></i>
			</li>
			<li>
				<img src="../assets/wechat.png">
			</li>
		</ul>
		<div class="payment-button">
			<button>确认支付</button>
		</div>

		<!-- 底部菜单 -->
		<Footer></Footer>

	</div>
</template>

<script>
	import Footer from '../components/Footer.vue';

	export default {
		name: 'Payment',
		data() {
			return {
				orderId: this.$route.query.orderId,
				orders: {
					business: {}
				},
				isShowDetailet: false
			}
		},

		created() {
			this.$axios.post('OrdersController/getOrdersById', this.$qs.stringify({
				orderId: this.orderId
			})).then(response => {
				this.orders = response.data;
				console.log(this.orders)
			}).catch(error => {
				console.error(error);
			});
		},

		mounted() {
			//一旦路由到在线支付组件，就不能回到订单确认组件
			//先将当前url添加到history对象中
			history.pushState(null, null, document.URL);
			//popstate事件能够监听history对象的变化
			window.onpopstate = () => {
				this.$router.push('/index');
			}
		},

		// beforeRouteLeave(to, from, next) {
  		// // 在离开该路由前，手动保存历史状态
		// history.replaceState(history.state, '', document.URL);
		// next();
		// },


		destroyed() {
			window.onpopstate = null;
		},

		methods: {
			detailetShow() {
				this.isShowDetailet = !this.isShowDetailet;
			}
		},

		components: {
			Footer
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

	/****************** 订单信息 ****************/
	.wrapper h3 {
		margin-top: 12vw;
		box-sizing: border-box;
		padding: 4vw 4vw 0;

		font-size: 4vw;
		font-weight: 300;
		color: #999;
	}

	.wrapper .order-info {
		box-sizing: border-box;
		padding: 4vw;
		font-size: 4vw;
		color: #666;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .order-info p:last-child {
		color: orangered;
	}

	/****************** 订单明细 ****************/
	.wrapper .order-detailed {
		width: 100%;
	}

	.wrapper .order-detailed li {
		box-sizing: border-box;
		padding: 1vw 4vw;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .order-detailed li p {
		font-size: 3vw;
		color: #666;
	}

	/****************** 支付方式 ****************/
	.wrapper .payment-type {
		width: 100%;
	}

	.wrapper .payment-type li {
		width: 100%;
		box-sizing: border-box;
		padding: 4vw;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .payment-type li img {
		width: 33vw;
		height: 8.9vw;
	}

	.wrapper .payment-type li .fa-check-circle {
		font-size: 5vw;
		color: #38ca73;
	}

	.wrapper .payment-button {
		width: 100%;
		box-sizing: border-box;
		padding: 4vw;
	}

	.wrapper .payment-button button {
		width: 100%;
		height: 10vw;
		border: none;
		/*去掉外轮廓线*/
		outline: none;
		border-radius: 4px;
		background-color: #38ca73;
		color: #fff;

	}
</style>
