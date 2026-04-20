<template>
  <div class="admin-merchants">
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
      <h1>商家管理</h1>
      <div class="card">
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>店铺名称</th>
              <th>商家</th>
              <th>商品数</th>
              <th>销售额</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="m in merchants" :key="m.id">
              <td>{{ m.id }}</td>
              <td>{{ m.shopName }}</td>
              <td>{{ m.owner }}</td>
              <td>{{ m.productCount }}</td>
              <td>¥{{ m.sales }}</td>
              <td>
                <span :class="['status', m.status]">{{ m.status === 'active' ? '正常' : '冻结' }}</span>
              </td>
              <td>
                <button class="btn-link">详情</button>
                <button class="btn-link">冻结</button>
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

const merchants = ref([
  { id: 1, shopName: 'GameStore01', owner: '张三', productCount: 156, sales: '125,000', status: 'active' },
  { id: 2, shopName: 'NFTCollector', owner: '李四', productCount: 89, sales: '89,500', status: 'active' },
  { id: 3, shopName: 'DigitalGames', owner: '王五', productCount: 234, sales: '210,000', status: 'frozen' }
])
</script>

<style scoped>
.admin-merchants {
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

.status.active {
  color: #10B981;
}

.status.frozen {
  color: #EF4444;
}

.btn-link {
  background: none;
  color: #4F46E5;
  margin-right: 10px;
}
</style>
