<template>
  <div class="home">
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
            <button v-if="!walletConnected" @click="connectWallet" class="btn btn-primary">
              连接钱包
            </button>
            <div v-else class="wallet-info">
              <span>{{ shortAddress }}</span>
              <router-link to="/profile" class="btn">个人中心</router-link>
            </div>
          </div>
        </div>
      </div>
    </header>

    <main>
      <section class="hero">
        <div class="container">
          <h1>Web3游戏资产交易平台</h1>
          <p>安全便捷的NFT与Steam游戏交易平台</p>
        </div>
      </section>

      <section class="featured container">
        <h2>热门NFT</h2>
        <div class="grid">
          <div v-for="nft in hotNFTs" :key="nft.id" class="card nft-card">
            <img :src="nft.image" :alt="nft.name" />
            <h3>{{ nft.name }}</h3>
            <p class="price">{{ nft.price }} USDT</p>
          </div>
        </div>
      </section>

      <section class="featured container">
        <h2>热门游戏</h2>
        <div class="grid">
          <div v-for="game in hotGames" :key="game.id" class="card game-card">
            <img :src="game.image" :alt="game.name" />
            <h3>{{ game.name }}</h3>
            <p class="price">{{ game.price }} USDT</p>
          </div>
        </div>
      </section>
    </main>

    <footer class="footer">
      <div class="container">
        <p>&copy; 2024 零壹Web3游戏资产交易平台. All rights reserved.</p>
      </div>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { web3Service } from '@/utils/web3'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const walletConnected = computed(() => userStore.isConnected)
const walletAddress = computed(() => userStore.walletAddress)

const shortAddress = computed(() => {
  const addr = walletAddress.value
  if (!addr) return ''
  return `${addr.slice(0, 6)}...${addr.slice(-4)}`
})

const hotNFTs = ref([
  { id: 1, name: 'CryptoPunk #001', price: '2.5', image: '' },
  { id: 2, name: 'BoredApe #888', price: '3.2', image: '' },
  { id: 3, name: 'Azuki #666', price: '1.8', image: '' },
  { id: 4, name: 'Doodle #333', price: '1.2', image: '' }
])

const hotGames = ref([
  { id: 1, name: 'Cyberpunk 2077', price: '29.9', image: '' },
  { id: 2, name: 'Elden Ring', price: '39.9', image: '' },
  { id: 3, name: 'Baldur\'s Gate 3', price: '59.9', image: '' },
  { id: 4, name: 'GTA V', price: '19.9', image: '' }
])

const connectWallet = async () => {
  try {
    const address = await web3Service.connectWallet()
    userStore.setWalletAddress(address)
  } catch (error) {
    console.error('连接钱包失败:', error)
    alert('连接钱包失败，请确保已安装钱包插件')
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
}

.logo {
  font-size: 24px;
  font-weight: bold;
  color: var(--primary-color);
}

.nav {
  display: flex;
  gap: 30px;
}

.nav a {
  color: var(--text-color);
  font-weight: 500;
}

.nav a:hover {
  color: var(--primary-color);
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
}

.hero {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 80px 0;
  text-align: center;
}

.hero h1 {
  font-size: 48px;
  margin-bottom: 20px;
}

.hero p {
  font-size: 20px;
  opacity: 0.9;
}

.featured {
  padding: 60px 20px;
}

.featured h2 {
  margin-bottom: 30px;
  font-size: 28px;
}

.grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.nft-card, .game-card {
  text-align: center;
}

.nft-card img, .game-card img {
  width: 100%;
  height: 200px;
  object-fit: cover;
  background: #f0f0f0;
  border-radius: 8px;
  margin-bottom: 15px;
}

.price {
  color: var(--primary-color);
  font-weight: bold;
  font-size: 18px;
}

.footer {
  background: #1F2937;
  color: white;
  padding: 30px 0;
  text-align: center;
}
</style>
