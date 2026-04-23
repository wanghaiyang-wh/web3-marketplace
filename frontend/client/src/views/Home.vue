<template>
  <div class="home">
    <AppHeader />

    <main>
      <!-- 轮播图 -->
      <section class="banner">
        <div class="banner-slider" :style="{ transform: `translateX(-${currentSlide * 100}%)` }">
          <div
            v-for="(banner, index) in banners"
            :key="index"
            class="banner-slide"
            :style="{ background: banner.bg }"
          >
            <div class="banner-content">
              <h1>{{ banner.title }}</h1>
              <p>{{ banner.desc }}</p>
              <router-link :to="banner.link" class="banner-btn">{{ banner.btnText }}</router-link>
            </div>
          </div>
        </div>
        <div class="banner-dots">
          <span
            v-for="(_, index) in banners"
            :key="index"
            class="dot"
            :class="{ active: index === currentSlide }"
            @click="currentSlide = index"
          ></span>
        </div>
      </section>

      <section class="featured container">
        <div class="section-header">
          <h2>热门NFT</h2>
          <router-link to="/nft" class="view-more">查看更多</router-link>
        </div>
        <div v-if="loadingNFT" class="loading">加载中...</div>
        <div v-else-if="hotNFTs.length === 0" class="empty">暂无商品</div>
        <div v-else class="grid">
          <div v-for="nft in hotNFTs" :key="nft.id" class="card nft-card" @click="goToNFTDetail(nft.id)">
            <div class="card-img-wrap">
              <img :src="getNftImage(nft.id)" :alt="nft.name" />
              <RarityBadge :level="getRarityLevel(nft.id)" class="rarity-badge-pos" />
            </div>
            <h3>{{ nft.name }}</h3>
            <p class="price">{{ nft.price }} USDT</p>
          </div>
        </div>
      </section>

      <section class="featured container">
        <div class="section-header">
          <h2>热门游戏</h2>
          <router-link to="/game" class="view-more">查看更多</router-link>
        </div>
        <div v-if="loadingGame" class="loading">加载中...</div>
        <div v-else-if="hotGames.length === 0" class="empty">暂无商品</div>
        <div v-else class="grid">
          <div v-for="game in hotGames" :key="game.id" class="card game-card" @click="goToGameDetail(game.id)">
            <div class="card-img-wrap">
              <img :src="getGameImage(game.id)" :alt="game.name" />
              <RarityBadge :level="getRarityLevel(game.id)" class="rarity-badge-pos" />
            </div>
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
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import AppHeader from '@/components/AppHeader.vue'
import RarityBadge from '@/components/RarityBadge.vue'
import request from '@/api'
import { getGameImage, getNftImage, getRarityLevel } from '@/utils/images'

const router = useRouter()
const hotNFTs = ref<any[]>([])
const hotGames = ref<any[]>([])
const loadingNFT = ref(false)
const loadingGame = ref(false)

// 轮播图数据
const banners = ref([
  {
    title: 'NFT交易市场',
    desc: '探索、购买和交易稀有的数字艺术品',
    btnText: '探索NFT',
    link: '/nft',
    bg: 'url(https://images.unsplash.com/photo-1614680376573-df3480f0c6ff?w=1920&q=80)'
  },
  {
    title: 'Steam游戏商城',
    desc: '海量Steam游戏CD-Key，低价优惠',
    btnText: '浏览游戏',
    link: '/game',
    bg: 'url(https://images.unsplash.com/photo-1542751371-adc38448a05e?w=1920&q=80)'
  },
  {
    title: '安全交易保障',
    desc: '区块链技术保障您的交易安全',
    btnText: '了解更多',
    link: '/',
    bg: 'url(https://images.unsplash.com/photo-1639762681485-074b7f938ba0?w=1920&q=80)'
  }
])

const currentSlide = ref(0)
let slideTimer: number | null = null

const nextSlide = () => {
  currentSlide.value = (currentSlide.value + 1) % banners.value.length
}

const startAutoSlide = () => {
  slideTimer = window.setInterval(nextSlide, 4000)
}

const stopAutoSlide = () => {
  if (slideTimer) {
    clearInterval(slideTimer)
    slideTimer = null
  }
}

const fetchHotNFTs = async () => {
  loadingNFT.value = true
  try {
    const res = await request.get('/api/client/product/nft/list', {
      params: { current: 1, size: 8, status: 1 }
    })
    hotNFTs.value = res.records || []
  } catch (e) {
    console.error('获取热门NFT失败', e)
  } finally {
    loadingNFT.value = false
  }
}

const fetchHotGames = async () => {
  loadingGame.value = true
  try {
    const res = await request.get('/api/client/product/game/list', {
      params: { current: 1, size: 8, status: 1 }
    })
    hotGames.value = res.records || []
  } catch (e) {
    console.error('获取热门游戏失败', e)
  } finally {
    loadingGame.value = false
  }
}

const goToNFTDetail = (id: number) => {
  router.push(`/nft/${id}`)
}

const goToGameDetail = (id: number) => {
  router.push(`/game/${id}`)
}

onMounted(() => {
  fetchHotNFTs()
  fetchHotGames()
  startAutoSlide()
})

onUnmounted(() => {
  stopAutoSlide()
})
</script>

<style scoped>
.home {
  background: #2D2F3E;
  min-height: 100vh;
}

/* 轮播图 */
.banner {
  position: relative;
  width: 100%;
  height: 400px;
  overflow: hidden;
}

.banner-slider {
  display: flex;
  transition: transform 0.5s ease-in-out;
  height: 100%;
}

.banner-slide {
  min-width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-size: cover;
  background-position: center;
  position: relative;
}

.banner-slide::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
}

.banner-content {
  text-align: center;
  color: white;
  padding: 20px;
  position: relative;
  z-index: 1;
}

.banner-content h1 {
  font-size: 48px;
  margin-bottom: 20px;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
}

.banner-content p {
  font-size: 20px;
  margin-bottom: 30px;
  opacity: 0.9;
}

.banner-btn {
  display: inline-block;
  padding: 15px 40px;
  background: #f6c243;
  color: #2D2F3E;
  font-size: 16px;
  font-weight: 600;
  border-radius: 8px;
  text-decoration: none;
  transition: all 0.3s;
}

.banner-btn:hover {
  background: #e5b13c;
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(246, 194, 67, 0.4);
}

.banner-dots {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 10px;
}

.dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.5);
  cursor: pointer;
  transition: all 0.3s;
}

.dot.active {
  background: #f6c243;
  transform: scale(1.2);
}

.dot:hover {
  background: rgba(255, 255, 255, 0.8);
}

.featured {
  padding: 60px 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.section-header h2 {
  font-size: 28px;
  margin: 0;
  color: #fff;
}

.view-more {
  color: #f6c243;
  font-size: 14px;
}

.view-more:hover {
  text-decoration: underline;
}

.loading, .empty {
  text-align: center;
  padding: 40px;
  color: #888;
}

.grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 15px;
}

.nft-card, .game-card {
  background: #363849;
  border-radius: 8px;
  padding: 15px;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.nft-card:hover, .game-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
}

.card-img-wrap {
  position: relative;
  width: 100%;
  height: 160px;
  overflow: hidden;
  border-radius: 8px;
  margin-bottom: 12px;
}

.card-img-wrap img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.rarity-badge-pos {
  position: absolute;
  top: 8px;
  left: 8px;
  z-index: 1;
}

.nft-card h3, .game-card h3 {
  color: #fff;
  font-size: 14px;
  margin: 0 0 10px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.price {
  color: #f6c243;
  font-weight: bold;
  font-size: 18px;
  margin: 0;
}

.footer {
  background: #1F2937;
  color: #888;
  padding: 30px 0;
  text-align: center;
}

.footer p {
  margin: 0;
}

.grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.nft-card, .game-card {
  text-align: center;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.nft-card:hover, .game-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
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
