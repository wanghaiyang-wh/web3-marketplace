<template>
  <div class="admin-products">
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
      <h1>商品审核</h1>
      <div class="tabs">
        <button :class="['tab', { active: activeTab === 'nft' }]" @click="activeTab = 'nft'">NFT商品</button>
        <button :class="['tab', { active: activeTab === 'game' }]" @click="activeTab = 'game'">游戏商品</button>
      </div>
      <div class="filters">
        <select v-model="statusFilter" @change="fetchProducts" class="select">
          <option value="">全部状态</option>
          <option :value="0">待审核</option>
          <option :value="1">审核通过</option>
          <option :value="2">审核拒绝</option>
          <option :value="3">已下架</option>
        </select>
      </div>
      <div class="card">
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>商品名称</th>
              <th>类型</th>
              <th>价格</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="p in products" :key="p.id">
              <td>{{ p.id }}</td>
              <td>{{ p.name }}</td>
              <td>{{ activeTab === 'nft' ? (p.chain || '-') : (p.category || '-') }}</td>
              <td>{{ p.price }} USDT</td>
              <td>
                <span :class="['status', getStatusClass(p.status)]">{{ getStatusText(p.status) }}</span>
              </td>
              <td>
                <button v-if="p.status === 0" class="btn-link success" @click="approveProduct(p)">通过</button>
                <button v-if="p.status === 0" class="btn-link danger" @click="rejectProduct(p)">拒绝</button>
                <button v-if="p.status === 1" class="btn-link warning" @click="offshelfProduct(p)">下架</button>
                <button class="btn-link danger" @click="deleteProduct(p)">删除</button>
              </td>
            </tr>
            <tr v-if="products.length === 0">
              <td colspan="6" class="empty">暂无商品</td>
            </tr>
          </tbody>
        </table>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'

const API_BASE = '/api/admin/product'
const activeTab = ref('nft')
const statusFilter = ref('')
const products = ref<any[]>([])

const getStatusText = (status: number) => {
  const map: Record<number, string> = {
    0: '待审核',
    1: '已上架',
    2: '已拒绝',
    3: '已下架'
  }
  return map[status] || '未知'
}

const getStatusClass = (status: number) => {
  const map: Record<number, string> = {
    0: 'pending',
    1: 'approved',
    2: 'rejected',
    3: 'offshelf'
  }
  return map[status] || ''
}

const fetchProducts = async () => {
  try {
    const type = activeTab.value
    const status = statusFilter.value === '' ? undefined : statusFilter.value
    const res = await fetch(`${API_BASE}/${type}/list?current=1&size=100&status=${status}`)
    const json = await res.json()
    if (json.code === 0) {
      products.value = json.data.records || []
    }
  } catch (e) {
    console.error('获取商品列表失败', e)
  }
}

const approveProduct = async (p: any) => {
  try {
    const res = await fetch(`${API_BASE}/${activeTab.value}/${p.id}/approve`, { method: 'POST' })
    const json = await res.json()
    if (json.code === 0) {
      p.status = 1
      alert('审核通过')
    }
  } catch (e) {
    alert('操作失败')
  }
}

const rejectProduct = async (p: any) => {
  const reason = prompt('请输入拒绝原因:')
  if (!reason) return
  try {
    const res = await fetch(`${API_BASE}/${activeTab.value}/${p.id}/reject?reason=${encodeURIComponent(reason)}`, { method: 'POST' })
    const json = await res.json()
    if (json.code === 0) {
      p.status = 2
      alert('已拒绝')
    }
  } catch (e) {
    alert('操作失败')
  }
}

const offshelfProduct = async (p: any) => {
  if (!confirm('确定要下架该商品吗？')) return
  try {
    const res = await fetch(`${API_BASE}/${activeTab.value}/${p.id}/offshelf`, { method: 'POST' })
    const json = await res.json()
    if (json.code === 0) {
      p.status = 3
      alert('已下架')
    }
  } catch (e) {
    alert('操作失败')
  }
}

const deleteProduct = async (p: any) => {
  if (!confirm(`确定要删除商品 "${p.name}" 吗？`)) return
  try {
    const res = await fetch(`${API_BASE}/${activeTab.value}/${p.id}`, { method: 'DELETE' })
    const json = await res.json()
    if (json.code === 0) {
      products.value = products.value.filter(item => item.id !== p.id)
      alert('删除成功')
    }
  } catch (e) {
    alert('删除失败')
  }
}

watch(activeTab, fetchProducts)
onMounted(fetchProducts)
</script>

<style scoped>
.admin-products {
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
.status.approved { color: #10B981; }
.status.rejected { color: #EF4444; }
.status.offshelf { color: #6B7280; }

.filters {
  margin-bottom: 20px;
}

.select {
  padding: 8px 15px;
  border: 1px solid #E5E7EB;
  border-radius: 6px;
}

.tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.tab {
  padding: 10px 20px;
  border: none;
  background: #E5E7EB;
  border-radius: 6px;
  cursor: pointer;
}

.tab.active {
  background: #4F46E5;
  color: white;
}

.btn-link {
  background: none;
  color: #4F46E5;
  margin-right: 10px;
  border: none;
  cursor: pointer;
}

.btn-link:hover { text-decoration: underline; }
.btn-link.danger { color: #EF4444; }

.empty {
  text-align: center;
  color: #9CA3AF;
  padding: 40px !important;
}
</style>
