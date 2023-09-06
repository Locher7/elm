<template>
	<div class="update-info-wrapper">
		<header>
			<p>修改密码</p>
		</header>
		<div class="head">
			<img src="../assets/userImg/yhtx04.png">
			<!-- <p>更换头像</p> -->
		</div>
		<ul class="info-section">
			<!-- <li class="info-item">
				<div class="content">
					<i class="fa fa-user"></i>
					<input type="text" v-model="username" placeholder="请输入用户名称">
				</div>
			</li> -->

			<li class="info-item">
				<div class="content">
					<i class="fa fa-lock"></i>
					<input type="password" v-model="password" placeholder="请输入密码">
				</div>
			</li>

			<li class="info-item">
				<div class="content">
					<i class="fa fa-check-circle"></i>
					<input type="password" v-model="confirmPassword" placeholder="确认密码">
				</div>

			</li>
		</ul>

		<div class="update-button">
			<button @click="updateInfo">确认更改</button>
		</div>
	</div>
</template>


<script>
	export default {
		name: 'UpdateInfo',
		data() {
			return {
				password: '',
				confirmPassword: '',
				user:{}
			};
		},
		created(){
			this.user = this.$getSessionStorage('user');
		},
		methods: {
			updateInfo() {
				if (this.password !== this.confirmPassword) {
					alert('密码不匹配!');
					return;
				}

				// 修改密码
				this.$axios.post('UserController/editPasswordByUserId', this.$qs.stringify({
					userId: this.user.userId,
					password: this.password
				})).then((response) => {
					// 根据你的后端响应来处理结果
					if (response.data.success) {
						alert('修改密码成功!');
					} else {
						alert('修改密码失败!');
					}
				}).catch((error) => {
					console.error('请求出错:', error);
					alert('请求出错，请稍后重试!');
				});
			}


		}
	}
</script>

<style scoped>
	.update-info-wrapper {
		width: 100%;
		height: 100%;
		background-color: #fff;
		display: flex;
		flex-direction: column;
	}

	.update-info-wrapper header {
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

	.head {
		margin-top: 25vw;
		margin-bottom: -10vw;

		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.head img {
		width: 30vw;
		height: 30vw;

		margin-bottom: 5vw
	}

	.head p {
		font-size: 4.8vw;
	}

	.info-section {
		width: 80%;
		height: 40vw;
		margin: 16vw auto;
		border-radius: 8px;

		display: flex;
		flex-direction: column;
		justify-content: space-around;
	}

	.info-section li {
		box-sizing: border-box;
		margin: 2vw 2vw 0 0;
		display: flex;
		align-items: center;
		border: #f4eeee solid;
		border-width: 0px 0px 1.8px 0px;
	}

	.info-item {
		display: flex;
		align-items: center;

		padding: 10px;
		margin-bottom: 20px;
	}

	.info-item i {
		margin-right: 10px;
		font-size: 20px;
		color: #0097ff;
	}

	.info-item input {
		flex: 1;
		font-size: 16px;
		border: none;
		outline: none;
	}

	.update-button {
		text-align: center;
		margin-top: 20px;
	}

	.update-button button {
		padding: 12px 40px;
		background-color: #0097ff;
		border: none;
		border-radius: 8px;
		color: white;
		font-size: 18px;
		font-weight: 500;
		cursor: pointer;
		transition: background-color 0.3s;
	}
</style>
