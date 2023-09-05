<template>
	<div class="wrapper">
		<!-- header -->
		<header>
			<p>确认订单</p>
		</header>

		<!-- 订单信息 -->
		<div class="order-info">
			<h5>订单配送至:</h5>
			<div class="order-info-address" @click="toUserAddress">
				<p>{{ deliveryaddress != null ? deliveryaddress.address : '请选择收货地址' }}</p>
				<i class="fa fa-angle-right"></i>
			</div>
			<p>{{ deliveryaddress ? deliveryaddress.contactName : '' }}{{ contactSex!=null? contactSex : ''}} {{ deliveryaddress ? deliveryaddress.contactTel : '' }}</p>
			
		</div>

		<h3>{{ business ? business.businessName : '' }}</h3>

		<!-- 订单明细 -->
		<ul class="order-detailed">
			<li v-for="item in cartArr" :key="item.id">
				<div class="order-detailed-left">
					<img :src="item.food.foodImg">
					<p>{{ item.food.foodName }} x {{ item.quantity }}</p>
				</div>
				<p>&#165;{{ item.food.foodPrice*item.quantity }}</p>
			</li>
		</ul>
		<div class="order-deliveryfee">
			<p>配送费</p>
			<p>&#165;{{ business.deliveryPrice }}</p>
		</div>

		<!-- ai聊天框 -->
		<div class="ai-header">AI健康助手</div>
		<div class="ai-chat">
			<img src="../assets/aiImg.png">
			<div class="ai-text">
				您好，我是您的AI健康助手。<br>
				您本餐选购了，预计摄入 卡路里。<br>
				您需要进行  公里的慢跑或者 小时的游泳可消耗这些热量。
			</div>
		</div>

		<!-- 订单合计 -->
		<div class="total">
			<div class="total-left">&#165;{{ totalPrice }}</div>
			<div class="total-right" @click="toPayment">去支付</div>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'Orders',
		data() {
			return {
				businessId: this.$route.query.businessId,
				business: {},
				user: {},
				cartArr: [],
				deliveryaddress: {}
			}
		},

		// created() {
		// 	this.user = this.$getSessionStorage('user');
		// 	this.deliveryaddress = this.$getLocalStorage(this.user.userId);

		// 	// 输出用户和地址信息，确保它们被正确加载
		// 	console.log('用户信息:', this.user);
		// 	console.log('收货地址信息:', this.deliveryaddress);

		// 	//查询当前商家
		// 	this.$axios.post('BusinessController/getBusinessById', this.$qs.stringify({
		// 		businessId: this.businessId
		// 	})).then(response => {
		// 		this.business = response.data;
		// 	}).catch(error => {
		// 		console.error(error);
		// 	});

		// 	//查询当前用户在购物车中的商家食品列表
		// 	this.$axios.post('CartController/listCart', this.$qs.stringify({
		// 		userId: this.user.userId,
		// 		businessId: this.businessId
		// 	})).then(response => {
		// 		this.cartArr = response.data;
		// 		console.log('购物车信息:', this.cartArr);
		// 	}).catch(error => {
		// 		console.error(error);
		// 	});
		// },

		// methods: {
		// 	toUserAddress() {
		// 		this.$router.push({
		// 			path: '/userAddress',
		// 			query: {
		// 				businessId: this.businessId
		// 			}
		// 		});
		// 	},
		// 	toPayment() {
		// 		if (this.deliveryaddress == null) {
		// 			alert('请选择送货地址');
		// 			return;
		// 		}

		// 		//创建订单
		// 		this.$axios.post('OrdersController/createOrders', this.$qs.stringify({
		// 			userId: this.user.userId,
		// 			businessId: this.businessId,
		// 			daId: this.deliveryaddress.daId,
		// 			orderTotal: this.totalPrice
		// 		})).then(response => {
		// 			let orderId = response.data;
		// 			if (orderId > 0) {
		// 				this.$router.push({
		// 					path: '/payment',
		// 					query: {
		// 						orderId: orderId
		// 					}
		// 				});
		// 			} else {
		// 				alter('创建订单失败');
		// 			}
		// 		}).catch(error => {
		// 			console.error(error);
		// 		});

		// 	},
		// },

		// computed: {
		// 	totalPrice() {
		// 		let totalPrice = 0;
		// 		for (let cartItem of this.cartArr) {
		// 			totalPrice += cartItem.food.foodPrice * cartItem.quantity;
		// 		}
		// 		totalPrice += this.business.deliveryPrice;
		// 		return totalPrice;
		// 	},
		// 	contactSex() {
		// 		if(this.deliveryaddress==null){
		// 			return;
		// 		}else{
		// 			return this.deliveryaddress.contactSex === 1 ? '先生' : '女士';
		// 		}
				
		// 	}
		// },

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
	.wrapper .order-info {
		width: 100%;
		margin-top: 12vw;
		background-color: #0097ef;
		box-sizing: border-box;
		padding: 2vw;
		color: #fff;
	}

	.wrapper .order-info h5 {
		font-size: 3vw;
		font-weight: 300;
	}

	.wrapper .order-info .order-info-address {
		width: 100%;
		display: flex;
		justify-content: space-between;
		align-items: center;

		font-weight: 700;
		user-select: none;
		cursor: pointer;
		margin: 1vw 0;
	}

	.wrapper .order-info .order-info-address p {
		width: 90%;
		font-size: 5vw;
	}

	.wrapper .order-info .order-info-address i {
		font-size: 6vw;
	}

	.wrapper .order-info p {
		font-size: 3vw;
	}

	.wrapper h3 {
		box-sizing: border-box;
		padding: 3vw;
		font-size: 4vw;
		color: #666;
		border-bottom: solid 1px #ddd;
	}

	/****************** 订单明细 ****************/
	.wrapper .order-detailed {
		width: 100%;
	}

	.wrapper .order-detailed li {
		width: 100%;
		height: 16vw;
		box-sizing: border-box;
		padding: 3vw;
		color: #666;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .order-detailed li .order-detailed-left {
		display: flex;
		align-items: center;
	}

	.wrapper .order-detailed li .order-detailed-left img {
		width: 10vw;
		height: 10vw;
	}

	.wrapper .order-detailed li .order-detailed-left p {
		font-size: 3.5vw;
		margin-left: 3vw;
	}

	.wrapper .order-detailed li p {
		font-size: 3.5vw;
	}

	.wrapper .order-deliveryfee {
		width: 100%;
		height: 16vw;
		box-sizing: border-box;
		padding: 3vw;
		color: #666;

		display: flex;
		justify-content: space-between;
		align-items: center;
		font-size: 3.5vw;
	}

	.wrapper .ai-chat{
		display: flex;
	}
	.wrapper .ai-chat img{
		height: 15vw;
		width: 15vw;
		margin: 5px;
	}

	/* .wrapper .ai-chat .ai-text{
		height: 15vw;
		margin: 10px 50px 10px 10px;
		padding: 15px;
		background: rgb(71, 213, 241);
		color: white;
		border-radius: 10px;
    } */

	.wrapper .ai-chat .ai-text {
    margin: 10px 50px 20px 10px;
	padding: 15px;
    background: rgba(255, 255, 255, 0.9); 
    border-radius: 8px;
    box-shadow: 
        0 4px 8px rgba(0, 0, 0, 0.1),      /* A small, close shadow */
        0 8px 16px rgba(0, 0, 0, 0.2),     /* A wider, distant shadow */
        inset 0 -4px 8px rgba(0, 0, 0, 0.05);  /* An inner shadow for depth */
    background: linear-gradient(160deg, rgba(255, 255, 255, 0.9), rgba(230, 230, 230, 0.9));

    /* Other styling */
    font-family: 'Arial', sans-serif;
    text-align: left;
    position: relative;
}

/* Optionally, you can add a small triangle to simulate a speaking bubble */
.wrapper .ai-chat .ai-text:after {
    content: '';
    width: 0;
    height: 0;
    border-top: 15px solid transparent;
    border-bottom: 15px solid transparent;
    border-right: 15px solid rgba(230, 230, 230, 0.9);
    position: absolute;
    top: 10px; /* Adjusted this to move the arrow closer to the top */
    left: 0;
    transform: translateX(-100%);
}



@keyframes borderBlink {
    0%, 100% {
        border-color: #000;
    }
    50% {
        border-color: rgba(0, 0, 0, 0.5);
    }
}

.wrapper  .ai-header {
	margin: 10px 0 10px 10px;
    font-family: 'Arial', sans-serif;
    font-size: 15px;
    background: linear-gradient(to right, #333, #777);
    color: transparent;
    background-clip: text;
    display: inline-block;
    padding: 10px 20px;
    transition: transform 0.3s ease;
    background-color: #f3f4f6;
    border: 1px solid #000;
    border-radius: 10px;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.05);
    animation: borderBlink 1.5s infinite;
}

.wrapper  .ai-header:hover {
    transform: translateY(-2px);
    box-shadow: 3px 3px 15px rgba(0, 0, 0, 0.1);
}


	/****************** 订单合计 ****************/
	.wrapper .total {
		width: 100%;
		height: 14vw;

		position: fixed;
		left: 0;
		bottom: 0;

		display: flex;
	}

	.wrapper .total .total-left {
		flex: 2;
		background-color: #505051;
		color: #fff;
		font-size: 4, 5vw;
		font-weight: 700;
		user-select: none;

		display: flex;
		justify-content: center;
		align-items: center;
	}

	.wrapper .total .total-right {
		flex: 1;
		background-color: #38ca73;
		color: #fff;
		font-size: 4.5vw;
		font-weight: 700;
		user-select: none;
		cursor: pointer;

		display: flex;
		justify-content: center;
		align-items: center;
	}
</style>
