import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    }
  },
  server: {
    port: 3002,
    proxy: {
      '/api/admin': {
        target: 'http://localhost:8083',
        changeOrigin: true
      },
      '/api/merchant': {
        target: 'http://localhost:8082',
        changeOrigin: true
      },
      '/api/client': {
        target: 'http://localhost:8081',
        changeOrigin: true
      }
    }
  }
})
