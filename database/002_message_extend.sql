-- 消息表扩展字段
ALTER TABLE t_message ADD COLUMN message_type VARCHAR(20) DEFAULT 'SYSTEM' COMMENT '消息类型: SYSTEM-系统通知 ORDER-订单通知 OFFER-议价通知';
ALTER TABLE t_message ADD COLUMN related_id BIGINT COMMENT '关联ID';
ALTER TABLE t_message ADD COLUMN is_read TINYINT DEFAULT 0 COMMENT '是否已读: 0-未读 1-已读';
ALTER TABLE t_message ADD COLUMN title VARCHAR(200) COMMENT '消息标题';
