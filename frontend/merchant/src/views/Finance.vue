<template>
  <div class="finance">
    <h1>财务管理</h1>
    <div class="stats-grid">
      <div class="card">
        <h3>账户余额</h3>
        <p class="value">¥5,000.00</p>
      </div>
      <div class="card">
        <h3>待结算</h3>
        <p class="value">¥1,200.00</p>
      </div>
      <div class="card">
        <h3>今日收入</h3>
        <p class="value">¥350.00</p>
      </div>
    </div>

    <div class="card mt-4">
      <h2>收支记录</h2>
      <table class="table">
        <thead>
          <tr>
            <th>时间</th>
            <th>类型</th>
            <th>金额</th>
            <th>状态</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in transactions" :key="item.id">
            <td>{{ item.time }}</td>
            <td>{{ item.type }}</td>
            <td>{{ item.amount }}</td>
            <td>{{ item.status }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const transactions = ref([
  { id: 1, time: '2024-04-01 10:30', type: '收入', amount: '+¥50.00', status: '已完成' },
  { id: 2, time: '2024-04-01 09:20', type: '提现', amount: '-¥500.00', status: '处理中' },
  { id: 3, time: '2024-03-31 15:45', type: '收入', amount: '+¥120.00', status: '已完成' }
])
</script>

<style scoped>
.finance {
  max-width: 1200px;
  margin: 0 auto;
  padding: 30px 20px;
}

h1 {
  margin-bottom: 30px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.card {
  background: white;
  padding: 20px;
  border-radius: 8px;
}

.stats-grid h3 {
  color: #6B7280;
  font-size: 14px;
}

.stats-grid .value {
  font-size: 28px;
  font-weight: bold;
  margin-top: 10px;
}

.mt-4 {
  margin-top: 30px;
}

.mt-4 h2 {
  margin-bottom: 20px;
}

.table {
  width: 100%;
}

.table th, .table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #E5E7EB;
}
</style>
