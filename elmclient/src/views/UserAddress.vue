<template>
    
    <!-- 底部菜单部分 -->
    <Footer></Footer>
</template>

<script>
    //导入共通组件
    import Footer from '../components/Footer.vue';

    export default{
        name:'UserAddress',
        data(){
            return{
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
        }
    }
</script>

<style scoped>

</style>