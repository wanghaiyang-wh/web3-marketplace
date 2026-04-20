import React from 'react';
import { 
  ShieldAlert, 
  Map as MapIcon, 
  Quote, 
  Eye, 
  ChevronRight,
  AlertTriangle,
  Zap,
  Gavel,
  ClipboardCheck,
  TrendingDown
} from 'lucide-react';
import { motion } from 'motion/react';

const riskOrders = [
  { id: '0x8a2...4f92', level: '高', reason: '关联钓鱼地址', value: '12.5 ETH', color: 'text-error', bg: 'bg-error/10' },
  { id: '0x3b1...e77d', level: '中', reason: '异常交易频率', value: '0.82 BTC', color: 'text-[#ffa8a3]', bg: 'bg-[#ffa8a3]/10' },
  { id: '0x5f9...d120', level: '低', reason: '新合约调用', value: '5,200 USDC', color: 'text-[#00ff99]', bg: 'bg-[#00ff99]/10' },
];

const actions = [
  { title: '立即阻断 (Intercept)', details: '冻结所有待处理的高风险交易', icon: ShieldAlert, color: 'text-error', bg: 'bg-error-container/20', border: 'border-error/30' },
  { title: '降权处理 (De-rank)', details: '在市场中限制资产的可见性', icon: TrendingDown, color: 'text-secondary', bg: 'bg-surface-container-high', border: 'border-outline-variant/20' },
  { title: '封禁地址 (Ban)', details: '永久黑名单处理源节点', icon: Gavel, color: 'text-tertiary', bg: 'bg-surface-container-high', border: 'border-outline-variant/20' },
  { title: '人工复核 (Review)', details: '提交至专家委员会进行人工审核', icon: ClipboardCheck, color: 'text-primary', bg: 'bg-primary/10', border: 'border-primary/30' },
];

export default function Security() {
  return (
    <motion.div 
      initial={{ opacity: 0, x: 20 }}
      animate={{ opacity: 1, x: 0 }}
      className="space-y-8"
    >
      <div className="flex items-end justify-between mb-8">
        <div>
          <h2 className="font-headline text-3xl font-bold tracking-tight text-on-surface">AI 交易安全风控</h2>
          <div className="flex items-center gap-2 mt-1">
            <div className="w-2 h-2 rounded-full bg-[#00ff99] animate-pulse"></div>
            <span className="text-xs text-on-surface-variant uppercase tracking-widest font-label">AI 神经元监控已激活</span>
          </div>
        </div>
        <div className="flex gap-3">
          <button className="px-4 py-2 bg-surface-container-high hover:bg-surface-container-highest transition-colors flex items-center gap-2 rounded-lg border border-outline-variant/10 text-xs font-label uppercase tracking-widest">
            筛选
          </button>
          <button className="px-4 py-2 bg-surface-container-high hover:bg-surface-container-highest transition-colors flex items-center gap-2 rounded-lg border border-outline-variant/10 text-xs font-label uppercase tracking-widest">
            导出
          </button>
        </div>
      </div>

      <div className="grid grid-cols-12 gap-6">
        {/* Left Column */}
        <div className="col-span-12 lg:col-span-8 space-y-6">
          <div className="grid grid-cols-2 gap-6">
            {/* Wash Trading */}
            <div className="glass-panel p-6 rounded-xl flex flex-col items-center justify-center min-h-[320px] relative overflow-hidden">
              <div className="w-full flex justify-between items-start mb-4">
                <h3 className="text-sm font-headline uppercase tracking-widest text-primary">刷单监控 (Wash Trading)</h3>
                <span className="text-[0.625rem] bg-primary/10 text-primary px-2 py-0.5 rounded border border-primary/20">实时监控</span>
              </div>
              <div className="relative w-48 h-48 border border-secondary/20 rounded-full flex items-center justify-center">
                <div className="absolute inset-0 border border-secondary/10 rounded-full scale-75 animate-pulse"></div>
                <div className="absolute inset-0 flex items-center justify-center">
                  <svg className="w-40 h-40 transform rotate-12" viewBox="0 0 100 100">
                    <polygon fill="rgba(0, 224, 255, 0.2)" points="50,10 90,40 80,85 20,85 10,40" stroke="#00e0ff" strokeWidth="1" />
                    <circle cx="50" cy="10" fill="#00e0ff" r="2" />
                    <circle cx="90" cy="40" fill="#00e0ff" r="2" />
                    <circle cx="80" cy="85" fill="#00e0ff" r="2" />
                    <circle cx="20" cy="85" fill="#00e0ff" r="2" />
                    <circle cx="10" cy="40" fill="#00e0ff" r="2" />
                  </svg>
                </div>
              </div>
              <div className="grid grid-cols-2 w-full mt-6 gap-4">
                <div className="text-center">
                  <p className="text-[0.625rem] text-on-surface-variant uppercase font-label">可疑成交额</p>
                  <p className="text-lg font-headline font-bold text-secondary">$142.8k</p>
                </div>
                <div className="text-center border-l border-outline-variant/30">
                  <p className="text-[0.625rem] text-on-surface-variant uppercase font-label">识别机器人</p>
                  <p className="text-lg font-headline font-bold text-tertiary">384</p>
                </div>
              </div>
            </div>

            {/* Map */}
            <div className="glass-panel p-6 rounded-xl relative overflow-hidden flex flex-col min-h-[320px]">
              <div className="flex justify-between items-start mb-4 z-10">
                <h3 className="text-sm font-headline uppercase tracking-widest text-primary">异常节点拓扑</h3>
                <MapIcon className="w-5 h-5 text-secondary" />
              </div>
              <div className="flex-1 rounded-lg bg-surface-container-lowest relative overflow-hidden border border-outline-variant/10">
                <img 
                  src="https://lh3.googleusercontent.com/aida-public/AB6AXuB3qY8lShH8vi7hYsf1D8qfX2tlWV8blQutRgBT7Vx0DOEAbpLMEblCVxCnFfSBuuq7PW_-gWxOT5Kn6S5Db__JyLyEC0o8J9QqYzPTCpa6m0krmY7MGisracNZcRPxZMDAR28_4vc9zXDFgiF3iqptQxozR1lIJ3yf3l3MdFYzmzUg1l4kK12CtfCta-T7SqnAhAxsn3frKi6UtzxHro9cAjutj6Z_Es4-lG7UU8nAPXNQPVNJ9MUdxczEya3t9Bq1UO47nbSm2n8D" 
                  alt="Map" 
                  className="w-full h-full object-cover opacity-40"
                  referrerPolicy="no-referrer"
                />
                <div className="absolute inset-0 p-4">
                  <div className="absolute top-1/4 left-1/3 w-3 h-3 bg-error rounded-full shadow-[0_0_10px_#ff716c]"></div>
                  <div className="absolute top-1/2 left-2/3 w-2 h-2 bg-error rounded-full opacity-60"></div>
                  <div className="absolute bottom-1/3 right-1/4 w-4 h-4 bg-error rounded-full shadow-[0_0_15px_#ff716c] animate-pulse"></div>
                </div>
              </div>
              <div className="mt-4 flex gap-4 text-[0.625rem] font-label uppercase tracking-tighter">
                <span className="flex items-center gap-1"><span className="w-2 h-2 bg-error rounded-full"></span> 高风险源</span>
                <span className="flex items-center gap-1"><span className="w-2 h-2 bg-secondary rounded-full"></span> 中继节点</span>
              </div>
            </div>
          </div>

          {/* Risk Table */}
          <div className="glass-panel rounded-xl overflow-hidden">
            <div className="p-6 border-b border-outline-variant/10 flex justify-between items-center">
              <h3 className="text-sm font-headline uppercase tracking-widest text-primary">高优先级风险队列</h3>
              <div className="flex gap-2">
                <div className="text-[0.6875rem] px-3 py-1 rounded bg-error/10 text-error border border-error/20 font-bold">12 紧急</div>
                <div className="text-[0.6875rem] px-3 py-1 rounded bg-secondary/10 text-secondary border border-secondary/20 font-bold">45 监控中</div>
              </div>
            </div>
            <div className="overflow-x-auto">
              <table className="w-full text-left">
                <thead className="bg-surface-container-low/50 text-on-surface-variant font-label text-[0.625rem] uppercase tracking-[0.1em]">
                  <tr>
                    <th className="px-6 py-4">交易 ID</th>
                    <th className="px-6 py-4">风险等级</th>
                    <th className="px-6 py-4">检测原因</th>
                    <th className="px-6 py-4 text-right">数值</th>
                    <th className="px-6 py-4 text-center">操作</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-outline-variant/10">
                  {riskOrders.map((order) => (
                    <tr key={order.id} className="hover:bg-surface-container-low transition-colors group">
                      <td className="px-6 py-4 font-label text-[0.6875rem] text-secondary">{order.id}</td>
                      <td className="px-6 py-4">
                        <span className={`px-2 py-0.5 rounded text-[0.625rem] font-bold ${order.bg} ${order.color} border border-current/30`}>{order.level}</span>
                      </td>
                      <td className="px-6 py-4 text-xs">{order.reason}</td>
                      <td className="px-6 py-4 text-right font-headline font-medium text-on-surface">{order.value}</td>
                      <td className="px-6 py-4">
                        <div className="flex justify-center gap-2">
                          <button className="p-1.5 hover:bg-error/20 rounded text-error transition-colors"><ShieldAlert className="w-4 h-4" /></button>
                          <button className="p-1.5 hover:bg-primary/20 rounded text-primary transition-colors"><Eye className="w-4 h-4" /></button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>

        {/* Right Column */}
        <div className="col-span-12 lg:col-span-4 space-y-6">
          <div className="glass-panel rounded-xl p-6">
            <h3 className="text-sm font-headline uppercase tracking-widest text-primary mb-6">全局威胁处置</h3>
            <div className="space-y-4">
              {actions.map((action) => (
                <button 
                  key={action.title}
                  className={`w-full flex items-center justify-between p-4 ${action.bg} border ${action.border} rounded-lg hover:brightness-110 transition-all group`}
                >
                  <div className="flex items-center gap-3">
                    <action.icon className={`w-5 h-5 ${action.color}`} />
                    <div className="text-left">
                      <p className="text-xs font-bold text-on-surface uppercase tracking-wider">{action.title}</p>
                      <p className={`text-[0.625rem] ${action.color}/80`}>{action.details}</p>
                    </div>
                  </div>
                  <ChevronRight className={`w-4 h-4 ${action.color} group-hover:translate-x-1 transition-transform`} />
                </button>
              ))}
            </div>
            <div className="mt-8 pt-6 border-t border-outline-variant/10 text-center">
              <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full bg-surface-container-lowest border border-outline-variant/30">
                <div className="w-2 h-2 rounded-full bg-primary animate-pulse"></div>
                <span className="text-[0.625rem] uppercase tracking-widest font-label text-on-surface-variant">AI 逻辑版本 4.2.0-Alpha</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </motion.div>
  );
}
