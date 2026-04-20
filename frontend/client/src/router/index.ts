import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/Home.vue')
  },
  {
    path: '/nft',
    name: 'NFTMarket',
    component: () => import('@/views/nft/NFTMarket.vue')
  },
  {
    path: '/nft/:id',
    name: 'NFTDetail',
    component: () => import('@/views/nft/NFTDetail.vue')
  },
  {
    path: '/game',
    name: 'GameMarket',
    component: () => import('@/views/game/GameMarket.vue')
  },
  {
    path: '/game/:id',
    name: 'GameDetail',
    component: () => import('@/views/game/GameDetail.vue')
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('@/views/user/Profile.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/orders',
    name: 'Orders',
    component: () => import('@/views/user/Orders.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/wallet',
    name: 'Wallet',
    component: () => import('@/views/user/Wallet.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/cart',
    name: 'Cart',
    component: () => import('@/views/user/Cart.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/messages',
    name: 'Messages',
    component: () => import('@/views/user/Messages.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/favorites',
    name: 'Favorites',
    component: () => import('@/views/user/Favorites.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/history',
    name: 'History',
    component: () => import('@/views/user/History.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/offers',
    name: 'Offers',
    component: () => import('@/views/user/Offers.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/tickets',
    name: 'Tickets',
    component: () => import('@/views/user/Tickets.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/payment',
    name: 'Payment',
    component: () => import('@/views/user/Payment.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/payment/result',
    name: 'PaymentResult',
    component: () => import('@/views/user/PaymentResult.vue'),
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
