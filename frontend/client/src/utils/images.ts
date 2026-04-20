// 游戏道具/装备相关默认图片
const gameImages = [
  // 武士刀
  'https://img12.360buyimg.com/n1/jfs/t1/336831/37/8389/111470/68be8c61F139f7208/d12b597d95bb6b67.jpg',
  // 裁纸刀
  'https://p.cgmol.com/20231206/b_679282_11144271158456027.png',
  // 原神武器
  'https://p8.itc.cn/images01/20210716/73d677a3f78d437380d0fc73783163f0.jpeg',
  // 武器2
  'https://p5.itc.cn/images01/20210716/24b81b2c13b04bfd9f5bfa498018ac54.png',
  // 武器3
  'https://p2.itc.cn/images01/20210716/7617e6b74d6442e499640afef737e863.png',
  // 武器4
  'https://p7.itc.cn/images01/20210716/290e0e31f1754a688312df240660eccd.jpeg',
  // 武器5
  'https://p4.itc.cn/images01/20210716/332a727377cc43d6abccf0e09e27bfdb.jpeg',
  // 武器6
  'https://p1.itc.cn/images01/20210716/c5cc0f0f2a534a27b59dc5621079ab06.jpeg'
]

// NFT/数字艺术品相关默认图片 - 枪械武器
const nftImages = [
  // SPAS-12霰弹枪
  'https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Franchi_SPAS_12_Grip_Safety.jpeg/330px-Franchi_SPAS_12_Grip_Safety.jpeg',
  // SPAS-12木制枪托
  'https://upload.wikimedia.org/wikipedia/commons/thumb/1/1d/Franchi_SPAS_12_Shotgun.JPG/330px-Franchi_SPAS_12_Shotgun.JPG',
  // 游戏武器1
  'https://n.sinaimg.cn/games/transform/781/w500h281/20230822/8580-da6a0cacd607488fcf269e5bba118a4e.png',
  // 游戏武器2
  'https://n.sinaimg.cn/games/transform/782/w500h282/20230822/5523-23191775fc3276ebba6005f245910144.png',
  // 游戏武器3
  'https://n.sinaimg.cn/games/transform/781/w500h281/20230822/1b33-c92f3240ca183f2896c3271a9128d3b3.png',
  // 游戏武器4
  'https://n.sinaimg.cn/games/transform/781/w500h281/20230822/ae70-07bb7d325e12eec161b4df0383f6f68a.png',
  // 游戏武器5
  'https://n.sinaimg.cn/games/transform/781/w500h281/20230822/aeea-4b80e3a269ab3b886a952532478a69de.png',
  // 游戏武器6
  'https://n.sinaimg.cn/games/transform/781/w500h281/20230822/c563-c2c131c53fbeceb7a216e389ded53fdd.png'
]

// 稀有度等级定义
export type RarityLevel = 1 | 2 | 3 | 4 | 5

export interface RarityInfo {
  level: RarityLevel
  name: string
  nameEn: string
  color: string
  bgColor: string
}

// 稀有度配置
export const RARITY_CONFIG: Record<RarityLevel, RarityInfo> = {
  1: { level: 1, name: '普通', nameEn: 'Common', color: '#9CA3AF', bgColor: 'rgba(156, 163, 175, 0.9)' },
  2: { level: 2, name: '稀有', nameEn: 'Uncommon', color: '#22C55E', bgColor: 'rgba(34, 197, 94, 0.9)' },
  3: { level: 3, name: '史诗', nameEn: 'Epic', color: '#A855F7', bgColor: 'rgba(168, 85, 247, 0.9)' },
  4: { level: 4, name: '传说', nameEn: 'Legendary', color: '#F59E0B', bgColor: 'rgba(245, 158, 11, 0.9)' },
  5: { level: 5, name: '神话', nameEn: 'Mythic', color: '#EF4444', bgColor: 'rgba(239, 68, 68, 0.9)' }
}

// 根据ID获取稀有度等级（1-5）
export function getRarityLevel(id?: number): RarityLevel {
  if (!id) return 1
  // 使用ID对5取余+1来分配稀有度
  return ((id - 1) % 5 + 1) as RarityLevel
}

// 获取稀有度信息
export function getRarityInfo(id?: number): RarityInfo {
  const level = getRarityLevel(id)
  return RARITY_CONFIG[level]
}

// 获取游戏商品默认图片
export function getGameImage(id?: number): string {
  if (!id) return gameImages[0]
  return gameImages[id % gameImages.length]
}

// 获取NFT商品默认图片
export function getNftImage(id?: number): string {
  if (!id) return nftImages[0]
  return nftImages[id % nftImages.length]
}
