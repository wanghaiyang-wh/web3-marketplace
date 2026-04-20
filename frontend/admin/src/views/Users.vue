<template>
  <div class="admin-users">
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
      <h1>用户管理</h1>
      <div class="card">
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>用户名</th>
              <th>邮箱</th>
              <th>钱包地址</th>
              <th>注册时间</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in users" :key="user.id">
              <td>{{ user.id }}</td>
              <td>{{ user.username }}</td>
              <td>{{ user.email }}</td>
              <td class="address">{{ user.wallet }}</td>
              <td>{{ user.registerTime }}</td>
              <td>
                <span :class="['status', user.status]">{{ user.status === 'active' ? '正常' : '禁用' }}</span>
              </td>
              <td>
                <button class="btn-link">详情</button>
                <button class="btn-link">{{ user.status === 'active' ? '禁用' : '启用' }}</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const users = ref([
  { id: 1, username: 'User001', email: 'user1@example.com', wallet: '0x1234...5678', registerTime: '2024-01-15', status: 'active' },
  { id: 2, username: 'User002', email: 'user2@example.com', wallet: '0xabcd...efgh', registerTime: '2024-02-20', status: 'active' },
  { id: 3, username: 'User003', email: 'user3@example.com', wallet: '0x9876...5432', registerTime: '2024-03-10', status: 'banned' }
])
</script>

<style scoped>
.admin-users {
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

.table {
  width: 100%;
}

.table th, .table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #E5E7EB;
}

.address {
  font-family: monospace;
  font-size: 12px;
}

.status.active {
  color: #10B981;
}

.status.banned {
  color: #EF4444;
}

.btn-link {
  background: none;
  color: #4F46E5;
  margin-right: 10px;
}
</style>
