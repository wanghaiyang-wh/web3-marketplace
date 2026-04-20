<template>
  <div class="admin-system">
    <aside class="sidebar">
      <div class="logo">管理后台</div>
      <nav class="nav">
        <router-link to="/">数据概览</router-link>
        <router-link to="/users">用户管理</router-link>
        <router-link to="/merchants">商家管理</router-link>
        <router-link to="/products">商品审核</router-link>
        <router-link to="/orders">订单管理</router-link>
        <router-link to="/system">系统设置</router-link>
      </nav>
    </aside>
    <main class="main">
      <h1>系统设置</h1>
      <div class="card">
        <h2>基本设置</h2>
        <div class="form-group">
          <label>平台名称</label>
          <input v-model="settings.platformName" type="text" class="input" />
        </div>
        <div class="form-group">
          <label>客服邮箱</label>
          <input v-model="settings.supportEmail" type="email" class="input" />
        </div>
      </div>

      <div class="card mt-4">
        <h2>交易设置</h2>
        <div class="form-group">
          <label>交易手续费 (%)</label>
          <input v-model="settings.fee" type="number" step="0.1" class="input" />
        </div>
        <div class="form-group">
          <label>最低提现金额</label>
          <input v-model="settings.minWithdraw" type="number" class="input" />
        </div>
      </div>

      <div class="card mt-4">
        <h2>区块链设置</h2>
        <div class="form-group">
          <label>默认链</label>
          <select v-model="settings.defaultChain" class="input">
            <option value="eth">Ethereum</option>
            <option value="bsc">BSC</option>
            <option value="polygon">Polygon</option>
          </select>
        </div>
        <div class="form-group">
          <label>NFT合约地址</label>
          <input v-model="settings.nftContract" type="text" class="input" />
        </div>
      </div>

      <button @click="save" class="btn btn-primary mt-4">保存设置</button>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const settings = ref({
  platformName: '零壹Web3游戏资产交易平台',
  supportEmail: 'support@01web3.com',
  fee: 2.5,
  minWithdraw: 10,
  defaultChain: 'eth',
  nftContract: ''
})

const save = () => {
  alert('设置已保存')
}
</script>

<style scoped>
.admin-system {
  display: flex;
  min-height: 100vh;
}

.sidebar {
  width: 240px;
  background: #1F2937;
  color: white;
  padding: 20px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  padding: 0 0 30px 0;
}

.nav {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.nav a {
  color: #9CA3AF;
  padding: 10px 15px;
  border-radius: 6px;
}

.nav a:hover, .nav a.router-link-active {
  background: #374151;
  color: white;
}

.main {
  flex: 1;
  padding: 30px;
}

h1 {
  margin-bottom: 30px;
}

h2 {
  margin-bottom: 20px;
  font-size: 18px;
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
  border: 1px solid #E5E7EB;
  border-radius: 6px;
}

.mt-4 {
  margin-top: 30px;
}
</style>
