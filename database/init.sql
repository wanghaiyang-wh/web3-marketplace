-- 零壹Web3游戏资产交易平台 - 数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS web3_marketplace DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE web3_marketplace;

-- 用户表
CREATE TABLE IF NOT EXISTS t_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    wallet_address VARCHAR(100) COMMENT '钱包地址',
    status TINYINT DEFAULT 1 COMMENT '状态: 1-正常 0-禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_username (username),
    UNIQUE KEY uk_email (email),
    UNIQUE KEY uk_wallet (wallet_address)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 商家表
CREATE TABLE IF NOT EXISTS t_merchant (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '商家ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    shop_name VARCHAR(100) NOT NULL COMMENT '店铺名称',
    description TEXT COMMENT '店铺描述',
    contact VARCHAR(100) COMMENT '联系方式',
    balance DECIMAL(18, 2) DEFAULT 0.00 COMMENT '账户余额',
    pending_settlement DECIMAL(18, 2) DEFAULT 0.00 COMMENT '待结算金额',
    status TINYINT DEFAULT 1 COMMENT '状态: 1-正常 0-冻结',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_user_id (user_id),
    UNIQUE KEY uk_shop_name (shop_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家表';

-- NFT商品表
CREATE TABLE IF NOT EXISTS t_nft_product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '商品ID',
    merchant_id BIGINT NOT NULL COMMENT '商家ID',
    name VARCHAR(200) NOT NULL COMMENT '商品名称',
    description TEXT COMMENT '商品描述',
    image VARCHAR(500) COMMENT '商品图片',
    contract_address VARCHAR(100) COMMENT 'NFT合约地址',
    token_id VARCHAR(100) COMMENT 'Token ID',
    chain VARCHAR(20) DEFAULT 'ETH' COMMENT '区块链: ETH, BSC, POLYGON',
    price DECIMAL(18, 4) NOT NULL COMMENT '价格(USDT)',
    stock INT DEFAULT 1 COMMENT '库存',
    status TINYINT DEFAULT 1 COMMENT '状态: 1-在售 0-下架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_merchant_id (merchant_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='NFT商品表';

-- 游戏商品表
CREATE TABLE IF NOT EXISTS t_game_product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '商品ID',
    merchant_id BIGINT NOT NULL COMMENT '商家ID',
    name VARCHAR(200) NOT NULL COMMENT '商品名称',
    description TEXT COMMENT '商品描述',
    image VARCHAR(500) COMMENT '商品图片',
    category VARCHAR(50) COMMENT '游戏分类',
    cd_key TEXT COMMENT 'CD-Key(加密存储)',
    price DECIMAL(18, 4) NOT NULL COMMENT '价格(USDT)',
    stock INT DEFAULT 1 COMMENT '库存',
    status TINYINT DEFAULT 1 COMMENT '状态: 1-在售 0-下架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_merchant_id (merchant_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游戏商品表';

-- 订单表
CREATE TABLE IF NOT EXISTS t_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
    order_no VARCHAR(50) NOT NULL COMMENT '订单号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    merchant_id BIGINT NOT NULL COMMENT '商家ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    product_type VARCHAR(20) NOT NULL COMMENT '商品类型: NFT, GAME',
    amount DECIMAL(18, 4) NOT NULL COMMENT '订单金额',
    pay_type VARCHAR(20) COMMENT '支付方式: USDT, WECHAT, ALIPAY',
    tx_hash VARCHAR(100) COMMENT '区块链交易哈希',
    status TINYINT DEFAULT 0 COMMENT '状态: 0-待支付 1-已支付 2-已完成 3-已取消',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_order_no (order_no),
    KEY idx_user_id (user_id),
    KEY idx_merchant_id (merchant_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 钱包表
CREATE TABLE IF NOT EXISTS t_wallet (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '钱包ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    address VARCHAR(100) NOT NULL COMMENT '钱包地址',
    balance DECIMAL(18, 8) DEFAULT 0 COMMENT '余额',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_user_id (user_id),
    UNIQUE KEY uk_address (address)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='钱包表';

-- 交易记录表
CREATE TABLE IF NOT EXISTS t_transaction (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '交易ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    type VARCHAR(20) NOT NULL COMMENT '类型: IN-充值, OUT-提现',
    amount DECIMAL(18, 8) NOT NULL COMMENT '金额',
    tx_hash VARCHAR(100) COMMENT '交易哈希',
    status TINYINT DEFAULT 0 COMMENT '状态: 0-处理中 1-已完成 2-失败',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_user_id (user_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易记录表';

-- 管理员表
CREATE TABLE IF NOT EXISTS t_admin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '管理员ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    role VARCHAR(20) DEFAULT 'admin' COMMENT '角色: admin-超级管理员, operator-运营',
    status TINYINT DEFAULT 1 COMMENT '状态: 1-正常 0-禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

-- 系统配置表
CREATE TABLE IF NOT EXISTS t_system_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '配置ID',
    config_key VARCHAR(50) NOT NULL COMMENT '配置键',
    config_value TEXT COMMENT '配置值',
    description VARCHAR(200) COMMENT '描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- 插入默认管理员账户 (admin/123456)
INSERT INTO t_admin (username, password, role) VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', 'admin');

-- 插入默认系统配置
INSERT INTO t_system_config (config_key, config_value, description) VALUES
    ('platform_name', '零壹Web3游戏资产交易平台', '平台名称'),
    ('platform_fee', '2.5', '交易手续费百分比'),
    ('min_withdraw', '10', '最低提现金额'),
    ('default_chain', 'ETH', '默认区块链'),
    ('nft_contract', '', 'NFT合约地址');
