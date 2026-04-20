-- 消息表
CREATE TABLE IF NOT EXISTS t_message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sender_id BIGINT COMMENT '发送者ID',
    receiver_id BIGINT NOT NULL COMMENT '接收者ID',
    product_id BIGINT COMMENT '商品ID',
    product_type VARCHAR(20) COMMENT '商品类型',
    content TEXT COMMENT '消息内容',
    status TINYINT DEFAULT 0 COMMENT '状态: 0-未读 1-已读',
    message_type VARCHAR(20) DEFAULT 'SYSTEM' COMMENT '消息类型: SYSTEM-系统通知 ORDER-订单通知 OFFER-议价通知',
    related_id BIGINT COMMENT '关联ID',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读: 0-未读 1-已读',
    title VARCHAR(200) COMMENT '消息标题',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    KEY idx_receiver_id (receiver_id),
    KEY idx_sender_id (sender_id),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息表';
