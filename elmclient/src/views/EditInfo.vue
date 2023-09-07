<template>
	<div class="wrapper">
		<header>
			<p>修改密码</p>
		</header>
		<div class="head">
			<!-- 显示灰色块 -->
			<div v-if="!this.user.userImg || this.user.userImg === ''" class="default-avatar" @click="openModal"></div>
        <!-- 如果有图片则显示图片 -->
        <img v-else :src="this.user.userImg" @click="openModal">
        <p @click="openModal">更换头像</p>
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

		<!-- 更改头像方法 -->
		<div class="modal" v-if="showModal">
			<div class="modal-content">
				<h2>选择头像</h2>
				<div class="images-grid">
					<img v-for="(img, index) in images" :key="index" :src="img" @click="selectImage(img)" alt="user-avatar" />
				</div>
				<button @click="showModal = false">关闭</button>
			</div>
		</div>
		<!-- 底部菜单部分 -->
		<Footer></Footer>
	</div>
</template>


<script>
	import Footer from '../components/Footer.vue';
	export default {
		name: 'UpdateInfo',
		data() {
			return {
				confirmPassword: '',
				user: {},
				password:'',
				showModal: false,
				images: [
					require('../assets/userImg/yhtx01.png'),
					require('../assets/userImg/yhtx02.png'),
					require('../assets/userImg/yhtx03.png'),
					require('../assets/userImg/yhtx04.png'),
					require('../assets/userImg/yhtx05.png'),
					require('../assets/userImg/yhtx06.png'),
					require('../assets/userImg/yhtx07.png'),
					require('../assets/userImg/yhtx08.png'),
					require('../assets/userImg/yhtx09.png'),
				]
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
				if (this.password == '') {
					alert('密码不能为空!');
					return;
				}
				if (this.password != this.confirmPassword) {
					alert('两次输入的密码不一致!');
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
			},
			openModal() {
				this.showModal = true;
			},

			//修改用户头像
			selectImage(imgPath) {
				let img = new Image();
				img.onload = () => {
					let canvas = document.createElement("canvas");
					canvas.width = img.width;
					canvas.height = img.height;
					let ctx = canvas.getContext("2d");
					ctx.drawImage(img, 0, 0);
					let base64String = canvas.toDataURL("image/png").split(",")[1];

					// 调用后端API
					this.uploadImage(base64String);

					// 将图片设置为用户的头像
					this.user.userImg = imgPath;
					this.showModal = false;
				};
				img.src = imgPath;
			},

			uploadImage(base64String) {
				this.$axios.post('UserController/editUserImgByUserId', this.$qs.stringify({
					UserImg: base64String,
					userId: this.user.userId
				})).then(response => {
					if (response.data.success) {
						alert("头像上传成功!");
					} else {
						alert("上传失败，请稍后重试!");
					}
				}).catch(error => {
					console.error("请求出错:", error);
					alert("请求出错，请稍后重试!");
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
		margin-top:20px;
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

	/* 选择头像框框 */
	.modal {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.7);
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.modal-content {
		width: 80%;
		background-color: #fff;
		padding: 20px 30px;
		/* 增加水平填充以获得更好的间距 */
		border-radius: 12px;
		/* 更大的圆角 */
		text-align: center;
		box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
		/* 添加阴影 */
	}

	.images-grid {
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
	}

	.images-grid img {
		width: 30%;
		margin-top: 15px;
		cursor: pointer;
	}

	.modal-content button {
		margin-top: 20px;
		padding: 10px 40px;
		background-color: #eee;
		border: none;
		border-radius: 25px;
		/* 圆角边 */
		color: #333;
		font-size: 18px;
		font-weight: 500;
		cursor: pointer;
		box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
		/* 添加阴影 */
		transition: background-color 0.3s, transform 0.3s;
		/* 添加过渡效果 */
	}

	.modal-content button:hover {
		background-color: #007ccd;
		/* 鼠标悬停时颜色稍微变深 */
		transform: translateY(-3px);
		/* 鼠标悬停时稍微上移 */
	}
	.default-avatar {
    width: 30vw;
    height: 30vw;
    background-color: #D3D3D3;  /* 灰色 */
    border-radius: 50%;  /* 使其成为一个圆形 */
}

</style>
