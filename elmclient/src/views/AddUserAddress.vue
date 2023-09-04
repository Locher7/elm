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

		<div class="button-add">
			<button @click="addUserAddress">保存</button>
		</div>

		<!-- 底部菜单 -->
		<Footer></Footer>

	</div>
</template>

<script>
	import Footer from '../components/Footer.vue';
	export default {
		name: 'AddUserAddress',
		data() {
			return {
				businessId: this.$route.query.businessId,
				user: {},
				deliveryAddress: {
					contactName: '',
					contactSex: 1,
					contactTel: '',
					address: ''
				}
			}
		},

		created() {
			this.user = this.$getSessionStorage('user');
		},

		components: {
			Footer
		},


		methods: {
			addUserAddress() {
				if (this.deliveryAddress.contactName == '') {
					alert('联系人姓名不能为空');
					return;
				}
				if (this.deliveryAddress.contactTel == '') {
					alert('联系人电话不能为空');
					return;
				}
				if (this.deliveryAddress.address == '') {
					alert('联系人地址不能为空');
					return;
				}

				this.deliveryAddress.userId = this.user.userId;
				//查询送货地址
				this.$axios.post('DeliveryAddressController/saveDeliveryAddress', this.$qs.stringify(
					this.deliveryAddress
				)).then(response => {
					if (response.data > 0) {
						this.$router.go(-1);
					} else {
						alert('新增地址失败!')
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
