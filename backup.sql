-- MySQL dump 10.13  Distrib 8.0.45, for Linux (x86_64)
--
-- Host: localhost    Database: web3_marketplace
-- ------------------------------------------------------
-- Server version	8.0.45-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_admin`
--

DROP TABLE IF EXISTS `t_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_admin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `role` varchar(20) DEFAULT 'admin' COMMENT '角色: admin-超级管理员, operator-运营',
  `status` tinyint DEFAULT '1' COMMENT '状态: 1-正常 0-禁用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_admin`
--

LOCK TABLES `t_admin` WRITE;
/*!40000 ALTER TABLE `t_admin` DISABLE KEYS */;
INSERT INTO `t_admin` VALUES (1,'admin','$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E','admin',1,'2026-04-12 11:16:50','2026-04-12 11:16:50');
/*!40000 ALTER TABLE `t_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_browse_history`
--

DROP TABLE IF EXISTS `t_browse_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_browse_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `product_type` varchar(20) NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_time` (`user_id`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='浏览历史表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_browse_history`
--

LOCK TABLES `t_browse_history` WRITE;
/*!40000 ALTER TABLE `t_browse_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_browse_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_cart`
--

DROP TABLE IF EXISTS `t_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `product_type` varchar(20) NOT NULL COMMENT 'NFT-游戏商品',
  `quantity` int DEFAULT '1' COMMENT '数量',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='购物车表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_cart`
--

LOCK TABLES `t_cart` WRITE;
/*!40000 ALTER TABLE `t_cart` DISABLE KEYS */;
INSERT INTO `t_cart` VALUES (2,7,4,'NFT',1,'2026-04-12 18:25:36','2026-04-12 18:25:36');
/*!40000 ALTER TABLE `t_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_favorite`
--

DROP TABLE IF EXISTS `t_favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_favorite` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `product_type` varchar(20) NOT NULL COMMENT 'NFT-游戏商品',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_product` (`user_id`,`product_id`,`product_type`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='收藏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_favorite`
--

LOCK TABLES `t_favorite` WRITE;
/*!40000 ALTER TABLE `t_favorite` DISABLE KEYS */;
INSERT INTO `t_favorite` VALUES (1,7,8,'GAME','2026-04-12 12:17:08'),(2,7,4,'NFT','2026-04-12 18:25:39');
/*!40000 ALTER TABLE `t_favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_follow`
--

DROP TABLE IF EXISTS `t_follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_follow` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `merchant_id` bigint NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_merchant` (`user_id`,`merchant_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='关注店铺表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_follow`
--

LOCK TABLES `t_follow` WRITE;
/*!40000 ALTER TABLE `t_follow` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_game_product`
--

DROP TABLE IF EXISTS `t_game_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_game_product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `merchant_id` bigint NOT NULL COMMENT '商家ID',
  `name` varchar(200) NOT NULL COMMENT '商品名称',
  `description` text COMMENT '商品描述',
  `image` varchar(500) DEFAULT NULL COMMENT '商品图片',
  `category` varchar(50) DEFAULT NULL COMMENT '游戏分类',
  `cd_key` text COMMENT 'CD-Key(加密存储)',
  `price` decimal(18,4) NOT NULL COMMENT '价格(USDT)',
  `stock` int DEFAULT '1' COMMENT '库存',
  `status` tinyint DEFAULT '1' COMMENT '状态: 1-在售 0-下架',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_merchant_id` (`merchant_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='游戏商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_game_product`
--

LOCK TABLES `t_game_product` WRITE;
/*!40000 ALTER TABLE `t_game_product` DISABLE KEYS */;
INSERT INTO `t_game_product` VALUES (1,1,'Cyberpunk 2077','赛博朋克2077 - 全新未拆封','https://store.cloudflare.steamstatic.com/public/images/v6/home/cluster_01_3.jpg','RPG',NULL,199.0000,10,3,'2026-04-12 11:51:35','2026-04-19 16:32:52'),(2,1,'Elden Ring','艾尔登法环 - Steam正品CD-Key','https://store.cloudflare.steamstatic.com/public/images/v6/home/cluster_02_3.jpg','RPG',NULL,268.0000,5,1,'2026-04-12 11:51:35','2026-04-12 11:51:35'),(3,1,'Baldurs Gate 3','博德之门3 - 终极版','https://store.cloudflare.steamstatic.com/public/images/v6/home/cluster_03_3.jpg','RPG',NULL,398.0000,3,1,'2026-04-12 11:51:35','2026-04-12 11:51:35'),(4,1,'GTA V','侠盗猎车手V - 豪华版','https://store.cloudflare.steamstatic.com/public/images/v6/home/cluster_04_3.jpg','RPG',NULL,128.0000,20,1,'2026-04-12 11:51:35','2026-04-12 11:51:35'),(5,1,'Red Dead Redemption 2','荒野大镖客2 - 终极版','https://store.cloudflare.steamstatic.com/public/images/v6/home/cluster_05_3.jpg','RPG',NULL,299.0000,8,1,'2026-04-12 11:51:35','2026-04-12 11:51:35'),(6,1,'The Witcher 3','巫师3 - 完整版','https://store.cloudflare.steamstatic.com/public/images/v6/home/cluster_06_3.jpg','RPG',NULL,99.0000,15,1,'2026-04-12 11:51:35','2026-04-12 11:51:35'),(7,1,'God of War','战神 - 全新未拆封','https://store.cloudflare.steamstatic.com/public/images/v6/home/cluster_07_3.jpg','动作冒险',NULL,189.0000,8,1,'2026-04-12 11:58:43','2026-04-12 11:58:43'),(8,1,'Spider-Man Remastered','漫威蜘蛛侠 - 重置版','https://store.cloudflare.steamstatic.com/public/images/v6/home/cluster_08_3.jpg','动作冒险',NULL,298.0000,5,1,'2026-04-12 11:58:43','2026-04-12 11:58:43'),(9,1,'Call of Duty','使命召唤 - 现代战争','https://store.cloudflare.steamstatic.com/public/images/v6/home/cluster_09_3.jpg','射击',NULL,358.0000,12,1,'2026-04-12 11:58:43','2026-04-12 11:58:43'),(10,1,'Counter-Strike 2','反恐精英2 - 全球攻势','https://store.cloudflare.steamstatic.com/public/images/v6/home/cluster_10_3.jpg','射击',NULL,148.0000,20,1,'2026-04-12 11:58:43','2026-04-12 11:58:43'),(11,1,'Civilization VI','文明6 - 完整版','https://store.cloudflare.steamstatic.com/public/images/v6/home/cluster_11_3.jpg','策略',NULL,198.0000,6,1,'2026-04-12 11:58:43','2026-04-12 11:58:43'),(12,1,'Age of Empires IV','帝国时代4','https://store.cloudflare.steamstatic.com/public/images/v6/home/cluster_12_3.jpg','策略',NULL,168.0000,10,1,'2026-04-12 11:58:43','2026-04-12 11:58:43'),(13,1,'EA FC 24','EA FC 24 - 终极版','https://store.cloudflare.steamstatic.com/public/images/v6/home/cluster_13_3.jpg','体育',NULL,468.0000,5,1,'2026-04-12 11:58:43','2026-04-12 11:58:43'),(14,1,'NBA 2K24','NBA 2K24 - 收藏版','https://store.cloudflare.steamstatic.com/public/images/v6/home/cluster_14_3.jpg','体育',NULL,298.0000,8,1,'2026-04-12 11:58:43','2026-04-12 11:58:43'),(15,6,'Cyberpunk 2077','经典科幻RPG游戏','https://img12.360buyimg.com/n1/jfs/t1/336831/37/8389/111470/68be8c61F139f7208/d12b597d95bb6b67.jpg','RPG',NULL,29.9000,1,1,'2026-04-12 18:55:57','2026-04-12 18:55:57'),(16,6,'Elden Ring','黑暗奇幻动作RPG','https://p.cgmol.com/20231206/b_679282_11144271158456027.png','RPG',NULL,39.9000,1,1,'2026-04-12 18:55:57','2026-04-12 18:55:57'),(17,6,'Baldurs Gate 3','经典策略RPG游戏','https://p8.itc.cn/images01/20210716/73d677a3f78d437380d0fc73783163f0.jpeg','RPG',NULL,59.9000,1,1,'2026-04-12 18:55:57','2026-04-12 18:55:57'),(18,6,'GTA V','开放世界动作冒险游戏','https://p5.itc.cn/images01/20210716/24b81b2c13b04bfd9f5bfa498018ac54.png','OPEN_WORLD',NULL,19.9000,1,1,'2026-04-12 18:55:57','2026-04-12 18:55:57'),(19,6,'Red Dead Redemption 2','西部开放世界游戏','https://p2.itc.cn/images01/20210716/7617e6b74d6442e499640afef737e863.png','OPEN_WORLD',NULL,49.9000,1,1,'2026-04-12 18:55:57','2026-04-12 18:55:57'),(20,6,'The Witcher 3','经典开放世界RPG','https://p7.itc.cn/images01/20210716/290e0e31f1754a688312df240660eccd.jpeg','RPG',NULL,24.9000,1,1,'2026-04-12 18:55:57','2026-04-12 18:55:57');
/*!40000 ALTER TABLE `t_game_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_merchant`
--

DROP TABLE IF EXISTS `t_merchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_merchant` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商家ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `shop_name` varchar(100) NOT NULL COMMENT '店铺名称',
  `description` text COMMENT '店铺描述',
  `contact` varchar(100) DEFAULT NULL COMMENT '联系方式',
  `balance` decimal(18,2) DEFAULT '0.00' COMMENT '账户余额',
  `pending_settlement` decimal(18,2) DEFAULT '0.00' COMMENT '待结算金额',
  `status` tinyint DEFAULT '1' COMMENT '状态: 1-正常 0-冻结',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  UNIQUE KEY `uk_shop_name` (`shop_name`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商家表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_merchant`
--

LOCK TABLES `t_merchant` WRITE;
/*!40000 ALTER TABLE `t_merchant` DISABLE KEYS */;
INSERT INTO `t_merchant` VALUES (6,1,'ETH Store','以太坊优质商户',NULL,0.00,0.00,1,'2026-04-12 18:42:53','2026-04-12 18:42:53'),(11,2,'BSC Store','币安智能链优质商户',NULL,0.00,0.00,1,'2026-04-12 18:43:18','2026-04-12 18:43:18'),(12,3,'Polygon Store','Polygon优质商户',NULL,0.00,0.00,1,'2026-04-12 18:43:18','2026-04-12 18:43:18'),(13,4,'Arbitrum Store','Arbitrum优质商户',NULL,0.00,0.00,1,'2026-04-12 18:43:18','2026-04-12 18:43:18'),(14,5,'Optimism Store','Optimism优质商户',NULL,0.00,0.00,1,'2026-04-12 18:43:18','2026-04-12 18:43:18');
/*!40000 ALTER TABLE `t_merchant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_merchant_apply`
--

DROP TABLE IF EXISTS `t_merchant_apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_merchant_apply` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `shop_name` varchar(100) NOT NULL,
  `description` text,
  `contact` varchar(100) DEFAULT NULL,
  `license_url` varchar(500) DEFAULT NULL,
  `id_card_url` varchar(500) DEFAULT NULL,
  `status` tinyint DEFAULT '0' COMMENT '0-待审核 1-通过 2-拒绝',
  `reject_reason` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商户申请表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_merchant_apply`
--

LOCK TABLES `t_merchant_apply` WRITE;
/*!40000 ALTER TABLE `t_merchant_apply` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_merchant_apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_message`
--

DROP TABLE IF EXISTS `t_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sender_id` bigint DEFAULT NULL COMMENT '发送者ID',
  `receiver_id` bigint NOT NULL COMMENT '接收者ID',
  `product_id` bigint DEFAULT NULL COMMENT '商品ID',
  `product_type` varchar(20) DEFAULT NULL COMMENT '商品类型',
  `content` text COMMENT '消息内容',
  `status` tinyint DEFAULT '0' COMMENT '状态: 0-未读 1-已读',
  `message_type` varchar(20) DEFAULT 'SYSTEM' COMMENT '消息类型: SYSTEM-系统通知 ORDER-订单通知 OFFER-议价通知',
  `related_id` bigint DEFAULT NULL COMMENT '关联ID',
  `is_read` tinyint DEFAULT '0' COMMENT '是否已读: 0-未读 1-已读',
  `title` varchar(200) DEFAULT NULL COMMENT '消息标题',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_receiver_id` (`receiver_id`),
  KEY `idx_sender_id` (`sender_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_message`
--

LOCK TABLES `t_message` WRITE;
/*!40000 ALTER TABLE `t_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_nft_product`
--

DROP TABLE IF EXISTS `t_nft_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_nft_product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `merchant_id` bigint NOT NULL COMMENT '商家ID',
  `name` varchar(200) NOT NULL COMMENT '商品名称',
  `description` text COMMENT '商品描述',
  `image` varchar(500) DEFAULT NULL COMMENT '商品图片',
  `contract_address` varchar(100) DEFAULT NULL COMMENT 'NFT合约地址',
  `token_id` varchar(100) DEFAULT NULL COMMENT 'Token ID',
  `chain` varchar(20) DEFAULT 'ETH' COMMENT '区块链: ETH, BSC, POLYGON',
  `price` decimal(18,4) NOT NULL COMMENT '价格(USDT)',
  `stock` int DEFAULT '1' COMMENT '库存',
  `status` tinyint DEFAULT '1' COMMENT '状态: 1-在售 0-下架',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `rarity` int DEFAULT '1' COMMENT '稀有度: 1-普通 2-稀有 3-史诗 4-传说 5-神话',
  `wear_value` decimal(3,2) DEFAULT '0.00' COMMENT '磨损值: 0.0-1.0',
  PRIMARY KEY (`id`),
  KEY `idx_merchant_id` (`merchant_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='NFT商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_nft_product`
--

LOCK TABLES `t_nft_product` WRITE;
/*!40000 ALTER TABLE `t_nft_product` DISABLE KEYS */;
INSERT INTO `t_nft_product` VALUES (1,6,'CryptoPunk #7804','稀有朋克头像，全球限量的经典NFT艺术品','https://cryptopunks.app/cryptopunks/cryptopunk7804.png','0xb47e3cd837dDF8e4c57F05d70Ab865de6e193BBB','7804','ETH',2.5000,1,3,'2026-04-12 18:43:42','2026-04-19 16:33:10',5,0.00),(2,6,'Bored Ape #3285','无聊猿俱乐部成员，拥有独特的随机特征','https://ipfs.io/ipfs/QmRRPWG96cmgTn2qSzjwr2qvfNEuhunv6FNeMFGa9bx6mQ','0xBC4CA0EdA7647A8aB7C2061c2E118A18a936f13D','3285','ETH',3.2000,1,1,'2026-04-12 18:43:42','2026-04-12 21:26:32',5,0.00),(3,11,'Mobox NFT Hero #1024','MOBOX平台的NFT英雄角色','https://ipfs.io/ipfs/QmHero1024','0x0fE2D8d1BbC4B9e18F18D1eB0c6cE2dF6D8E5C3','1024','BSC',0.8000,1,1,'2026-04-12 18:43:42','2026-04-12 21:11:41',4,0.00),(4,11,'Binemon NFT #567','Binemon世界的数字宠物NFT','https://ipfs.io/ipfs/QmBinemon567','0x0c8f7b8A5E2D8d1F3E2D9c2B5a7F6e8D9c3B4a5','567','BSC',0.5000,1,1,'2026-04-12 18:43:42','2026-04-12 21:11:41',4,0.00),(5,12,'Decentraland Parcel #1001','Decentraland虚拟土地','https://ipfs.io/ipfs/QmParcel1001','0x959e104E1a4dB6317fA58F8295F586e1A978c297','1001','POLYGON',1.5000,1,1,'2026-04-12 18:43:42','2026-04-12 21:11:41',3,0.00),(6,12,'OpenSea SeaDrop #888','OpenSea系列的稀有NFT','https://ipfs.io/ipfs/QmSeaDrop888','0x34d85c9C1B4e2aD1Ae7a2eC8E1dF6cB3A9e8D7c6','888','POLYGON',0.6000,1,1,'2026-04-12 18:43:42','2026-04-12 21:11:41',3,0.00),(7,13,'Arbitrum Odyssey #2024','Arbitrum生态的纪念NFT','https://ipfs.io/ipfs/QmOdyssey2024','0x1e6d6B5d6F7e8D9c0B3a4F5e6D7c8B9a0F1e2D3','2024','ARBITRUM',0.3000,1,1,'2026-04-12 18:43:42','2026-04-12 21:11:41',3,0.00),(8,13,'GMX Hero Collection #77','GMX系列英雄NFT','https://ipfs.io/ipfs/QmGMXHero77','0x2f7d8e6C5B4a3F2E1D0c9B8A7F6E5D4C3B2A1F0','77','ARBITRUM',0.4000,1,1,'2026-04-12 18:43:42','2026-04-12 21:11:41',3,0.00),(9,14,'Velodrome NFT #3030','Velodrome流动性NFT','https://ipfs.io/ipfs/QmVelodrome3030','0x3e8F7D6E5B4A2F1D0C9E8B7A6F5D4C3B2A1E0D9','3030','OPTIMISM',0.2500,1,3,'2026-04-12 18:43:42','2026-04-12 21:11:41',2,0.00),(10,14,'Optimism Quest Badge #99','Optimism生态任务徽章','https://ipfs.io/ipfs/QmQuestBadge99','0x4f9E8D7C6B5A3F2E1D0C9E8B7A6F5D4C3B2A1E0','99','OPTIMISM',0.1500,1,1,'2026-04-12 18:43:42','2026-04-12 21:26:43',1,0.00),(11,1,'1','永远的神','https://www.shutterstock.com/zh/search/%E5%88%80%E6%9E%AA','','12','BSC',24.9900,1,3,'2026-04-12 21:37:55','2026-04-12 21:37:55',1,0.00);
/*!40000 ALTER TABLE `t_nft_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order`
--

DROP TABLE IF EXISTS `t_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `merchant_id` bigint NOT NULL COMMENT '商家ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `product_type` varchar(20) NOT NULL COMMENT '商品类型: NFT, GAME',
  `amount` decimal(18,4) NOT NULL COMMENT '订单金额',
  `pay_type` varchar(20) DEFAULT NULL COMMENT '支付方式: USDT, WECHAT, ALIPAY',
  `tx_hash` varchar(100) DEFAULT NULL COMMENT '区块链交易哈希',
  `status` tinyint DEFAULT '0' COMMENT '状态: 0-待支付 1-已支付 2-已完成 3-已取消',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_merchant_id` (`merchant_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order`
--

LOCK TABLES `t_order` WRITE;
/*!40000 ALTER TABLE `t_order` DISABLE KEYS */;
INSERT INTO `t_order` VALUES (1,'ORD17759674456798850',7,1,8,'GAME',298.0000,'USDT',NULL,3,'2026-04-12 12:17:25','2026-04-12 12:17:25'),(2,'ORD1776062548432967',7,11,3,'NFT',0.8000,'USDT',NULL,3,'2026-04-13 14:42:28','2026-04-13 14:42:28'),(3,'ORD17760625730165349',7,11,4,'NFT',0.5000,'USDT',NULL,3,'2026-04-13 14:42:53','2026-04-13 14:42:53');
/*!40000 ALTER TABLE `t_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_config`
--

DROP TABLE IF EXISTS `t_system_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_system_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `config_key` varchar(50) NOT NULL COMMENT '配置键',
  `config_value` text COMMENT '配置值',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_config_key` (`config_key`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_config`
--

LOCK TABLES `t_system_config` WRITE;
/*!40000 ALTER TABLE `t_system_config` DISABLE KEYS */;
INSERT INTO `t_system_config` VALUES (1,'platform_name','零壹Web3游戏资产交易平台','平台名称','2026-04-12 11:16:50','2026-04-12 11:16:50'),(2,'platform_fee','2.5','交易手续费百分比','2026-04-12 11:16:50','2026-04-12 11:16:50'),(3,'min_withdraw','10','最低提现金额','2026-04-12 11:16:50','2026-04-12 11:16:50'),(4,'default_chain','ETH','默认区块链','2026-04-12 11:16:50','2026-04-12 11:16:50'),(5,'nft_contract','','NFT合约地址','2026-04-12 11:16:50','2026-04-12 11:16:50');
/*!40000 ALTER TABLE `t_system_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_ticket`
--

DROP TABLE IF EXISTS `t_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_ticket` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ticket_no` varchar(50) NOT NULL,
  `user_id` bigint NOT NULL,
  `merchant_id` bigint DEFAULT NULL,
  `type` varchar(20) NOT NULL COMMENT 'CONSULT-咨询 COMPLAINT-投诉 AFTER_SALES-售后',
  `title` varchar(200) NOT NULL,
  `content` text NOT NULL,
  `status` tinyint DEFAULT '0' COMMENT '0-待处理 1-处理中 2-已完成 3-已关闭',
  `admin_id` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_merchant_id` (`merchant_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_ticket`
--

LOCK TABLES `t_ticket` WRITE;
/*!40000 ALTER TABLE `t_ticket` DISABLE KEYS */;
INSERT INTO `t_ticket` VALUES (1,'AP1775992558991',1,6,'申诉','NFT商品申诉 - ID:2','商品ID: 2\n商品类型: NFT\n申诉原因: 商品审核被拒绝，希望申诉重新审核',0,NULL,'2026-04-12 19:15:59','2026-04-12 19:15:59');
/*!40000 ALTER TABLE `t_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_ticket_reply`
--

DROP TABLE IF EXISTS `t_ticket_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_ticket_reply` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ticket_id` bigint NOT NULL,
  `sender_id` bigint NOT NULL,
  `sender_type` varchar(20) DEFAULT NULL COMMENT 'USER,MERCHANT,ADMIN',
  `content` text NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_ticket_id` (`ticket_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工单回复表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_ticket_reply`
--

LOCK TABLES `t_ticket_reply` WRITE;
/*!40000 ALTER TABLE `t_ticket_reply` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_ticket_reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_transaction`
--

DROP TABLE IF EXISTS `t_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '交易ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `type` varchar(20) NOT NULL COMMENT '类型: IN-充值, OUT-提现',
  `amount` decimal(18,8) NOT NULL COMMENT '金额',
  `tx_hash` varchar(100) DEFAULT NULL COMMENT '交易哈希',
  `status` tinyint DEFAULT '0' COMMENT '状态: 0-处理中 1-已完成 2-失败',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='交易记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_transaction`
--

LOCK TABLES `t_transaction` WRITE;
/*!40000 ALTER TABLE `t_transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `wallet_address` varchar(100) DEFAULT NULL COMMENT '钱包地址',
  `status` tinyint DEFAULT '1' COMMENT '状态: 1-正常 0-禁用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `role` varchar(20) DEFAULT 'USER' COMMENT '角色: USER-买家, MERCHANT-商户, ADMIN-管理员',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  UNIQUE KEY `uk_wallet` (`wallet_address`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'testuser1','test1@example.com',NULL,'123456','0x742d35Cc6634C0532925a3b844Bc9e7595f0fEb1',1,'2026-04-12 11:49:50','2026-04-12 11:49:50','USER'),(2,'testuser2','test2@example.com',NULL,'123456','0x8Ba1f109551bD432803012645Ac136ddd64DBA72',1,'2026-04-12 11:49:50','2026-04-12 11:49:50','USER'),(3,'merchant1','merchant1@example.com',NULL,'123456','0x1234567890abcdef1234567890abcdef12345678',1,'2026-04-12 11:49:50','2026-04-12 11:49:50','USER'),(7,'wallet_1082602901',NULL,NULL,'793faaa0b193112840d632af132c572b','0x3da7e368d6044dcc6b6ec27b5f7b623f98f90e9b',1,'2026-04-12 12:07:11','2026-04-12 12:07:11','USER');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_wallet`
--

DROP TABLE IF EXISTS `t_wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_wallet` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '钱包ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `address` varchar(100) NOT NULL COMMENT '钱包地址',
  `balance` decimal(18,8) DEFAULT '0.00000000' COMMENT '余额',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  UNIQUE KEY `uk_address` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='钱包表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_wallet`
--

LOCK TABLES `t_wallet` WRITE;
/*!40000 ALTER TABLE `t_wallet` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_wallet` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-21 13:53:43
