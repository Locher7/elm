<template>
	<div class="wrapper">
		<header>
			<p>修改密码</p>
		</header>
		<!-- <div class="head">
			<div v-if="!this.user.userImg || this.user.userImg === ''" class="default-avatar" @click="openModal"></div>
			<img v-else :src="this.user.userImg" @click="openModal">
			<p @click="openModal">更换头像</p>
		</div> -->
		<ul class="info-section">
			<li class="info-item">
				<div class="content">
					<i class="fa fa-user"></i>
					<input type="password" v-model="pastPassword" placeholder="请输入旧密码">
				</div>
			</li>

			<li class="info-item">
				<div class="content">
					<i class="fa fa-lock"></i>
					<input type="password" v-model="newPassword" placeholder="请输入新密码">
				</div>
			</li>

			<li class="info-item">
				<div class="content">
					<i class="fa fa-check-circle"></i>
					<input type="password" v-model="confirmPassword" placeholder="确认新密码">
				</div>

			</li>
		</ul>

		<div class="update-button">
			<button @click="updateInfo">确认更改</button>
		</div>


		<!-- 底部菜单部分 -->
		<Footer></Footer>
	</div>
</template>


<script>
	import md5 from 'js-md5';
	import Footer from '../components/Footer.vue';
	export default {
		name: 'UpdateInfo',
		data() {
			return {
				confirmPassword: '',
				user: {},
				newPassword: '',
				pastPassword: ''
			};
		},
		created() {
			this.user = this.$getSessionStorage('user');
		},
		components: {
			Footer
		},
		methods: {

			updateInfo() {
				if (this.pastPassword == '') {
					alert('旧密码不能为空!');
					return;
				}
				if (this.newPassword == '') {
					alert('密码不能为空!');
					return;
				}
				if (this.newPassword != this.confirmPassword) {
					alert('两次输入的密码不一致!');
					return;
				}
				// 修改密码
				this.checkOldPassword().then(isValid => {
					if (isValid) {
						// 如果旧密码正确，则更新新密码
						this.$axios.post('UserController/editPasswordByUserId', this.$qs.stringify({
							userId: this.user.userId,
							password: md5(this.newPassword)
						})).then((response) => {
							if (response.data == 1) {
								alert('修改密码成功!');
								this.$router.push('/myInfo');
							} else {
								alert('修改密码失败!');
							}
						}).catch((error) => {
							console.error('请求出错:', error);
							alert('请求出错，请稍后重试!');
						});
					} else {
						alert('旧密码错误!');
					}
				});
			},

			checkOldPassword() {
				// 这是一个示例方法。你应该用实际的API调用来验证旧密码。
				// 返回一个promise，如果旧密码正确则解析为true，否则为false。
				return new Promise((resolve, reject) => {
					this.$axios.post('UserController/getUserByIdByPass', this.$qs.stringify({
						userId: this.user.userId,
						password: md5(this.pastPassword)
					})).then(response => {
						if (response.data == 0) {
							resolve(false); // 旧密码错误
						} else {
							resolve(true); // 旧密码正确
						}
					}).catch(error => {
						console.error('验证旧密码时出错:', error);
						alert('验证旧密码时出错，请稍后重试!');
						reject(error);
					});
				});
			}

		}
	}
</script>

<style scoped>
	.wrapper {
		width: 100%;
		height: 100%;
		background-color: #fff;
		display: flex;
		flex-direction: column;
	}

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
		margin-top: 20px;
		font-size: 4.8vw;
		color: #6d6d6d;
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
		width: 10%;
	}

	.info-item input {
		flex: 1;
		font-size: 16px;
		border: none;
		outline: none;
	}

	.info-item .content {
		width: 100%;
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
