<template>
  <div class="profile">
    <div class="container">
      <h1>个人中心</h1>
      <div class="profile-content">
        <div class="sidebar">
          <div class="menu">
            <router-link to="/profile" class="menu-item active">个人资料</router-link>
            <router-link to="/orders" class="menu-item">我的订单</router-link>
            <router-link to="/wallet" class="menu-item">钱包管理</router-link>
            <a href="#" class="menu-item">账户设置</a>
            <a href="#" class="menu-item">安全设置</a>
          </div>
        </div>
        <div class="main">
          <div class="card">
            <h2>基本信息</h2>
            <div class="form-group">
              <label>用户名</label>
              <input v-model="userInfo.username" type="text" class="input" />
            </div>
            <div class="form-group">
              <label>邮箱</label>
              <input v-model="userInfo.email" type="email" class="input" />
            </div>
            <div class="form-group">
              <label>手机号</label>
              <input v-model="userInfo.phone" type="tel" class="input" />
            </div>
            <button @click="saveProfile" class="btn btn-primary">保存修改</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const userInfo = ref({
  username: 'User123',
  email: 'user@example.com',
  phone: '13800138000'
})

const saveProfile = () => {
  alert('保存成功')
}
</script>

<style scoped>
.profile {
  padding: 40px 0;
}

h1 {
  margin-bottom: 30px;
}

.profile-content {
  display: grid;
  grid-template-columns: 250px 1fr;
  gap: 30px;
}

.sidebar {
  background: white;
  border-radius: 8px;
  padding: 20px;
}

.menu-item {
  display: block;
  padding: 12px 15px;
  color: var(--text-color);
  border-radius: 6px;
  margin-bottom: 5px;
}

.menu-item:hover, .menu-item.active {
  background: #F3F4F6;
  color: var(--primary-color);
}

.main h2 {
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
}

.input {
  width: 100%;
  padding: 10px 15px;
  border: 1px solid var(--border-color);
  border-radius: 6px;
  font-size: 14px;
}
</style>
