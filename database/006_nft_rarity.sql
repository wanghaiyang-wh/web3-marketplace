-- 添加rarity字段
ALTER TABLE t_nft_product ADD COLUMN rarity TINYINT DEFAULT NULL COMMENT '稀有度: 1-普通 2-稀有 3-史诗 4-传说 5-神话' AFTER chain;

-- 更新现有NFT数据设置稀有度 (每个链2条不同稀有度)
-- Ethereum (id=1,2)
UPDATE t_nft_product SET rarity = 1 WHERE id = 1;
UPDATE t_nft_product SET rarity = 3 WHERE id = 2;

-- BSC (id=3,4)
UPDATE t_nft_product SET rarity = 2 WHERE id = 3;
UPDATE t_nft_product SET rarity = 4 WHERE id = 4;

-- Polygon (id=5,6)
UPDATE t_nft_product SET rarity = 1 WHERE id = 5;
UPDATE t_nft_product SET rarity = 5 WHERE id = 6;

-- Arbitrum (id=7,8)
UPDATE t_nft_product SET rarity = 2 WHERE id = 7;
UPDATE t_nft_product SET rarity = 3 WHERE id = 8;

-- Optimism (id=9,10)
UPDATE t_nft_product SET rarity = 4 WHERE id = 9;
UPDATE t_nft_product SET rarity = 5 WHERE id = 10;
