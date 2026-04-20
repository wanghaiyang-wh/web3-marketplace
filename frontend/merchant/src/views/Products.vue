<template>
  <div class="products">
    <div class="page-header">
      <h1>商品管理</h1>
      <router-link to="/products/add" class="btn btn-primary">添加商品</router-link>
    </div>
    <div class="tabs">
      <button :class="['tab', { active: type === 'nft' }]" @click="type = 'nft'">NFT</button>
      <button :class="['tab', { active: type === 'game' }]" @click="type = 'game'">游戏</button>
    </div>
    <table class="table">
      <thead>
        <tr>
          <th>ID</th>
          <th>商品名称</th>
          <th>价格</th>
          <th>库存</th>
          <th>状态</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.id">
          <td>{{ product.id }}</td>
          <td>{{ product.name }}</td>
          <td>{{ product.price }} USDT</td>
          <td>{{ product.stock }}</td>
          <td>
            <span :class="['status', getStatusClass(product.status)]">
              {{ getStatusText(product.status) }}
            </span>
          </td>
          <td>
            <button v-if="product.status === 1" class="btn-link" @click="offshelfProduct(product)">下架</button>
            <button v-if="product.status === 2 || product.status === 3" class="btn-link btn-reshelf" @click="reshelfProduct(product)">重新上架</button>
            <button v-if="product.status === 2" class="btn-link btn-appeal" @click="showAppealDialog(product)">申诉</button>
          </td>
        </tr>
        <tr v-if="products.length === 0">
          <td colspan="6" class="empty">暂无商品</td>
        </tr>
      </tbody>
    </table>

    <!-- 申诉弹窗 -->
    <div v-if="appealDialogVisible" class="dialog-overlay" @click="appealDialogVisible = false">
      <div class="dialog" @click.stop>
        <h3>商品申诉</h3>
        <div class="dialog-content">
          <p>商品: {{ appealProduct?.name }}</p>
          <p>当前状态: {{ appealProduct ? getStatusText(appealProduct.status) : '' }}</p>
          <textarea v-model="appealReason" placeholder="请输入申诉原因..." rows="4"></textarea>
        </div>
        <div class="dialog-actions">
          <button class="btn btn-secondary" @click="appealDialogVisible = false">取消</button>
          <button class="btn btn-primary" @click="submitAppeal">提交申诉</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'

const API_BASE = '/api/merchant/product'
const type = ref('nft')
const products = ref<any[]>([])

// 申诉弹窗相关
const appealDialogVisible = ref(false)
const appealProduct = ref<any>(null)
const appealReason = ref('')

const getStatusText = (status: number) => {
  const map: Record<number, string> = {
    0: '待审核',
    1: '已上架',
    2: '审核拒绝',
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
    const productType = type.value || 'nft'
    const res = await fetch(`${API_BASE}/${productType}/list?current=1&size=100`)
    const json = await res.json()
    if (json.code === 0) {
      products.value = json.data.records || []
    }
  } catch (e) {
    console.error('获取商品列表失败', e)
  }
}

const offshelfProduct = async (p: any) => {
  if (!confirm('确定要下架该商品吗？')) return
  try {
    const res = await fetch(`${API_BASE}/${type.value}/update`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ id: p.id, status: 3 })
    })
    const json = await res.json()
    if (json.code === 0) {
      p.status = 3
      alert('已下架')
    }
  } catch (e) {
    alert('操作失败')
  }
}

const reshelfProduct = async (p: any) => {
  if (!confirm('确定要申请重新上架该商品吗？')) return
  try {
    const res = await fetch(`${API_BASE}/${type.value}/${p.id}/reshelf`, {
      method: 'POST'
    })
    const json = await res.json()
    if (json.code === 0) {
      p.status = 0 // 待审核
      alert('已提交重新上架申请，请等待平台审核')
    }
  } catch (e) {
    alert('操作失败')
  }
}

const showAppealDialog = (p: any) => {
  appealProduct.value = p
  appealReason.value = ''
  appealDialogVisible.value = true
}

const submitAppeal = async () => {
  if (!appealReason.value.trim()) {
    alert('请输入申诉原因')
    return
  }
  try {
    const res = await fetch(`${API_BASE}/appeal`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        productId: appealProduct.value.id,
        productType: type.value.toUpperCase(),
        reason: appealReason.value,
        merchantId: 6
      })
    })
    const json = await res.json()
    if (json.code === 0) {
      alert('申诉已提交，请等待平台处理')
      appealDialogVisible.value = false
    }
  } catch (e) {
    alert('提交申诉失败')
  }
}

watch(type, fetchProducts)
onMounted(fetchProducts)
</script>

<style scoped>
.products {
  max-width: 1200px;
  margin: 0 auto;
  padding: 30px 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.btn {
  padding: 10px 20px;
  border-radius: 6px;
  text-decoration: none;
  font-size: 14px;
  cursor: pointer;
}

.btn-primary {
  background: #4F46E5;
  color: white;
  border: none;
}

.filters {
  display: flex;
  gap: 15px;
  margin-bottom: 20px;
}

.select {
  padding: 8px 15px;
  border: 1px solid #E5E7EB;
  border-radius: 6px;
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

.status.pending {
  color: #F59E0B;
}

.status.approved {
  color: #10B981;
}

.status.rejected {
  color: #EF4444;
}

.status.offshelf {
  color: #6B7280;
}

.tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.tab {
  padding: 8px 16px;
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
  border: none;
  cursor: pointer;
  margin-right: 10px;
}

.empty {
  text-align: center;
  color: #9CA3AF;
  padding: 40px !important;
}

.btn-reshelf {
  color: #10B981;
}

.btn-appeal {
  color: #F59E0B;
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
  border-radius: 8px;
  padding: 20px;
  width: 400px;
  max-width: 90%;
}

.dialog h3 {
  margin-bottom: 20px;
}

.dialog-content {
  margin-bottom: 20px;
}

.dialog-content p {
  margin-bottom: 10px;
  color: #666;
}

.dialog-content textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #E5E7EB;
  border-radius: 6px;
  resize: vertical;
  font-size: 14px;
}

.dialog-actions {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
}

.btn-secondary {
  background: #E5E7EB;
  color: #666;
}
</style>
