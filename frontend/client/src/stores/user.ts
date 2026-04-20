import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const token = ref<string>('')
  const userInfo = ref<any>(null)
  const walletAddress = ref<string>('')
  const isConnected = ref<boolean>(false)
  const role = ref<string>('USER')

  function setToken(newToken: string) {
    token.value = newToken
  }

  function setUserInfo(info: any) {
    userInfo.value = info
    role.value = info.role || 'USER'
  }

  function setWalletAddress(address: string) {
    walletAddress.value = address
    isConnected.value = !!address
  }

  function logout() {
    token.value = ''
    userInfo.value = null
    walletAddress.value = ''
    isConnected.value = false
    role.value = 'USER'
  }

  return {
    token,
    userInfo,
    walletAddress,
    isConnected,
    role,
    setToken,
    setUserInfo,
    setWalletAddress,
    logout
  }
})
