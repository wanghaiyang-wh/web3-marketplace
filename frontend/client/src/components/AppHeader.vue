<template>
  <header class="header">
    <div class="container">
      <div class="header-content">
        <div class="logo">零壹Web3</div>
        <nav class="nav">
          <router-link to="/">首页</router-link>
          <router-link to="/nft">NFT市场</router-link>
          <router-link to="/game">游戏市场</router-link>
        </nav>
        <div class="header-actions">
          <router-link to="/cart" class="cart-link">
            <span class="cart-icon">🛒</span>
            <span v-if="cartCount > 0" class="cart-badge">{{ cartCount }}</span>
          </router-link>
          <button v-if="!walletConnected" @click="connectWallet" class="btn btn-primary">
            连接钱包
          </button>
          <div v-else class="wallet-info">
            <span>{{ shortAddress }}</span>
            <div class="user-dropdown">
              <button class="btn">个人中心</button>
              <div class="dropdown-menu">
                <router-link to="/profile">我的资料</router-link>
                <router-link to="/orders">我的订单</router-link>
                <router-link to="/favorites">我的收藏</router-link>
                <router-link to="/history">浏览历史</router-link>
                <router-link to="/messages">消息中心</router-link>
                <router-link to="/offers">我的议价</router-link>
                <router-link to="/tickets">我的工单</router-link>
                <a @click="logout">退出登录</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { useCartStore } from '@/stores/cart'
import { web3Service } from '@/utils/web3'
import request from '@/api'

const router = useRouter()
const userStore = useUserStore()
const cartStore = useCartStore()

const walletConnected = computed(() => userStore.isConnected)
const walletAddress = computed(() => userStore.walletAddress)
const cartCount = computed(() => cartStore.cartCount)

const shortAddress = computed(() => {
  const addr = walletAddress.value
  if (!addr) return ''
  return `${addr.slice(0, 6)}...${addr.slice(-4)}`
})

const connectWallet = async () => {
  try {
    const address = await web3Service.connectWallet()
    userStore.setWalletAddress(address)

    // 调用后端钱包登录接口
    const res = await request.post('/api/client/user/wallet/login', {
      walletAddress: address
    })
    userStore.setToken(res.token)
    userStore.setUserInfo({
      id: res.userId,
      username: res.username,
      role: res.role,
      walletAddress: res.walletAddress
    })
    // 获取购物车数量
    await cartStore.fetchCartList(res.userId)
  } catch (error) {
    console.error('连接钱包失败:', error)
    alert('连接钱包失败，请确保已安装钱包插件')
  }
}

const logout = () => {
  userStore.logout()
  router.push('/')
}
</script>

<style scoped>
.header {
  background: #363849;
  padding: 15px 0;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  font-size: 24px;
  font-weight: bold;
  color: #f6c243;
}

.nav {
  display: flex;
  gap: 30px;
}

.nav a {
  color: #ccc;
  font-weight: 500;
  text-decoration: none;
  transition: color 0.2s;
}

.nav a:hover,
.nav a.router-link-active {
  color: #f6c243;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 15px;
}

.wallet-info {
  display: flex;
  align-items: center;
  gap: 15px;
  color: #fff;
}

.btn {
  padding: 8px 16px;
  background: #f6c243;
  border: none;
  border-radius: 4px;
  color: #2D2F3E;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  text-decoration: none;
}

.btn:hover {
  background: #e5b13c;
}

.platform-switch {
  display: flex;
  gap: 10px;
}

.switch-link {
  color: #ccc;
  font-size: 14px;
  text-decoration: none;
  padding: 8px 12px;
  border: 1px solid #4a4d5e;
  border-radius: 4px;
  transition: all 0.2s;
}

.switch-link:hover {
  color: #f6c243;
  border-color: #f6c243;
}

.cart-link {
  position: relative;
  display: flex;
  align-items: center;
  padding: 8px;
  color: #fff;
  text-decoration: none;
}

.cart-icon {
  font-size: 20px;
}

.cart-badge {
  position: absolute;
  top: 0;
  right: 0;
  background: #ff4d4f;
  color: white;
  font-size: 12px;
  padding: 2px 6px;
  border-radius: 10px;
  min-width: 18px;
  text-align: center;
}

.user-dropdown {
  position: relative;
}

.dropdown-menu {
  display: none;
  position: absolute;
  top: 100%;
  right: 0;
  background: white;
  border-radius: 4px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  min-width: 150px;
  z-index: 100;
}

.user-dropdown:hover .dropdown-menu {
  display: block;
}

.dropdown-menu a {
  display: block;
  padding: 10px 15px;
  color: #333;
  text-decoration: none;
  cursor: pointer;
}

.dropdown-menu a:hover {
  background: #f5f5f5;
}
</style>
