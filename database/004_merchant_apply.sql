-- 商户申请表
CREATE TABLE IF NOT EXISTS t_merchant_apply (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    shop_name VARCHAR(100) NOT NULL,
    description TEXT,
    contact VARCHAR(100),
    license_url VARCHAR(500),
    id_card_url VARCHAR(500),
    status TINYINT DEFAULT 0 COMMENT '0-待审核 1-通过 2-拒绝',
    reject_reason VARCHAR(500),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户申请表';
