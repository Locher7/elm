<template>
    <div class="update-info-wrapper">
      <header>
        <p>修改信息</p>
      </header>
      
      <div class="info-section">
        <div class="info-item">
          <i class="fa fa-user"></i>
          <input type="text" v-model="username" placeholder="用户名称">
        </div>
        
        <div class="info-item">
          <i class="fa fa-lock"></i>
          <input type="password" v-model="password" placeholder="密码">
        </div>
  
        <div class="info-item">
          <i class="fa fa-check-circle"></i>
          <input type="password" v-model="confirmPassword" placeholder="确认密码">
        </div>
        
        <div class="update-button">
          <button @click="updateInfo">更改</button>
        </div>
      </div>
    </div>
  </template>
  
<script>
export default {
  name: 'UpdateInfo',
  data() {
    return {
      username: '',
      password: '',
      confirmPassword: ''
    };
  },
  methods: {
    updateInfo() {
      // Update logic here
      if (this.password !== this.confirmPassword) {
        alert('密码不匹配!');
        return;
      }
      // Continue with update logic...
    }
  }
}
</script>

<style scoped>
.update-info-wrapper {
  width: 100%;
  height: 100%;
  background-color: #F5F4F6;
  display: flex;
  flex-direction: column;
}

header {
  width: 100%;
  padding: 20px 0;
  background-color: #0097ff;
  color: white;
  text-align: center;
  font-size: 20px;
  font-weight: 500;
  border-bottom: 1px solid #E5E5E5;
}

.info-section {
  width: 90%;
  margin: 40px auto;
}

.info-item {
  display: flex;
  align-items: center;
  background: #FFF;
  border-radius: 8px;
  padding: 10px;
  margin-bottom: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.info-item i {
  margin-right: 10px;
  font-size: 20px;
  color: #0097ff;
}

.info-item input {
  flex: 1;
  font-size: 16px;
  border: none;
  outline: none;
}

.update-button {
  text-align: center;
  margin-top: 20px;
}

.update-button button {
  padding: 12px 40px;
  background-color: #0097ff;
  border: none;
  border-radius: 8px;
  color: white;
  font-size: 18px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.3s;
}

.update-button button:hover {
  background-color: #0075d1;
}
</style>
