<template>
	<div class="wrapper">
		<!-- header -->
		<header>
			<p>用户注册</p>
		</header>
		<img src="../assets/brand.png">
		<!-- 表单部分 -->
		<ul class="form-box">
			<li>
				<div class="title">
					手机号码:
				</div>
				<div class="content">
					<input type="text" @blur="checkUserId" v-model="user.userId" placeholder="请输入手机号码">
				</div>
			</li>
			<li>
				<div class="title">
					密码:
				</div>
				<div class="content">
					<input type="password" v-model="user.password" placeholder="请输入密码">
				</div>
			</li>
			<li>
				<div class="title">
					确认密码:
				</div>
				<div class="content">
					<input type="password" v-model="confirmPassword" placeholder="请确认密码">
				</div>
			</li>
			<li>
				<div class="title">
					用户名称:
				</div>
				<div class="content">
					<input type="password" v-model="user.userName" placeholder="请输入用户名称">
				</div>
			</li>
			<li>
				<div class="title">性别:</div>
				<div class="content" style="font-size: 3vw;">
					<input type="radio" v-model="user.userSex" value="1" style="width: 6vw;height: 3.2vw;">男
					<input type="radio" v-model="user.userSex" value="0" style="width: 6vw;height: 3.2vw;">女
				</div>
			</li>
		</ul>

		<div class="button-register">
			<button @click="register">注册</button>
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
			return {
				user:{
					userId:'',
					password:'',
					userName:'',
					userSex:1
				},
				confirmPassword:''
			}
		},

		components: {
			Footer
		},

		methods: {

			checkUserId(){
				this.$axios.post('UserController/getUserById',this.$qs.stringify({
					userId: this.user.userId,
				})).then(response=>{
					let user=response.data;
					if(response.data==1){
						this.user.userId='';
						alert('此手机号码已存在!')
					}
				}).catch(error=>{
					console.error(error);
				});
			},

			register(){
				if(this.user.userId==''){
					alert('手机号码不能为空!');
					return;
				}
				if(this.user.password==''){
					alert('密码不能为空!');
					return;
				}
				if(this.user.password !=this.confirmPassword){
					alert('两次输入的密码不一致!');
					return;
				}
				if(this.user.userName==''){
					alert('用户名称不能为空!');
					return;
				}

				// 创建一个包含 user 属性的对象
				const userData = {
					user: this.user,
				};
				
				//注册请求
				this.$axios.post('UserController/saveUser',this.$qs.stringify(userData)).then(response=>{
					if(response.data>0){
						alert('注册成功!');
						this.$router.go(-1);
					}else{
						alert('注册失败!');
					}
				}).catch(error=>{
					console.error(error);
				});
			},
			
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
		margin-top: 12vw;
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
		font-weight: 700;
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

	.wrapper .button-register {
		width: 100%;
		box-sizing: border-box;
		padding: 10vw 10vw 0 10vw;
	}

	.wrapper .button-register button {
		width: 100%;
		height: 13vw;
		font-size: 5vw;
		font-weight: 550;
		color: #fff;
		background-color: #0097ff;
		border-radius: 50px;

		border: none;
		outline: none;
	}
</style>
