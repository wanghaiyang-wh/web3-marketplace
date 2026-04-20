<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-header">
        <h1>零壹Web3</h1>
        <p>游戏资产交易平台</p>
      </div>

      <div class="tabs">
        <button :class="tabClass(true)" @click="isLogin = true">登录</button>
        <button :class="tabClass(false)" @click="isLogin = false">注册</button>
      </div>

      <form v-if="isLogin" @submit.prevent="handleLogin" class="login-form">
        <div class="form-group">
          <input v-model="loginForm.username" type="text" placeholder="用户名" required />
        </div>
        <div class="form-group">
          <input v-model="loginForm.password" type="password" placeholder="密码" required />
        </div>
        <button type="submit" class="btn-primary" :disabled="loading">
          {{ loading ? '登录中...' : '登录' }}
        </button>
      </form>

      <form v-else @submit.prevent="handleRegister" class="login-form">
        <div class="form-group">
          <input v-model="registerForm.username" type="text" placeholder="用户名" required />
        </div>
        <div class="form-group">
          <input v-model="registerForm.email" type="email" placeholder="邮箱" required />
        </div>
        <div class="form-group">
          <input v-model="registerForm.password" type="password" placeholder="密码" required />
        </div>
        <div class="form-group">
          <input v-model="registerForm.confirmPassword" type="password" placeholder="确认密码" required />
        </div>

        <div class="form-group">
          <label>选择身份</label>
          <div class="role-select">
            <label :class="roleClass('USER')">
              <input type="radio" v-model="registerForm.role" value="USER" />
              <span class="role-icon">🛒</span>
              <span class="role-name">买家</span>
            </label>
            <label :class="roleClass('MERCHANT')">
              <input type="radio" v-model="registerForm.role" value="MERCHANT" />
              <span class="role-icon">🏪</span>
              <span class="role-name">商户</span>
            </label>
            <label :class="roleClass('ADMIN')">
              <input type="radio" v-model="registerForm.role" value="ADMIN" />
              <span class="role-icon">⚙️</span>
              <span class="role-name">平台</span>
            </label>
          </div>
        </div>

        <button type="submit" class="btn-primary" :disabled="loading">
          {{ loading ? '注册中...' : '注册' }}
        </button>
      </form>

      <div class="wallet-login">
        <div class="divider"><span>或</span></div>
        <button @click="handleWalletLogin" class="btn-wallet">
          <span>🔗</span> 使用钱包登录
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import request from '@/api'
import { web3Service } from '@/utils/web3'

const router = useRouter()
const userStore = useUserStore()

const isLogin = ref(true)
const loading = ref(false)

const loginForm = ref({
  username: '',
  password: ''
})

const registerForm = ref({
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
  role: 'USER'
})

const tabClass = function(active) {
  return { tab: true, active: active }
}

const roleClass = function(role) {
  return { 'role-option': true, selected: registerForm.value.role === role }
}

const handleLogin = async function() {
  loading.value = true
  try {
    const res = await request.post('/api/client/user/login', {
      username: loginForm.value.username,
      password: loginForm.value.password
    })

    userStore.setToken(res.token)
    userStore.setUserInfo({
      id: res.userId,
      username: res.username,
      role: res.role
    })

    redirectByRole(res.role)
  } catch (e) {
    alert(e.message || '登录失败')
  } finally {
    loading.value = false
  }
}

const handleRegister = async function() {
  if (registerForm.value.password !== registerForm.value.confirmPassword) {
    alert('两次密码输入不一致')
    return
  }

  loading.value = true
  try {
    const res = await request.post('/api/client/user/register', {
      username: registerForm.value.username,
      email: registerForm.value.email,
      password: registerForm.value.password,
      role: registerForm.value.role
    })

    userStore.setToken(res.token)
    userStore.setUserInfo({
      id: res.userId,
      username: res.username,
      role: registerForm.value.role
    })

    redirectByRole(registerForm.value.role)
  } catch (e) {
    alert(e.message || '注册失败')
  } finally {
    loading.value = false
  }
}

const handleWalletLogin = async function() {
  try {
    const address = await web3Service.connectWallet()
    userStore.setWalletAddress(address)

    const res = await request.post('/api/client/user/wallet/login', {
      walletAddress: address
    })

    userStore.setToken(res.token)
    userStore.setUserInfo({
      id: res.userId,
      username: res.username,
      role: res.role,
      walletAddress: address
    })

    redirectByRole(res.role)
  } catch (e) {
    alert(e.message || '钱包登录失败')
  }
}

const redirectByRole = function(role) {
  switch (role) {
    case 'MERCHANT':
      window.location.href = 'http://localhost:3001'
      break
    case 'ADMIN':
      window.location.href = 'http://localhost:3002'
      break
    default:
      router.push('/home')
  }
}
</script>

<style>
.login-page {
  min-height: 100vh;
  display: flex !important;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
}

.login-container {
  width: 420px;
  background: #fff !important;
  border-radius: 16px;
  padding: 40px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h1 {
  font-size: 32px;
  color: #667eea !important;
  margin-bottom: 10px;
}

.login-header p {
  color: #666 !important;
}

.tabs {
  display: flex;
  margin-bottom: 30px;
  border-bottom: 2px solid #eee;
}

.tabs .tab {
  flex: 1;
  padding: 15px;
  border: none;
  background: none;
  font-size: 16px;
  cursor: pointer;
  color: #666 !important;
  transition: all 0.3s;
}

.tabs .tab.active {
  color: #667eea !important;
  border-bottom: 2px solid #667eea;
  margin-bottom: -2px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group input {
  width: 100%;
  padding: 14px 16px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
  box-sizing: border-box;
}

.form-group input:focus {
  outline: none;
  border-color: #667eea;
}

.form-group label {
  display: block;
  margin-bottom: 10px;
  font-weight: 500;
  color: #333 !important;
}

.role-select {
  display: flex;
  gap: 10px;
}

.role-option {
  flex: 1;
  padding: 15px 10px;
  border: 2px solid #ddd;
  border-radius: 8px;
  cursor: pointer;
  text-align: center;
  transition: all 0.3s;
}

.role-option:hover {
  border-color: #667eea;
}

.role-option.selected {
  border-color: #667eea;
  background: #f0f5ff;
}

.role-option input {
  display: none;
}

.role-icon {
  font-size: 24px;
  display: block;
  margin-bottom: 5px;
}

.role-name {
  font-weight: 600;
  color: #333 !important;
  display: block;
}

.role-desc {
  font-size: 12px;
  color: #999 !important;
}

.btn-primary {
  width: 100%;
  padding: 14px;
  background: #667eea !important;
  color: #fff !important;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
}

.btn-primary:hover {
  background: #5a6fd6 !important;
}

.btn-primary:disabled {
  background: #ccc !important;
}

.wallet-login {
  margin-top: 30px;
}

.divider {
  text-align: center;
  position: relative;
  margin-bottom: 20px;
}

.divider::before {
  content: '';
  position: absolute;
  left: 0;
  right: 0;
  top: 50%;
  height: 1px;
  background: #eee;
}

.divider span {
  background: #fff !important;
  padding: 0 15px;
  color: #999 !important;
  position: relative;
}

.btn-wallet {
  width: 100%;
  padding: 14px;
  background: #1F2937 !important;
  color: #fff !important;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.btn-wallet:hover {
  background: #374151 !important;
}
</style>
