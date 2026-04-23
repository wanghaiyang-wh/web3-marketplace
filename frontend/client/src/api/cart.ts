import request from './index'

export interface CartItem {
  id: number
  userId: number
  productId: number
  productType: string
  quantity: number
  createTime: string
  productName: string
  productImage: string
  productPrice: number
  productStock: number
  merchantId: number
  merchantName: string
}

// 添加商品到购物车
export function addToCart(data: {
  userId: number
  productId: number
  productType: string
  quantity?: number
}) {
  return request.post('/api/client/cart/add', data)
}

// 更新购物车商品数量
export function updateCartQuantity(cartId: number, quantity: number) {
  return request.put(`/api/client/cart/update/${cartId}?quantity=${quantity}`)
}

// 从购物车删除
export function removeFromCart(cartId: number) {
  return request.delete(`/api/client/cart/remove/${cartId}`)
}

// 批量删除购物车商品
export function batchRemoveFromCart(cartIds: number[]) {
  return request.post('/api/client/cart/batchRemove', cartIds)
}

// 获取用户购物车列表
export function getUserCart(userId: number) {
  return request.get('/api/client/cart/list', { params: { userId } })
}

// 获取用户购物车分页列表
export function getUserCartPage(userId: number, current: number = 1, size: number = 10) {
  return request.get('/api/client/cart/page', {
    params: { userId, current, size }
  })
}

// 清空用户购物车
export function clearUserCart(userId: number) {
  return request.delete('/api/client/cart/clear', { params: { userId } })
}

// 检查商品是否在购物车中
export function checkProductInCart(userId: number, productId: number, productType: string) {
  return request.get('/api/client/cart/check', {
    params: { userId, productId, productType }
  })
}
