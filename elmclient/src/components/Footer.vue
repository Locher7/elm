<template>
	<!-- 底部菜单 -->
	<ul class="footer">
		<li @click="toIndex">
			<i class="fa fa-home"></i>
			<p>首页</p>
		</li>
		<li>
			<i class="fa fa-compass"></i>
			<p>发现</p>
		</li>
		<li @click="toOrderList">
			<i class="fa fa-file-text-o"></i>
			<p>订单</p>
		</li>
		<li @click="toMyInfo">
			<i class="fa fa-user-o"></i>
			<p>我的</p>
		</li>
	</ul>
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

	export default {
		name: 'Footer',
		setup() {
			const router = useRouter();

			const toIndex = () => {
				router.push('/index');
			}

			const toOrderList = () => {
				router.push('/orderList');
			}

			const toMyInfo = () => {
				router.push('/myInfo');
			}

			return {
				toIndex,
				toOrderList,
				toMyInfo
			}
		}
	}
</script>

<style>
	/****************** 底部菜单 ****************/
	.wrapper .footer {
		width: 100%;
		height: 14vw;
		border-top: solid 1px #ddd;
		background-color: #fff;

		position: fixed;
		left: 0;
		bottom: 0;

		display: flex;
		justify-content: space-around;
		align-items: center;
		z-index: 2;
	}

	.wrapper .footer li {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;

		color: #999;
		user-select: none;
		cursor: pointer;
	}

	.wrapper .footer li p {
		font-size: 2.8vw;

	}

	.wrapper .footer li i {
		font-size: 5vw;
	}
</style>
