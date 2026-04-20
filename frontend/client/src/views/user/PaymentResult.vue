<template>
  <div class="result-page">
    <div class="container">
      <div class="result-card">
        <div class="result-icon" :class="status">
          {{ status === 'success' ? '✓' : '✕' }}
        </div>
        <h1>{{ status === 'success' ? '支付成功' : '支付失败' }}</h1>
        <p class="amount">¥{{ total }}</p>

        <div class="order-info" v-if="orderIds">
          <p>订单号: {{ orderIds }}</p>
        </div>

        <div class="actions">
          <router-link to="/orders" class="btn">查看订单</router-link>
          <router-link to="/" class="btn btn-primary">返回首页</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const status = ref('success')
const total = ref('0')
const orderIds = ref('')

onMounted(() => {
  total.value = route.query.total as string || '0'
  orderIds.value = route.query.orderIds as string || ''

  // 根据订单状态显示结果（这里简单处理）
  // 实际应该查询订单状态
})
</script>

<style scoped>
.result-page {
  min-height: 100vh;
  background: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
}

.container {
  width: 100%;
  max-width: 500px;
  padding: 20px;
}

.result-card {
  background: white;
  border-radius: 12px;
  padding: 40px;
  text-align: center;
}

.result-icon {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 40px;
  margin: 0 auto 20px;
}

.result-icon.success {
  background: #f6ffed;
  color: #52c41a;
}

.result-icon.error {
  background: #fff1f0;
  color: #ff4d4f;
}

h1 {
  font-size: 24px;
  margin-bottom: 10px;
}

.amount {
  font-size: 36px;
  font-weight: bold;
  color: #ff4d4f;
  margin-bottom: 20px;
}

.order-info {
  padding: 15px;
  background: #f9f9f9;
  border-radius: 8px;
  margin-bottom: 20px;
}

.order-info p {
  color: #666;
  font-size: 14px;
}

.actions {
  display: flex;
  gap: 15px;
  justify-content: center;
}

.btn {
  padding: 12px 30px;
  border: 1px solid #ddd;
  border-radius: 4px;
  text-decoration: none;
  color: #666;
}

.btn-primary {
  background: #667eea;
  color: white;
  border: none;
}
</style>
