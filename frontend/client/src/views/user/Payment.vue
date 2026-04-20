<template>
  <div class="payment-page">
    <div class="container">
      <h1>确认订单</h1>

      <div class="content">
        <div class="order-list">
          <div class="order-item" v-for="item in cartItems" :key="item.id">
            <img :src="item.productImage" class="product-img" />
            <div class="product-info">
              <h3>{{ item.productName }}</h3>
              <p>店铺: {{ item.merchantName }}</p>
            </div>
            <div class="quantity">x{{ item.quantity }}</div>
            <div class="price">¥{{ (item.productPrice * item.quantity).toFixed(2) }}</div>
          </div>
        </div>

        <div class="summary">
          <div class="summary-row">
            <span>商品总数:</span>
            <span>{{ totalQuantity }}件</span>
          </div>
          <div class="summary-row total">
            <span>合计:</span>
            <span class="total-price">¥{{ totalPrice.toFixed(2) }}</span>
          </div>
        </div>

        <div class="payment-method">
          <h2>支付方式</h2>
          <div class="methods">
            <label :class="['method', { selected: payType === 'USDT' }]">
              <input type="radio" v-model="payType" value="USDT" />
              <span class="icon">₿</span>
              <span>USDT</span>
            </label>
            <label :class="['method', { selected: payType === 'WECHAT' }]">
              <input type="radio" v-model="payType" value="WECHAT" />
              <span class="icon">💚</span>
              <span>微信支付</span>
            </label>
            <label :class="['method', { selected: payType === 'ALIPAY' }]">
              <input type="radio" v-model="payType" value="ALIPAY" />
              <span class="icon">💙</span>
              <span>支付宝</span>
            </label>
          </div>
        </div>

        <div class="actions">
          <button class="btn-secondary" @click="goBack">返回购物车</button>
          <button class="btn-primary" @click="submitOrder" :disabled="submitting">
            {{ submitting ? '提交中...' : '确认支付' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import request from '@/api'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const cartItems = ref<any[]>([])
const payType = ref('USDT')
const submitting = ref(false)

const totalQuantity = computed(() => {
  return cartItems.value.reduce((sum, item) => sum + item.quantity, 0)
})

const totalPrice = computed(() => {
  return cartItems.value.reduce((sum, item) => sum + item.productPrice * item.quantity, 0)
})

function goBack() {
  router.push('/cart')
}

async function submitOrder() {
  if (submitting.value) return

  submitting.value = true
  try {
    // 创建订单
    const orders = []
    for (const item of cartItems.value) {
      const res = await request.post('/api/client/order/create', {
        userId: userStore.userInfo.id,
        merchantId: item.merchantId,
        productId: item.productId,
        productType: item.productType,
        payType: payType.value
      })
      orders.push(res)
    }

    // 跳转到支付结果页
    router.push({
      path: '/payment/result',
      query: {
        orderIds: orders.map(o => o.id).join(','),
        total: totalPrice.value.toString()
      }
    })
  } catch (error) {
    console.error('创建订单失败:', error)
    alert('创建订单失败，请重试')
  } finally {
    submitting.value = false
  }
}

onMounted(async () => {
  if (!userStore.userInfo?.id) {
    alert('请先登录')
    router.push('/')
    return
  }

  const cartIds = route.query.cartIds as string
  if (!cartIds) {
    alert('购物车为空')
    router.push('/cart')
    return
  }

  // 获取购物车详情
  try {
    const res = await request.get('/api/client/cart/list', {
      params: { userId: userStore.userInfo.id }
    })
    const selectedIds = cartIds.split(',').map(Number)
    cartItems.value = (res || []).filter((item: any) => selectedIds.includes(item.id))
  } catch (error) {
    console.error('获取购物车失败:', error)
    router.push('/cart')
  }
})
</script>

<style scoped>
.payment-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 20px 0;
}

.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 0 20px;
}

h1 {
  font-size: 28px;
  margin-bottom: 20px;
}

.content {
  background: white;
  border-radius: 8px;
  padding: 20px;
}

.order-list {
  margin-bottom: 20px;
}

.order-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 15px 0;
  border-bottom: 1px solid #eee;
}

.product-img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 8px;
}

.product-info {
  flex: 1;
}

.product-info h3 {
  font-size: 16px;
  margin-bottom: 5px;
}

.product-info p {
  color: #666;
  font-size: 14px;
}

.quantity {
  width: 50px;
  text-align: center;
  color: #666;
}

.price {
  width: 100px;
  text-align: right;
  font-weight: bold;
  color: #ff4d4f;
}

.summary {
  padding: 20px 0;
  border-bottom: 1px solid #eee;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.summary-row.total {
  font-size: 18px;
  font-weight: bold;
}

.total-price {
  color: #ff4d4f;
  font-size: 24px;
}

.payment-method {
  padding: 20px 0;
}

.payment-method h2 {
  font-size: 18px;
  margin-bottom: 15px;
}

.methods {
  display: flex;
  gap: 15px;
}

.method {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 15px 20px;
  border: 2px solid #ddd;
  border-radius: 8px;
  cursor: pointer;
}

.method.selected {
  border-color: #667eea;
  background: #f0f5ff;
}

.method input {
  display: none;
}

.icon {
  font-size: 24px;
}

.actions {
  display: flex;
  gap: 15px;
  justify-content: flex-end;
  padding-top: 20px;
}

.btn-primary, .btn-secondary {
  padding: 12px 30px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.btn-primary {
  background: #667eea;
  color: white;
}

.btn-primary:disabled {
  background: #ccc;
}

.btn-secondary {
  background: #f5f5f5;
  color: #666;
}
</style>
