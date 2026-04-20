<template>
  <div class="nft-detail">
    <AppHeader />

    <div class="detail-container">
      <!-- 左侧商品图片区域 -->
      <div class="goods-left">
        <div class="goods-main-img">
          <img :src="getNftImage(nft?.id)" :alt="nft?.name" />
          <RarityBadge :level="getRarityLevel(nft?.id)" class="rarity-badge-pos" />
        </div>
        <div class="goods-thumbnails">
          <div class="thumb-item active">
            <img :src="getNftImage(nft?.id)" alt="商品图" />
          </div>
        </div>
      </div>

      <!-- 右侧商品信息区域 -->
      <div class="goods-right">
        <div class="goods-title">
          <h1>{{ nft?.name }}</h1>
        </div>

        <div class="goods-meta">
          <div class="meta-item">
            <span class="meta-label">卖家</span>
            <span class="meta-value">{{ nft?.merchantName || '平台卖家' }}</span>
          </div>
          <div class="meta-item">
            <span class="meta-label">区块链</span>
            <span class="meta-value">{{ nft?.chain || 'ETH' }}</span>
          </div>
          <div class="meta-item">
            <span class="meta-label">库存</span>
            <span class="meta-value">{{ nft?.stock || 1 }} 件</span>
          </div>
        </div>

        <!-- 价格区域 -->
        <div class="goods-price-box">
          <div class="price-row">
            <span class="price-label">售价</span>
            <div class="price-value">
              <span class="price-num">{{ nft?.price }}</span>
              <span class="price-unit">USDT</span>
            </div>
          </div>
          <div class="price-tips">
            <span>约等于 ¥{{ (nft?.price * 7.2).toFixed(2) }} 人民币</span>
          </div>
        </div>

        <!-- 属性信息 -->
        <div class="goods-attr" v-if="nft?.contractAddress || nft?.tokenId">
          <div class="attr-title">商品属性</div>
          <div class="attr-list">
            <div class="attr-item" v-if="nft?.contractAddress">
              <span class="attr-label">合约地址</span>
              <span class="attr-value">{{ nft?.contractAddress }}</span>
            </div>
            <div class="attr-item" v-if="nft?.tokenId">
              <span class="attr-label">Token ID</span>
              <span class="attr-value">{{ nft?.tokenId }}</span>
            </div>
          </div>
        </div>

        <!-- 描述 -->
        <div class="goods-desc" v-if="nft?.description">
          <div class="desc-title">商品描述</div>
          <p>{{ nft?.description }}</p>
        </div>

        <!-- 操作按钮 -->
        <div class="goods-actions">
          <button @click="buyNow" class="btn btn-primary btn-buy">立即购买</button>
          <button @click="addToCart" class="btn btn-cart">加入购物车</button>
          <button @click="toggleFavorite" :class="['btn', isFavorited ? 'btn-favorited' : 'btn-outline']">
            {{ isFavorited ? '❤️ 已收藏' : '🤍 收藏' }}
          </button>
          <button @click="showOfferModal = true" class="btn btn-secondary">还价</button>
          <button @click="showMessageModal = true" class="btn btn-outline">联系卖家</button>
        </div>

        <!-- 保障信息 -->
        <div class="goods-security">
          <div class="security-item">
            <span class="security-icon">🔒</span>
            <span>安全保障</span>
          </div>
          <div class="security-item">
            <span class="security-icon">⚡</span>
            <span>快速发货</span>
          </div>
          <div class="security-item">
            <span class="security-icon">💰</span>
            <span>安全支付</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 出价弹窗 -->
    <div v-if="showOfferModal" class="modal" @click.self="showOfferModal = false">
      <div class="modal-content">
        <div class="modal-header">
          <h2>还价</h2>
          <button class="modal-close" @click="showOfferModal = false">&times;</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>您的出价 (USDT)</label>
            <input v-model="offerPrice" type="number" step="0.01" class="input" placeholder="请输入出价金额" />
          </div>
          <div class="form-group">
            <label>留言 (可选)</label>
            <textarea v-model="offerMessage" class="input" rows="3" placeholder="可以告诉卖家为什么是这个价格"></textarea>
          </div>
          <div class="offer-tips">满 100 USDT 可发起还价</div>
        </div>
        <div class="modal-footer">
          <button @click="submitOffer" class="btn btn-primary">提交出价</button>
          <button @click="showOfferModal = false" class="btn">取消</button>
        </div>
      </div>
    </div>

    <!-- 联系卖家弹窗 -->
    <div v-if="showMessageModal" class="modal" @click.self="showMessageModal = false">
      <div class="modal-content">
        <div class="modal-header">
          <h2>联系卖家</h2>
          <button class="modal-close" @click="showMessageModal = false">&times;</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>留言内容</label>
            <textarea v-model="messageContent" class="input" rows="4" placeholder="请输入您想咨询的内容"></textarea>
          </div>
        </div>
        <div class="modal-footer">
          <button @click="sendMessage" class="btn btn-primary">发送消息</button>
          <button @click="showMessageModal = false" class="btn">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import request from '@/api'
import AppHeader from '@/components/AppHeader.vue'
import RarityBadge from '@/components/RarityBadge.vue'
import { getNftImage, getRarityLevel } from '@/utils/images'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const nft = ref<any>({})
const showOfferModal = ref(false)
const showMessageModal = ref(false)
const offerPrice = ref<number | string>('')
const offerMessage = ref('')
const messageContent = ref('')
const isFavorited = ref(false)

// 添加到购物车
const addToCart = async () => {
  if (!userStore.userInfo?.id) {
    alert('请先登录')
    return
  }
  try {
    await request.post('/api/client/cart/add', {
      userId: userStore.userInfo.id,
      productId: nft.value.id,
      productType: 'NFT',
      quantity: 1
    })
    alert('已添加到购物车！')
  } catch (e: any) {
    alert(e.message || '添加失败')
  }
}

// 收藏/取消收藏
const toggleFavorite = async () => {
  if (!userStore.userInfo?.id) {
    alert('请先登录')
    return
  }
  try {
    if (isFavorited.value) {
      await request.delete('/api/client/favorite/remove', {
        params: {
          userId: userStore.userInfo.id,
          productId: nft.value.id,
          productType: 'NFT'
        }
      })
      isFavorited.value = false
      alert('已取消收藏')
    } else {
      await request.post('/api/client/favorite/add', {
        userId: userStore.userInfo.id,
        productId: nft.value.id,
        productType: 'NFT'
      })
      isFavorited.value = true
      alert('收藏成功！')
    }
  } catch (e: any) {
    alert(e.message || '操作失败')
  }
}

// 检查是否已收藏
const checkFavorite = async () => {
  if (!userStore.userInfo?.id) return
  try {
    const res = await request.get('/api/client/favorite/check', {
      params: {
        userId: userStore.userInfo.id,
        productId: nft.value.id,
        productType: 'NFT'
      }
    })
    isFavorited.value = res || false
  } catch (e) {
    console.error('检查收藏状态失败', e)
  }
}

const fetchDetail = async () => {
  try {
    const res = await request.get(`/api/client/product/nft/${route.params.id}`)
    nft.value = res || {}
  } catch (e) {
    console.error('获取详情失败', e)
  }
}

const buyNow = async () => {
  if (!userStore.userInfo?.id) {
    alert('请先登录')
    return
  }

  if (!nft.value.merchantId || !nft.value.id) {
    alert('商品信息不完整')
    return
  }

  try {
    const res = await request.post('/api/client/order/create', {
      userId: userStore.userInfo.id,
      merchantId: nft.value.merchantId,
      productId: nft.value.id,
      productType: 'NFT',
      payType: 'USDT'
    })
    alert('订单创建成功！')
    router.push('/orders')
  } catch (e: any) {
    alert(e.message || '创建订单失败')
  }
}

const submitOffer = async () => {
  if (!userStore.userInfo?.id) {
    alert('请先登录')
    return
  }

  if (!offerPrice.value || Number(offerPrice.value) <= 0) {
    alert('请输入有效的出价金额')
    return
  }

  try {
    await request.post('/api/client/offer/create', {
      productId: nft.value.id,
      productType: 'NFT',
      buyerId: userStore.userInfo.id,
      merchantId: nft.value.merchantId,
      price: offerPrice.value,
      message: offerMessage.value
    })
    alert('出价提交成功！')
    showOfferModal.value = false
    offerPrice.value = ''
    offerMessage.value = ''
  } catch (e: any) {
    alert(e.message || '出价失败')
  }
}

const sendMessage = async () => {
  if (!userStore.userInfo?.id) {
    alert('请先登录')
    return
  }

  if (!messageContent.value.trim()) {
    alert('请输入留言内容')
    return
  }

  try {
    await request.post('/api/client/message/send', {
      senderId: userStore.userInfo.id,
      receiverId: nft.value.merchantId,
      productId: nft.value.id,
      productType: 'NFT',
      content: messageContent.value
    })
    alert('消息发送成功！')
    showMessageModal.value = false
    messageContent.value = ''
  } catch (e: any) {
    alert(e.message || '发送消息失败')
  }
}

onMounted(() => {
  fetchDetail().then(() => {
    checkFavorite()
  })
})
</script>

<style scoped>
.nft-detail {
  min-height: 100vh;
  background: #2D2F3E;
}

.detail-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 30px 20px;
  display: flex;
  gap: 30px;
}

/* 左侧商品图片 */
.goods-left {
  width: 440px;
  flex-shrink: 0;
}

.goods-main-img {
  width: 100%;
  height: 440px;
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
  position: relative;
}

.goods-main-img img {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
}

.rarity-badge-pos {
  position: absolute;
  top: 12px;
  left: 12px;
}

.goods-thumbnails {
  display: flex;
  gap: 10px;
}

.thumb-item {
  width: 80px;
  height: 80px;
  border: 2px solid transparent;
  border-radius: 4px;
  overflow: hidden;
  cursor: pointer;
}

.thumb-item.active {
  border-color: #4a90d9;
}

.thumb-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 右侧商品信息 */
.goods-right {
  flex: 1;
}

.goods-title h1 {
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 15px;
  color: #fff;
}

.goods-meta {
  display: flex;
  gap: 30px;
  padding: 15px 0;
  border-bottom: 1px solid #eee;
  margin-bottom: 20px;
}

.meta-item {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.meta-label {
  font-size: 12px;
  color: #999;
}

.meta-value {
  font-size: 14px;
  color: #e0e0e0;
}

/* 价格区域 */
.goods-price-box {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
}

.price-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.price-label {
  font-size: 14px;
  color: #666;
}

.price-value {
  display: flex;
  align-items: baseline;
}

.price-num {
  font-size: 36px;
  font-weight: bold;
  color: #ff4d4f;
}

.price-unit {
  font-size: 16px;
  color: #666;
  margin-left: 5px;
}

.price-tips {
  margin-top: 10px;
  font-size: 12px;
  color: #999;
}

/* 属性信息 */
.goods-attr {
  background: #fff;
  border-radius: 8px;
  padding: 15px 20px;
  margin-bottom: 20px;
}

.attr-title, .desc-title {
  font-size: 14px;
  font-weight: 600;
  color: #333;
  margin-bottom: 15px;
}

.attr-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.attr-item {
  display: flex;
  justify-content: space-between;
  font-size: 13px;
}

.attr-label {
  color: #999;
}

.attr-value {
  color: #333;
  word-break: break-all;
  max-width: 300px;
  text-align: right;
}

/* 描述 */
.goods-desc {
  background: #fff;
  border-radius: 8px;
  padding: 15px 20px;
  margin-bottom: 20px;
}

.goods-desc p {
  font-size: 13px;
  color: #666;
  line-height: 1.8;
}

/* 操作按钮 */
.goods-actions {
  display: flex;
  gap: 15px;
  margin-bottom: 25px;
}

.btn-buy {
  flex: 1;
  height: 50px;
  font-size: 16px;
}

.btn-secondary {
  height: 50px;
  font-size: 14px;
}

.btn-cart {
  height: 50px;
  font-size: 14px;
  background: #ff9800;
  color: white;
  border: none;
}

.btn-cart:hover {
  background: #f57c00;
}

.btn-favorited {
  height: 50px;
  font-size: 14px;
  background: #ff4d4f;
  color: white;
  border: none;
}

.btn-outline {
  height: 50px;
  font-size: 14px;
  background: transparent;
  border: 1px solid #ddd;
}

/* 保障信息 */
.goods-security {
  display: flex;
  gap: 30px;
  padding: 15px 0;
}

.security-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 12px;
  color: #666;
}

.security-icon {
  font-size: 16px;
}

/* 弹窗样式 */
.modal {
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

.modal-content {
  background: white;
  border-radius: 12px;
  width: 90%;
  max-width: 480px;
  overflow: hidden;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.modal-header h2 {
  margin: 0;
  font-size: 18px;
}

.modal-close {
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: #999;
}

.modal-body {
  padding: 20px;
}

.modal-footer {
  padding: 15px 20px;
  border-top: 1px solid #eee;
  display: flex;
  gap: 15px;
  justify-content: flex-end;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  font-size: 14px;
}

.input {
  width: 100%;
  padding: 10px 15px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 14px;
}

.offer-tips {
  font-size: 12px;
  color: #999;
}
</style>
