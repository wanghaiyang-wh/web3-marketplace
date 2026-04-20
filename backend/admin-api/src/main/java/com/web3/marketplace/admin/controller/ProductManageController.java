package com.web3.marketplace.admin.controller;

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

@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
public class ProductManageController {

    private final ProductService productService;

    @GetMapping("/nft/list")
    public Result<PageResult<NftProductDTO>> getNftList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String chain,
            @RequestParam(required = false) Integer status) {

        Page<NftProduct> page = new Page<>(current, size);
        IPage<NftProductDTO> result = productService.getNftProductPage(page, keyword, chain, null, null, null, status);

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

    @PutMapping("/nft/update")
    public Result<NftProduct> updateNftProduct(@RequestBody NftProduct product) {
        return Result.success(productService.updateNftProduct(product));
    }

    @DeleteMapping("/nft/{id}")
    public Result<Void> deleteNftProduct(@PathVariable Long id) {
        productService.deleteNftProduct(id);
        return Result.success();
    }

    // NFT审核
    @PostMapping("/nft/{id}/approve")
    public Result<NftProduct> approveNftProduct(@PathVariable Long id) {
        return Result.success(productService.approveNftProduct(id));
    }

    @PostMapping("/nft/{id}/reject")
    public Result<NftProduct> rejectNftProduct(@PathVariable Long id, @RequestParam String reason) {
        return Result.success(productService.rejectNftProduct(id, reason));
    }

    @PostMapping("/nft/{id}/offshelf")
    public Result<NftProduct> offshelfNftProduct(@PathVariable Long id) {
        NftProduct product = productService.rejectNftProduct(id, "管理员下架");
        product.setStatus(3); // 已下架
        return Result.success(productService.updateNftProduct(product));
    }

    @GetMapping("/game/list")
    public Result<PageResult<GameProductDTO>> getGameList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer status) {

        Page<GameProduct> page = new Page<>(current, size);
        IPage<GameProductDTO> result = productService.getGameProductPage(page, keyword, category, null, null, status);

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

    @PutMapping("/game/update")
    public Result<GameProduct> updateGameProduct(@RequestBody GameProduct product) {
        return Result.success(productService.updateGameProduct(product));
    }

    @DeleteMapping("/game/{id}")
    public Result<Void> deleteGameProduct(@PathVariable Long id) {
        productService.deleteGameProduct(id);
        return Result.success();
    }

    // 游戏审核
    @PostMapping("/game/{id}/approve")
    public Result<GameProduct> approveGameProduct(@PathVariable Long id) {
        return Result.success(productService.approveGameProduct(id));
    }

    @PostMapping("/game/{id}/reject")
    public Result<GameProduct> rejectGameProduct(@PathVariable Long id, @RequestParam String reason) {
        return Result.success(productService.rejectGameProduct(id, reason));
    }

    @PostMapping("/game/{id}/offshelf")
    public Result<GameProduct> offshelfGameProduct(@PathVariable Long id) {
        GameProduct product = productService.rejectGameProduct(id, "管理员下架");
        product.setStatus(3); // 已下架
        return Result.success(productService.updateGameProduct(product));
    }
}
