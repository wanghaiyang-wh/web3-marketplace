<template>
  <div class="orders">
    <h1>订单管理</h1>
    <table class="table">
      <thead>
        <tr>
          <th>订单号</th>
          <th>商品</th>
          <th>买家</th>
          <th>金额</th>
          <th>状态</th>
          <th>时间</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="order in orders" :key="order.id">
          <td>{{ order.orderNo }}</td>
          <td>{{ order.productName }}</td>
          <td>{{ order.buyer }}</td>
          <td>{{ order.amount }} USDT</td>
          <td>
            <span :class="['status', order.status]">{{ getStatus(order.status) }}</span>
          </td>
          <td>{{ order.time }}</td>
          <td>
            <button class="btn-link">详情</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const orders = ref([
  { id: 1, orderNo: '202404010001', productName: 'CryptoPunk #001', buyer: '0x1234...', amount: '2.5', status: 'paid', time: '2024-04-01 10:30' },
  { id: 2, orderNo: '202404020002', productName: 'Cyberpunk 2077', buyer: '0x5678...', amount: '29.9', status: 'pending', time: '2024-04-02 15:20' },
  { id: 3, orderNo: '202404030003', productName: 'Elden Ring', buyer: '0xabcd...', amount: '39.9', status: 'shipped', time: '2024-04-03 09:45' }
])

const getStatus = (status: string) => {
  const map: Record<string, string> = {
    pending: '待支付',
    paid: '已支付',
    shipped: '已发货',
    completed: '已完成'
  }
  return map[status] || status
}
</script>

<style scoped>
.orders {
  max-width: 1200px;
  margin: 0 auto;
  padding: 30px 20px;
}

h1 {
  margin-bottom: 30px;
}

.table {
  width: 100%;
  background: white;
  border-radius: 8px;
  overflow: hidden;
}

.table th, .table td {
  padding: 15px;
  text-align: left;
  border-bottom: 1px solid #E5E7EB;
}

.table th {
  background: #F9FAFB;
  font-weight: 500;
}

.btn-link {
  background: none;
  color: #4F46E5;
  border: none;
  cursor: pointer;
  margin-right: 10px;
}
</style>
