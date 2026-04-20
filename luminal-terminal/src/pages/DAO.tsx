import React from 'react';
import { 
  Gavel, 
  Users, 
  FileText, 
  PieChart, 
  ChevronRight,
  Plus,
  CheckCircle2,
  XCircle
} from 'lucide-react';
import { motion } from 'motion/react';

const proposals = [
  { id: 'LIP-042', title: '增加 L2 跨链桥流动性上限', status: '投票中', votes: '1.2M / 2M', time: '2天后结束', color: 'text-secondary' },
  { id: 'LIP-041', title: '调整商户分润比例至 2.5%', status: '已通过', votes: '1.8M / 2M', time: '已结束', color: 'text-[#00ff99]' },
  { id: 'LIP-040', title: '新增对 Solana 资产的支持', status: '已通过', votes: '1.9M / 2M', time: '已结束', color: 'text-[#00ff99]' },
];

export default function DAO() {
  return (
    <motion.div 
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      className="space-y-8"
    >
      <div className="flex justify-between items-end">
        <div>
          <h2 className="font-headline text-3xl font-bold text-on-surface tracking-tight">DAO 治理终端</h2>
          <p className="text-on-surface-variant mt-1 font-body">Luminal 协议的去中心化决策与参数调整中心。</p>
        </div>
        <button className="px-6 py-2 bg-tertiary text-on-tertiary text-xs font-bold font-label uppercase tracking-widest rounded-md hover:brightness-110 transition-all flex items-center gap-2">
          <Plus className="w-4 h-4" />
          发起提案
        </button>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div className="lg:col-span-2 space-y-6">
          <div className="glass-panel p-6 rounded-xl">
            <h3 className="text-sm font-headline uppercase tracking-widest text-primary mb-6">活跃提案</h3>
            <div className="space-y-4">
              {proposals.map((prop) => (
                <div key={prop.id} className="p-4 bg-surface-container-high/50 rounded-lg border border-outline-variant/10 hover:border-secondary/30 transition-all group cursor-pointer">
                  <div className="flex justify-between items-start mb-2">
                    <div className="flex items-center gap-3">
                      <span className="text-[0.6rem] font-bold font-mono text-on-surface-variant px-1.5 py-0.5 bg-surface-container-lowest rounded">{prop.id}</span>
                      <h4 className="text-sm font-bold text-on-surface">{prop.title}</h4>
                    </div>
                    <span className={`text-[0.6rem] font-bold uppercase tracking-widest ${prop.color}`}>{prop.status}</span>
                  </div>
                  <div className="flex items-center gap-4 mt-4">
                    <div className="flex-1 h-1.5 bg-surface-container-lowest rounded-full overflow-hidden">
                      <div className={`h-full ${prop.color.replace('text-', 'bg-')}`} style={{ width: '60%' }}></div>
                    </div>
                    <span className="text-[0.6rem] font-label text-on-surface-variant">{prop.votes} 票</span>
                    <span className="text-[0.6rem] font-label text-on-surface-variant">{prop.time}</span>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>

        <div className="space-y-6">
          <div className="glass-panel p-6 rounded-xl">
            <h3 className="text-sm font-headline uppercase tracking-widest text-primary mb-6">治理概览</h3>
            <div className="space-y-6">
              <div className="flex justify-between items-center">
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 rounded-lg bg-secondary/10 flex items-center justify-center">
                    <Users className="w-5 h-5 text-secondary" />
                  </div>
                  <div>
                    <p className="text-xs font-bold text-on-surface">持币地址</p>
                    <p className="text-[0.6rem] text-on-surface-variant font-label">12,402</p>
                  </div>
                </div>
                <ChevronRight className="w-4 h-4 text-on-surface-variant" />
              </div>
              <div className="flex justify-between items-center">
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 rounded-lg bg-tertiary/10 flex items-center justify-center">
                    <PieChart className="w-5 h-5 text-tertiary" />
                  </div>
                  <div>
                    <p className="text-xs font-bold text-on-surface">质押总量</p>
                    <p className="text-[0.6rem] text-on-surface-variant font-label">42.8M LUMI</p>
                  </div>
                </div>
                <ChevronRight className="w-4 h-4 text-on-surface-variant" />
              </div>
            </div>
          </div>

          <div className="glass-panel p-6 rounded-xl border border-secondary/20">
            <h3 className="text-sm font-headline uppercase tracking-widest text-secondary mb-4">您的投票权重</h3>
            <div className="text-3xl font-headline font-bold text-on-surface mb-2">1,240 <span className="text-xs text-on-surface-variant">VP</span></div>
            <p className="text-[0.6rem] text-on-surface-variant font-label uppercase tracking-wider mb-4">基于质押的 LUMI 资产</p>
            <button className="w-full py-2 bg-secondary/10 text-secondary text-[0.65rem] font-bold uppercase tracking-widest border border-secondary/30 hover:bg-secondary/20 transition-all">
              增加质押
            </button>
          </div>
        </div>
      </div>
    </motion.div>
  );
}
