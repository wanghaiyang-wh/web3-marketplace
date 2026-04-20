<template>
  <div class="add-product">
    <div class="header">
      <div class="header-content">
        <div class="logo">商家中心</div>
        <nav class="nav">
          <router-link to="/">首页</router-link>
          <router-link to="/products">商品管理</router-link>
        </nav>
      </div>
    </div>
    <main class="main">
      <h1>添加商品</h1>
      <div class="card form-card">
        <div class="form-group">
          <label>商品类型</label>
          <select v-model="product.type" class="input">
            <option value="NFT">NFT</option>
            <option value="GAME">Steam游戏</option>
          </select>
        </div>
        <div class="form-group">
          <label>商品名称</label>
          <input v-model="product.name" type="text" class="input" placeholder="请输入商品名称" />
        </div>
        <div class="form-group">
          <label>价格 (USDT)</label>
          <input v-model="product.price" type="number" step="0.01" class="input" placeholder="请输入价格" />
        </div>
        <div class="form-group">
          <label>商品描述</label>
          <textarea v-model="product.description" class="input textarea" rows="4" placeholder="请输入商品描述"></textarea>
        </div>
        <div class="form-group">
          <label>商品图片URL</label>
          <input v-model="product.image" type="text" class="input" placeholder="请输入图片URL" />
        </div>

        <!-- 游戏商品额外字段 -->
        <template v-if="product.type === 'GAME'">
          <div class="form-group">
            <label>游戏分类</label>
            <select v-model="product.category" class="input">
              <option value="action">动作</option>
              <option value="rpg">角色扮演</option>
              <option value="strategy">策略</option>
              <option value="adventure">冒险</option>
              <option value="simulation">模拟</option>
              <option value="sports">体育</option>
              <option value="other">其他</option>
            </select>
          </div>
          <div class="form-group">
            <label>CD-Key</label>
            <input v-model="product.cdKey" type="text" class="input" placeholder="请输入CD-Key" />
          </div>
        </template>

        <!-- NFT商品额外字段 -->
        <template v-if="product.type === 'NFT'">
          <div class="form-group">
            <label>区块链</label>
            <select v-model="product.chain" class="input">
              <option value="ETH">Ethereum</option>
              <option value="BSC">BSC</option>
              <option value="POLYGON">Polygon</option>
            </select>
          </div>
          <div class="form-group">
            <label>合约地址</label>
            <input v-model="product.contractAddress" type="text" class="input" placeholder="请输入NFT合约地址" />
          </div>
          <div class="form-group">
            <label>Token ID</label>
            <input v-model="product.tokenId" type="text" class="input" placeholder="请输入Token ID" />
          </div>
        </template>

        <div class="form-group">
          <label>状态</label>
          <select v-model="product.status" class="input">
            <option :value="0">下架</option>
            <option :value="1">上架</option>
          </select>
        </div>

        <div class="actions">
          <button @click="submit" class="btn btn-primary" :disabled="loading">
            {{ loading ? '提交中...' : '提交' }}
          </button>
          <router-link to="/products" class="btn">取消</router-link>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const loading = ref(false)

const product = ref({
  type: 'NFT',
  name: '',
  price: '',
  description: '',
  image: '',
  category: 'action',
  cdKey: '',
  chain: 'ETH',
  contractAddress: '',
  tokenId: '',
  status: 1
})

const submit = async () => {
  if (!product.value.name) {
    alert('请输入商品名称')
    return
  }
  if (!product.value.price) {
    alert('请输入价格')
    return
  }

  loading.value = true
  try {
    const API_BASE = '/api/merchant/product'
    const payload: any = {
      merchantId: 1, // TODO: 从登录获取
      name: product.value.name,
      description: product.value.description,
      image: product.value.image || 'https://picsum.photos/400/300',
      price: parseFloat(product.value.price),
      stock: 1,
      status: product.value.status
    }

    if (product.value.type === 'GAME') {
      payload.category = product.value.category
      payload.cdKey = product.value.cdKey
      await fetch(`${API_BASE}/game/create`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      })
    } else {
      payload.chain = product.value.chain
      payload.contractAddress = product.value.contractAddress
      payload.tokenId = product.value.tokenId
      await fetch(`${API_BASE}/nft/create`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      })
    }

    alert('商品添加成功！')
    router.push('/products')
  } catch (e: any) {
    alert(e.message || '添加失败，请稍后重试')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.header {
  background: white;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  padding: 15px 0;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: var(--primary-color);
}

.main {
  max-width: 800px;
  margin: 0 auto;
  padding: 40px 20px;
}

h1 {
  margin-bottom: 30px;
}

.form-card {
  padding: 30px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
}

.input {
  width: 100%;
  padding: 10px 15px;
  border: 1px solid var(--border-color);
  border-radius: 6px;
  font-size: 14px;
}

.textarea {
  resize: vertical;
}

.actions {
  display: flex;
  gap: 15px;
}
</style>
