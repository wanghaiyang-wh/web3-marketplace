# Railway 部署指南

## 步骤1：推送代码到GitHub

```bash
cd /home/why/web3-marketplace
git add .
git commit -m "Add Dockerfile for Railway deployment"
git push origin main
```

## 步骤2：在Railway创建项目

1. 访问 https://railway.app 注册/登录
2. 点击 "New Project"
3. 选择 "Deploy from GitHub repo"
4. 选择你的仓库

## 步骤3：添加MySQL数据库

1. 在Railway项目页面，点击 "New"
2. 选择 "Database" -> "MySQL"
3. Railway会自动创建MySQL并生成环境变量

## 步骤4：配置环境变量

在Railway项目的 "Variables" 标签页添加：

```
DB_HOST=${MYSQL_PORT_3306_TCP_ADDR}
DB_PORT=3306
DB_NAME=railway
DB_USER=root
DB_PASSWORD=${MYSQL_PASSWORD}
```

## 步骤5：部署

Railway会自动检测Dockerfile并部署。

## 步骤6：获取公网URL

部署完成后，在 "Deployments" -> 点击最新的 -> 查看 "Domains"

## 前端配置

获取到后端URL后，在Netlify前端环境变量中添加：
- `VITE_API_URL` = 你的Railway后端URL

例如：`https://your-project.railway.app`

## 注意事项

- Railway免费版每月有$5额度，足够用
- 不使用时请停止服务避免扣费
- 需要同时运行多个服务（client-api, merchant-api, admin-api）请告诉我
