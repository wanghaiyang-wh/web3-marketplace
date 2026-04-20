<template>
  <div class="dashboard">
    <header class="header">
      <div class="header-content">
        <div class="logo">商家中心</div>
        <nav class="nav">
          <router-link to="/">首页</router-link>
          <router-link to="/products">商品管理</router-link>
          <router-link to="/orders">订单管理</router-link>
          <router-link to="/finance">财务管理</router-link>
        </nav>
        <div class="user-info">
          <span>商家: GameStore01</span>
        </div>
      </div>
    </header>
    <main class="main">
      <h1>数据概览</h1>
      <div class="stats-grid">
        <div class="card stat-card">
          <h3>今日订单</h3>
          <p class="value">25</p>
        </div>
        <div class="card stat-card">
          <h3>今日销售额</h3>
          <p class="value">¥1,250</p>
        </div>
        <div class="card stat-card">
          <h3>在售商品</h3>
          <p class="value">156</p>
        </div>
        <div class="card stat-card">
          <h3>待处理</h3>
          <p class="value">8</p>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
// Dashboard
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

.nav {
  display: flex;
  gap: 30px;
}

.nav a {
  color: var(--text-color);
}

.nav a:hover, .nav a.router-link-active {
  color: var(--primary-color);
}

.main {
  max-width: 1200px;
  margin: 0 auto;
  padding: 40px 20px;
}

h1 {
  margin-bottom: 30px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.stat-card h3 {
  color: var(--text-secondary);
  font-size: 14px;
  margin-bottom: 10px;
}

.stat-card .value {
  font-size: 32px;
  font-weight: bold;
}
</style>
