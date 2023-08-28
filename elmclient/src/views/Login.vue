<template>
	<div class="wrapper">
		<!-- header -->
		<header>
			<p>用户登录</p>
		</header>
		<img src="../assets/brand.png">
		<!-- 表单部分 -->
		<ul class="form-box">
			<li>
				<div class="title">
					手机号码:
				</div>
				<div class="content">
					<input type="text"  v-model="userId" placeholder="请输入手机号">
				</div>
			</li>
			<li>
				<div class="title">
					密码:
				</div>
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
			<button @click="toRegister">注册</button>
		</div>

		<!-- 底部菜单 -->
		<Footer></Footer>
	</div>
</template>

<script>
import { setSessionStorage } from '@/common';
	import Footer from '../components/Footer.vue';
	export default {
		name: 'Login',
		data() {
			return {
				userId: '',
				password: ''
			}
		},
		components: {
			Footer
		},
		methods: {
			login(){
				if(this.userId==''){
					alert('手机号码不能为空');
					return;
				}
				if(this.password==''){
					alert('密码不能为空');
					return;
				}

				//登录请求
				this.$axios.post('UserController/getUserByIdByPass',this.$qs.stringify({
					userId:this.userId,
					password:this.password
				})).then(response=>{
					let user=response.data;
					if(user==null){
						alert('用户名或密码不正确!');
					}else{
						//sessionstorage有容量限制，为了防止数据溢出，所以不将userImg放入数据中
						user.userImg='';
						this.$setSessionStorage('user',user);
						this.$go(-1);
					}
				}).catch(error=>{
					console.error(error);
				});
			},
			toRegister() {
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
	.wrapper img{
		height: 20vh;
		width: 50vw;
		display: block;
		margin: 20vw auto 0vw auto;
	}
	/****************** 表单部分 ****************/
	.wrapper .form-box {
		width: 100%;
	}

	.wrapper .form-box li {
		box-sizing: border-box;
		margin: 6vw 10vw 0 10vw;
		display: flex;
		align-items: center;
		border: #f4eeee solid;
		border-width: 0px 0px 1.8px 0px;
	}

	.wrapper .form-box li .title {
		margin: 2vw 0vw;
		flex: 0 0 18vw;
		font-size: 3.8vw;
		font-weight: 550;
		color: #666;
	}

	.wrapper .form-box li .content {
		flex: 1;
		margin: 2vw 0vw 2vw 2vw;
	}

	.wrapper .form-box li .content input {
		border: none;
		outline: none;
		width: 100%;
		height: 4vw;
		font-size: 3.5vw;
		background-color: transparent;
	}

	.wrapper .button-login {
		width: 100%;
		box-sizing: border-box;
		padding: 10vw 10vw 0vw 10vw;
	}

	.wrapper .button-login button {
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

	.wrapper .button-register {
		width: 100%;
		box-sizing: border-box;
		padding: 0vw 10vw 0 10vw;
	}

	.wrapper .button-register button {
		width: 100%;
		height: 13vw;
		font-size: 5vw;
		font-weight: 700;
		color: #666;
		background-color: #eee;
		border-radius: 50px;

		border: none;
		outline: none;
		border: solid 1px #ddd;
	}


	.wrapper .divider{
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
</style>
