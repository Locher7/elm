<template>
	<div class="wrapper">
		<!-- header -->
		<header>
			<p>用户登录</p>
		</header>

		<div class="login">
			<h2>密码登录</h2>
			<img src="../assets/elm-brand.png">
		</div>
		<!-- 表单部分 -->
		<ul class="form-box">
			<li>
				<div class="content">
					<input type="text" v-model="userId" placeholder="请输入手机号">
				</div>
			</li>
			<li>
				<div class="content">
					<input type="password" v-model="password" placeholder="请输入密码">
				</div>
			</li>
		</ul>

		<div class="button-login">
			<button @click="login">登录</button>
		</div>

		<div class="divider">
			<div class="line"></div>
			<div class="text">OR</div>
			<div class="line"></div>
		</div>
		<div class="button-register">
			<button @click="register">注册</button>
		</div>

		<div class="brand">
			<img src="../assets/brand.png">
		</div>
	</div>
</template>

<script>
import md5 from 'js-md5';
	import Footer from '../components/Footer.vue';
	export default {
		name: 'Login',
		data() {
			return {
				user:{},
				userId: '',
				password: ''
			}
		},
		components: {
			Footer
		},
		methods: {
			login() {
				if (this.userId == '') {
					alert('手机号码不能为空');
					return;
				}
				if (this.password == '') {
					alert('密码不能为空');
					return;
				}

				//登录请求
				this.$axios.post('UserController/getUserByIdByPass', this.$qs.stringify({
					userId: this.userId,
					password: md5(this.password)
				})).then(response => {
					let user = response.data;
					console.log(typeof user);  // 查看 user 的类型
					console.log(user);  // 查看 user 的内容

					if (user==0) {
						alert('用户名或密码不正确!');
					} else {
						//sessionstorage有容量限制，为了防止数据溢出，所以不将userImg放入数据中
						user.userImg = '';
						this.$setSessionStorage('user', user);
						this.$router.go(-1);
					}
				}).catch(error => {
					console.error(error);
				});
				
			},
			register() {
				this.$router.push('/register');
			}
		},
	}
</script>

<style scoped>
	/****************** 总容器 ****************/
	.wrapper {
		width: 100%;
		height: 100%;
		overflow: auto;
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

	.wrapper .login {
		margin: 30vw 0 10vw 12vw;
	}

	.wrapper .login img {
		width: 40vw;
		opacity: 15%;

		position: absolute;
		top: 22%;
		left: 30%;

	}

	/****************** 表单部分 ****************/
	.wrapper .form-box {
		width: 100%;
		margin-bottom: 10vw;
	}

	.wrapper .form-box li {
		box-sizing: border-box;
		margin: 6vw 10vw 0 10vw;
		display: flex;
		align-items: center;
		border: #f4eeee solid;
		border-width: 0px 0px 1.8px 0px;

		position: relative;
	}

	.wrapper .form-box li .content {
		width: 100%;
		margin: 2vw 1vw 2vw 2vw;
	}

	.wrapper .form-box li .content input {
		border: none;
		outline: none;
		width: 100%;
		height: 4vw;
		font-size: 4vw;
		background-color: transparent;
	}

	.wrapper .button-login {
		width: 100%;
		box-sizing: border-box;
		padding: 10vw 10vw 0vw 10vw;
	}

	.wrapper .button-login button {
		width: 100%;
		height: 11vw;
		font-size: 5vw;
		font-weight: 700;
		color: #fff;
		background-color: #0097ff;
		border-radius: 50px;

		border: none;
		outline: none;
	}

	.wrapper .button-register {
		width: 100%;
		box-sizing: border-box;
		padding: 0vw 10vw 0 10vw;
	}

	.wrapper .button-register button {
		width: 100%;
		height: 11vw;
		font-size: 5vw;
		font-weight: 700;
		color: #666;
		background-color: #eee;
		border-radius: 50px;

		border: none;
		outline: none;
		border: solid 1px #ddd;
	}


	.wrapper .divider {
		display: flex;
		margin: 5vw 6vw;
	}

	.wrapper .line {
		height: 1px;
		width: 30vw;
		background-color: #d6d2d2;
		margin: 3vw 6vw 0vw 6vw;
	}

	.wrapper .text {
		height: 100%;
		width: 34px;
		color: #d6d2d2;
		font-size: 17px;
		text-align: center;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.wrapper .brand {
		position: absolute;
		bottom: 0;
		left: 37.5vw;
	}

	.wrapper .brand img {
		width: 25vw;
	}
</style>
