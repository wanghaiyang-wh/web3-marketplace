<template>
  <div class="offers-page">
    <div class="container">
      <h1>我的议价</h1>

      <div class="tabs">
        <button
          v-for="tab in tabs"
          :key="tab.value"
          :class="['tab', { active: currentTab === tab.value }]"
          @click="currentTab = tab.value"
        >
          {{ tab.label }}
        </button>
      </div>

      <div class="content">
        <div v-if="loading" class="loading">加载中...</div>
        <div v-else-if="list.length === 0" class="empty">
          <p>暂无议价记录</p>
        </div>
        <div v-else class="offer-list">
          <div class="offer-item" v-for="item in list" :key="item.id">
            <img :src="item.productImage" class="product-img" />
            <div class="offer-info">
              <h3>{{ item.productName }}</h3>
              <p class="merchant">店铺: {{ item.merchantName }}</p>
              <p class="original-price">原价: ¥{{ item.productPrice }}</p>
              <p class="offer-price">我的报价: ¥{{ item.price }}</p>
            </div>
            <div class="offer-status">
              <span :class="['status', item.status]">{{ getStatusText(item.status) }}</span>
              <div class="actions" v-if="item.status === 'PENDING'">
                <button class="btn-accept" @click="acceptOffer(item.id)">接受</button>
                <button class="btn-reject" @click="rejectOffer(item.id)">拒绝</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import request from '@/api'

const loading = ref(false)
const currentTab = ref('all')
const list = ref<any[]>([])

const tabs = [
  { label: '全部', value: 'all' },
  { label: '待处理', value: 'PENDING' },
  { label: '已接受', value: 'ACCEPTED' },
  { label: '已拒绝', value: 'REJECTED' }
]

function getStatusText(status: string) {
  const map: Record<string, string> = {
    PENDING: '待处理',
    ACCEPTED: '已接受',
    REJECTED: '已拒绝',
    EXPIRED: '已过期'
  }
  return map[status] || status
}

async function fetchList() {
  loading.value = true
  try {
    const res = await request.get('/api/client/offer/my', { params: { buyerId: 1 } })
    list.value = res?.records || []
  } catch (error) {
    console.error('获取议价列表失败:', error)
    list.value = []
  } finally {
    loading.value = false
  }
}

async function acceptOffer(offerId: number) {
  try {
    await request.post('/api/client/offer/respond', { offerId, status: 'ACCEPTED' })
    await fetchList()
  } catch (error) {
    console.error('接受议价失败:', error)
  }
}

async function rejectOffer(offerId: number) {
  try {
    await request.post('/api/client/offer/respond', { offerId, status: 'REJECTED' })
    await fetchList()
  } catch (error) {
    console.error('拒绝议价失败:', error)
  }
}

onMounted(() => {
  fetchList()
})

watch(currentTab, () => {
  fetchList()
})
</script>

<style scoped>
.offers-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 20px 0;
}

.container {
  max-width: 900px;
  margin: 0 auto;
  padding: 0 20px;
}

h1 {
  font-size: 28px;
  margin-bottom: 20px;
}

.tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.tab {
  padding: 10px 20px;
  border: none;
  background: white;
  border-radius: 8px;
  cursor: pointer;
}

.tab.active {
  background: #667eea;
  color: white;
}

.offer-list {
  background: white;
  border-radius: 8px;
}

.offer-item {
  display: flex;
  gap: 20px;
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.product-img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 8px;
}

.offer-info {
  flex: 1;
}

.offer-info h3 {
  font-size: 16px;
  margin-bottom: 5px;
}

.merchant {
  color: #666;
  font-size: 14px;
}

.original-price {
  color: #999;
  font-size: 14px;
}

.offer-price {
  color: #ff4d4f;
  font-weight: bold;
}

.offer-status {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 10px;
}

.status {
  padding: 5px 10px;
  border-radius: 4px;
  font-size: 14px;
}

.status.PENDING {
  background: #fff7e6;
  color: #fa8c16;
}

.status.ACCEPTED {
  background: #f6ffed;
  color: #52c41a;
}

.status.REJECTED {
  background: #fff1f0;
  color: #ff4d4f;
}

.actions {
  display: flex;
  gap: 10px;
}

.btn-accept, .btn-reject {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-accept {
  background: #52c41a;
  color: white;
}

.btn-reject {
  background: #ff4d4f;
  color: white;
}

.loading, .empty {
  text-align: center;
  padding: 60px;
  color: #999;
}
</style>
