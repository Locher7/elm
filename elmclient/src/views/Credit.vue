<template>
	<div class="wrapper">
		<!-- header -->
		<header>
			<p>我的积分</p>
		</header>

		<!-- 总积分 -->
		<div class="credit">
			<div class="credit-icon"><i class="fa fa-gift"></i></div>
			<h2>{{ credit }}</h2>
			<p>总积分</p>
			<div class="credit-use">
				<button @click="toIndex">去使用</button>
			</div>
		</div>
		<!-- 积分明细 -->
		<h3>
			积分明细
			<span class="credit-rule" @click="showRuleModal">
				积分规则
				<i class="fa fa-question-circle"></i>
			</span>
		</h3>

		<!-- 积分明细 -->
		<ul class="credit-detailed">
			<ul class="credit-detailed">
				<li v-for="item in detailArr" :key="item.id">
					<div class="credit-detailed-left" v-if="item.points > 0">
						<p>获得积分</p>
						<p>{{ item.integrationDate }}</p>
					</div>
					<div class="credit-detailed-left" v-if="item.points < 0 && item.integrationState==1">
						<p>积分过期</p>
					</div>

					<div class="credit-detailed-left" v-if="item.points < 0 && item.integrationState==2">
						<p>使用积分</p>
						<p>{{ item.integrationDate }}</p>
					</div>
					<div class="credit-detailed-right">
						<p :style="{color: item.points > 0 ? 'green' : 'red'}"><span v-if="item.points > 0">+</span>{{ item.points }}</p>

					</div>
				</li>
			</ul>
		</ul>

		<!-- 底部菜单部分 -->
		<Footer></Footer>
	</div>

	<!-- 使用规则 -->
	<div v-if="isRuleModalVisible" class="rule-modal-overlay" @click="closeRuleModal">
		<div class="rule-modal-content" @click.stop>
			<h4>积分规则</h4>
			<p class="rule-modal-content-title">1.积分累计</p>
			<p>&nbsp;&nbsp;&nbsp;用户每完成一个订单，将获得与订单总金额相同数量的积分。例如：订单金额为100元，用户将获得100积分。<br></p>
			<p class="rule-modal-content-title">2.积分使用</p>
			<p>&nbsp;&nbsp;&nbsp;当用户进行支付时，积分可以抵扣订单金额的十分之一数量的钱。例如：若用户拥有1000积分，最多可以抵扣100元。
				若用户的积分少于订单金额，则该订单不可使用积分抵扣。
				使用积分抵扣的金额部分不会再获得积分。<br>
				积分抵扣金额按照四舍五入进行计算。例如：如果金额为24.5元，则使用25的积分，抵扣2.5元。</p>
			<p class="rule-modal-content-title">3.积分有效期</p>
			<p>&nbsp;&nbsp;&nbsp;获得的积分具有一年的有效期。例如：2023年9月1日获得的积分，将会在2024年9月1日过期。<br></p>
			<p class="rule-modal-content-title">4.积分查询</p>
			<p>&nbsp;&nbsp;&nbsp;用户可以在“我的” -> “我的积分”中查看剩余积分和积分明细。</p>
			<p class="rule-modal-content-title">5.其他</p>
			<p>&nbsp;&nbsp;&nbsp;饿了么保留在法律允许范围内更改、暂停或终止此积分规则的权利，用户需定期查看以了解最新规则。</p>
			<p class="rule-modal-content-bottom">&nbsp;&nbsp;&nbsp;感谢您选择饿了么，我们将不断努力为您带来更好的服务和更多的优惠！</p>
		</div>

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
	export default {
		name: 'Credit',
		components: {
			Footer
		},
		setup() {
			const detailArr = ref([]);
			const user = ref(JSON.parse(sessionStorage.getItem('user')));
			const isRuleModalVisible = ref(false);
			const credit = ref(0);
			const router = useRouter();

			const toIndex = () => {
				router.push('/index');
			};
			const showRuleModal = () => {
				isRuleModalVisible.value = true;
			};
			const closeRuleModal = () => {
				isRuleModalVisible.value = false;
			};

			// 请求总积分
			const getCreditByUserId = () => {
				axios.get(`/users/${user.value.userId}/integrations/credit`)
					.then(response => {
						if (response.data == '') {
							credit.value = 0;
						} else {
							credit.value = response.data;
						}
						// console.log('总积分:', credit.value);
					})
					.catch(error => {
						console.error(error);
					});
			};

			// 请求积分明细
			const getDetailsByUserId = () => {
				axios.post('IntegrationController/getDetailsByUserId', qs.stringify({
						userId: user.value.userId,
					}))
					.then(response => {
						detailArr.value = response.data;
						// console.log('积分信息:', detailArr.value);
					})
					.catch(error => {
						console.error(error);
					});
			};

			onMounted(() => {
				getCreditByUserId();
				getDetailsByUserId();
			});


			return {
				detailArr,
				user,
				isRuleModalVisible,
				credit,
				toIndex,
				showRuleModal,
				closeRuleModal
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
		overflow: hidden;
		display: flex;
		flex-direction: column;
		padding-bottom: 85vw;
	}

	/****************** header ****************/
	.wrapper header {
		width: 100%;
		height: 20vw;
		background-color: #0097ff;
		color: #fff;
		font-size: 4.8vw;
		position: fixed;
		left: 0;
		top: 0;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	/****************** 总积分 ****************/
	.wrapper .credit {
		width: 90%;
		height: 80vw;
		margin: 15vw auto 5vw;
		background-color: #fff;
		border-radius: 2vw;
		box-sizing: border-box;
		display: flex;
		flex-direction: column;
		align-items: center;
		z-index: 2;
	}

	.wrapper .credit-icon {
		margin: 15vw 10px 10px 10px;
	}

	.wrapper .credit i {
		font-size: 9vw;
		color: mediumpurple
	}

	.wrapper .credit h2 {
		font-size: 8vw;
		margin-bottom: 1vw;
	}

	.wrapper .credit p {
		font-size: 3vw;
		color: #777;
		margin-bottom: 3vw;
	}

	.wrapper .credit .credit-use {
		width: 100%;
		box-sizing: border-box;
		padding: 5vw 10vw 10vw 10vw;
	}

	.wrapper .credit .credit-use button {
		width: 100%;
		height: 13vw;
		font-size: 5vw;
		font-weight: 700;
		color: #fff;
		background-color: #0097ff;
		border-radius: 50px;
		border: none;
		outline: none;
	}

	/****************** 积分明细 ****************/
	.wrapper .credit-detailed {
		width: 100%;
		z-index: 2;
	}

	.wrapper .credit-detailed::after {
		content: "";
		display: block;
		height: 14vw;
	}

	.wrapper h3 {
		margin-left: 6vw;
		font-size: 4vw;
	}

	.wrapper .credit-detailed li {
		width: 90%;
		height: 15vw;
		background-color: #fff;
		margin: 2vw auto;
		border-radius: 2vw;

		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.wrapper .credit-detailed li .credit-detailed-left {
		margin-left: 4vw;
	}

	.wrapper .credit-detailed li .credit-detailed-left p:first-child {
		font-size: 3.8vw;
		color: #111;
	}

	.wrapper .credit-detailed li .credit-detailed-left p:last-child {
		font-size: 3vw;
		color: #777;
		margin-top: 1vw;
	}

	.wrapper .credit-detailed li .credit-detailed-right {
		margin-right: 4vw;
		margin-bottom: 5vw;
	}

	.wrapper .credit-detailed li .credit-detailed-right p {
		font-size: 3.5vw;
		color: red;
	}

	.credit-rule {
		font-size: 2.6vw;
		margin-left: 2vw;
		color: #5d5f60;
		cursor: pointer;
	}

	/****************** 积分规则弹窗 ****************/
	.credit-rule i {
		margin-left: 1vw;
		font-size: 2vw;
		color: grey;
	}

	.rule-modal-overlay {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(0, 0, 0, 0.4);
		display: flex;
		justify-content: center;
		align-items: center;
		z-index: 1000;
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
</style>
