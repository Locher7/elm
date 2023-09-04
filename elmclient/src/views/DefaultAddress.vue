<template>
	<div class="wrapper">
		<!-- header -->
		<header>
			<p>地址管理</p>
		</header>

		<!-- 地址列表 -->
		<ul class="address">
			<li v-for="(item, index) in deliveryAddressArr" :key="item.id">
				<div class="text-address">
					<div class="address-info">
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
				</div>
				<label @click="setDefaultAddress(item)">
					<input type="radio" v-model="defaultAddressId" :value="item.daId">
					默认地址
				</label>
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
	//导入共通组件
	import Footer from '../components/Footer.vue';

	export default {
		name: 'UserAddress',
		data() {
			return {
				user: {},
				deliveryAddressArr: [],
				defaultAddressId: null
			};
		},

		created() {
			this.user = this.$getSessionStorage('user');
			this.listDeliveryAddressByUserId();
			this.defaultAddressId = localStorage.getItem('defaultAddressId');
			this.defaultAddressIndex = this.deliveryAddressArr.findIndex(address => address.daId === this.defaultAddressId);
			console.log('用户信息:', this.user);
		},



		components: {
			Footer
		},

		methods: {
			contactSex(index) {
				return this.deliveryAddressArr[index].contactSex === 1 ? '先生' : '女士';
			},

			listDeliveryAddressByUserId(callback) {
				this.$axios.post('DeliveryAddressController/listDeliveryAddressByUserId', this.$qs.stringify({
					userId: this.user.userId
				})).then(response => {
					this.deliveryAddressArr = response.data;
					console.log('地址信息:', this.deliveryAddressArr);

					// 如果提供了回调函数，则执行它
					if (callback && typeof callback === 'function') {
						callback();
					}

				}).catch(error => {
					console.error(error);
				});
			},



			setDefaultAddress(item) {
				this.defaultAddressId = item.daId;
				localStorage.setItem('defaultAddressId', item.daId); // 设置选定的地址为默认地址
				this.$setLocalStorage(this.user.userId, item);
			},



			toAddUserAddress() {
				this.$router.push('/addUserAddress');
			},

			editUserAddress(daId) {
				this.$router.push('/editUserAddress');
			},

			removeUserAddress(daId) {
				if (!confirm('确认要删除此送货地址吗?')) {
					return;
				}

				this.$axios.post('DeliveryAddressController/removeDeliveryAddress', this.$qs.stringify({
					daId: daId
				})).then(response => {
					if (response.data > 0) {
						let deliveryAddress = this.$getLocalStorage(this.user.userId);
						if (deliveryAddress != null && deliveryAddress.daId == daId) {
							this.$removeLocalStorage(this.user.userId);
						}
						this.listDeliveryAddressByUserId(() => {
							// 删除地址后，检查是否还有其他地址
							if (this.deliveryAddressArr.length == 0) {
								this.$removeLocalStorage('defaultAddressId'); // 从本地存储中删除默认地址ID
								this.$removeLocalStorage(this.user.userId); // 从本地存储中删除用户的地址信息
							}
						});
						// Check for any remaining addresses after deletion
						if (this.deliveryAddressArr.length == 0) {
							this.$removeLocalStorage('defaultAddressId'); // Remove the default address ID from local storage
							this.$removeLocalStorage(this.user.userId); // Remove user's address info from local storage
						} else {
							// The previous check for default address removal remains the same
							if (this.defaultAddressId == daId) {
								this.$nextTick(() => {
									if (this.deliveryAddressArr.length > 0) {
										this.setDefaultAddress(this.deliveryAddressArr[0]);
									} else {
										this.defaultAddressId = null; // If no other addresses, set defaultAddressId to null
									}
								});
							}
						}
					} else {
						alert('删除地址失败!');
					}
				}).catch(error => {
					console.error(error);
				});
			}


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
		flex-direction: column;
		/* justify-content: space-between;
		align-items: center; */
	}

	.wrapper .address .text-address {
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

	/* 添加到现有样式中 */
	.wrapper .address li .address-info .address-info-detailed p.default-address {
		font-size: 5vw;
		color: #0097ff;
		margin-right: 2vw;
		/* 可以添加其他你想要的默认地址样式 */
	}
</style>