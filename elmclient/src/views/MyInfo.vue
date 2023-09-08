<template>
	<div class="wrapper">
		<!-- Header -->
		<header class="header">
			<h1 class="title">个人中心</h1>
		</header>

		<!-- User Info Section -->
		<section class="user-info">
			<div class="avatar" @click="toggleAvatarModal">
				<div class="avatar-display" :style="avatarStyle"></div>
			</div>
			<div class="user-details">
				<h2 class="username" @click="showModal = true">{{ user.userName }}</h2>
				<p class="student-id">{{ user.userId }}</p>
			</div>

			<!-- 修改用户名称弹窗 -->
			<div v-if="showModal" class="modal-overlay">
				<div class="modal-window">
					<h3>修改用户名称</h3>
					<input v-model="userName" type="text" placeholder="输入新的用户名称">
					<button @click="updateUsername">保存</button>
					<button @click="showModal = false">取消</button>
				</div>
			</div>

			<!-- 点击放大头像 -->
			<div v-if="showAvatarModal" class="avatar-modal-overlay" @click="toggleAvatarModal">
				<div class="enlarged-avatar-display" :style="avatarStyle"></div>
			</div>



		</section>



		<nav class="nav-section">
			<div class="nav-item" @click="toMyAddress">
				<i class="fa fa-list icon"></i>
				<p>地址管理</p>
			</div>
			<div class="nav-item" @click="toCredit">
				<i class="fa fa-clock-o icon"></i>
				<p>我的积分</p>
			</div>
		</nav>


		<section class="instructions">
			<div class="instruction-item" @click="toEditInfo">
				<p>修改信息</p>
				<img src="../assets/more-icon.png">
			</div>
			<div class="instruction-item">
				<p>使用说明</p>
				<img src="../assets/more-icon.png">
			</div>
			<div class="instruction-item">
				<p>问题反馈</p>
				<img src="../assets/more-icon.png">
			</div>
			<div class="instruction-item">
				<p>检查更新</p>
				<img src="../assets/more-icon.png">
			</div>
		</section>

		<button type="danger" @click="logout()">退出登录</button>

		<!-- 底部菜单部分 -->
		<Footer></Footer>

	</div>

</template>

<script>
	import Footer from '../components/Footer.vue';
	export default {
		name: 'MyInfo',
		data() {
			return {
				user: {},
				showModal: false,
				showAvatarModal: false
			}
		},
		components: {
			Footer
		},
		methods: {
			toMyAddress() {
				this.$router.push('/myAddress');
			},
			logout() {
				// 清除sessionStorage的用户信息
				this.$removeSessionStorage('user');
				this.$router.push('/login');
			},
			toCredit() {
				this.$router.push('/credit');
			},
			toEditInfo() {
				this.$router.push('/editInfo');
			},
			toggleAvatarModal() {
				this.showAvatarModal = !this.showAvatarModal;
			},
			updateUsername() {
				this.user.userName = this.newUsername;
				this.showModal = false;
				if (this.user.userName == '') {
					alert('用户名称不能为空!');
					return;
				}

				// 修改用户名称
				this.$axios.post('UserController/editUserNameByUserId', this.$qs.stringify({
					userId: this.user.userId,
					userName: this.user.userName,
				})).then((response) => {
					// 根据你的后端响应来处理结果
					if (response.data == 1) {
						alert('修改用户昵称成功!');
					} else {
						alert('修改用户昵称失败!');
					}
				}).catch((error) => {
					console.error('请求出错:', error);
					alert('请求出错，请稍后重试!');
				});

			}
		},
		created() {
			this.user = this.$getSessionStorage('user');
			console.log('用户信息：', this.user);
		},
		computed: {
			avatarStyle() {
				if (this.user.userImg) {
					return {
						backgroundImage: `url(${this.user.userImg})`,
						backgroundSize: 'cover'
					};
				}
				return {};
			}
		}

	}
</script>

<style scoped>
	* {
		box-sizing: border-box;
	}

	.wrapper {
		height: 100%;
		width: 100%;
		display: flex;
		flex-direction: column;
		background: #f5f5f7;
		overflow: auto;
	}

	.header {
		background-color: white;
		padding: 20px;
		text-align: center;
		font-weight: bold;
		font-size: 0.9rem;
		color: #0097ff;
		font-family: SourceHanSansCN-Bold;
	}

	.user-info {
		display: flex;
		align-items: center;
		padding: 20px 20px 20px 30px;
		background-color: #0097ff;
	}

	.user-details {
		color: #ffffff;
		margin: 0 20px;
	}

	.user-details .username {
		font-size: 1.2rem;
		margin-bottom: 10px;
	}

	.nav-section {
		display: flex;
		justify-content: space-between;
		margin: 20px 20px;
		background-color: #ffffff;
		padding: 15px 0;
		border-radius: 15px;
		box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	}

	.nav-item {
		display: flex;
		flex-direction: column;
		align-items: center;
		color: #4e4e4e;
		padding: 10px;
		flex: 1;
		text-align: center;
	}


	.icon {
		font-size: 24px;
		margin-bottom: 10px;
		color: #4e4e4e;
	}


	.instructions {
		display: flex;
		flex-direction: column;
		margin: 0 20px 20px 20px;
		background-color: #ffffff;
		padding: 10px 0;
		border-radius: 15px;
		box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	}

	.instruction-item {
		margin: 0 15px;
		display: flex;
		justify-content: space-between;
		padding: 10px 20px;
		border-bottom: 1px solid #dfd8d8;
		cursor: pointer;
	}

	.instruction-item img {
		height: 2vh;
		width: 2.5vw;
		margin-top: 2px;
	}

	.instruction-item:last-child {
		border-bottom: none;
	}

	.wrapper button {
		height: 45px;
		width: calc(100% - 40px);
		margin: 20px 20px 0 20px;
		font-size: 1rem;
		color: #fff;
		background-color: #e57373;
		border-radius: 25px;
		border: none;
		cursor: pointer;
		transition: background-color 0.3s;
	}

	.wrapper button:hover {
		background-color: #ef5350;
	}


	/* 弹出修改用户昵称框 */
	.modal-overlay {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.6);
		display: flex;
		justify-content: center;
		align-items: center;
		z-index: 1000;
	}

	.modal-window {
		width: 80%;
		max-width: 400px;
		padding: 30px;
		background-color: #f9f9f9;
		border-radius: 15px;
		display: flex;
		flex-direction: column;
		gap: 20px;
		box-shadow: 0 5px 15px rgba(0, 0, 0, 0.15);
	}

	.modal-window h3 {
		color: #444;
		margin-bottom: 20px;
		font-size: 1.2rem;
		font-weight: 600;
	}

	.modal-window input {
		padding: 10px;
		font-size: 1rem;
		border: 1px solid #e4e4e4;
		border-radius: 10px;
		outline: none;
	}

	.modal-window input:focus {
		border-color: #aaa;
	}

	.modal-window button {
		padding: 10px 20px;
		font-size: 1rem;
		color: #333;
		background-color: #eee;
		border: 1px solid #ddd;
		border-radius: 10px;
		cursor: pointer;
		transition: background-color 0.3s, color 0.3s;
	}

	.modal-window button:hover {
		background-color: #ddd;
		color: #fff;
	}

	/* 放大头像框框 */
	.avatar-modal-overlay {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.8);
		display: flex;
		justify-content: center;
		align-items: center;
		z-index: 1001;
	}



	.avatar-display,
	.enlarged-avatar-display {
		border-radius: 50%;
		background-color: #eee;
		/* Default gray background */
	}

	.avatar-display {
		width: 80px;
		height: 80px;
	}

	.enlarged-avatar-display {
		max-width: 95%;
		max-height: 95%;
	}
</style>
