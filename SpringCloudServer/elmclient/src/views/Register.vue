<template>
	<div class="wrapper">
		<!-- header -->
		<header>
			<p>用户注册</p>
		</header>
		<!-- 表单部分 -->
		<div class="register">
			<h2>手机号注册</h2>
			<img src="../assets/elm-brand.png">
		</div>
		<ul class="form-box">
			<li>
				<div class="content">
					<input type="text" @blur="checkUserId" v-model="user.userId" placeholder="请输入手机号码">
				</div>
			</li>
			<li>
				<div class="content">
					<input type="password" v-model="user.password" placeholder="请输入密码">
				</div>
			</li>
			<li>
				<div class="content">
					<input type="password" v-model="confirmPassword" placeholder="请确认密码">
				</div>
			</li>
			<li>
				<div class="content">
					<input type="text" v-model="user.userName" placeholder="请输入用户名称">
				</div>
			</li>
			<li>
				<div class="title">
					请选择性别：
				</div>
				<div class="content" style="font-size: 3vw;">
					<input type="radio" v-model="user.userSex" value="1" style="width: 6vw;height: 3.2vw;">男
					<input type="radio" v-model="user.userSex" value="0" style="width: 6vw;height: 3.2vw;">女
				</div>
			</li>
		</ul>

		<div class="button-register">
			<button @click="register">注册</button>
		</div>
		<div class="brand">
			<img src="../assets/brand.png">
		</div>

	</div>
</template>

<script>
	import {
		ref,
		inject,
		onMounted,
		watch,
		computed,
		onUnmounted
	} from 'vue';
	import {
		useRouter
	} from 'vue-router';
	import axios from 'axios';
	import qs from 'qs';
	import {
		setSessionStorage,
	} from '../common.js'
	import md5 from "js-md5";
	import Footer from '../components/Footer.vue';

	export default {
		name: 'Register',
		components: {
			Footer
		},
		setup() {
			const router = useRouter();
			const user = ref({
				userId: '',
				password: '',
				userName: '',
				userSex: 1
			});
			const confirmPassword = ref('');

			// 检查是否手机号已注册
			const checkUserId = () => {
				let url1 =`UserController/getUserById/${user.value.userId}`;
				axios.get(url1).then(response => {
					if (response.data.result == 1) {
						user.value.userId = '';
						alert('此手机号码已存在!');
					}
				}).catch(error => {
					console.error(error);
				});
			};

			const register = () => {
				if (user.value.userId == '') {
					alert('手机号码不能为空!');
					return;
				}
				if (user.value.password == '') {
					alert('密码不能为空!');
					return;
				}
				if (user.value.password != confirmPassword.value) {
					alert('两次输入的密码不一致!');
					return;
				}
				if (user.value.userName == '') {
					alert('用户名称不能为空!');
					return;
				}
				router.push('/')
				// user.value.password = md5(user.value.password);
				// 注册请求
				let url2 =`UserController/saveUser/${user.value.userId}/${user.value.password}/${user.value.userName}/${user.value.userSex}`;
				axios.post(url2).then(response => {
					if (response.data.result > 0) {
						alert('注册成功!');
						router.push('/Login');
					} else {
						alert('注册失败!');
					}
				}).catch(error => {
					console.error(error);
				});
			};

			onMounted(() => {
				// console.log('Component is mounted');
				document.onscroll = () => {
					console.log('Scroll event triggered');
				};
			});

			return {
				user,
				confirmPassword,
				checkUserId,
				register
			};
		}
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

	.wrapper .register {
		margin: 30vw 0 2vw 12vw;
		position: relative;
	}

	.wrapper .register h2 {
		font-weight: 550;
		margin-bottom: 1vw;
	}

	.wrapper .register img {
		width: 60vw;
		opacity: 15%;
		position: absolute;
		top: 260%;
		left: 10%;
		z-index: -1;
	}

	/****************** 表单部分 ****************/
	.wrapper .form-box {
		width: 100%;
		margin-top: 9vw;
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
		flex: 0 0 24vw;
		font-size: 3.8vw;
		font-weight: 700;
		color: #666;
	}

	.wrapper .form-box li .content {
		width: 100%;
		flex: 1;
		margin: 2vw 0vw 2vw 2vw;
	}

	.wrapper .form-box li .content input {
		border: none;
		outline: none;
		width: 100%;
		height: 4vw;
		font-size: 4vw;
		background-color: transparent;
	}

	/****************** 注册按钮 ****************/
	.wrapper .button-register {
		width: 100%;
		box-sizing: border-box;
		padding: 10vw 10vw 0 10vw;
	}

	.wrapper .button-register button {
		width: 100%;
		height: 11vw;
		font-size: 5vw;
		font-weight: 550;
		color: #fff;
		background-color: #0097ff;
		border-radius: 50px;

		border: none;
		outline: none;

		position: relative;
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
