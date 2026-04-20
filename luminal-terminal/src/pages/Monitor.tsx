import React from 'react';
import { 
  Activity, 
  Zap, 
  RefreshCw, 
  AlertCircle, 
  Info, 
  Network as NetworkIcon,
  Search,
  Download,
  ShieldAlert
} from 'lucide-react';
import { motion } from 'motion/react';

const chains = [
  { id: 'ETH', name: '以太坊', status: '运行良好', latency: '12ms', tps: '14.2', health: 85, color: 'bg-secondary' },
  { id: 'SOL', name: 'Solana', status: '最佳状态', latency: '450ms', tps: '2,410', health: 98, color: 'bg-secondary' },
  { id: 'APT', name: 'Aptos', status: '不稳定', latency: '1.2s', tps: '184', health: 40, color: 'bg-error' },
  { id: 'MATIC', name: 'Polygon', status: '运行良好', latency: '24ms', tps: '65.8', health: 80, color: 'bg-secondary' },
  { id: 'ARB', name: 'Arbitrum', status: '运行良好', latency: '18ms', tps: '112', health: 92, color: 'bg-secondary' },
];

const nodes = [
  { name: 'US-EAST-01 (主节点)', status: '活跃', color: 'bg-secondary' },
  { name: 'EU-CENTRAL-04', status: '活跃', color: 'bg-secondary' },
  { name: 'ASIA-SOUTH-02', status: '待命', color: 'bg-tertiary' },
  { name: 'SA-EAST-01', status: '离线', color: 'bg-error' },
];

const logs = [
  { type: '紧急', time: '14:52:01', message: '检测到 APT 节点故障。正在触发向 ASIA-SOUTH-02 的故障转移。', color: 'border-error', text: 'text-error' },
  { type: '信息', time: '14:48:33', message: '以太坊主网 Gas 优化脚本已执行。', color: 'border-primary', text: 'text-primary' },
  { type: '网络', time: '14:45:10', message: 'Solana TPS 激增至 2.8k。正在监控负载均衡。', color: 'border-secondary', text: 'text-secondary' },
];

export default function Monitor() {
  return (
    <motion.div 
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      className="space-y-8"
    >
      <div className="flex justify-between items-end">
        <div>
          <h2 className="font-headline text-4xl font-bold text-on-surface tracking-tight">协议监控终端</h2>
          <div className="flex items-center mt-2">
            <div className="pulse-dot mr-2"></div>
            <span className="font-label text-xs uppercase tracking-widest text-on-surface-variant">实时跨链网络同步已激活</span>
          </div>
        </div>
        <div className="flex gap-3">
          <button className="px-4 py-2 glass-panel text-[0.7rem] font-bold tracking-widest text-secondary rounded-lg hover:brightness-125 transition-all">导出报告</button>
          <button className="px-4 py-2 bg-primary/10 border border-primary/30 text-primary text-[0.7rem] font-bold tracking-widest rounded-lg hover:bg-primary/20 transition-all">系统强制干预</button>
        </div>
      </div>

      {/* Chain Health Grid */}
      <div className="grid grid-cols-1 md:grid-cols-5 gap-4">
        {chains.map((chain) => (
          <div key={chain.id} className="surface-container-high p-5 rounded-xl border-t border-secondary/20 relative overflow-hidden group">
            <div className="flex justify-between items-start mb-4">
              <span className="font-headline text-lg font-bold">{chain.id}</span>
              <div className="flex items-center">
                <span className={`text-[0.6rem] font-label ${chain.color.replace('bg-', 'text-')} mr-1.5 uppercase`}>{chain.status}</span>
                <div className={`w-1.5 h-1.5 rounded-full ${chain.color} shadow-[0_0_6px_currentColor]`}></div>
              </div>
            </div>
            <div className="space-y-3">
              <div className="flex justify-between text-[0.65rem] font-label tracking-tight text-on-surface-variant">
                <span>延迟</span>
                <span className="text-on-surface">{chain.latency}</span>
              </div>
              <div className="flex justify-between text-[0.65rem] font-label tracking-tight text-on-surface-variant">
                <span>TPS</span>
                <span className="text-on-surface">{chain.tps}</span>
              </div>
              <div className="w-full h-1 bg-surface-container-lowest rounded-full overflow-hidden">
                <div className={`h-full ${chain.color}`} style={{ width: `${chain.health}%` }}></div>
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Gas Monitor */}
        <div className="lg:col-span-2 space-y-6">
          <div className="surface-container-low p-6 rounded-xl border border-outline-variant/10 relative overflow-hidden">
            <div className="flex justify-between items-center mb-6">
              <div>
                <h3 className="font-headline text-xl font-bold">GAS 价格动态</h3>
                <p className="font-label text-[0.65rem] text-on-surface-variant tracking-widest uppercase">全球 L1/L2 网络拥堵指数</p>
              </div>
              <div className="flex gap-2">
                <span className="px-2 py-1 bg-surface-container-highest text-[0.6rem] font-bold text-secondary rounded">5分钟</span>
                <span className="px-2 py-1 bg-surface-container-lowest text-[0.6rem] font-bold text-on-surface-variant rounded">1小时</span>
              </div>
            </div>
            <div className="h-64 flex items-end gap-1 px-2 relative">
              {[40, 45, 55, 50, 65, 80, 75, 85, 70, 60, 50, 45].map((h, i) => (
                <div key={i} className={`flex-1 ${i > 4 && i < 10 ? 'bg-secondary/20' : 'bg-primary/20'} relative`} style={{ height: `${h}%` }}>
                  <div className={`absolute top-0 w-full h-1 ${i > 4 && i < 10 ? 'bg-secondary shadow-[0_0_10px_#00e0ff]' : 'bg-primary shadow-[0_0_10px_#88adff]'}`}></div>
                </div>
              ))}
            </div>
          </div>
          
          <div className="grid grid-cols-2 gap-4">
            <div className="surface-container-high p-6 rounded-xl border-l-4 border-tertiary">
              <span className="font-label text-[0.65rem] text-tertiary uppercase tracking-widest font-bold">跨链交易量</span>
              <div className="mt-2 flex items-baseline gap-3">
                <span className="font-headline text-3xl font-bold text-on-surface">$1.24B</span>
                <span className="text-[#00ff99] text-xs font-label">+12.4%</span>
              </div>
            </div>
            <div className="surface-container-high p-6 rounded-xl border-l-4 border-secondary">
              <span className="font-label text-[0.65rem] text-secondary uppercase tracking-widest font-bold">平均跨链延迟</span>
              <div className="mt-2 flex items-baseline gap-3">
                <span className="font-headline text-3xl font-bold text-on-surface">3.8s</span>
                <span className="text-on-surface-variant text-xs font-label">最佳值</span>
              </div>
            </div>
          </div>
        </div>

        {/* Node & Logs */}
        <div className="space-y-6">
          <div className="surface-container-low p-6 rounded-xl border border-outline-variant/10 flex flex-col">
            <div className="flex justify-between items-center mb-6">
              <h3 className="font-headline text-sm font-bold uppercase tracking-widest">节点集群</h3>
              <RefreshCw className="w-4 h-4 text-on-surface-variant cursor-pointer" />
            </div>
            <div className="space-y-4">
              {nodes.map((node) => (
                <div key={node.name} className="flex items-center justify-between group cursor-pointer">
                  <div className="flex items-center">
                    <div className={`w-1.5 h-1.5 rounded-full ${node.color} mr-3`}></div>
                    <span className="text-xs font-label font-medium">{node.name}</span>
                  </div>
                  <span className="text-[0.6rem] font-label text-on-surface-variant">{node.status}</span>
                </div>
              ))}
            </div>
          </div>

          <div className="surface-container-low p-6 rounded-xl border border-outline-variant/10 flex-1 flex flex-col">
            <h3 className="font-headline text-sm font-bold uppercase tracking-widest mb-6">安全日志</h3>
            <div className="space-y-4">
              {logs.map((log, i) => (
                <div key={i} className={`bg-surface-container-lowest/50 p-3 rounded border-l-2 ${log.color}`}>
                  <div className="flex justify-between items-start">
                    <span className={`text-[0.6rem] font-label font-bold ${log.text}`}>{log.type}</span>
                    <span className="text-[0.55rem] font-label text-on-surface-variant">{log.time}</span>
                  </div>
                  <p className="text-[0.7rem] font-label mt-1 text-on-surface">{log.message}</p>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </motion.div>
  );
}
