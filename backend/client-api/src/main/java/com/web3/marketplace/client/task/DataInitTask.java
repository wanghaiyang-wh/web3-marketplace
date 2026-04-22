package com.web3.marketplace.client.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.web3.marketplace.core.entity.Merchant;
import com.web3.marketplace.core.entity.NftProduct;
import com.web3.marketplace.core.mapper.MerchantMapper;
import com.web3.marketplace.core.mapper.NftProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitTask implements CommandLineRunner {

    private final MerchantMapper merchantMapper;
    private final NftProductMapper nftProductMapper;

    @Value("${app.init-data.enabled:true}")
    private boolean initDataEnabled;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (!initDataEnabled) {
            log.info("数据初始化任务已禁用");
            return;
        }
        initMerchants();
        initHotNFTs();
    }

    private void initMerchants() {
        // 获取现有商户数量，不足5个则补充
        List<Merchant> merchants = merchantMapper.selectList(null);

        // 创建测试商户
        List<Merchant> newMerchants = Arrays.asList(
            createMerchant("ETH Store", "以太坊优质商户"),
            createMerchant("BSC Store", "币安智能链优质商户"),
            createMerchant("Polygon Store", "Polygon优质商户"),
            createMerchant("Arbitrum Store", "Arbitrum优质商户"),
            createMerchant("Optimism Store", "Optimism优质商户")
        );

        for (Merchant m : newMerchants) {
            // 检查是否已存在同名商户
            LambdaQueryWrapper<Merchant> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Merchant::getShopName, m.getShopName());
            Long existCount = merchantMapper.selectCount(wrapper);
            if (existCount == 0) {
                merchantMapper.insert(m);
            }
        }

        log.info("商户数据初始化完成");
    }

    private Merchant createMerchant(String shopName, String description) {
        Merchant merchant = new Merchant();
        merchant.setUserId(1L);
        merchant.setShopName(shopName);
        merchant.setDescription(description);
        merchant.setStatus(1);
        merchant.setCreateTime(LocalDateTime.now());
        return merchant;
    }

    private void initHotNFTs() {
        // 检查是否已有NFT数据
        Long count = nftProductMapper.selectCount(null);
        if (count > 0) {
            log.info("NFT数据已存在，跳过初始化");
            return;
        }

        // 获取所有商户
        List<Merchant> merchants = merchantMapper.selectList(null);
        if (merchants.size() < 5) {
            log.warn("商户数据不足5个，无法初始化完整NFT数据");
            return;
        }

        // 每个链2条热门NFT数据
        List<NftProduct> nftProducts = Arrays.asList(
            // Ethereum
            createNFT(merchants.get(0).getId(), "CryptoPunk #7804", "稀有朋克头像，全球限量的经典NFT艺术品",
                "0xb47e3cd837dDF8e4c57F05d70Ab865de6e193BBB", "7804", "ETH",
                new BigDecimal("2.5"), "https://cryptopunks.app/cryptopunks/cryptopunk7804.png"),
            createNFT(merchants.get(0).getId(), "Bored Ape #3285", "无聊猿俱乐部成员，拥有独特的随机特征",
                "0xBC4CA0EdA7647A8aB7C2061c2E118A18a936f13D", "3285", "ETH",
                new BigDecimal("3.2"), "https://ipfs.io/ipfs/QmRRPWG96cmgTn2qSzjwr2qvfNEuhunv6FNeMFGa9bx6mQ"),

            // BSC
            createNFT(merchants.get(1).getId(), "Mobox NFT Hero #1024", "MOBOX平台的NFT英雄角色",
                "0x0fE2D8d1BbC4B9e18F18D1eB0c6cE2dF6D8E5C3", "1024", "BSC",
                new BigDecimal("0.8"), "https://ipfs.io/ipfs/QmHero1024"),
            createNFT(merchants.get(1).getId(), "Binemon NFT #567", "Binemon世界的数字宠物NFT",
                "0x0c8f7b8A5E2D8d1F3E2D9c2B5a7F6e8D9c3B4a5", "567", "BSC",
                new BigDecimal("0.5"), "https://ipfs.io/ipfs/QmBinemon567"),

            // Polygon
            createNFT(merchants.get(2).getId(), "Decentraland Parcel #1001", "Decentraland虚拟土地",
                "0x959e104E1a4dB6317fA58F8295F586e1A978c297", "1001", "POLYGON",
                new BigDecimal("1.5"), "https://ipfs.io/ipfs/QmParcel1001"),
            createNFT(merchants.get(2).getId(), "OpenSea SeaDrop #888", "OpenSea系列的稀有NFT",
                "0x34d85c9C1B4e2aD1Ae7a2eC8E1dF6cB3A9e8D7c6", "888", "POLYGON",
                new BigDecimal("0.6"), "https://ipfs.io/ipfs/QmSeaDrop888"),

            // Arbitrum
            createNFT(merchants.size() > 3 ? merchants.get(3).getId() : merchants.get(0).getId(),
                "Arbitrum Odyssey #2024", "Arbitrum生态的纪念NFT",
                "0x1e6d6B5d6F7e8D9c0B3a4F5e6D7c8B9a0F1e2D3", "2024", "ARBITRUM",
                new BigDecimal("0.3"), "https://ipfs.io/ipfs/QmOdyssey2024"),
            createNFT(merchants.size() > 3 ? merchants.get(3).getId() : merchants.get(0).getId(),
                "GMX Hero Collection #77", "GMX系列英雄NFT",
                "0x2f7d8e6C5B4a3F2E1D0c9B8A7F6E5D4C3B2A1F0", "77", "ARBITRUM",
                new BigDecimal("0.4"), "https://ipfs.io/ipfs/QmGMXHero77"),

            // Optimism
            createNFT(merchants.size() > 4 ? merchants.get(4).getId() : merchants.get(0).getId(),
                "Velodrome NFT #3030", "Velodrome流动性NFT",
                "0x3e8F7D6E5B4A2F1D0C9E8B7A6F5D4C3B2A1E0D9", "3030", "OPTIMISM",
                new BigDecimal("0.25"), "https://ipfs.io/ipfs/QmVelodrome3030"),
            createNFT(merchants.size() > 4 ? merchants.get(4).getId() : merchants.get(0).getId(),
                "Optimism Quest Badge #99", "Optimism生态任务徽章",
                "0x4f9E8D7C6B5A3F2E1D0C9E8B7A6F5D4C3B2A1E0", "99", "OPTIMISM",
                new BigDecimal("0.15"), "https://ipfs.io/ipfs/QmQuestBadge99")
        );

        nftProducts.forEach(nftProductMapper::insert);
        log.info("初始化热门NFT数据完成: {} 条", nftProducts.size());
    }

    private NftProduct createNFT(Long merchantId, String name, String description,
                                  String contractAddress, String tokenId, String chain,
                                  BigDecimal price, String image) {
        NftProduct nft = new NftProduct();
        nft.setMerchantId(merchantId);
        nft.setName(name);
        nft.setDescription(description);
        nft.setContractAddress(contractAddress);
        nft.setTokenId(tokenId);
        nft.setChain(chain);
        nft.setPrice(price);
        nft.setStock(1);
        nft.setStatus(1); // 已审核通过
        nft.setImage(image);
        nft.setCreateTime(LocalDateTime.now());
        return nft;
    }
}
