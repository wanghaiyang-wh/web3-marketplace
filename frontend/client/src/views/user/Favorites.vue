<template>
  <div class="favorites-page">
    <div class="container">
      <h1>我的收藏</h1>

      <div class="tabs">
        <button
          :class="['tab', { active: currentTab === 'product' }]"
          @click="currentTab = 'product'"
        >
          商品收藏
        </button>
        <button
          :class="['tab', { active: currentTab === 'merchant' }]"
          @click="currentTab = 'merchant'"
        >
          关注店铺
        </button>
      </div>

      <div class="content">
        <div v-if="loading" class="loading">加载中...</div>
        <div v-else-if="list.length === 0" class="empty">
          <p>{{ currentTab === 'product' ? '暂无收藏商品' : '暂无关注店铺' }}</p>
          <router-link to="/" class="btn">去逛逛</router-link>
        </div>
        <div v-else class="grid">
          <div class="card" v-for="item in list" :key="item.id">
            <img :src="currentTab === 'product' ? item.productImage : item.merchantImage" />
            <div class="card-info">
              <h3>{{ currentTab === 'product' ? item.productName : item.merchantName }}</h3>
              <p v-if="currentTab === 'product'">¥{{ item.productPrice }}</p>
              <button class="btn-unfollow" @click="unfollow(item.id)">取消收藏</button>
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
const currentTab = ref('product')
const list = ref<any[]>([])

async function fetchList() {
  loading.value = true
  try {
    const url = currentTab.value === 'product'
      ? '/api/client/favorite/list'
      : '/api/client/follow/list'
    const res = await request.get(url, { params: { userId: 1 } })
    list.value = res || []
  } catch (error) {
    console.error('获取列表失败:', error)
    list.value = []
  } finally {
    loading.value = false
  }
}

async function unfollow(id: number) {
  try {
    const url = currentTab.value === 'product'
      ? `/api/client/favorite/remove/${id}`
      : `/api/client/follow/remove/${id}`
    await request.delete(url)
    list.value = list.value.filter(item => item.id !== id)
  } catch (error) {
    console.error('取消收藏失败:', error)
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
.favorites-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 20px 0;
}

.container {
  max-width: 1200px;
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

.grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.card {
  background: white;
  border-radius: 8px;
  overflow: hidden;
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

.btn-unfollow {
  margin-top: 10px;
  padding: 8px 16px;
  border: 1px solid #ff4d4f;
  background: white;
  color: #ff4d4f;
  border-radius: 4px;
  cursor: pointer;
}

.loading, .empty {
  text-align: center;
  padding: 60px;
  color: #999;
}
</style>
