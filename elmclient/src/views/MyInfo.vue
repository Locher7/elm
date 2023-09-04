<template>
	<div class="wrapper">
		<!-- Header -->
		<header class="header">
			<h1 class="title">个人中心</h1>
		</header>

		<!-- User Info Section -->
		<section class="user-info">
			<div class="avatar">
				<img src="../assets/userImg/yhtx04.png">
			</div>
			<div class="user-details">
				<h2 class="username">用户名称</h2>
				<p class="student-id">手机号码</p>
			</div>
		</section>

		<!-- Navigation Section -->
		<nav class="nav-section">
			<div class="nav-item-left" @click="toMyAddress">
				<img src="../assets/myAddress.png">
				<p>地址管理</p>
			</div>
			<div class="nav-item-right" @click="toWodefabu">
				<img src="../assets/editInfo.png">
				<p>修改信息</p>
			</div>
		</nav>

		<!-- Instructions Section -->
		<section class="instructions">
			<div class="instruction-item" @click="tosysm">
				<p>我的积分</p>
				<div class="arrow-icon"></div>
			</div>
			<div class="instruction-item" @click="feedbackQ">
				<p>使用说明</p>
				<div class="arrow-icon"></div>
			</div>
			<div class="instruction-item" @click="editInfo">
				<p>问题反馈</p>
				<div class="arrow-icon"></div>
			</div>
			<div class="instruction-item" @click="toCheck">
				<p>检查更新</p>
				<div class="arrow-icon"></div>
			</div>
		</section>

		<button type="danger">退出登录</button>

		<!-- 底部菜单部分 -->
		<Footer></Footer>

	</div>

</template>

<script>
	import Footer from '../components/Footer.vue';
	export default {
		name: 'MyInfo',
		data() {},
		components: {
			Footer
		},
		methods:{
			toMyAddress() {
				this.$router.push('/myAddress');
			},
		}
	}
</script>

<style scoped>
	.wrapper {
		height: 100%;
		width: 100%;
		display: flex;
		flex-direction: column;
		background: rgb(246, 243, 243);
		overflow: scroll;
	}

	.header {
		background-color: white;
		padding: 10px 0 10px 0;
		text-align: center;
		font-weight: bold;
		font-size: 0.8rem;
		color: #0097ff;
		font-family: SourceHanSansCN-Bold;
	}

	.user-info {
		display: flex;
		align-items: center;
		padding: 30px 35px;
		background-color: #0097ff;
	}

	.avatar img {
		height: 90px;
		width: 90px;
		border-radius: 50%;
		overflow: hidden;
		margin: 0 10px;
	}

	.user-details {
		margin: 10px 20px;
		white-space: nowrap;
		color: white;
	}

	.user-details .username {
		margin: 10px 0;
		font-size: 1.3rem;
		font-weight: medium;
	}

	.user-details .student-id {
		margin: 10px 0;
		font-size: 1rem;
		font-weight: medium;
	}

	.nav-section {
		display: flex;
		justify-content: space-around;
		margin: 15px;
		background-color: white;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
		border-radius: 10px;
	}

	.nav-item-left {
		display: flex;
		flex-direction: column;
		align-items: center;
		color: #4e4e4e;
		padding: 10px;
	}

	.nav-item-right {
		display: flex;
		flex-direction: column;
		align-items: center;
		color: #4e4e4e;
		padding: 10px;
	}

	.nav-item-left img {
		height: 3.5vh;
		width: 10vw;
		margin: 13px 0 5px 0;
	}

	.nav-item-right img {
		height: 3.1vh;
		width: 6vw;
		margin: 15px 0 8px 0;
	}

	.instructions {
		display: flex;
		flex-direction: column;
		padding: 10px 10px 15px 10px;
		margin: 0 15px;
		background-color: white;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
		border-radius: 10px;
	}

	.instruction-item {
		display: flex;
		justify-content: space-between;
		padding: 10px 20px;
		border-bottom: 1px solid #dfd8d8;
	}

	.instruction-item p{
		color: #4e4e4e;
		font-size: 0.9rem;
		font-weight: medium;
	}

	
	.wrapper button {
		height: 13vw;
		font-size: 4vw;
		font-weight: 700;
		margin: 20px;
		color: #fff;
		background-color: #0097ff;
		border-radius: 50px;
		border: none;
		outline: none;
	}
</style>
