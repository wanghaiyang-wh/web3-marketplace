<template>
  <div id="app">
    <!-- 顶部导航栏 -->
    <header class="header">
      <div class="header-inner">
        <router-link to="/" class="logo">商家中心</router-link>
        <nav class="nav">
          <router-link to="/products">商品管理</router-link>
          <router-link to="/orders">订单管理</router-link>
          <router-link to="/finance">财务管理</router-link>
        </nav>
        <div class="user-info">
          <span>商家</span>
        </div>
      </div>
    </header>

    <!-- 主内容区域 -->
    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script setup lang="ts">
// App entry component
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

#app {
  width: 100%;
  min-height: 100vh;
  background: #f5f5f5;
}

/* 固定顶部导航 */
.header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 60px;
  background: white;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  z-index: 100;
}

.header-inner {
  max-width: 1200px;
  margin: 0 auto;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: #4F46E5;
  text-decoration: none;
}

.nav {
  display: flex;
  gap: 30px;
  height: 100%;
}

.nav a {
  display: flex;
  align-items: center;
  color: #6B7280;
  text-decoration: none;
  font-weight: 500;
  height: 100%;
  border-bottom: 3px solid transparent;
  transition: all 0.2s;
}

.nav a:hover,
.nav a.router-link-active {
  color: #4F46E5;
  border-bottom-color: #4F46E5;
}

.user-info {
  color: #6B7280;
  font-size: 14px;
}

/* 主内容区域 */
.main-content {
  margin-top: 60px;
  min-height: calc(100vh - 60px);
}
</style>
