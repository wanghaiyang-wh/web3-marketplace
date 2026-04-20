<template>
  <div class="tickets-page">
    <div class="container">
      <div class="header">
        <h1>我的工单</h1>
        <button class="btn-create" @click="showCreateDialog = true">创建工单</button>
      </div>

      <div class="content">
        <div v-if="loading" class="loading">加载中...</div>
        <div v-else-if="list.length === 0" class="empty">
          <p>暂无工单</p>
          <button class="btn" @click="showCreateDialog = true">创建工单</button>
        </div>
        <div v-else class="ticket-list">
          <div class="ticket-item" v-for="item in list" :key="item.id" @click="viewDetail(item)">
            <div class="ticket-header">
              <span class="ticket-no">{{ item.ticketNo }}</span>
              <span :class="['status', item.status]">{{ getStatusText(item.status) }}</span>
            </div>
            <h3>{{ item.title }}</h3>
            <p class="ticket-type">类型: {{ getTypeText(item.type) }}</p>
            <span class="ticket-time">{{ formatTime(item.createTime) }}</span>
          </div>
        </div>
      </div>

      <!-- 创建工单对话框 -->
      <div v-if="showCreateDialog" class="dialog-overlay" @click="showCreateDialog = false">
        <div class="dialog" @click.stop>
          <h2>创建工单</h2>
          <form @submit.prevent="createTicket">
            <div class="form-group">
              <label>工单类型</label>
              <select v-model="newTicket.type" required>
                <option value="CONSULT">咨询</option>
                <option value="COMPLAINT">投诉</option>
                <option value="AFTER_SALES">售后</option>
              </select>
            </div>
            <div class="form-group">
              <label>标题</label>
              <input v-model="newTicket.title" type="text" required />
            </div>
            <div class="form-group">
              <label>内容</label>
              <textarea v-model="newTicket.content" rows="5" required></textarea>
            </div>
            <div class="form-actions">
              <button type="button" @click="showCreateDialog = false">取消</button>
              <button type="submit" class="btn-primary">提交</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import request from '@/api'

const loading = ref(false)
const list = ref<any[]>([])
const showCreateDialog = ref(false)
const newTicket = ref({
  type: 'CONSULT',
  title: '',
  content: ''
})

function getStatusText(status: number) {
  const map: Record<number, string> = {
    0: '待处理',
    1: '处理中',
    2: '已完成',
    3: '已关闭'
  }
  return map[status] || '未知'
}

function getTypeText(type: string) {
  const map: Record<string, string> = {
    CONSULT: '咨询',
    COMPLAINT: '投诉',
    AFTER_SALES: '售后'
  }
  return map[type] || type
}

function formatTime(time: string) {
  return new Date(time).toLocaleString('zh-CN')
}

function viewDetail(item: any) {
  // TODO: 跳转到工单详情页
  console.log('查看工单详情:', item.id)
}

async function fetchList() {
  loading.value = true
  try {
    const res = await request.get('/api/client/ticket/list', { params: { userId: 1 } })
    list.value = res || []
  } catch (error) {
    console.error('获取工单列表失败:', error)
    list.value = []
  } finally {
    loading.value = false
  }
}

async function createTicket() {
  try {
    await request.post('/api/client/ticket/create', {
      userId: 1,
      ...newTicket.value
    })
    showCreateDialog.value = false
    newTicket.value = { type: 'CONSULT', title: '', content: '' }
    await fetchList()
  } catch (error) {
    console.error('创建工单失败:', error)
  }
}

onMounted(() => {
  fetchList()
})
</script>

<style scoped>
.tickets-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 20px 0;
}

.container {
  max-width: 900px;
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

.btn-create {
  padding: 10px 20px;
  background: #667eea;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.ticket-list {
  background: white;
  border-radius: 8px;
}

.ticket-item {
  padding: 20px;
  border-bottom: 1px solid #eee;
  cursor: pointer;
}

.ticket-item:hover {
  background: #f9f9f9;
}

.ticket-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.ticket-no {
  color: #666;
  font-size: 14px;
}

.status {
  padding: 3px 8px;
  border-radius: 3px;
  font-size: 12px;
}

.status-0 { background: #fff7e6; color: #fa8c16; }
.status-1 { background: #e6f7ff; color: #1890ff; }
.status-2 { background: #f6ffed; color: #52c41a; }
.status-3 { background: #f5f5f5; color: #999; }

.ticket-item h3 {
  font-size: 16px;
  margin-bottom: 5px;
}

.ticket-type {
  color: #666;
  font-size: 14px;
}

.ticket-time {
  color: #999;
  font-size: 12px;
}

.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.dialog {
  background: white;
  padding: 30px;
  border-radius: 8px;
  width: 500px;
}

.dialog h2 {
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 500;
}

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.form-actions {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
  margin-top: 20px;
}

.form-actions button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-primary {
  background: #667eea;
  color: white;
}

.loading, .empty {
  text-align: center;
  padding: 60px;
  color: #999;
}
</style>
