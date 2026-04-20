package com.web3.marketplace.client.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web3.marketplace.common.result.PageResult;
import com.web3.marketplace.common.result.Result;
import com.web3.marketplace.core.dto.GameProductDTO;
import com.web3.marketplace.core.dto.NftProductDTO;
import com.web3.marketplace.core.entity.GameProduct;
import com.web3.marketplace.core.entity.NftProduct;
import com.web3.marketplace.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/client/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/nft/list")
    public Result<PageResult<NftProductDTO>> getNftList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String chain,
            @RequestParam(required = false) Integer rarity,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "1") Integer status) {

        Page<NftProduct> page = new Page<>(current, size);
        IPage<NftProductDTO> result = productService.getNftProductPage(page, keyword, chain, rarity, minPrice, maxPrice, status);

        return Result.success(PageResult.of(
                result.getRecords(),
                result.getTotal(),
                result.getCurrent(),
                result.getSize()
        ));
    }

    @GetMapping("/nft/{id}")
    public Result<NftProductDTO> getNftDetail(@PathVariable Long id) {
        return Result.success(productService.getNftProductById(id));
    }

    @GetMapping("/game/list")
    public Result<PageResult<GameProductDTO>> getGameList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "1") Integer status) {

        Page<GameProduct> page = new Page<>(current, size);
        IPage<GameProductDTO> result = productService.getGameProductPage(page, keyword, category, minPrice, maxPrice, status);

        return Result.success(PageResult.of(
                result.getRecords(),
                result.getTotal(),
                result.getCurrent(),
                result.getSize()
        ));
    }

    @GetMapping("/game/{id}")
    public Result<GameProductDTO> getGameDetail(@PathVariable Long id) {
        return Result.success(productService.getGameProductById(id));
    }
}
