<template>
	<div class="wrapper">
		<!-- header -->
		<header>
			<p>商家信息</p>
		</header>

		<!-- 商家logo -->
		<div class="business-logo">
			<img src="../assets/sj01.png">
		</div>

		<!-- 商家信息 -->
		<div class="business-info">
			<h1>万家饺子(软件园E18店)</h1>
			<p>&#165;15起送 配送&#165;3</p>
			<p>特色美食</p>
		</div>

		<!-- 食品列表 -->
		<ul class="food">
			<li>
				<div class="food-left">
					<img src="../assets/sp01.png">
					<div class="food-left-info">
						<h3>纯肉鲜肉(水饺)</h3>
						<p>新鲜猪肉</p>
						<p>&#165;15</p>
					</div>
				</div>
				<div class="food-right">
					<div>
						<i class="fa fa-minus-circle"></i>
					</div>
					<p><span>2</span></p>
					<div>
						<i class="fa fa-plus-circle"></i>
					</div>
				</div>
			</li>
			<li>
				<div class="food-left">
					<img src="../assets/sp02.png">
					<div class="food-left-info">
						<h3>玉米鲜肉(水饺)</h3>
						<p>玉米鲜肉</p>
						<p>&#165;16</p>
					</div>
				</div>
				<div class="food-right">
					<div>
						<i class="fa fa-minus-circle"></i>
					</div>
					<p><span>3</span></p>
					<div>
						<i class="fa fa-plus-circle"></i>
					</div>
				</div>
			</li>
			<li>
				<div class="food-left">
					<img src="../assets/sp03.png">
					<div class="food-left-info">
						<h3>虾仁三鲜(蒸饺)</h3>
						<p>新鲜虾仁</p>
						<p>&#165;22</p>
					</div>
				</div>
				<div class="food-right">
					<div>
						<i class="fa fa-minus-circle"></i>
					</div>
					<p><span>1</span></p>
					<div>
						<i class="fa fa-plus-circle"></i>
					</div>
				</div>
			</li>
			<li>
				<div class="food-left">
					<img src="../assets/sp04.png">
					<div class="food-left-info">
						<h3>素三鲜(蒸饺)</h3>
						<p>素三鲜</p>
						<p>&#165;13</p>
					</div>
				</div>
				<div class="food-right">
					<div></div>
					<p></p>
					<div>
						<i class="fa fa-plus-circle"></i>
					</div>
				</div>
			</li>
			<li>
				<div class="food-left">
					<img src="../assets/sp05.png">
					<div class="food-left-info">
						<h3>角瓜鸡蛋(蒸饺)</h3>
						<p>新鲜鸡蛋</p>
						<p>&#165;12</p>
					</div>
				</div>
				<div class="food-right">
					<div></div>
					<p></p>
					<div>
						<i class="fa fa-plus-circle"></i>
					</div>
				</div>
			</li>
			<li>
				<div class="food-left">
					<img src="../assets/sp06.png">
					<div class="food-left-info">
						<h3>小白菜肉(水饺)</h3>
						<p>新鲜猪肉</p>
						<p>&#165;15</p>
					</div>
				</div>
				<div class="food-right">
					<div></div>
					<p></p>
					<div>
						<i class="fa fa-plus-circle"></i>
					</div>
				</div>
			</li>
			<li>
				<div class="food-left">
					<img src="../assets/sp07.png">
					<div class="food-left-info">
						<h3>芹菜牛肉(水饺)</h3>
						<p>新鲜牛肉</p>
						<p>&#165;15</p>
					</div>
				</div>
				<div class="food-right">
					<div></div>
					<p></p>
					<div>
						<i class="fa fa-plus-circle"></i>
					</div>
				</div>
			</li>
			<li>
				<div class="food-left">
					<img src="../assets/sp08.png">
					<div class="food-left-info">
						<h3>虾腰鲜肉(水饺)</h3>
						<p>新鲜猪肉</p>
						<p>&#165;20</p>
					</div>
				</div>
				<div class="food-right">
					<div></div>
					<p></p>
					<div>
						<i class="fa fa-plus-circle"></i>
					</div>
				</div>
			</li>
			<li>
				<div class="food-left">
					<img src="../assets/sp09.png">
					<div class="food-left-info">
						<h3>青椒鲜肉(水饺)</h3>
						<p>新鲜猪肉</p>
						<p>&#165;15</p>
					</div>
				</div>
				<div class="food-right">
					<div></div>
					<p></p>
					<div>
						<i class="fa fa-plus-circle"></i>
					</div>
				</div>
			</li>
		</ul>

		<!-- 购物车 -->
		<div class="cart">
			<div class="cart-left">
				<div class="cart-left-icon">
					<i class="fa fa-shopping-cart"></i>
					<div class="cart-left-icon-quantity">3</div>
				</div>
				<div class="cart-left-info">
					<p>&#165;12.88</p>
					<p>另需配送费3元</p>
				</div>
			</div>
			<div class="cart-right">
				<!-- 不够起送费 -->
				<!--<div class="cart-right-item">&#165;15起送</div>-->
				<!-- 达到起送费 -->
				<div class="cart-right-item" @click="toOrder">去结算</div>
			</div>
		</div>

	</div>
</template>

<script>
	export default {
		name: 'BusinessInfo',
		data() {
			// return {
			// 	businessId:this.$query.businessId,
			// 	business:{}
			// }
		},
		// created(){
		//     //根据businessId查询商家信息
		// 	this.$axios.post('BusinessController/getBuinessById',this.$qs.stringify({
		// 		businessId:this.businessId
		// 	})).then(response=>{
		// 		this.business=response.data;
		// 	}).catch(errpr=>{
		// 		console.error(error);
		// 	})
		// },
		methods: {
			toOrder() {
				this.$router.push('/orders');
			}
		},
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

	/*达到起送费时的样式*/
	.wrapper .cart .cart-right .cart-right-item {
		width: 100%;
		height: 100%;
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

	/*不够起送费时的样式*/
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
