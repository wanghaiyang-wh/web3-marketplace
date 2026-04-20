<template>
  <div class="settings">
    <div class="header">
      <div class="header-content">
        <div class="logo">商家中心</div>
        <nav class="nav">
          <router-link to="/">首页</router-link>
          <router-link to="/products">商品管理</router-link>
          <router-link to="/orders">订单管理</router-link>
          <router-link to="/finance">财务管理</router-link>
          <router-link to="/settings">设置</router-link>
        </nav>
      </div>
    </div>
    <main class="main">
      <h1>店铺设置</h1>
      <div class="card">
        <div class="form-group">
          <label>店铺名称</label>
          <input v-model="shopName" type="text" class="input" />
        </div>
        <div class="form-group">
          <label>店铺描述</label>
          <textarea v-model="description" class="input" rows="3"></textarea>
        </div>
        <div class="form-group">
          <label>联系方式</label>
          <input v-model="contact" type="text" class="input" />
        </div>
        <button @click="save" class="btn btn-primary">保存</button>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const shopName = ref('GameStore01')
const description = ref('专业游戏CDK和NFT交易')
const contact = ref('support@gamestore01.com')

const save = () => {
  alert('保存成功')
}
</script>

<style scoped>
.header {
  background: white;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  padding: 15px 0;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: var(--primary-color);
}

.main {
  max-width: 800px;
  margin: 0 auto;
  padding: 40px 20px;
}

h1 {
  margin-bottom: 30px;
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
}
</style>
