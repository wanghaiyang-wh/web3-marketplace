<template>
  <div class="wallet">
    <div class="container">
      <h1>钱包管理</h1>
      <div class="wallet-content">
        <div class="card balance-card">
          <h2>账户余额</h2>
          <div class="balance">
            <span class="amount">{{ balance }}</span>
            <span class="currency">USDT</span>
          </div>
          <div class="actions">
            <button @click="showRecharge" class="btn btn-primary">充值</button>
            <button @click="showWithdraw" class="btn btn-secondary">提现</button>
          </div>
        </div>

        <div class="card">
          <h2>钱包地址</h2>
          <div v-if="walletAddress" class="wallet-address">
            <p class="address">{{ walletAddress }}</p>
            <button @click="copyAddress" class="btn">复制</button>
          </div>
          <button v-else @click="connectWallet" class="btn btn-primary">连接钱包</button>
        </div>

        <div class="card">
          <h2>支付方式</h2>
          <div class="payment-methods">
            <div class="method">
              <span class="icon">USDT</span>
              <span>TRC20</span>
            </div>
            <div class="method">
              <span class="icon">微信</span>
              <span>微信支付</span>
            </div>
            <div class="method">
              <span class="icon">支付宝</span>
              <span>支付宝</span>
            </div>
          </div>
        </div>

        <div class="card">
          <h2>交易记录</h2>
          <div class="transaction-list">
            <div v-for="tx in transactions" :key="tx.id" class="transaction-item">
              <div class="tx-info">
                <span :class="['type', tx.type]">{{ tx.type === 'in' ? '充值' : '提现' }}</span>
                <span class="amount">{{ tx.type === 'in' ? '+' : '-' }}{{ tx.amount }} USDT</span>
              </div>
              <span class="time">{{ tx.time }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useUserStore } from '@/stores/user'
import { web3Service } from '@/utils/web3'
import request from '@/api'

const userStore = useUserStore()
const balance = ref('0.00')
const walletAddress = ref(userStore.walletAddress)

const transactions = ref([
  { id: 1, type: 'in', amount: '100', time: '2024-04-01 10:30' },
  { id: 2, type: 'out', amount: '50', time: '2024-03-28 15:20' },
  { id: 3, type: 'in', amount: '200', time: '2024-03-25 09:45' }
])

const connectWallet = async () => {
  try {
    const address = await web3Service.connectWallet()
    userStore.setWalletAddress(address)
    walletAddress.value = address

    // 调用后端钱包登录接口
    const res = await request.post('/api/client/user/wallet/login', {
      walletAddress: address
    })
    userStore.setToken(res.token)
    userStore.setUserInfo({
      id: res.userId,
      username: res.username,
      walletAddress: res.walletAddress
    })
  } catch (error) {
    console.error('连接钱包失败:', error)
  }
}

const copyAddress = () => {
  navigator.clipboard.writeText(walletAddress.value)
  alert('地址已复制')
}

const showRecharge = () => {
  alert('充值功能开发中...')
}

const showWithdraw = () => {
  alert('提现功能开发中...')
}
</script>

<style scoped>
.wallet {
  padding: 40px 0;
}

h1 {
  margin-bottom: 30px;
}

.wallet-content {
  display: grid;
  gap: 20px;
}

.balance-card {
  text-align: center;
}

.balance {
  margin: 30px 0;
}

.amount {
  font-size: 48px;
  font-weight: bold;
  color: var(--primary-color);
}

.currency {
  font-size: 24px;
  color: var(--text-secondary);
  margin-left: 10px;
}

.actions {
  display: flex;
  gap: 15px;
  justify-content: center;
}

.wallet-address {
  display: flex;
  align-items: center;
  gap: 15px;
}

.address {
  flex: 1;
  font-family: monospace;
  word-break: break-all;
}

.payment-methods {
  display: flex;
  gap: 20px;
}

.method {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 15px;
  background: #F3F4F6;
  border-radius: 8px;
}

.icon {
  font-weight: bold;
}

.transaction-list {
  display: flex;
  flex-direction: column;
}

.transaction-item {
  display: flex;
  justify-content: space-between;
  padding: 15px 0;
  border-bottom: 1px solid var(--border-color);
}

.transaction-item:last-child {
  border-bottom: none;
}

.tx-info {
  display: flex;
  gap: 15px;
}

.type {
  padding: 4px 12px;
  border-radius: 4px;
  font-size: 12px;
}

.type.in {
  background: #D1FAE5;
  color: #065F46;
}

.type.out {
  background: #FEE2E2;
  color: #991B1B;
}

.amount {
  font-weight: bold;
}

.time {
  color: var(--text-secondary);
  font-size: 14px;
}
</style>
