<template>
	<div class="wrapper">
		<!-- header -->
		<header>
			<p>商家列表</p>
		</header>

		<!-- 商家列表 -->
		<ul class="business">
			<li v-for="item in businessArr" :key="item.id" @click="toBusinessInfo(item.businessId)">
				<div class="business-img">
					<img :src="item.businessImg">
					<div class="business-img-quantity" v-show="item.quantity>0">{{ item.quantity }}</div>
				</div>
				<div class="business-info">
					<h3>{{ item.businessName }}</h3>
					<p>&#165;{{ item.starPrice/100 }}起送 | &#165;{{ item.deliveryPrice/100 }}配送</p>
					<p>{{ item.businessExplain }}</p>
				</div>
			</li>
		</ul>

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
	import Footer from '../components/Footer.vue';
	import {
		useRoute
	} from 'vue-router';
	export default {
		name: 'BusinessList',
		components: {
			Footer
		},
		setup() {
			const route = useRoute(); // Initialize the useRoute hook
			const router = useRouter();
			const orderTypeId = ref(route.query.orderTypeId || null);
			const user = ref(JSON.parse(sessionStorage.getItem('user')));
			const businessArr = ref([]);


			// 请求购物车信息
			const listCart = () => {
				let url = `http://localhost:10400/CartController/listCart/${user.value.userId}`

				axios.get(url).then(response => {
					let cartArr = response.data.result;
					//遍历所有食品列表
					for (let businessItem of businessArr.value) {
						businessItem.quantity = 0;
						for (let cartItem of cartArr) {
							if (cartItem.businessId == businessItem.businessId) {
								businessItem.quantity += cartItem.quantity;
							}
						}
					}
					businessArr.value.sort();
				}).catch(error => {
					console.error(error);
				});
			};

			const toBusinessInfo = (businessId) => {
				router.push({
					path: '/businessInfo',
					query: {
						businessId: businessId
					}
				});
			}

			// 请求商家信息
			const fetchBusinessInfo = () => {
				let url = `http://localhost:10300/BusinessController/listBusinessByOrderTypeId/${orderTypeId.value}`;
				axios.get(url)
					.then(response => {
						businessArr.value = response.data.result;
						// console.log(businessArr.value);

						if (user.value !== null) {
							listCart();
						}
					})
					.catch(error => {
						console.error(error);
					});
			};

			onMounted(() => {
				fetchBusinessInfo();
				// console.log(orderTypeId.value);
			});

			return {
				orderTypeId,
				businessArr,
				user,
				toBusinessInfo,
				listCart
			}
		}
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

	/****************** 商家列表 ****************/
	.wrapper .business {
		width: 100%;
		margin-top: 12vw;
	}

	.wrapper .business::after {
		content: "";
		display: block;
		height: 14vw;
	}

	.wrapper .business li {
		width: 100%;
		box-sizing: border-box;
		padding: 2.5vw;
		border-bottom: solid 1px #ddd;
		user-select: none;
		cursor: pointer;

		display: flex;
		align-items: center;
	}

	.wrapper .business li .business-img {
		position: relative;

	}

	.wrapper .business li .business-img img {
		width: 20vw;
		height: 20vw;
	}

	.wrapper .business li .business-img .business-img-quantity {
		width: 5vw;
		height: 5vw;
		background-color: red;
		color: #fff;
		font-size: 3, 6vw;
		border-radius: 2.5vw;

		display: flex;
		justify-content: center;
		align-items: center;

		position: absolute;
		right: -1.5vw;
		top: -1.5vw;
	}

	.wrapper .business li .business-info {
		margin-left: 3vw;
	}

	.wrapper .business li .business-info h3 {
		font-size: 3.8vw;
		color: #555;
	}

	.wrapper .business li .business-info p {
		font-size: 3vw;
		color: #888;
		margin-top: 2vw;
	}
</style>
