<template>
	<div class="wrapper">
		<!-- header -->
		<header>
			<p>新增收货地址</p>
		</header>

		<!-- 表单部分 -->
		<ul class="form-box">
			<li>
				<div class="title">联系人: </div>
				<div class="content">
					<input type="text" v-model="deliveryAddress.contactName" placeholder="联系人姓名">
				</div>
			</li>
			<li>
				<div class="title">性别: </div>
				<div class="content" style="font-size: 3vw;">
					<input type="radio" v-model="deliveryAddress.contactSex" value="1" style="width: 6vw;height: 3.2vw;">男
					<input type="radio" v-model="deliveryAddress.contactSex" value="0" style="width: 6vw;height: 3.2vw;">女
				</div>
			</li>
			<li>
				<div class="title">电话: </div>
				<div class="content">
					<input type="content" v-model="deliveryAddress.contactTel" placeholder="电话">
				</div>
			</li>
			<li>
				<div class="title">收货地址: </div>
				<div class="content">
					<input type="content" v-model="deliveryAddress.address" placeholder="收货地址">
				</div>
			</li>
		</ul>

		<!-- 保存按钮 -->
		<div class="button-add">
			<button @click="addUserAddress">保存</button>
		</div>

		<!-- 底部菜单 -->
		<Footer></Footer>

	</div>
</template>

<script>
	import {
		ref,
		inject,
		onMounted,
		watch,
		computed
	} from 'vue';
	import {
		useRoute
	} from 'vue-router';
	import {
		useRouter
	} from 'vue-router';
	import axios from 'axios';
	import qs from 'qs';
	import Footer from '../components/Footer.vue';


	export default {
		name: 'AddUserAddress',
		components: {
			Footer
		},
		setup() {
			const route = useRoute();
			const router = useRouter();
			const businessId = ref(route.query.businessId);
			const user = ref(JSON.parse(sessionStorage.getItem('user')));
			const deliveryAddress = ref({
				contactName: '',
				contactSex: 1,
				contactTel: '',
				address: ''
			});


			// 添加用户地址
			const addUserAddress = () => {
				if (deliveryAddress.value.contactName === '') {
					alert('联系人姓名不能为空！');
					return;
				}
				if (deliveryAddress.value.contactTel === '') {
					alert('联系人电话不能为空！');
					return;
				}
				if (deliveryAddress.value.address === '') {
					alert('联系人地址不能为空！');
					return;
				}

				deliveryAddress.value.userId = user.value.userId;


				// 新增地址请求
				axios.post('DeliveryAddressController/saveDeliveryAddress', qs.stringify(deliveryAddress.value))
					.then(response => {
						if (response.data > 0) {
							router.go(-1);
						} else {
							alert('新增地址失败！');
						}
					})
					.catch(error => {
						console.error(error);
					});
			};

			return {
				businessId,
				user,
				deliveryAddress,
				addUserAddress
			};
		}
	};
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

	/****************** 表单部分 ****************/

	.wrapper .form-box {
		width: 100%;
		margin-top: 20vw;
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

	/****************** 保存按钮 ****************/
	.wrapper .button-add {
		width: 100%;
		box-sizing: border-box;
		padding: 10vw 10vw 0 10vw;
	}

	.wrapper .button-add button {
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
