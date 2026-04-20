package com.web3.marketplace.merchant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web3.marketplace.common.result.PageResult;
import com.web3.marketplace.common.result.Result;
import com.web3.marketplace.core.dto.GameProductDTO;
import com.web3.marketplace.core.dto.NftProductDTO;
import com.web3.marketplace.core.entity.GameProduct;
import com.web3.marketplace.core.entity.NftProduct;
import com.web3.marketplace.core.entity.Ticket;
import com.web3.marketplace.core.mapper.TicketMapper;
import com.web3.marketplace.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/merchant/product")
@RequiredArgsConstructor
public class ProductManageController {

    private final ProductService productService;
    private final TicketMapper ticketMapper;

    @PostMapping("/nft/create")
    public Result<NftProduct> createNftProduct(@RequestBody NftProduct product) {
        return Result.success(productService.createNftProduct(product));
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

    @GetMapping("/nft/list")
    public Result<PageResult<NftProductDTO>> getNftList(
            @RequestParam(required = false) Long merchantId,
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String chain,
            @RequestParam(required = false) Integer status) {

        Page<NftProduct> page = new Page<>(current, size);
        IPage<NftProductDTO> result = productService.getNftProductPageByMerchant(page, merchantId, keyword, chain, null, status);

        return Result.success(PageResult.of(
                result.getRecords(),
                result.getTotal(),
                result.getCurrent(),
                result.getSize()
        ));
    }

    // NFT重新上架（申请待审核）
    @PostMapping("/nft/{id}/reshelf")
    public Result<NftProduct> reshelfNftProduct(@PathVariable Long id) {
        return Result.success(productService.reshelfNftProduct(id));
    }

    @PostMapping("/game/create")
    public Result<GameProduct> createGameProduct(@RequestBody GameProduct product) {
        return Result.success(productService.createGameProduct(product));
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

    @GetMapping("/game/list")
    public Result<PageResult<GameProductDTO>> getGameList(
            @RequestParam(required = false) Long merchantId,
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer status) {

        Page<GameProduct> page = new Page<>(current, size);
        IPage<GameProductDTO> result = productService.getGameProductPageByMerchant(page, merchantId, keyword, category, status);

        return Result.success(PageResult.of(
                result.getRecords(),
                result.getTotal(),
                result.getCurrent(),
                result.getSize()
        ));
    }

    // 游戏重新上架（申请待审核）
    @PostMapping("/game/{id}/reshelf")
    public Result<GameProduct> reshelfGameProduct(@PathVariable Long id) {
        return Result.success(productService.approveGameProduct(id));
    }

    // 商品申诉
    @PostMapping("/appeal")
    public Result<Ticket> appealProduct(@RequestBody Map<String, Object> request) {
        Long productId = Long.valueOf(request.get("productId").toString());
        String productType = request.get("productType").toString();
        String reason = request.get("reason").toString();
        Long merchantId = Long.valueOf(request.get("merchantId").toString());

        Ticket ticket = new Ticket();
        ticket.setTicketNo("AP" + System.currentTimeMillis());
        ticket.setUserId(1L); // 默认用户
        ticket.setMerchantId(merchantId);
        ticket.setType("申诉");
        ticket.setTitle(productType + "商品申诉 - ID:" + productId);
        ticket.setContent("商品ID: " + productId + "\n商品类型: " + productType + "\n申诉原因: " + reason);
        ticket.setStatus(0); // 待处理
        ticket.setCreateTime(LocalDateTime.now());

        ticketMapper.insert(ticket);
        return Result.success(ticket);
    }
}
