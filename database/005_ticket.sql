-- 工单表
CREATE TABLE IF NOT EXISTS t_ticket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ticket_no VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL,
    merchant_id BIGINT,
    type VARCHAR(20) NOT NULL COMMENT 'CONSULT-咨询 COMPLAINT-投诉 AFTER_SALES-售后',
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    status TINYINT DEFAULT 0 COMMENT '0-待处理 1-处理中 2-已完成 3-已关闭',
    admin_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_user_id (user_id),
    KEY idx_merchant_id (merchant_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单表';

-- 工单回复表
CREATE TABLE IF NOT EXISTS t_ticket_reply (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ticket_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    sender_type VARCHAR(20) COMMENT 'USER,MERCHANT,ADMIN',
    content TEXT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    KEY idx_ticket_id (ticket_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工单回复表';
