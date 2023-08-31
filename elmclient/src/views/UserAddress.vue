<template>
    <div class="wrapper">
			<!-- header -->
			<header>
				<p>地址管理</p>
			</header>
			
			<!-- 地址列表 -->
			<ul class="address">
				<li v-for="item in deliveryAddressArr" :key="item.id">
					<div class="address-info" @click="setDeliveryAddress(item)">
						<div class="address-info-detailed">
							<p>{{ item.contactName }}{{ userSex }}</p>
							<p>{{ item.contactTel }}</p>
						</div>
						<p>{{ item.address }}</p>
					</div>
					<div class="address-icon">
						<i class="fa fa-edit" @click="editUserAddress(item.daId)"></i>
						<i class="fa fa-remove"></i>
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
    //导入共通组件
    import Footer from '../components/Footer.vue';

    export default{
        name:'UserAddress',
        data(){
            return{
                businessId:this.$route.query.businessId,
                user:{},
                deliveryAddressArr:[]
            }
        },

        created(){
            this.user=this.$getSessionStorage('user');

            //查询送货地址
            this.$axios.post('DeliveryAddressController/listDeliveryAddressByUserId',this.$qs.stringify({
				userId:this.user.userId
			})).then(response=>{
				this.deliveryAddressArr=response.data;
			}).catch(error=>{
				console.error(error);
			});

        },

        components:{
            Footer
        },

        computed:{
			userSex() {
				return this.user.userSex === 1 ? '先生' : '女士';
			}
		},
        
        methods:{
            setDeliveryAddress(item){
                //把用户选择的默认送货地址存储到localStorage
                this.$setLocalStorage(this.user.userId,deliveryAddress);
                this.$router.push({path:'/orders',query:{businessId:this.businessId}});
            },

			toAddUserAddress(){
				this.$router.push({path:'/addUserAddress',query:{businessId:this.businessId}});
			},

			editUserAddress(daId){
				this.$router.push({path:'/editUserAddress',query:{businessId:this.businessId,daId:daId}});
			}
        }
    }
</script>

<style scoped>
/****************** 总容器 ****************/
.wrapper{
	width: 100%;
	height: 100%;
	background-color: #F5F4F6;
}

/****************** header ****************/
.wrapper header{
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
.wrapper .address{
	width: 100%;
	margin-top: 12vw;
	margin-bottom: 4vw;
}
.wrapper .address li{
	width: 100%;
	height: 18vw;
	background-color: #fff;
	border-bottom: solid 1px #ddd;
	
	display: flex;
	justify-content: space-between;
	align-items: center;
}
.wrapper .address li .address-info{
	width: 100%;
	margin-left: 3vw;
}
.wrapper .address li .address-info .address-info-detailed{
	display: flex;
	align-items: center;
}
.wrapper .address li .address-info .address-info-detailed p:first-child{
	font-size: 5vw;
	color: #777;
	margin-right: 2vw;
}
.wrapper .address li .address-info .address-info-detailed p:last-child{
	font-size: 5vw;
	color: #777;
}
.wrapper .address li .address-info p{
	font-size: 4vw;
	color: #777;
}
.wrapper .address li .address-icon{
	display: flex;
	margin-right: 4vw;
}
.wrapper .address li .address-icon i{
	font-size: 5vw;
	color: #777;
	margin-right: 2vw;
}
.wrapper .address-new{
	width: 100%;
	height: 15vw;
	background-color: #fff;
	border-top: solid 1px #ddd;
	border-bottom: solid 1px #ddd;
	
	display: flex;
	justify-content: center;
	align-items: center;
}
.wrapper .address-new i{
	font-size: 4vw;
	color: #0097ff;
	margin-right: 2vw;
}
.wrapper .address-new p{
	font-size: 4vw;
	color: #0097ff;
}

</style>