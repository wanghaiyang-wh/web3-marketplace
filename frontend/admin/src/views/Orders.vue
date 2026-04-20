<template>
  <div class="admin-orders">
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
      <h1>订单管理</h1>
      <div class="card">
        <table class="table">
          <thead>
            <tr>
              <th>订单号</th>
              <th>商品</th>
              <th>买家</th>
              <th>商家</th>
              <th>金额</th>
              <th>状态</th>
              <th>时间</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="o in orders" :key="o.id">
              <td>{{ o.orderNo }}</td>
              <td>{{ o.product }}</td>
              <td>{{ o.buyer }}</td>
              <td>{{ o.merchant }}</td>
              <td>{{ o.amount }} USDT</td>
              <td>
                <span :class="['status', o.status]">{{ getStatus(o.status) }}</span>
              </td>
              <td>{{ o.time }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const orders = ref([
  { id: 1, orderNo: '202404010001', product: 'CryptoPunk #001', buyer: 'User001', merchant: 'NFTCollector', amount: '2.5', status: 'completed', time: '2024-04-01 10:30' },
  { id: 2, orderNo: '202404020002', product: 'Cyberpunk 2077', buyer: 'User002', merchant: 'GameStore01', amount: '29.9', status: 'paid', time: '2024-04-02 15:20' },
  { id: 3, orderNo: '202404030003', product: 'Elden Ring', buyer: 'User003', merchant: 'GameStore01', amount: '39.9', status: 'pending', time: '2024-04-03 09:45' }
])

const getStatus = (status: string) => {
  const map: Record<string, string> = {
    pending: '待支付',
    paid: '已支付',
    completed: '已完成'
  }
  return map[status] || status
}
</script>

<style scoped>
.admin-orders {
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

.status.pending { color: #F59E0B; }
.status.paid { color: #3B82F6; }
.status.completed { color: #10B981; }
</style>
