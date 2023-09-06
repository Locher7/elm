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
				<h2 class="username" @click="showModal = true">{{ user.userName }}</h2>
				<p class="student-id">{{ user.userId }}</p>
			</div>

			<!-- 修改用户名称弹窗 -->
			<div v-if="showModal" class="modal-overlay">
				<div class="modal-window">
					<h3>修改用户名称</h3>
					<input v-model="newUsername" type="text" placeholder="输入新的用户名称">
					<button @click="updateUsername">保存</button>
					<button @click="showModal = false">取消</button>
				</div>
			</div>

		</section>



		<nav class="nav-section">
			<div class="nav-item" @click="toMyAddress">
				<i class="fa fa-list icon"></i> <!-- 替换成 Font Awesome 的家图标 -->
				<p>地址管理</p>
			</div>
			<div class="nav-item" @click="toCredit">
				<i class="fa fa-clock-o icon"></i> <!-- 替换成 Font Awesome 的编辑图标 -->
				<p>我的积分</p>
			</div>
		</nav>




		<!-- Instructions Section -->
		<section class="instructions">
			<div class="instruction-item" @click="toEditInfo">
				<p>修改密码</p>
				<img src="../assets/more-icon.png">
			</div>
			<div class="instruction-item" >
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
				newUsername: ''
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
			updateUsername() {
				this.user.userName = this.newUsername;
				this.showModal = false;
				// 修改密码
				this.$axios.post('UserController/editUserNameByUserId', this.$qs.stringify({
					userId: this.user.userId,
					userName: this.newUsername
				})).then((response) => {
					// 根据你的后端响应来处理结果
					if (response.data.success) {
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
		}
	}
</script>

<style scoped>
	/* 通用样式 */
	* {
		box-sizing: border-box;
	}

	.wrapper {
		height: 100%;
		width: 100%;
		display: flex;
		flex-direction: column;
		background: #f5f5f7;
		/* 更加淡雅的背景 */
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

	.avatar img {
		height: 80px;
		width: 80px;
		border-radius: 50%;
		margin-right: 20px;
	}

	.user-details {
		color: #ffffff;
		/* 白色文字 */
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
		/* 指示这是可点击的 */
	}

	.instruction-item img {
		height: 2vh;
		width: 2.5vw;
		margin-top: 2px;
	}

	.instruction-item:last-child {
		border-bottom: none;
		/* 去除最后一个元素的底部边框 */
	}

	.wrapper button {
		height: 45px;
		width: calc(100% - 40px);
		margin: 20px 20px 0 20px;
		font-size: 1rem;
		color: #fff;
		background-color: #e57373;
		/* 突出的红色按钮 */
		border-radius: 25px;
		border: none;
		cursor: pointer;
		transition: background-color 0.3s;
	}

	.wrapper button:hover {
		background-color: #ef5350;
		/* 悬浮时颜色 */
	}


	/* 弹出修改用户昵称框 */
	.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.6);  /* 半透明背景 */
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;  /* 保证在其他元素之上 */
}

.modal-window {
    width: 80%;
    max-width: 400px;
    padding: 30px;
    background-color: #fff;
    border-radius: 15px;  /* 圆角 */
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);  /* 阴影效果 */
    display: flex;
    flex-direction: column;
    gap: 20px;  /* 间距 */
}

.modal-window h3 {
    color: #0097ff;  /* 饿了么的蓝色 */
    margin-bottom: 20px;
    font-size: 1.2rem;
    font-weight: 500;
}

.modal-window input {
    padding: 10px;
    font-size: 1rem;
    border: 1px solid #e4e4e4;
    border-radius: 10px;
    outline: none;  /* 去除焦点时的蓝色边框 */
}

.modal-window input:focus {
    border-color: #0097ff;  /* 焦点时的蓝色边框 */
}

.modal-window button {
    padding: 10px 20px;
    font-size: 1rem;
    color: #fff;
    background-color: #0097ff;  /* 饿了么的蓝色 */
    border: none;
    border-radius: 10px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.modal-window button:hover {
    background-color: #0076c2;  /* 深蓝色 */
}

</style>
