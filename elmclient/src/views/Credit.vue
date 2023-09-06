<template>
	<div class="wrapper">
		<!-- header -->
		<header>
			<p>我的积分</p>
		</header>

		<!-- 总积分 -->
		<div class="credit">
			<div class="credit-icon"><i class="fa fa-gift"></i></div>
			<h2>26</h2>
			<p>总积分</p>
			<!-- <div class="credit-use-icons">
				<div class="credit-detail-icon">
					<i class="fa fa-list"></i>
					<p>积分明细</p>
				</div>
				<div class="credit-divider"></div>
				<div class="credit-expire-icon">
					<i class="fa fa-clock-o"></i>
					<p>过期积分</p>
				</div>
			</div> -->
			<div class="credit-use">
				<button @click="toIndex">去使用</button>
			</div>
		</div>
		<!-- 积分明细 -->
		<h3>积分明细</h3>
		<ul class="credit-detailed">
			<li>
				<div class="credit-detailed-left">
					<p>使用积分</p>
					<p>2023-08-14 17:21:33</p>
				</div>
				<div class="credit-detailed-right">
					<p>-9</p>
				</div>
			</li>
			<li>
				<div class="credit-detailed-left">
					<p>获得积分</p>
					<p>2023-08-14 17:21:33</p>
				</div>
				<div class="credit-detailed-right">
					<p>+9</p>
				</div>
			</li>
			<li>
				<div class="credit-detailed-left">
					<p>使用积分</p>
					<p>2023-08-14 17:21:33</p>
				</div>
				<div class="credit-detailed-right">
					<p>-9</p>
				</div>
			</li>
			<li>
				<div class="credit-detailed-left">
					<p>获得积分</p>
					<p>2023-08-14 17:21:33</p>
				</div>
				<div class="credit-detailed-right">
					<p>+9</p>
				</div>
			</li>
			<li>
				<div class="credit-detailed-left">
					<p>使用积分</p>
					<p>2023-08-14 17:21:33</p>
				</div>
				<div class="credit-detailed-right">
					<p>-9</p>
				</div>
			</li>
		</ul>


		<!-- 底部菜单部分 -->
		<Footer></Footer>
	</div>
</template>

<script>
	import Footer from '../components/Footer.vue';
	export default {
		name: 'Credit',
		data() {
			return{
				detailArr: [],
				user: {},
			}
		},
		methods: {
			toIndex() {
				this.$router.push('/index');
			},
		},
		created(){
			this.user = this.$getSessionStorage('user');
			//查询积分信息
			this.$axios.post('PointController/getDetailsByUserId', this.$qs.stringify({
				userId: this.user.userId,
			})).then(response => {
				this.detailArr = response.data;
				console.log('积分信息:', this.detailArr);
			}).catch(error => {
				console.error(error);
			});
		},
		components: {
			Footer
		},
	}
</script>

<style scoped>
	/****************** 总容器 ****************/
	.wrapper {
		width: 100%;
		height: 100%;
		background-color: #F5F4F6;
		overflow: hidden;
		display: flex;
		flex-direction: column;
	}

	/****************** header ****************/
	.wrapper header {
		width: 100%;
		height: 20vw;
		background-color: #0097ff;
		color: #fff;
		font-size: 4.8vw;
		position: fixed;
		left: 0;
		top: 0;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	/****************** 总积分 ****************/
	.wrapper .credit {
		width: 90%;
		height: 80vw;
		margin: 15vw auto 5vw;
		background-color: #fff;
		border-radius: 2vw;
		box-sizing: border-box;
		display: flex;
		flex-direction: column;
		align-items: center;
		z-index: 2;
	}

	.wrapper .credit-icon {
		margin: 15vw 10px 10px 10px;
	}

	.wrapper .credit i {
		font-size: 9vw;
		color: mediumpurple
	}

	.wrapper .credit h2 {
		font-size: 8vw;
		margin-bottom: 1vw;
	}

	.wrapper .credit p {
		font-size: 3vw;
		color: #777;
		margin-bottom: 3vw;
	}

	.wrapper .credit .credit-use {
		width: 100%;
		box-sizing: border-box;
		padding: 10vw 10vw 0vw 10vw;
	}

	.wrapper .credit .credit-use button {
		width: 100%;
		height: 13vw;
		font-size: 5vw;
		font-weight: 700;
		color: #fff;
		background-color: #0097ff;
		border-radius: 50px;

		border: none;
		outline: none;
	}

	/****************** 积分明细 ****************/
	.wrapper .credit-detailed {
		width: 100%;
	}

	.wrapper .credit-detailed::after {
		content: "";
		display: block;
		height: 14vw;
	}

	.wrapper h3 {
		margin-left: 6vw;
		font-size: 4vw;
	}

	.wrapper .credit-detailed li {
		width: 90%;
		height: 15vw;
		background-color: #fff;
		margin: 2vw auto;
		border-radius: 2vw;

		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.wrapper .credit-detailed li .credit-detailed-left {
		margin-left: 4vw;
	}

	.wrapper .credit-detailed li .credit-detailed-left p:first-child {
		font-size: 3.8vw;
		color: #111;
	}

	.wrapper .credit-detailed li .credit-detailed-left p:last-child {
		font-size: 3vw;
		color: #777;
		margin-top: 1vw;
	}

	.wrapper .credit-detailed li .credit-detailed-right {
		margin-right: 4vw;
		margin-bottom: 5vw;
	}

	.wrapper .credit-detailed li .credit-detailed-right p {
		font-size: 3.5vw;
		color: red;
	}

	/* ***************** Credit Use Icons *************** */
	/* .wrapper .credit-use-icons {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    margin-bottom: 2vw;
}

.wrapper .credit-detail-icon, 
.wrapper .credit-expire-icon {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 45%;
    font-size: 3.8vw;
}

.wrapper .credit-detail-icon i, 
.wrapper .credit-expire-icon i {
    font-size: 4.5vw;
    margin-bottom: 1vw;
}

.wrapper .credit-divider {
    height: 6vw;
    width: 1px;
    background-color: rgba(0,0,0,0.1);
    margin-top: -1vw;
} */
</style>
