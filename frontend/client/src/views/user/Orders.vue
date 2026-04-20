<template>
  <div class="orders">
    <AppHeader />
    <div class="container">
      <h1>我的订单</h1>
      <div class="tabs">
        <button @click="filter = ''" :class="{ active: filter === '' }" class="tab">全部</button>
        <button @click="filter = 'pending'" :class="{ active: filter === 'pending' }" class="tab">待支付</button>
        <button @click="filter = 'paid'" :class="{ active: filter === 'paid' }" class="tab">已支付</button>
        <button @click="filter = 'completed'" :class="{ active: filter === 'completed' }" class="tab">已完成</button>
      </div>
      <div class="order-list">
        <div v-if="loading" class="loading">加载中...</div>
        <div v-else-if="filteredOrders.length === 0" class="empty">暂无订单</div>
        <div v-else v-for="order in filteredOrders" :key="order.id" class="order-card">
          <div class="order-header">
            <span class="order-id">订单号: {{ order.orderNo }}</span>
            <div class="order-header-right">
              <span :class="['status', getStatusClass(order.status)]">{{ getStatusText(order.status) }}</span>
              <button
                v-if="order.status === 0"
                class="btn-cancel"
                @click="cancelOrder(order.id)"
                :disabled="cancelling === order.id"
              >
                {{ cancelling === order.id ? '取消中...' : '取消订单' }}
              </button>
            </div>
          </div>
          <div class="order-content">
            <div class="order-img-wrap">
              <img :src="getOrderImage(order)" :alt="order.name" class="order-image" />
              <RarityBadge :level="getRarityLevel(order.id)" class="rarity-badge-pos" />
            </div>
            <div class="order-info">
              <h3>{{ order.productName || '商品' }}</h3>
              <p class="order-type">{{ order.productType === 'NFT' ? 'NFT' : 'Steam游戏' }}</p>
            </div>
            <div class="order-price">
              <span class="price">{{ order.amount }} USDT</span>
              <span class="time">{{ formatTime(order.createTime) }}</span>
              <span v-if="order.status === 0" class="countdown">{{ getCountdown(order.createTime) }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useUserStore } from '@/stores/user'
import request from '@/api'
import AppHeader from '@/components/AppHeader.vue'
import RarityBadge from '@/components/RarityBadge.vue'
import { getGameImage, getNftImage, getRarityLevel } from '@/utils/images'

const userStore = useUserStore()
const filter = ref('')
const loading = ref(false)
const cancelling = ref<number | null>(null)
const currentTime = ref(Date.now())
let timeTimer: number | null = null

const orders = ref<any[]>([])

const getOrderImage = (order: any) => {
  return order.productType === 'NFT' ? getNftImage(order.id) : getGameImage(order.id)
}

const fetchOrders = async () => {
  if (!userStore.userInfo?.id) {
    return
  }
  loading.value = true
  try {
    const res = await request.get('/api/client/order/list', {
      params: {
        userId: userStore.userInfo.id,
        current: 1,
        size: 100
      }
    })
    orders.value = res.records || []
  } catch (e) {
    console.error('获取订单失败', e)
  } finally {
    loading.value = false
  }
}

const cancelOrder = async (orderId: number) => {
  if (!confirm('确定要取消此订单吗？')) {
    return
  }

  cancelling.value = orderId
  try {
    await request.post(`/api/client/order/cancel/${orderId}`)
    alert('订单已取消')
    fetchOrders()
  } catch (e: any) {
    alert(e.message || '取消订单失败')
  } finally {
    cancelling.value = null
  }
}

const filteredOrders = computed(() => {
  if (!filter.value) return orders.value
  const statusMap: Record<string, number> = {
    pending: 0,
    paid: 1,
    completed: 2
  }
  const status = statusMap[filter.value]
  if (status === undefined) return orders.value
  return orders.value.filter(o => o.status === status)
})

const getStatusText = (status: number) => {
  const map: Record<number, string> = {
    0: '待支付',
    1: '已支付',
    2: '已完成',
    3: '已取消'
  }
  return map[status] || '未知'
}

const getStatusClass = (status: number) => {
  const map: Record<number, string> = {
    0: 'pending',
    1: 'paid',
    2: 'completed',
    3: 'cancelled'
  }
  return map[status] || 'pending'
}

const formatTime = (time: string) => {
  if (!time) return ''
  return time.replace('T', ' ').substring(0, 19)
}

const getCountdown = (createTime: string) => {
  if (!createTime) return ''
  const create = new Date(createTime).getTime()
  const expire = create + 30 * 60 * 1000 // 30分钟
  const remaining = expire - currentTime.value

  if (remaining <= 0) {
    // 自动取消超时订单
    fetchOrders()
    return '已超时'
  }

  const minutes = Math.floor(remaining / 60000)
  const seconds = Math.floor((remaining % 60000) / 1000)
  return `剩余支付: ${minutes}:${seconds.toString().padStart(2, '0')}`
}

onMounted(() => {
  fetchOrders()
  // 每秒更新倒计时
  timeTimer = window.setInterval(() => {
    currentTime.value = Date.now()
  }, 1000)
})

onUnmounted(() => {
  if (timeTimer) {
    clearInterval(timeTimer)
  }
})
</script>

<style scoped>
.orders {
  min-height: 100vh;
  background: #2D2F3E;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 30px 20px;
}

h1 {
  color: #fff;
  margin-bottom: 30px;
}

.tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.tab {
  padding: 10px 20px;
  background: #363849;
  border: 1px solid #4a4d5e;
  border-radius: 6px;
  color: #aaa;
  cursor: pointer;
  transition: all 0.2s;
}

.tab:hover {
  border-color: #f6c243;
  color: #f6c243;
}

.tab.active {
  background: #f6c243;
  color: #2D2F3E;
  border-color: #f6c243;
}

.order-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.loading, .empty {
  text-align: center;
  color: #888;
  padding: 40px;
}

.order-card {
  background: #363849;
  border-radius: 8px;
  padding: 20px;
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #4a4d5e;
}

.order-header-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.order-id {
  color: #888;
  font-size: 14px;
}

.status {
  padding: 4px 12px;
  border-radius: 4px;
  font-size: 12px;
}

.status.pending {
  background: #FEF3C7;
  color: #92400E;
}

.status.paid {
  background: #DBEAFE;
  color: #1E40AF;
}

.status.completed {
  background: #D1FAE5;
  color: #065F46;
}

.status.cancelled {
  background: #FEE2E2;
  color: #991B1B;
}

.btn-cancel {
  padding: 6px 12px;
  background: transparent;
  border: 1px solid #e74c3c;
  border-radius: 4px;
  color: #e74c3c;
  font-size: 12px;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-cancel:hover {
  background: #e74c3c;
  color: #fff;
}

.btn-cancel:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.order-content {
  display: flex;
  align-items: center;
  gap: 20px;
}

.order-img-wrap {
  position: relative;
  width: 80px;
  height: 80px;
  flex-shrink: 0;
}

.order-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 8px;
  background: #2D2F3E;
}

.rarity-badge-pos {
  position: absolute;
  top: -4px;
  left: -4px;
  transform: scale(0.8);
}

.order-info {
  flex: 1;
}

.order-info h3 {
  color: #fff;
  margin-bottom: 5px;
}

.order-type {
  color: #888;
  font-size: 14px;
}

.order-price {
  text-align: right;
}

.price {
  display: block;
  font-size: 18px;
  font-weight: bold;
  color: #f6c243;
}

.time {
  color: #888;
  font-size: 14px;
  display: block;
  margin-top: 5px;
}

.countdown {
  display: block;
  color: #e74c3c;
  font-size: 12px;
  margin-top: 5px;
}
</style>
