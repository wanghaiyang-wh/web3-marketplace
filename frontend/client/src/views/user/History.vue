<template>
  <div class="history-page">
    <div class="container">
      <div class="header">
        <h1>浏览历史</h1>
        <button class="btn-clear" @click="clearHistory">清空历史</button>
      </div>

      <div class="content">
        <div v-if="loading" class="loading">加载中...</div>
        <div v-else-if="list.length === 0" class="empty">
          <p>暂无浏览记录</p>
          <router-link to="/" class="btn">去逛逛</router-link>
        </div>
        <div v-else class="grid">
          <div class="card" v-for="item in list" :key="item.id">
            <img :src="item.productImage" @click="goDetail(item)" />
            <div class="card-info">
              <h3>{{ item.productName }}</h3>
              <p class="price">¥{{ item.productPrice }}</p>
              <span class="time">{{ formatTime(item.createTime) }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import request from '@/api'

const router = useRouter()
const loading = ref(false)
const list = ref<any[]>([])

function formatTime(time: string) {
  return new Date(time).toLocaleString('zh-CN')
}

function goDetail(item: any) {
  const path = item.productType === 'NFT' ? `/nft/${item.productId}` : `/game/${item.productId}`
  router.push(path)
}

async function fetchHistory() {
  loading.value = true
  try {
    const res = await request.get('/api/client/history/list', { params: { userId: 1 } })
    list.value = res || []
  } catch (error) {
    console.error('获取浏览记录失败:', error)
    list.value = []
  } finally {
    loading.value = false
  }
}

async function clearHistory() {
  if (!confirm('确定要清空所有浏览记录吗?')) return
  try {
    await request.delete('/api/client/history/clear', { params: { userId: 1 } })
    list.value = []
  } catch (error) {
    console.error('清空失败:', error)
  }
}

onMounted(() => {
  fetchHistory()
})
</script>

<style scoped>
.history-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 20px 0;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

h1 {
  font-size: 28px;
}

.btn-clear {
  padding: 10px 20px;
  border: 1px solid #ff4d4f;
  background: white;
  color: #ff4d4f;
  border-radius: 4px;
  cursor: pointer;
}

.grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.card {
  background: white;
  border-radius: 8px;
  overflow: hidden;
  cursor: pointer;
}

.card img {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.card-info {
  padding: 15px;
}

.card-info h3 {
  font-size: 16px;
  margin-bottom: 5px;
}

.price {
  color: #ff4d4f;
  font-weight: bold;
}

.time {
  color: #999;
  font-size: 12px;
}

.loading, .empty {
  text-align: center;
  padding: 60px;
  color: #999;
}
</style>
