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
			<p>&#165;{{ orders.orderTotal/100 }}</p>
		</div>

		<!-- 订单明细 -->
		<ul class="order-detailed" v-show="isShowDetailet">
			<li v-for="item in orders.list" :key="item.id">
				<p>{{ item.food.foodName }} x {{ item.quantity }}</p>
				<p>&#165;{{ item.food.foodPrice/100*item.quantity }}</p>
			</li>
			<li>
				<p>配送费</p>
				<p>&#165;{{ orders.business.deliveryPrice/100 }}</p>
			</li>
		</ul>

		<!-- 积分抵扣 -->

		<div class="credit-deduction">
			<div class="credit-info">
				<p>剩余{{credit}}积分</p>
				<p v-if="useCredit" class="credit-used-text">使用{{ Math.round(this.orders.orderTotal/100) }}积分，抵扣<label class="orangered">{{ Math.round(this.orders.orderTotal/100)/10 }}</label>&#165;</p>
			</div>
			<div class="credit-toggle">
				<span class="credit-use">{{ useCredit ? '使用' : '不使用' }}</span>
				<i class="fa" :class="useCredit ? 'fa-toggle-on' : 'fa-toggle-off'" @click="toggleCreditUse"></i>
			</div>
		</div>

		<div class="current-total">
			<p>总金额: <span>&#165;{{ discountedTotal/100 }}</span></p>
		</div>

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
		<div class="payment-button" @click="pay">
			<button>确认支付</button>
		</div>

		<!-- 支付成功弹窗 -->
		<div class="modal" v-if="successShowModal">
			<div class="modal-content">
				<i class="fa fa-check-circle success-icon"></i>
				<p>支付成功</p>
			</div>
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
				user: {},
				orderId: this.$route.query.orderId,
				orders: {
					business: {},
				},
				isShowDetailet: false,
				credit: '',
				useCredit: false,
				discountedTotal: 0,
				useIntegration: 0,
				successShowModal: false
			}
		},


		created() {
			this.user = this.$getSessionStorage('user');

			//查询订单
			this.$axios.post('OrdersController/getOrdersById', this.$qs.stringify({
				orderId: this.orderId
			})).then(response => {
				this.orders = response.data;
				this.discountedTotal = this.orders.orderTotal;
				console.log(this.orders)
			}).catch(error => {
				console.error(error);
			});

			//查询总积分
			this.$axios.post('IntegrationController/getCreditByUserId', this.$qs.stringify({
				userId: this.user.userId,
			})).then(response => {
				this.credit = response.data;
				console.log(this.credit)
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

		destroyed() {
			window.onpopstate = null;
		},

		methods: {
			//是否展示订单明细
			detailetShow() {
				this.isShowDetailet = !this.isShowDetailet;
			},
			//支付
			pay() {

				if (this.useCredit == true) {
					this.useIntegration = 1;
				}
				this.$axios.post('IntegrationController/payCredit', this.$qs.stringify({
					userId: this.user.userId,
					usedPoints: this.orderId,
					integrationState: this.useIntegration
				})).then(response => {
					if (response.data == 1) {
						this.successShowModal = true;
						setTimeout(() => {
							this.$router.push('/index');
						}, 1000);
					} else {
						alert("支付失败")
					}
				}).catch(error => {
					console.error(error);
					alert("请求出错，请稍后重试!")
				});
			},

			toggleCreditUse() {
				if (this.credit < Math.round(this.orders.orderTotal/100)) {
					alert("剩余积分不足");
				} else {
					this.useCredit = !this.useCredit;
				}
				if (!this.useCredit) {
					this.discountedTotal = this.orders.orderTotal;
				} else {
					this.discountedTotal = this.orders.orderTotal - Math.round(this.orders.orderTotal) / 10;
				}
			},

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
		padding: 9vw;
	}

	.wrapper .payment-button button {
		width: 100%;
		height: 13vw;
		font-size: 5vw;
		font-weight: 700;
		color: #fff;
		background-color: #38ca73;
		border-radius: 50px;
		border: none;
		outline: none;

	}




	/****************** 积分抵扣 ****************/
	.wrapper .credit-deduction {
		margin: 6vw 4vw;
		padding: 2vw;
		border: 1px solid #e0e0e0;
		border-radius: 1vw;
		background-color: #ffffff;
		box-shadow: 0 1vw 2vw rgba(0, 0, 0, 0.1);
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .credit-deduction .credit-info {
		display: flex;
		flex-direction: column;
	}

	.wrapper .credit-deduction p {
		margin: 0;
		font-size: 4vw;
		color: #666;
	}

	.wrapper .credit-deduction .credit-used-text {
		margin-top: 3vw;
		font-size: 3.5vw;
	}

	.wrapper .credit-deduction label {
		color: orangered;
	}

	.wrapper .credit-deduction .credit-toggle {
		display: flex;
		align-items: center;
	}

	.wrapper .credit-deduction .credit-toggle .credit-use {
		margin-right: 1vw;
		font-size: 3.5vw;
		color: #666;
	}

	.wrapper .credit-deduction .credit-toggle .fa {
		font-size: 5vw;
		color: white;
		background-color: #666;
		border-radius: 50%;
		padding: 0.5vw;
		transition: transform 0.3s;
	}


	/****************** 总金额 ******************/
	.wrapper .current-total {
		margin: 4vw 4vw;
		text-align: right;
		font-size: 4vw;
		color: #555;
	}

	.wrapper .current-total span {
		color: orangered;
		font-weight: bold;
	}


	.modal {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.75);
		display: flex;
		justify-content: center;
		align-items: center;
		z-index: 2000;
	}

	.modal-content {
		width: 80%;
		max-width: 500px;
		padding: 30px;
		border-radius: 15px;
		background-color: #ffffff;
		text-align: center;
		box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.5);
	}

	.success-icon {
		font-size: 80px;
		color: #4CAF50;
		margin-bottom: 30px;
	}
</style>
