<template>
	<div class="wrapper">
		<header>
			<p>修改密码</p>
		</header>
		<div class="head">
			<img src="../assets/brand.png">
		</div>

		<!-- 输入框 -->
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

		<!-- 修改成功弹窗 -->
		<div class="modal" v-if="successShowModal">
			<div class="modal-content">
				<i class="fa fa-check-circle success-icon"></i>
				<p>修改密码成功</p>
			</div>
		</div>

		<!-- 底部菜单部分 -->
		<Footer></Footer>
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
	import md5 from 'js-md5';
	import Footer from '../components/Footer.vue';
	export default {
		name: 'UpdateInfo',
		components: {
			Footer
		},
		setup() {
			const confirmPassword = ref('');
			const user = ref(JSON.parse(sessionStorage.getItem('user')));
			const newPassword = ref('');
			const router = useRouter();
			const pastPassword = ref('');
			const successShowModal = ref(false);


			// 更新密码请求
			const updateInfo = () => {
				if (pastPassword.value == '') {
					alert('旧密码不能为空!');
					return;
				}
				if (newPassword.value == '') {
					alert('密码不能为空!');
					return;
				}
				if (newPassword.value != confirmPassword.value) {
					alert('两次输入的密码不一致!');
					return;
				}
				// 修改密码
				checkOldPassword().then(isValid => {
					if (isValid) {
						// 如果旧密码正确，则更新新密码
						axios.post('UserController/editPasswordByUserId', qs.stringify({
							userId: user.value.userId,
							password: md5(newPassword.value)
						})).then((response) => {
							if (response.data == 1) {
								successShowModal.value = true;
								setTimeout(() => {
									router.push('/myInfo');
								}, 1000);

							} else {
								alert('修改密码失败!');
							}
						}).catch((error) => {
							console.error(error);
						});
					} else {
						alert('旧密码错误!');
					}
				});
			};

			// 检查旧密码是否正确
			const checkOldPassword = () => {
				//如果旧密码正确则解析为true，否则为false。
				return new Promise((resolve, reject) => {
					axios.post('UserController/getUserByIdByPass', qs.stringify({
						userId: user.value.userId,
						password: md5(pastPassword.value)
					})).then(response => {
						if (response.data == 0) {
							resolve(false); // 旧密码错误
						} else {
							resolve(true); // 旧密码正确
						}
					}).catch(error => {
						console.error(error);
						alert('验证旧密码时出错，请稍后重试!');
						reject(error);
					});
				});
			};


			return {
				confirmPassword,
				user,
				newPassword,
				pastPassword,
				successShowModal,
				updateInfo,
				checkOldPassword
			};
		}
	}
</script>

<style scoped>
	/* 总容器 */
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

	/****************** header ****************/
	.head {
		margin-top: 25vw;
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.head img {
		width: 40vw;
		height: 40vw;
	}

	/****************** 表单信息 ****************/
	.info-section {
		width: 80%;
		margin: 6vw auto;
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

	/****************** 修改密码成功弹窗 ****************/
	.modal {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.75);
		display: flex;
		justify-content: center;
		align-items: center;
		z-index: 2000;
	}

	.modal-content {
		width: 80%;
		max-width: 500px;
		padding: 30px;
		border-radius: 15px;
		background-color: #ffffff;
		text-align: center;
		box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.5);
	}

	.success-icon {
		font-size: 80px;
		color: #4CAF50;
		margin-bottom: 30px;
	}
</style>
