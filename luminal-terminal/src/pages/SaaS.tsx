import React from 'react';
import { 
  Handshake, 
  Cloud, 
  Zap, 
  Settings, 
  ExternalLink,
  CheckCircle2,
  Clock,
  BarChart4
} from 'lucide-react';
import { motion } from 'motion/react';

const partners = [
  { name: 'Aether Games', type: '游戏工作室', status: '已连接', volume: '$1.2M', api: 'v2.4.1', color: 'text-[#00ff99]' },
  { name: 'Nexus Marketplace', type: '聚合器', status: '同步中', volume: '$450k', api: 'v1.0.2', color: 'text-secondary' },
  { name: 'Titan Guild', type: 'DAO 组织', status: '待审核', volume: '-', api: '-', color: 'text-tertiary' },
];

export default function SaaS() {
  return (
    <motion.div 
      initial={{ opacity: 0, scale: 0.98 }}
      animate={{ opacity: 1, scale: 1 }}
      className="space-y-8"
    >
      <div className="flex justify-between items-end">
        <div>
          <h2 className="font-headline text-3xl font-bold text-on-surface tracking-tight">商户 SaaS 合作中心</h2>
          <p className="text-on-surface-variant mt-1 font-body">管理外部游戏工作室、公会和市场的 API 集成与分润协议。</p>
        </div>
        <button className="px-6 py-2 bg-primary text-on-primary text-xs font-bold font-label uppercase tracking-widest rounded-md hover:brightness-110 transition-all">
          邀请新商户
        </button>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {partners.map((partner, i) => (
          <div key={partner.name} className="glass-panel p-6 rounded-xl relative overflow-hidden group hover:border-primary/30 transition-all">
            <div className="flex justify-between items-start mb-6">
              <div className="w-12 h-12 bg-surface-container-highest rounded-lg flex items-center justify-center border border-outline-variant/10">
                <Cloud className="w-6 h-6 text-primary" />
              </div>
              <span className={`text-[0.6rem] font-bold uppercase tracking-widest px-2 py-1 rounded bg-surface-container-low border border-outline-variant/10 ${partner.color}`}>
                {partner.status}
              </span>
            </div>
            <h3 className="text-xl font-headline font-bold text-on-surface">{partner.name}</h3>
            <p className="text-xs text-on-surface-variant font-label uppercase tracking-wider mb-6">{partner.type}</p>
            
            <div className="space-y-3 mb-6">
              <div className="flex justify-between text-xs font-label">
                <span className="text-on-surface-variant">月交易额</span>
                <span className="text-on-surface font-bold">{partner.volume}</span>
              </div>
              <div className="flex justify-between text-xs font-label">
                <span className="text-on-surface-variant">API 版本</span>
                <span className="text-on-surface font-mono">{partner.api}</span>
              </div>
            </div>
            
            <div className="flex gap-2">
              <button className="flex-1 py-2 bg-surface-container-highest text-[0.65rem] font-bold uppercase tracking-widest hover:bg-surface-variant transition-colors">管理协议</button>
              <button className="p-2 bg-surface-container-highest hover:bg-surface-variant transition-colors">
                <ExternalLink className="w-4 h-4 text-on-surface-variant" />
              </button>
            </div>
          </div>
        ))}
      </div>

      <div className="glass-panel p-8 rounded-xl">
        <h3 className="text-xl font-headline font-bold text-on-surface mb-8">集成健康度</h3>
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div className="text-center">
            <div className="text-4xl font-headline font-bold text-secondary mb-2">99.9%</div>
            <div className="text-[0.6rem] font-label uppercase tracking-[0.2em] text-on-surface-variant">平均正常运行时间</div>
          </div>
          <div className="text-center">
            <div className="text-4xl font-headline font-bold text-[#00ff99] mb-2">12ms</div>
            <div className="text-[0.6rem] font-label uppercase tracking-[0.2em] text-on-surface-variant">平均响应延迟</div>
          </div>
          <div className="text-center">
            <div className="text-4xl font-headline font-bold text-primary mb-2">1.2M</div>
            <div className="text-[0.6rem] font-label uppercase tracking-[0.2em] text-on-surface-variant">每日 API 调用</div>
          </div>
          <div className="text-center">
            <div className="text-4xl font-headline font-bold text-tertiary mb-2">0</div>
            <div className="text-[0.6rem] font-label uppercase tracking-[0.2em] text-on-surface-variant">待处理错误</div>
          </div>
        </div>
      </div>
    </motion.div>
  );
}
