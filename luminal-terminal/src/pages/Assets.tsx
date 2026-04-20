import React from 'react';
import { 
  Package, 
  Link as LinkIcon, 
  MoreVertical, 
  CheckCircle, 
  Filter, 
  Plus, 
  Star,
  Users
} from 'lucide-react';
import { motion } from 'motion/react';

const assets = [
  { name: '先锋军刀 #102', category: '神话武器', network: 'POLYGON / 0X...3E2', price: '1.24 ETH', status: '已上架', image: 'https://lh3.googleusercontent.com/aida-public/AB6AXuC7IRf-Md5yAsOVc2U1TWDWo4ek_6HCKBx0TT8QLmIlrYJVl0sghcfbplp2-dbO8WBKZ5BGMy2ngnpPEvb87GKosagX9nCcy1b7hClUWdLptvnHaa3Dct2JbtxAfa_tJ6aUTe73R1uPCXaG_C1F_3pEQVC1eV8gaMYalg7RacGUF3DgSxKBec4m2zpUM7p5-q_y48jumXbNpdUuW-pjgEusLlnQvTAWSrvFwM5AkjPaOCExihyXV9lfkDrXjuDrQn78PZe_0zxD7dXP' },
  { name: '影之护盾', category: '稀有防具', network: 'ETHEREUM / 0X...A1C', price: '0.52 ETH', status: '托管中', image: 'https://lh3.googleusercontent.com/aida-public/AB6AXuC_jaiN0UOYXasJe-RtT8aRiCM6Ntx6ydpydxSNt_XwmPmxzDeedYtJPxWbB3zRMjU4OMaJxDxB_zv-PMEP4yYEpJ95foPqe5bCEDzN6Z__lNYra6P1y8My3TYzauYJ9mmz1UPfS1dcYyjwE8cViWV4H_RkZuixIWH-1Yc_mDoCvVEV5W2nSqFcYaMYG7GttSmZsQVKZkcXLH_bEyIKtw57Y9_EXk-usYGyxUVr_pI-3atFFhVYR_9zvnOviIUWlmDMjvMaiaB-od2Q' },
  { name: '量子核心', category: '史诗插件', network: 'ARBITRUM / 0X...99F', price: '0.88 ETH', status: '已上架', image: 'https://lh3.googleusercontent.com/aida-public/AB6AXuBTveLrsKKv2zAmsmQJBH-eOm-nv8oYDp9fgwVEQ3JoWCbY9YWoyXZTOtk93iomj0_YdzgQeiYScmPmzYUL-HoVCxxgb57Xl63WipW_23mZQBgKahNdp-JBVJ56owxbnswkjnwojrsuOIAH882upK8sPYGKcLGASiJWooM_NAU1Z0diIqmD644YD2PAzdxg-hRc3QEdvic1jaNO0voOMENxMnh07gOPJniF3FnEcrIEcD-X6FFuL1aMqJdZRwC4Q9P1ZDcDYixEIjBT' },
];

const sellers = [
  { name: 'ZeroX_Nexus', id: 'SL-9012', rank: '精英卖家', sales: '1,402 资产', penalty: '0 记录', stars: 4, color: 'from-tertiary to-primary' },
  { name: 'Krono_Forge', id: 'SL-2281', rank: '审核中', sales: '89 资产', penalty: '3 记录', stars: 2, color: 'bg-surface-container-highest', error: true },
  { name: 'Velo_Auction', id: 'SL-4432', rank: '已认证', sales: '412 资产', penalty: '0 记录', stars: 3, color: 'from-secondary to-primary-dim' },
];

export default function Assets() {
  return (
    <motion.div 
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      className="space-y-8"
    >
      <div className="mb-10 flex items-end justify-between">
        <div>
          <h1 className="font-headline text-3xl font-bold tracking-tight text-on-surface">资产与卖家<span className="text-secondary">生态系统</span></h1>
          <p className="font-body text-on-surface-variant text-sm mt-1">多链游戏库存和商家声誉的高级管理系统。</p>
        </div>
        <div className="flex gap-4">
          <button className="bg-surface-container-high hover:bg-surface-variant text-on-surface text-xs font-label px-4 py-2 flex items-center gap-2 border border-outline-variant/30 transition-all">
            <Filter className="w-4 h-4" />
            高级筛选
          </button>
          <button className="bg-gradient-to-r from-primary to-primary-container text-on-primary-container text-xs font-bold font-label px-6 py-2 rounded-md tracking-wider">
            新增挂单
          </button>
        </div>
      </div>

      <div className="grid grid-cols-12 gap-6">
        {/* Main Table */}
        <section className="col-span-12 xl:col-span-8 space-y-6">
          <div className="glass-panel p-6 rounded-xl border border-outline-variant/10">
            <div className="flex items-center justify-between mb-6">
              <h2 className="font-headline text-lg font-semibold flex items-center gap-2">
                <Package className="w-5 h-5 text-secondary" />
                资产综合列表
              </h2>
              <div className="flex gap-2">
                <span className="px-3 py-1 bg-surface-container-lowest text-[0.6rem] font-bold font-label border border-outline-variant/30 text-on-surface-variant">总计: 14,204</span>
                <span className="px-3 py-1 bg-surface-container-lowest text-[0.6rem] font-bold font-label border border-secondary/30 text-secondary">上架中: 8,912</span>
              </div>
            </div>
            
            <div className="overflow-hidden">
              <table className="w-full text-left border-collapse">
                <thead>
                  <tr className="text-[0.65rem] font-label text-outline uppercase tracking-[0.2em] border-b border-outline-variant/20">
                    <th className="pb-4 font-medium pl-2">资产详情</th>
                    <th className="pb-4 font-medium">网络/ID</th>
                    <th className="pb-4 font-medium">价格 (ETH)</th>
                    <th className="pb-4 font-medium text-center">状态</th>
                    <th className="pb-4 font-medium text-right pr-2">操作</th>
                  </tr>
                </thead>
                <tbody className="text-sm font-body divide-y divide-outline-variant/10">
                  {assets.map((asset) => (
                    <tr key={asset.name} className="hover:bg-surface-container-low transition-colors group">
                      <td className="py-4 pl-2 flex items-center gap-4">
                        <div className="w-12 h-12 bg-surface-container-highest flex items-center justify-center rounded overflow-hidden">
                          <img src={asset.image} alt={asset.name} className="w-full h-full object-cover opacity-80" referrerPolicy="no-referrer" />
                        </div>
                        <div>
                          <div className="font-bold text-on-surface">{asset.name}</div>
                          <div className="text-[0.65rem] text-on-surface-variant font-label uppercase">{asset.category}</div>
                        </div>
                      </td>
                      <td className="py-4 font-label text-[0.7rem] text-secondary">
                        <div className="flex items-center gap-1">
                          <LinkIcon className="w-3 h-3" />
                          {asset.network}
                        </div>
                      </td>
                      <td className="py-4 font-headline font-semibold">{asset.price}</td>
                      <td className="py-4 text-center">
                        <span className={`inline-flex items-center gap-1.5 px-2 py-0.5 rounded-full text-[0.6rem] font-bold uppercase border ${asset.status === '已上架' ? 'text-[#00ff99] border-[#00ff99]/20' : 'text-on-surface-variant border-outline-variant/30'}`}>
                          {asset.status === '已上架' && <span className="w-1.5 h-1.5 bg-[#00ff99] rounded-full animate-pulse"></span>}
                          {asset.status}
                        </span>
                      </td>
                      <td className="py-4 text-right pr-2">
                        <button className="text-outline hover:text-secondary transition-colors">
                          <MoreVertical className="w-4 h-4" />
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>

          <div className="bg-surface-container-high border-l-4 border-primary px-6 py-4 flex items-center justify-between shadow-lg">
            <div className="flex items-center gap-4">
              <CheckCircle className="w-5 h-5 text-primary" />
              <span className="text-sm font-label font-bold text-on-surface uppercase tracking-wider">已选择 3 个资产</span>
            </div>
            <div className="flex gap-3">
              <button className="text-[0.65rem] font-bold font-label uppercase px-4 py-2 border border-outline-variant hover:bg-surface-variant transition-colors">批量下架</button>
              <button className="text-[0.65rem] font-bold font-label uppercase px-4 py-2 bg-primary text-on-primary hover:brightness-110 transition-all">移动至归档</button>
            </div>
          </div>
        </section>

        {/* Side Panel */}
        <section className="col-span-12 xl:col-span-4 space-y-6">
          <div className="glass-panel p-6 rounded-xl border border-outline-variant/10 h-full">
            <div className="mb-6">
              <h2 className="font-headline text-lg font-semibold flex items-center gap-2">
                <Users className="w-5 h-5 text-tertiary" />
                卖家账户
              </h2>
              <p className="text-[0.7rem] text-on-surface-variant mt-1 uppercase tracking-widest font-label">声誉与违规记录</p>
            </div>
            <div className="space-y-4">
              {sellers.map((seller) => (
                <div key={seller.name} className={`p-4 bg-surface-container-highest/30 rounded-lg border border-outline-variant/10 group hover:border-secondary/20 transition-all ${seller.error ? 'hover:border-error/20' : ''}`}>
                  <div className="flex items-center justify-between mb-3">
                    <div className="flex items-center gap-3">
                      <div className={`w-10 h-10 rounded-full bg-gradient-to-br ${seller.color} flex items-center justify-center font-headline text-on-tertiary font-bold text-xs`}>
                        {seller.name.substring(0, 2).toUpperCase()}
                      </div>
                      <div>
                        <div className="text-xs font-bold text-on-surface">{seller.name}</div>
                        <div className="text-[0.6rem] text-on-surface-variant font-label uppercase">ID: {seller.id}</div>
                      </div>
                    </div>
                    <div className="text-right">
                      <div className={`text-[0.6rem] font-label font-bold tracking-widest uppercase ${seller.error ? 'text-error' : 'text-secondary'}`}>{seller.rank}</div>
                      <div className="flex gap-0.5 justify-end mt-0.5">
                        {[...Array(5)].map((_, i) => (
                          <Star key={i} className={`w-2.5 h-2.5 ${i < seller.stars ? (seller.error ? 'text-outline fill-current' : 'text-secondary fill-current') : 'text-outline opacity-30'}`} />
                        ))}
                      </div>
                    </div>
                  </div>
                  <div className="grid grid-cols-2 gap-2 text-[0.65rem] font-label">
                    <div className="bg-surface-container-lowest p-2 border border-outline-variant/10">
                      <span className="block text-outline mb-0.5 uppercase">销售额</span>
                      <span className="text-on-surface font-headline">{seller.sales}</span>
                    </div>
                    <div className={`p-2 border ${seller.error ? 'bg-error/5 border-error/20' : 'bg-surface-container-lowest border-outline-variant/10'}`}>
                      <span className={`block mb-0.5 uppercase ${seller.error ? 'text-error/80' : 'text-outline'}`}>处罚</span>
                      <span className={`font-headline ${seller.error ? 'text-error' : 'text-[#00ff99]'}`}>{seller.penalty}</span>
                    </div>
                  </div>
                  {seller.error && (
                    <button className="w-full mt-3 py-1.5 bg-error/10 text-error text-[0.6rem] font-bold uppercase tracking-widest border border-error/20 hover:bg-error/20 transition-all">查看违规详情</button>
                  )}
                </div>
              ))}
            </div>
            <button className="w-full mt-6 py-3 text-[0.65rem] font-bold font-label uppercase tracking-widest border border-outline-variant/30 hover:bg-surface-variant text-on-surface-variant transition-all">查看所有商家账户</button>
          </div>
        </section>
      </div>
    </motion.div>
  );
}
