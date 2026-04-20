import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import * as cartApi from '@/api/cart'
import type { CartItem } from '@/api/cart'

export const useCartStore = defineStore('cart', () => {
  const cartList = ref<CartItem[]>([])
  const loading = ref(false)

  // 计算购物车总数
  const cartCount = computed(() => {
    return cartList.value.reduce((sum, item) => sum + item.quantity, 0)
  })

  // 计算购物车总价格
  const cartTotal = computed(() => {
    return cartList.value.reduce((sum, item) => {
      return sum + item.productPrice * item.quantity
    }, 0)
  })

  // 获取购物车列表
  async function fetchCartList(userId: number) {
    loading.value = true
    try {
      const data = await cartApi.getUserCart(userId)
      cartList.value = data || []
    } catch (error) {
      console.error('获取购物车列表失败:', error)
      cartList.value = []
    } finally {
      loading.value = false
    }
  }

  // 添加商品到购物车
  async function addToCart(userId: number, productId: number, productType: string, quantity: number = 1) {
    await cartApi.addToCart({ userId, productId, productType, quantity })
    await fetchCartList(userId)
  }

  // 更新商品数量
  async function updateQuantity(cartId: number, quantity: number, userId: number) {
    await cartApi.updateCartQuantity(cartId, quantity)
    await fetchCartList(userId)
  }

  // 删除商品
  async function removeItem(cartId: number, userId: number) {
    await cartApi.removeFromCart(cartId)
    await fetchCartList(userId)
  }

  // 批量删除
  async function batchRemove(cartIds: number[], userId: number) {
    await cartApi.batchRemoveFromCart(cartIds)
    await fetchCartList(userId)
  }

  // 清空购物车
  async function clearCart(userId: number) {
    await cartApi.clearUserCart(userId)
    cartList.value = []
  }

  return {
    cartList,
    loading,
    cartCount,
    cartTotal,
    fetchCartList,
    addToCart,
    updateQuantity,
    removeItem,
    batchRemove,
    clearCart
  }
})
