package com.web3.marketplace.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web3.marketplace.core.entity.Cart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {
}
