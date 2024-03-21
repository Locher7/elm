<template>
	<div class="wrapper">
		<!-- Header -->
		<header class="header">
			<h1 class="title">个人中心</h1>
		</header>


		<section class="user-info">
			<div class="avatar" @click="toggleAvatarModal">
				<img :src="user.userImg ? user.userImg : require('../assets/userImg/yhtx01.png')">
			</div>

			<div class="user-details">
				<h2 class="username" @click="editNameShowModal = true">{{ user.userName }}</h2>
				<p class="student-id">{{ user.userId }}</p>
			</div>

			<!-- 修改用户名称弹窗 -->
			<div v-if="editNameShowModal" class="modal-overlay">
				<div class="modal-window">
					<h3>修改用户名称</h3>
					<input v-model="newUserName" type="text" placeholder="输入新的用户名称">
					<button @click="updateUsername">保存</button>
					<button @click="closeEditName">取消</button>
				</div>
			</div>


			<!-- 点击放大头像 -->
			<div v-if="showAvatarModal" class="avatar-modal-overlay" @click="toggleAvatarModal">
				<div class="avatar-modal">
					<img class="enlarged-avatar-display" :src="user.userImg ? user.userImg : require('../assets/userImg/yhtx01.png')">
					<p @click="openModal">更换头像</p>
				</div>
			</div>


			<!-- 更改头像方法 -->
			<div class="modal" v-if="editImgShowModal">
				<div class="modal-content">
					<h2>选择头像</h2>
					<div class="images-grid">
						<img v-for="(img, index) in images" :key="index" :src="img" @click="selectImage(img)" alt="user-avatar" />
					</div>
					<button @click="editImgShowModal = false">关闭</button>
				</div>
			</div>

			<!-- 昵称修改成功弹窗 -->
			<div v-if="successNameShowModal" class="modal-overlay">
				<div class="modal-content">
					<i class="fa fa-check-circle" aria-hidden="true"></i>

					<p>您的昵称已成功修改！</p>
				</div>
			</div>

			<!-- 头像修改成功弹窗 -->
			<div v-if="successImgShowModal" class="modal-overlay">
				<div class="modal-content">
					<i class="fa fa-check-circle" aria-hidden="true"></i>

					<p>您的头像已成功修改！</p>
				</div>
			</div>
		</section>

		<!-- 图标信息 -->
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

		<!-- 表单信息 -->
		<section class="instructions">
			<div class="instruction-item" @click="toEditInfo">
				<p>修改信息</p>
				<img src="../assets/more-icon.png">
			</div>
			<div class="instruction-item" @click="toggleRulesModal">
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

		<!-- 使用规则弹窗 -->
		<div v-if="showRulesModal" class="rules-modal-overlay" @click="toggleRulesModal">

			<div class="rule-modal-content" @click.stop>
				<h4>饿了么使用规则</h4>

				<p class="rule-modal-content-title">1.注册与登录</p>
				<p>&nbsp;&nbsp;&nbsp;用户需要注册并登录才能进行点餐操作。<br>
					&nbsp;&nbsp;&nbsp;请用户确保注册时提供的信息是准确和完整的。</p>

				<p class="rule-modal-content-title">2.下单与支付</p>
				<p>&nbsp;&nbsp;&nbsp;用户可以从列出的餐厅中选择想要的食物，添加到购物车，并进行下单。用户在选择食物时，请仔细确认其详细信息。若选择在线支付且支付失败，订单将被取消。</p>

				<p class="rule-modal-content-title">3.配送</p>
				<p>&nbsp;&nbsp;&nbsp;用户在下单时需确保提供的送餐地址是准确的。<br></p>


				<p class="rule-modal-content-title">4.评价与投诉</p>
				<p>&nbsp;&nbsp;&nbsp;如果用户对餐厅的食物或服务不满意，也可以通过饿了么平台进行投诉。</p>

				<p class="rule-modal-content-title">5.隐私与安全</p>
				<p>&nbsp;&nbsp;&nbsp;饿了么尊重并保护用户的隐私。<br>
					&nbsp;&nbsp;&nbsp;除非获得用户的许可，饿了么不会向第三方披露用户的个人信息。</p>

				<p class="rule-modal-content-title">6.其他</p>
				<p>&nbsp;&nbsp;&nbsp;饿了么保留在法律允许范围内更改、暂停或终止此使用规则的权利。<br>
					&nbsp;&nbsp;&nbsp;对规则的任何修改，饿了么都会在平台上公告。</p>

				<p class="rule-modal-content-bottom">&nbsp;&nbsp;&nbsp;感谢您选择饿了么，我们将不断努力为您带来更好的服务！</p>
				<button @click="toggleRulesModal">关闭</button>
			</div>
		</div>

		<button type="danger" @click="logout()">退出登录</button>

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
	import {
		removeSessionStorage,
	} from '../common.js'
	import Footer from '../components/Footer.vue';

	export default {
		name: 'MyInfo',
		components: {
			Footer
		},
		setup() {
			const user = ref(JSON.parse(sessionStorage.getItem('user')));
			const editNameShowModal = ref(false);
			const showAvatarModal = ref(false);
			const showRulesModal = ref(false);
			const successNameShowModal = ref(false);
			const editImgShowModal = ref(false);
			const successImgShowModal = ref(false);
			const newUserName = ref('');
			const router = useRouter();
			const images = ref([
				require('../assets/userImg/yhtx01.png'),
				require('../assets/userImg/yhtx02.png'),
				require('../assets/userImg/yhtx03.png'),
				require('../assets/userImg/yhtx04.png'),
				require('../assets/userImg/yhtx05.png'),
				require('../assets/userImg/yhtx06.png'),
				require('../assets/userImg/yhtx07.png'),
				require('../assets/userImg/yhtx08.png'),
				require('../assets/userImg/yhtx09.png'),
			]);


			const toMyAddress = () => {
				router.push('/myAddress');
			};

			const logout = () => {
				// 清除sessionStorage的用户信息
				removeSessionStorage('user');
				router.push('/login');
			};


			const toCredit = () => {
				router.push('/credit');
			};
			const toEditInfo = () => {
				router.push('/editInfo');
			};
			const toggleAvatarModal = () => {
				showAvatarModal.value = !showAvatarModal.value;
			};


			const closeEditName = () => {
				editNameShowModal.value = false;
				newUserName.value = '';
			}


			// 修改用户名称
			const updateUsername = () => {
				if (!newUserName.value) {
					alert('用户名称不能为空!');
					return;
				}
				let url1 =`http://localhost:10100/UserController/editUserNameByUserId/${user.value.userId}/${newUserName.value}`;
				axios.put(url1)
					.then((response) => {
						if (response.data.result == 1) {
							user.value.userName = newUserName.value;
							sessionStorage.setItem('user', JSON.stringify(user.value));
							newUserName.value = '';
							successNameShowModal.value = true;
							editNameShowModal.value = false;
							setTimeout(() => {
								successNameShowModal.value = false;
								location.reload();
							}, 1000);
						} else {
							alert('修改用户昵称失败!');
						}
					})
					.catch((error) => {
						console.error(error);
						alert('请求出错，请稍后重试!');
					});
			};


			//将头像信息转换为base64字符串
			const selectImage = (imgPath) => {
				let img = new Image();
				img.onload = () => {
					let canvas = document.createElement("canvas");
					canvas.width = img.width;
					canvas.height = img.height;
					let ctx = canvas.getContext("2d");
					ctx.drawImage(img, 0, 0);
					// 保留整个base64字符串，不需要拆分
					let base64String = canvas.toDataURL("image/png");
					// 使用整个base64字符串
					uploadImage(base64String);
					// 仍然使用原始图像路径作为显示的图像
					user.value.userImg = imgPath;
					editImgShowModal.value = false;
				};
				img.src = imgPath;
			};

			//修改用户头像
			const uploadImage = (base64String) => {
				let url2 =`http://localhost:10100/UserController/editUserImgByUserId/${user.value.userId}/${base64String}`;
				axios.put(url2)
					.then(response => {
						if (response.data.result == 1) {
							editImgShowModal.value = false;
							successImgShowModal.value = true;
							setTimeout(() => {
								successImgShowModal.value = false;
							}, 1000);
						} else {
							alert("上传失败，请稍后重试!");
						}
					})
					.catch(error => {
						console.error(error);
						// alert("请求出错，请稍后重试!");
					});
			};


			const openModal = () => {
				editImgShowModal.value = true;
			};

			const toggleRulesModal = () => {
				showRulesModal.value = !showRulesModal.value;
			};

			// 计算头像样式
			const avatarStyle = computed(() => {
				if (user.value.userImg) {
					return {
						backgroundImage: `url(${user.value.userImg})`,
						backgroundSize: 'cover'
					};
				}
				return {};
			});

			// 重新请求用户信息
			onMounted(async () => {
				if (!user.value.userId) return;
				try {
					const response = await axios.post('DeliveryAddressController/getDeliveryAddressById', qs.stringify({
						userId: user.value.userId,
					}));
					if (response.data) {
						// console.log("收到响应:", response.data);
						user.value = response.data;
						// console.log('user')
					}
				} catch (error) {
					console.error(error);
				}
			});

			return {
				user,
				editNameShowModal,
				showAvatarModal,
				showRulesModal,
				newUserName,
				successNameShowModal,
				editImgShowModal,
				successImgShowModal,
				images,
				toMyAddress,
				logout,
				toCredit,
				toEditInfo,
				toggleAvatarModal,
				updateUsername,
				selectImage,
				openModal,
				toggleRulesModal,
				uploadImage,
				avatarStyle,
				closeEditName
			}
		}
	}
</script>

<style scoped>
	/****************** 总容器 ****************/
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

	/****************** header ****************/
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

	/****************** 弹出修改用户昵称框 ****************/
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

	/****************** 放大头像框框 ****************/
	.avatar-modal {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.8);
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		z-index: 1001;
	}

	.avatar-modal p {
		margin: 50px;
		font-size: 1rem;
		color: white;
	}

	.avatar img {
		width: 80px;
		height: 80px;
		border-radius: 50%;
		background-color: #eee;
	}


	.avatar-display,
	.enlarged-avatar-display {
		border-radius: 50%;
		background-color: #eee;
	}

	.avatar-display {
		width: 80px;
		height: 80px;
	}

	.enlarged-avatar-display {
		max-width: 95%;
		max-height: 95%;
	}

	/****************** 使用说明弹窗 ****************/
	.rules-modal-overlay {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.6);
		display: flex;
		justify-content: center;
		align-items: center;
		/* 保证这个弹窗出现在其他弹窗的上面 */
		z-index: 1002;
	}



	.rule-modal-content {
		background-color: #fff;
		padding: 4vw;
		border-radius: 2.5vw;
		box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
		max-width: 85vw;
		width: 100%;
		position: relative;
		max-height: 70vh;
		overflow-y: auto;
	}



	.rule-modal-content h4 {
		font-size: 5vw;
		margin-bottom: 1.5vw;
		font-weight: 600;
		border-bottom: 2px solid #dedede;
		padding-bottom: 1vw;
		text-align: center;
		color: #333;
		font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
		border-image: linear-gradient(to right, #aaa, #333, #aaa);
		border-image-slice: 1;
	}



	.rule-modal-content p {
		font-size: 4vw;
		line-height: 5.5vw;
		color: #666;
		margin-bottom: 1.2vw;
	}


	.rule-modal-content-title {
		font-size: 4.5vw;
		color: black;
		font-weight: bold;
		margin-bottom: 1.5vw;
	}

	.rule-modal-content-bottom {
		font-size: 4.5vw;
		font-weight: 500;
		color: #444;
		margin-top: 2vw;
		padding-left: 1.5vw;
		border-top: 1px solid #dedede;
		padding-top: 1.5vw;
	}

	/****************** 弹窗 ****************/
	.modal-overlay {
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
		background-color: #fff;
		padding: 20px;
		text-align: center;
		border-radius: 8px;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
	}

	.icon {
		width: 50px;
	}

	/****************** 选择头像框框 ****************/
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
		border-radius: 12px;
		text-align: center;
		/* 添加阴影 */
		box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
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
		color: #333;
		font-size: 18px;
		font-weight: 500;
		cursor: pointer;
		box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
		transition: background-color 0.3s, transform 0.3s;
	}

	.modal-content button:hover {
		/* 鼠标悬停时颜色稍微变深 */
		background-color: #007ccd;
		/* 鼠标悬停时稍微上移 */
		transform: translateY(-3px);
	}

	.default-avatar {
		width: 30vw;
		height: 30vw;
		background-color: #D3D3D3;
		/* 使其成为一个圆形 */
		border-radius: 50%;
	}
</style>
