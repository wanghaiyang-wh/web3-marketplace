package com.web3.marketplace.common.constant;

public class StatusConstants {

    // 用户状态
    public static final int USER_STATUS_NORMAL = 1;
    public static final int USER_STATUS_DISABLED = 0;

    // 商家状态
    public static final int MERCHANT_STATUS_NORMAL = 1;
    public static final int MERCHANT_STATUS_FROZEN = 0;

    // 商品状态（审核相关）
    public static final int PRODUCT_STATUS_PENDING = 0;      // 待审核
    public static final int PRODUCT_STATUS_APPROVED = 1;    // 审核通过（已上架）
    public static final int PRODUCT_STATUS_REJECTED = 2;    // 审核拒绝
    public static final int PRODUCT_STATUS_OFF_SALE = 3;    // 已下架

    // 订单状态
    public static final int ORDER_STATUS_PENDING = 0;
    public static final int ORDER_STATUS_PAID = 1;
    public static final int ORDER_STATUS_COMPLETED = 2;
    public static final int ORDER_STATUS_CANCELLED = 3;

    // 交易状态
    public static final int TRANSACTION_STATUS_PROCESSING = 0;
    public static final int TRANSACTION_STATUS_COMPLETED = 1;
    public static final int TRANSACTION_STATUS_FAILED = 2;

    // 商品类型
    public static final String PRODUCT_TYPE_NFT = "NFT";
    public static final String PRODUCT_TYPE_GAME = "GAME";

    // 支付方式
    public static final String PAY_TYPE_USDT = "USDT";
    public static final String PAY_TYPE_WECHAT = "WECHAT";
    public static final String PAY_TYPE_ALIPAY = "ALIPAY";
}
