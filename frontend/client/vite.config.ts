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
    port: 3000,
    hmr: {
      overlay: false
    },
    proxy: {
      '/api/client': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        bypass(req) {
          // 绕过系统代理
          if (req.headers['via'] || req.headers['x-forwarded-for']) {
            delete req.headers['via'];
            delete req.headers['x-forwarded-for'];
          }
        }
      },
      '/api/merchant': {
        target: 'http://localhost:8082',
        changeOrigin: true
      }
    }
  }
})
