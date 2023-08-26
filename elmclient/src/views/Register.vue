<template>
	<div class="wrapper">
		<!-- header -->
		<header>
			<p>用户注册</p>
		</header>

		<!-- 表单部分 -->
		<ul class="form-box">
			<li>
				<div class="title">
					手机号码:
				</div>
				<div class="content">
					<input type="text" placeholder="手机号码">
				</div>
			</li>
			<li>
				<div class="title">
					密码:
				</div>
				<div class="content">
					<input type="password" placeholder="密码">
				</div>
			</li>
			<li>
				<div class="title">
					确认密码:
				</div>
				<div class="content">
					<input type="password" placeholder="确认密码">
				</div>
			</li>
			<li>
				<div class="title">
					性别:
				</div>
				<div class="content" style="font-size: 3vw;">
					<input type="radio" name="sex" checked style="width: 6vw;height: 3.2vw;">男
					<input type="radio" name="sex" style="width: 6vw;height: 3.2vw;">女
				</div>
			</li>
		</ul>

		<div class="button-register">
			<button @click="toLogin">注册</button>
		</div>

		<!-- 底部菜单 -->
		<Footer></Footer>

	</div>
</template>

<script>
	import Footer from '../components/Footer.vue';
	export default {
		name: 'Register',
		data() {

		},
		components: {
			Footer
		},
		methods: {
			toLogin() {
				this.$router.push('/login');
			}
		}
	}
</script>

<style>
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

	/****************** 表单部分 ****************/
	.wrapper .form-box {
		width: 100%;
		margin-top: 12vw;
	}

	.wrapper .form-box li {
		box-sizing: border-box;
		padding: 4vw 3vw 0 3vw;

		display: flex;
		align-items: center;
	}

	.wrapper .form-box li .title {
		flex: 0 0 18vw;
		font-size: 3vw;
		font-weight: 700;
		color: #666;
	}

	.wrapper .form-box li .content {
		flex: 1;
	}

	.wrapper .form-box li .content input {
		border: none;
		outline: none;
		width: 100%;
		height: 4vw;
		font-size: 3vw;
	}

	.wrapper .button-register {
		width: 100%;
		box-sizing: border-box;
		padding: 4vw 3vw 0 3vw;
	}

	.wrapper .button-register button {
		width: 100%;
		height: 10vw;
		font-size: 3.8vw;
		font-weight: 700;
		color: #fff;
		background-color: #38ca73;
		border-radius: 4px;

		border: none;
		outline: none;
	}
</style>
