<template>
  <div class="messages-page">
    <div class="container">
      <h1>消息中心</h1>

      <div class="tabs">
        <button
          v-for="tab in tabs"
          :key="tab.value"
          :class="['tab', { active: currentTab === tab.value }]"
          @click="currentTab = tab.value"
        >
          {{ tab.label }}
          <span v-if="tab.count > 0" class="badge">{{ tab.count }}</span>
        </button>
      </div>

      <div class="message-list">
        <div v-if="loading" class="loading">加载中...</div>
        <div v-else-if="messages.length === 0" class="empty">
          <p>暂无消息</p>
        </div>
        <div v-else class="message-item" v-for="msg in messages" :key="msg.id">
          <div class="message-icon">{{ getIcon(msg.messageType) }}</div>
          <div class="message-content">
            <div class="message-header">
              <span class="message-title">{{ msg.title }}</span>
            </div>
            <p class="message-text">{{ msg.content }}</p>
            <span class="message-time">{{ formatTime(msg.createTime) }}</span>
          </div>
          <button v-if="!msg.isRead" class="mark-read" @click="markAsRead(msg.id)">标记已读</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import request from '@/api'

const loading = ref(false)
const currentTab = ref('all')
const messages = ref<any[]>([])

const tabs = computed(() => [
  { label: '全部', value: 'all', count: messages.value.length },
  { label: '系统通知', value: 'SYSTEM', count: messages.value.filter(m => m.messageType === 'SYSTEM').length },
  { label: '订单通知', value: 'ORDER', count: messages.value.filter(m => m.messageType === 'ORDER').length },
  { label: '议价通知', value: 'OFFER', count: messages.value.filter(m => m.messageType === 'OFFER').length }
])

function getIcon(type: string) {
  const icons: Record<string, string> = {
    SYSTEM: '📢',
    ORDER: '📦',
    OFFER: '💰'
  }
  return icons[type] || '📧'
}

function formatTime(time: string) {
  return new Date(time).toLocaleString('zh-CN')
}

async function fetchMessages() {
  loading.value = true
  try {
    const res = await request.get('/api/client/message/received', {
      params: { userId: 1, type: currentTab.value === 'all' ? null : currentTab.value }
    })
    messages.value = res?.records || []
  } catch (error) {
    console.error('获取消息失败:', error)
    messages.value = []
  } finally {
    loading.value = false
  }
}

async function markAsRead(messageId: number) {
  try {
    await request.post(`/api/client/message/read/${messageId}`)
    const msg = messages.value.find(m => m.id === messageId)
    if (msg) msg.isRead = 1
  } catch (error) {
    console.error('标记已读失败:', error)
  }
}

onMounted(() => {
  fetchMessages()
})
</script>

<style scoped>
.messages-page {
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
  background: white;
  padding: 15px;
  border-radius: 8px;
}

.tab {
  padding: 8px 16px;
  border: none;
  background: #f5f5f5;
  border-radius: 20px;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 5px;
}

.tab.active {
  background: #667eea;
  color: white;
}

.badge {
  background: #ff4d4f;
  color: white;
  font-size: 12px;
  padding: 2px 6px;
  border-radius: 10px;
}

.message-list {
  background: white;
  border-radius: 8px;
}

.message-item {
  display: flex;
  gap: 15px;
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.message-icon {
  font-size: 24px;
}

.message-content {
  flex: 1;
}

.message-title {
  font-weight: bold;
}

.message-text {
  color: #666;
  margin: 5px 0;
}

.message-time {
  color: #999;
  font-size: 12px;
}

.mark-read {
  padding: 5px 10px;
  border: 1px solid #667eea;
  background: white;
  color: #667eea;
  border-radius: 4px;
  cursor: pointer;
}

.loading, .empty {
  text-align: center;
  padding: 60px;
  color: #999;
}
</style>
