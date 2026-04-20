<template>
  <div class="nft-market">
    <AppHeader />

    <div class="market-container">
      <!-- 左侧筛选栏 -->
      <aside class="sidebar">
        <div class="sidebar-section">
          <h3 class="section-title">筛选</h3>
        </div>

        <div class="sidebar-section">
          <div class="search-box">
            <input
              v-model="searchKeyword"
              type="text"
              class="search-input"
              placeholder="搜索NFT..."
              @input="debounceSearch"
            />
          </div>
        </div>

        <div class="sidebar-section">
          <h4 class="filter-title">区块链</h4>
          <div class="filter-options">
            <label class="filter-option">
              <input type="radio" v-model="selectedChain" value="" @change="fetchList" />
              <span>全部</span>
            </label>
            <label class="filter-option">
              <input type="radio" v-model="selectedChain" value="ETH" @change="fetchList" />
              <span>Ethereum</span>
            </label>
            <label class="filter-option">
              <input type="radio" v-model="selectedChain" value="BSC" @change="fetchList" />
              <span>BSC</span>
            </label>
            <label class="filter-option">
              <input type="radio" v-model="selectedChain" value="POLYGON" @change="fetchList" />
              <span>Polygon</span>
            </label>
            <label class="filter-option">
              <input type="radio" v-model="selectedChain" value="ARBITRUM" @change="fetchList" />
              <span>Arbitrum</span>
            </label>
            <label class="filter-option">
              <input type="radio" v-model="selectedChain" value="OPTIMISM" @change="fetchList" />
              <span>Optimism</span>
            </label>
          </div>
        </div>

        <div class="sidebar-section">
          <h4 class="filter-title">稀有度</h4>
          <div class="filter-options">
            <label class="filter-option">
              <input type="radio" v-model="selectedRarity" :value="null" @change="fetchList" />
              <span>全部</span>
            </label>
            <label class="filter-option">
              <input type="radio" v-model="selectedRarity" :value="1" @change="fetchList" />
              <span class="rarity-1">普通</span>
            </label>
            <label class="filter-option">
              <input type="radio" v-model="selectedRarity" :value="2" @change="fetchList" />
              <span class="rarity-2">稀有</span>
            </label>
            <label class="filter-option">
              <input type="radio" v-model="selectedRarity" :value="3" @change="fetchList" />
              <span class="rarity-3">史诗</span>
            </label>
            <label class="filter-option">
              <input type="radio" v-model="selectedRarity" :value="4" @change="fetchList" />
              <span class="rarity-4">传说</span>
            </label>
            <label class="filter-option">
              <input type="radio" v-model="selectedRarity" :value="5" @change="fetchList" />
              <span class="rarity-5">神话</span>
            </label>
          </div>
        </div>

        <div class="sidebar-section">
          <h4 class="filter-title">价格区间 (USDT)</h4>
          <div class="filter-options">
            <label class="filter-option">
              <input type="radio" v-model="priceRange" value="" @change="handlePriceChange" />
              <span>全部</span>
            </label>
            <label class="filter-option">
              <input type="radio" v-model="priceRange" value="0-1" @change="handlePriceChange" />
              <span>0 - 1</span>
            </label>
            <label class="filter-option">
              <input type="radio" v-model="priceRange" value="1-5" @change="handlePriceChange" />
              <span>1 - 5</span>
            </label>
            <label class="filter-option">
              <input type="radio" v-model="priceRange" value="5-10" @change="handlePriceChange" />
              <span>5 - 10</span>
            </label>
            <label class="filter-option">
              <input type="radio" v-model="priceRange" value="10-50" @change="handlePriceChange" />
              <span>10 - 50</span>
            </label>
            <label class="filter-option">
              <input type="radio" v-model="priceRange" value="50+" @change="handlePriceChange" />
              <span>50+</span>
            </label>
          </div>
        </div>
      </aside>

      <!-- 右侧商品列表 -->
      <main class="goods-list">
        <div class="list-header">
          <h2 class="list-title">NFT市场</h2>
          <span class="total-count">共 {{ total }} 件商品</span>
        </div>

        <div v-if="loading" class="loading-wrap">
          <div class="loading">加载中...</div>
        </div>
        <div v-else-if="nftList.length === 0" class="empty-wrap">
          <div class="empty">暂无商品</div>
        </div>
        <div v-else class="goods-grid">
          <div
            v-for="nft in nftList"
            :key="nft.id"
            class="goods-card"
            @click="goToDetail(nft.id)"
          >
            <div class="goods-img">
              <img :src="getNftImage(nft.id)" :alt="nft.name" />
              <RarityBadge :level="nft.rarity || getRarityLevel(nft.id)" class="rarity-badge-pos" />
            </div>
            <div class="goods-info">
              <h3 class="goods-name">{{ nft.name }}</h3>
              <div class="goods-meta">
                <span class="chain-tag">{{ nft.chain }}</span>
              </div>
              <div class="goods-bottom">
                <div class="goods-price">
                  <span class="price-value">{{ nft.price }}</span>
                  <span class="price-unit">USDT</span>
                </div>
                <button class="btn-buy">购买</button>
              </div>
            </div>
          </div>
        </div>

        <!-- 分页 -->
        <div class="pagination" v-if="totalPages > 1">
          <button
            class="page-btn"
            :disabled="current <= 1"
            @click="changePage(current - 1)"
          >
            上一页
          </button>
          <div class="page-numbers">
            <button
              v-for="page in visiblePages"
              :key="page"
              class="page-num"
              :class="{ active: page === current }"
              @click="changePage(page)"
            >
              {{ page }}
            </button>
          </div>
          <button
            class="page-btn"
            :disabled="current >= totalPages"
            @click="changePage(current + 1)"
          >
            下一页
          </button>
        </div>
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import request from '@/api'
import AppHeader from '@/components/AppHeader.vue'
import RarityBadge from '@/components/RarityBadge.vue'
import { getNftImage, getRarityLevel } from '@/utils/images'

const router = useRouter()
const searchKeyword = ref('')
const selectedChain = ref('')
const selectedRarity = ref<number | null>(null)
const priceRange = ref('')
const minPrice = ref<number | undefined>()
const maxPrice = ref<number | undefined>()
const nftList = ref<any[]>([])
const loading = ref(false)
const current = ref(1)
const size = ref(20)
const total = ref(0)

const totalPages = computed(() => Math.ceil(total.value / size.value))

const visiblePages = computed(() => {
  const pages: number[] = []
  const start = Math.max(1, current.value - 2)
  const end = Math.min(totalPages.value, current.value + 2)
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  return pages
})

const handlePriceChange = () => {
  if (!priceRange.value) {
    minPrice.value = undefined
    maxPrice.value = undefined
  } else if (priceRange.value === '50+') {
    minPrice.value = 50
    maxPrice.value = undefined
  } else {
    const [min, max] = priceRange.value.split('-').map(Number)
    minPrice.value = min
    maxPrice.value = max
  }
  current.value = 1
  fetchList()
}

let searchTimer: number
const debounceSearch = () => {
  clearTimeout(searchTimer)
  searchTimer = window.setTimeout(() => {
    current.value = 1
    fetchList()
  }, 500)
}

const fetchList = async () => {
  loading.value = true
  try {
    const params: any = {
      current: current.value,
      size: size.value,
      status: 1,
      keyword: searchKeyword.value || undefined,
      chain: selectedChain.value || undefined,
      rarity: selectedRarity.value || undefined,
      minPrice: minPrice.value,
      maxPrice: maxPrice.value
    }
    const res = await request.get('/api/client/product/nft/list', { params })
    nftList.value = res.records || []
    total.value = res.total || res.records?.length || 0
  } catch (e: any) {
    console.error('获取NFT列表失败', e)
  } finally {
    loading.value = false
  }
}

const changePage = (page: number) => {
  current.value = page
  fetchList()
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

const goToDetail = (id: number) => {
  router.push(`/nft/${id}`)
}

onMounted(() => {
  fetchList()
})
</script>

<style scoped>
.nft-market {
  min-height: 100vh;
  background: #2D2F3E;
}

.market-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  display: flex;
  gap: 20px;
}

/* 左侧筛选栏 */
.sidebar {
  width: 220px;
  flex-shrink: 0;
}

.sidebar-section {
  background: #363849;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 15px;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  color: #fff;
  margin: 0 0 15px 0;
}

.search-input {
  width: 100%;
  padding: 10px 12px;
  background: #2D2F3E;
  border: 1px solid #4a4d5e;
  border-radius: 6px;
  color: #fff;
  font-size: 14px;
}

.search-input::placeholder {
  color: #888;
}

.search-input:focus {
  outline: none;
  border-color: #f6c243;
}

.filter-title {
  font-size: 14px;
  font-weight: 500;
  color: #ccc;
  margin: 0 0 12px 0;
}

.filter-options {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.filter-option {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  color: #aaa;
  font-size: 13px;
}

.filter-option:hover {
  color: #f6c243;
}

.filter-option .rarity-1 { color: #9CA3AF; }
.filter-option .rarity-2 { color: #22C55E; }
.filter-option .rarity-3 { color: #A855F7; }
.filter-option .rarity-4 { color: #F59E0B; }
.filter-option .rarity-5 { color: #EF4444; }

.filter-option input[type="radio"] {
  appearance: none;
  width: 14px;
  height: 14px;
  border: 2px solid #555;
  border-radius: 50%;
  cursor: pointer;
}

.filter-option input[type="radio"]:checked {
  border-color: #f6c243;
  background: #f6c243;
}

/* 右侧商品列表 */
.goods-list {
  flex: 1;
}

.list-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
}

.list-title {
  font-size: 20px;
  font-weight: 600;
  color: #fff;
  margin: 0;
}

.total-count {
  color: #888;
  font-size: 14px;
}

.loading-wrap,
.empty-wrap {
  background: #363849;
  border-radius: 8px;
  padding: 60px 0;
}

.loading,
.empty {
  text-align: center;
  color: #888;
  font-size: 14px;
}

.goods-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 15px;
}

.goods-card {
  background: #363849;
  border-radius: 8px;
  overflow: hidden;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.goods-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
}

.goods-img {
  width: 100%;
  height: 180px;
  background: #2D2F3E;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.goods-img img {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
}

.rarity-badge-pos {
  position: absolute;
  top: 8px;
  left: 8px;
}

.goods-info {
  padding: 12px;
}

.goods-name {
  font-size: 14px;
  font-weight: 500;
  color: #fff;
  margin: 0 0 8px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.goods-meta {
  margin-bottom: 10px;
}

.chain-tag {
  display: inline-block;
  padding: 2px 8px;
  background: #4a5568;
  border-radius: 4px;
  font-size: 11px;
  color: #aaa;
}

.goods-bottom {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.goods-price {
  display: flex;
  align-items: baseline;
}

.price-value {
  font-size: 18px;
  font-weight: bold;
  color: #f6c243;
}

.price-unit {
  font-size: 12px;
  color: #888;
  margin-left: 4px;
}

.btn-buy {
  padding: 6px 16px;
  background: #f6c243;
  border: none;
  border-radius: 4px;
  color: #2D2F3E;
  font-size: 12px;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s;
}

.btn-buy:hover {
  background: #e5b13c;
}

/* 分页 */
.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-top: 30px;
}

.page-btn {
  padding: 8px 16px;
  background: #363849;
  border: 1px solid #4a4d5e;
  border-radius: 4px;
  color: #aaa;
  font-size: 13px;
  cursor: pointer;
  transition: all 0.2s;
}

.page-btn:hover:not(:disabled) {
  border-color: #f6c243;
  color: #f6c243;
}

.page-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.page-numbers {
  display: flex;
  gap: 5px;
}

.page-num {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #363849;
  border: 1px solid #4a4d5e;
  border-radius: 4px;
  color: #aaa;
  font-size: 13px;
  cursor: pointer;
  transition: all 0.2s;
}

.page-num:hover {
  border-color: #f6c243;
  color: #f6c243;
}

.page-num.active {
  background: #f6c243;
  border-color: #f6c243;
  color: #2D2F3E;
}
</style>
