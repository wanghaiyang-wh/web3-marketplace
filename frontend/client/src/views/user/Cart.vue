<template>
  <div class="cart-page">
    <div class="container">
      <h1>购物车</h1>

      <div v-if="loading" class="loading">加载中...</div>

      <div v-else-if="cartList.length === 0" class="empty-cart">
        <div class="empty-icon">🛒</div>
        <p>购物车是空的</p>
        <router-link to="/" class="btn btn-primary">去逛逛</router-link>
      </div>

      <div v-else class="cart-content">
        <div class="cart-list">
          <div class="cart-item" v-for="item in cartList" :key="item.id">
            <div class="checkbox-col">
              <input
                type="checkbox"
                :checked="selectedItems.includes(item.id)"
                @change="toggleSelect(item.id)"
              />
            </div>
            <div class="product-col">
              <img :src="item.productImage || '/placeholder.png'" :alt="item.productName" />
              <div class="product-info">
                <h3>{{ item.productName }}</h3>
                <p class="merchant">店铺: {{ item.merchantName }}</p>
                <p class="type">{{ item.productType === 'NFT' ? 'NFT' : '游戏' }}</p>
              </div>
            </div>
            <div class="price-col">¥{{ item.productPrice }}</div>
            <div class="quantity-col">
              <button @click="decreaseQuantity(item)">-</button>
              <span>{{ item.quantity }}</span>
              <button @click="increaseQuantity(item)">+</button>
            </div>
            <div class="subtotal-col">¥{{ (item.productPrice * item.quantity).toFixed(2) }}</div>
            <div class="action-col">
              <button class="delete-btn" @click="deleteItem(item.id)">删除</button>
            </div>
          </div>
        </div>

        <div class="cart-summary">
          <div class="summary-row">
            <span>已选 {{ selectedItems.length }} 件商品</span>
          </div>
          <div class="summary-row total">
            <span>合计:</span>
            <span class="total-price">¥{{ totalPrice.toFixed(2) }}</span>
          </div>
          <div class="summary-actions">
            <button class="btn btn-secondary" @click="clearSelected">清空选中</button>
            <button class="btn btn-primary" @click="checkout" :disabled="selectedItems.length === 0">
              结算
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '@/stores/cart'
import { useUserStore } from '@/stores/user'
import type { CartItem } from '@/api/cart'

const router = useRouter()
const cartStore = useCartStore()
const userStore = useUserStore()

const loading = ref(false)
const selectedItems = ref<number[]>([])

const cartList = computed(() => cartStore.cartList)

const totalPrice = computed(() => {
  return cartList.value
    .filter(item => selectedItems.value.includes(item.id))
    .reduce((sum, item) => sum + item.productPrice * item.quantity, 0)
})

onMounted(async () => {
  if (!userStore.userInfo?.id) {
    alert('请先登录')
    router.push('/')
    return
  }
  loading.value = true
  await cartStore.fetchCartList(userStore.userInfo.id)
  loading.value = false

  // 默认全选
  selectedItems.value = cartList.value.map(item => item.id)
})

function toggleSelect(itemId: number) {
  const index = selectedItems.value.indexOf(itemId)
  if (index > -1) {
    selectedItems.value.splice(index, 1)
  } else {
    selectedItems.value.push(itemId)
  }
}

async function increaseQuantity(item: CartItem) {
  if (!userStore.userInfo?.id) return
  await cartStore.updateQuantity(item.id, item.quantity + 1, userStore.userInfo.id)
}

async function decreaseQuantity(item: CartItem) {
  if (!userStore.userInfo?.id) return
  if (item.quantity <= 1) return
  await cartStore.updateQuantity(item.id, item.quantity - 1, userStore.userInfo.id)
}

async function deleteItem(itemId: number) {
  if (!userStore.userInfo?.id) return
  if (confirm('确定要删除这件商品吗?')) {
    await cartStore.removeItem(itemId, userStore.userInfo.id)
    // 移除选中状态
    const index = selectedItems.value.indexOf(itemId)
    if (index > -1) {
      selectedItems.value.splice(index, 1)
    }
  }
}

async function clearSelected() {
  if (!userStore.userInfo?.id) return
  if (selectedItems.value.length === 0) return
  if (confirm('确定要清空选中的商品吗?')) {
    await cartStore.batchRemove(selectedItems.value, userStore.userInfo.id)
    selectedItems.value = []
  }
}

function checkout() {
  if (selectedItems.value.length === 0) {
    alert('请选择要结算的商品')
    return
  }
  // 跳转到支付页面，传递选中的购物车项ID
  router.push({
    path: '/payment',
    query: { cartIds: selectedItems.value.join(',') }
  })
}
</script>

<style scoped>
.cart-page {
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

.loading {
  text-align: center;
  padding: 60px;
  color: #999;
}

.empty-cart {
  text-align: center;
  padding: 80px 20px;
  background: white;
  border-radius: 8px;
}

.empty-icon {
  font-size: 64px;
  margin-bottom: 20px;
}

.empty-cart p {
  color: #666;
  margin-bottom: 20px;
}

.cart-content {
  display: flex;
  gap: 20px;
}

.cart-list {
  flex: 1;
  background: white;
  border-radius: 8px;
  overflow: hidden;
}

.cart-item {
  display: flex;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.checkbox-col {
  margin-right: 15px;
}

.checkbox-col input {
  width: 18px;
  height: 18px;
}

.product-col {
  display: flex;
  gap: 15px;
  flex: 1;
}

.product-col img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 8px;
  background: #f0f0f0;
}

.product-info h3 {
  font-size: 16px;
  margin-bottom: 5px;
}

.product-info .merchant {
  color: #666;
  font-size: 14px;
}

.product-info .type {
  color: #999;
  font-size: 12px;
}

.price-col {
  width: 100px;
  text-align: center;
  color: #666;
}

.quantity-col {
  display: flex;
  align-items: center;
  gap: 10px;
  width: 120px;
}

.quantity-col button {
  width: 28px;
  height: 28px;
  border: 1px solid #ddd;
  background: white;
  cursor: pointer;
  border-radius: 4px;
}

.quantity-col button:hover {
  border-color: #667eea;
  color: #667eea;
}

.quantity-col span {
  min-width: 30px;
  text-align: center;
}

.subtotal-col {
  width: 100px;
  text-align: center;
  font-weight: bold;
  color: #ff4d4f;
}

.action-col {
  width: 80px;
  text-align: center;
}

.delete-btn {
  padding: 5px 10px;
  border: none;
  background: #ff4d4f;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}

.cart-summary {
  width: 300px;
  background: white;
  padding: 20px;
  border-radius: 8px;
  height: fit-content;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
}

.summary-row.total {
  font-size: 18px;
  font-weight: bold;
  padding-top: 15px;
  border-top: 1px solid #eee;
}

.total-price {
  color: #ff4d4f;
  font-size: 24px;
}

.summary-actions {
  display: flex;
  gap: 10px;
  margin-top: 20px;
}

.summary-actions .btn {
  flex: 1;
}

.btn {
  padding: 12px 24px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 16px;
}

.btn-primary {
  background: #667eea;
  color: white;
}

.btn-primary:hover {
  background: #5a6fd6;
}

.btn-primary:disabled {
  background: #ccc;
  cursor: not-allowed;
}

.btn-secondary {
  background: #f5f5f5;
  color: #666;
}

.btn-secondary:hover {
  background: #eee;
}
</style>
