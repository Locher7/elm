<template>
	<div class="wrapper">
		<!-- header -->
		<header>
			<p>地址管理</p>
		</header>

		<!-- 地址列表 -->
		<ul class="address">
			<li v-for="(item,index) in deliveryAddressArr" :key="item.id">
				<div class="address-info" @click="setDeliveryAddress(item)">
					<div class="address-info-detailed">
						<p>{{ item.contactName }}{{ contactSex(index) }}</p>
						<p>{{ item.contactTel }}</p>
					</div>
					<p>{{ item.address }}</p>
				</div>
				<div class="address-icon">
					<i class="fa fa-edit" @click="editUserAddress(item.daId)"></i>
					<i class="fa fa-remove" @click="removeUserAddress(item.daId)"></i>
				</div>
			</li>
		</ul>

		<!-- 新增地址 -->
		<div class="address-new" @click="toAddUserAddress">
			<i class="fa fa-plus-circle"></i>
			<p>新增收货地址</p>
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
		name: 'UserAddress',
		components: {
			Footer
		},
		setup() {
			const route = useRoute();
			const router = useRouter();
			const businessId = ref(route.query.businessId);
			const user = ref({});
			const deliveryAddressArr = ref([])

			onMounted(() => {
				// console.log(businessId.value);
				user.value = JSON.parse(sessionStorage.getItem('user'));
				listDeliveryAddressByUserId();
			});

			const contactSex = (index) => {
				return deliveryAddressArr.value[index].contactSex === 1 ? '先生' : '女士';
			};

			// 请求所有地址信息
			const listDeliveryAddressByUserId = () => {
				axios.post('DeliveryAddressController/listDeliveryAddressByUserId', qs.stringify({
					userId: user.value.userId
				})).then(response => {
					deliveryAddressArr.value = response.data;
					// console.log('地址信息:', deliveryAddressArr.value);
				}).catch(error => {
					console.error(error);
				});
			};

			// 把选择地址放入localStorage
			const setDeliveryAddress = (deliveryAddress) => {
				localStorage.setItem(user.value.userId, JSON.stringify(deliveryAddress));
				router.push({
					path: '/orders',
					query: {
						businessId: businessId.value
					}
				});
			};

			const toAddUserAddress = () => {
				router.push({
					path: '/addUserAddress',
					query: {
						businessId: businessId.value
					}
				});
			};

			const editUserAddress = (daId) => {
				router.push({
					path: '/editUserAddress',
					query: {
						businessId: businessId.value
					}
				});
				// 把地址号放入sessionStorage
				sessionStorage.setItem('daId', daId);
			};


			const removeUserAddress = (daId) => {
				if (!confirm('确认要删除此送货地址吗?')) {
					return;
				}
				// 删除地址请求
				axios.post('DeliveryAddressController/removeDeliveryAddress', qs.stringify({
					daId: daId,
				})).then(response => {
					if (response.data > 0) {
						if (JSON.parse(localStorage.getItem(user.value.userId)).daId == daId) {
							localStorage.removeItem(user.value.userId);
						}
						listDeliveryAddressByUserId();
					} else {
						alert('删除地址失败!');
					}
				}).catch(error => {
					console.error(error);
				});
			};


			return {
				businessId,
				user,
				deliveryAddressArr,
				contactSex,
				listDeliveryAddressByUserId,
				setDeliveryAddress,
				toAddUserAddress,
				editUserAddress,
				removeUserAddress
			};
		}
	}
</script>

<style scoped>
	/****************** 总容器 ****************/
	.wrapper {
		width: 100%;
		height: 100%;
		background-color: #F5F4F6;
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

	/****************** 地址列表 ****************/
	.wrapper .address {
		width: 100%;
		margin-top: 12vw;
		margin-bottom: 4vw;
	}

	.wrapper .address li {
		width: 100%;
		height: 18vw;
		background-color: #fff;
		border-bottom: solid 1px #ddd;

		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.wrapper .address li .address-info {
		width: 100%;
		margin-left: 3vw;
	}

	.wrapper .address li .address-info .address-info-detailed {
		display: flex;
		align-items: center;
	}

	.wrapper .address li .address-info .address-info-detailed p:first-child {
		font-size: 5vw;
		color: #777;
		margin-right: 2vw;
	}

	.wrapper .address li .address-info .address-info-detailed p:last-child {
		font-size: 5vw;
		color: #777;
	}

	.wrapper .address li .address-info p {
		font-size: 4vw;
		color: #777;
	}

	.wrapper .address li .address-icon {
		display: flex;
		margin-right: 4vw;
	}

	.wrapper .address li .address-icon i {
		font-size: 5vw;
		color: #777;
		margin-right: 2vw;
	}

	.wrapper .address-new {
		width: 100%;
		height: 15vw;
		background-color: #fff;
		border-top: solid 1px #ddd;
		border-bottom: solid 1px #ddd;

		display: flex;
		justify-content: center;
		align-items: center;
	}

	.wrapper .address-new i {
		font-size: 4vw;
		color: #0097ff;
		margin-right: 2vw;
	}

	.wrapper .address-new p {
		font-size: 4vw;
		color: #0097ff;
	}
</style>
