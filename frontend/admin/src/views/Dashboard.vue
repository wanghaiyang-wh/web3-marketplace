<template>
  <div class="admin-dashboard">
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
      <header class="header">
        <h1>数据概览</h1>
      </header>
      <div class="stats-grid">
        <div class="card">
          <h3>总用户数</h3>
          <p class="value">12,345</p>
        </div>
        <div class="card">
          <h3>总商家数</h3>
          <p class="value">567</p>
        </div>
        <div class="card">
          <h3>今日订单</h3>
          <p class="value">128</p>
        </div>
        <div class="card">
          <h3>今日交易额</h3>
          <p class="value">¥45,678</p>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
.admin-dashboard {
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

.header {
  margin-bottom: 30px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.stats-grid h3 {
  color: #6B7280;
  font-size: 14px;
}

.stats-grid .value {
  font-size: 32px;
  font-weight: bold;
  margin-top: 10px;
}
</style>
