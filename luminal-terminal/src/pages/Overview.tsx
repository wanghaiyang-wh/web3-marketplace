import React from 'react';
import { 
  TrendingUp, 
  Clock, 
  BarChart3, 
  Users, 
  Filter, 
  Download,
  Shield,
  Zap,
  ArrowUpRight
} from 'lucide-react';
import { motion } from 'motion/react';

const stats = [
  { label: '总交易额 (GMV)', value: '$4,281,092', change: '+12.4%', icon: TrendingUp, color: 'text-secondary' },
  { label: '待处理订单', value: '1,842', change: '队列中', icon: Clock, color: 'text-tertiary' },
  { label: '转化率', value: '94.2%', change: '表现优秀', icon: BarChart3, color: 'text-primary' },
  { label: '活跃策展人', value: '12.4k', change: '增长中', icon: Users, color: 'text-secondary-fixed-dim' },
];

const tasks = [
  { title: '部署 L2 验证补丁', category: '协议升级', time: '2小时前', color: 'border-primary' },
  { title: '验证鲸鱼钱包 0x4F...a2', category: '风险审查', time: '5小时前', color: 'border-secondary' },
  { title: '更新 GameFi 费率结构', category: '商户同步', time: '1天前', color: 'border-tertiary' },
];

const riskEvents = [
  { title: '检测到滑点激增', details: 'ETH/USDT 池波动 > 4.2%', status: '紧急', time: '0.2秒前', icon: Shield, color: 'text-error' },
  { title: '闪电贷活动', details: '来源: 0x82...192b | 区块 19283', status: '监控中', time: '12秒前', icon: Zap, color: 'text-secondary' },
];

const nodes = [
  { name: '以太坊', type: '主网', latency: '12ms', symbol: 'ETH' },
  { name: 'Solana', type: 'v1.17.x', latency: '84ms', symbol: 'SOL' },
  { name: 'Base L2', type: '运行稳定', latency: '4ms', symbol: 'BASE' },
  { name: 'Polygon', type: 'zkEVM', latency: '18ms', symbol: 'POLY' },
];

export default function Overview() {
  return (
    <motion.div 
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      className="space-y-8"
    >
      {/* Header Section */}
      <div className="flex justify-between items-end">
        <div>
          <h2 className="text-5xl font-headline font-bold tracking-tight text-on-surface">平台总览</h2>
          <p className="text-on-surface-variant mt-2 font-body max-w-md">平台实时遥测数据与跨链资产分布矩阵。</p>
        </div>
        <div className="flex gap-3">
          <button className="px-4 py-2 glass-panel rounded-lg flex items-center gap-2 border border-outline-variant/20 hover:border-secondary transition-all text-xs font-label uppercase tracking-widest">
            <Filter className="w-4 h-4" />
            筛选周期
          </button>
          <button className="px-4 py-2 bg-primary/10 border border-primary/30 rounded-lg flex items-center gap-2 hover:bg-primary/20 transition-all text-xs font-label uppercase tracking-widest text-primary">
            <Download className="w-4 h-4" />
            导出日志
          </button>
        </div>
      </div>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
        {stats.map((stat, i) => (
          <motion.div
            key={stat.label}
            initial={{ opacity: 0, scale: 0.9 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ delay: i * 0.1 }}
            className="glass-panel p-6 rounded-xl cinematic-glow relative overflow-hidden group"
          >
            <div className="absolute top-0 right-0 w-32 h-32 bg-primary/5 rounded-full -mr-16 -mt-16 blur-3xl"></div>
            <div className="flex justify-between items-start mb-4">
              <span className="text-[0.6rem] font-label uppercase tracking-[0.2em] text-on-surface-variant">{stat.label}</span>
              <stat.icon className={`w-5 h-5 ${stat.color}`} />
            </div>
            <div className="text-3xl font-headline font-bold text-on-surface tracking-tight">{stat.value}</div>
            <div className="mt-4 flex items-center gap-2">
              <div className="h-[2px] flex-1 bg-surface-container-highest relative">
                <div className="absolute left-0 top-0 h-full w-3/4 bg-gradient-to-r from-secondary to-primary"></div>
              </div>
              <span className={`text-[0.6rem] font-label ${stat.color}`}>{stat.change}</span>
            </div>
          </motion.div>
        ))}
      </div>

      {/* Middle Section */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Chart Area */}
        <div className="lg:col-span-2 glass-panel p-8 rounded-xl relative overflow-hidden h-[400px]">
          <div className="flex justify-between items-start mb-8">
            <div>
              <h3 className="text-xl font-headline font-bold text-on-surface">交易速率</h3>
              <p className="text-xs text-on-surface-variant font-label">全球 24 小时实时结算追踪</p>
            </div>
            <div className="flex gap-4">
              <div className="flex items-center gap-2">
                <span className="w-2 h-2 rounded-full bg-secondary"></span>
                <span className="text-[0.6rem] uppercase tracking-wider text-on-surface-variant">成功</span>
              </div>
              <div className="flex items-center gap-2">
                <span className="w-2 h-2 rounded-full bg-error"></span>
                <span className="text-[0.6rem] uppercase tracking-wider text-on-surface-variant">失败</span>
              </div>
            </div>
          </div>
          
          {/* Decorative Chart SVG */}
          <div className="absolute bottom-0 left-0 w-full px-8 h-48 flex items-end">
            <svg className="w-full h-full" preserveAspectRatio="none" viewBox="0 0 1000 200">
              <path 
                className="drop-shadow-[0_0_10px_rgba(0,224,255,0.8)]"
                d="M0,150 Q50,130 100,140 T200,100 T300,120 T400,80 T500,90 T600,50 T700,70 T800,30 T900,40 T1000,10" 
                fill="none" 
                stroke="#00e0ff" 
                strokeLinecap="round" 
                strokeWidth="3"
              />
              <path 
                d="M0,150 Q50,130 100,140 T200,100 T300,120 T400,80 T500,90 T600,50 T700,70 T800,30 T900,40 T1000,10 L1000,200 L0,200 Z" 
                fill="url(#chart-grad)" 
                opacity="0.1"
              />
              <defs>
                <linearGradient id="chart-grad" x1="0" x2="0" y1="0" y2="1">
                  <stop offset="0%" stopColor="#00e0ff" />
                  <stop offset="100%" stopColor="transparent" />
                </linearGradient>
              </defs>
            </svg>
          </div>
          <div className="grid grid-cols-6 absolute bottom-4 left-8 right-8 text-[0.6rem] font-label text-on-surface-variant uppercase tracking-tighter opacity-50">
            <span>00:00</span>
            <span>04:00</span>
            <span>08:00</span>
            <span>12:00</span>
            <span>16:00</span>
            <span className="text-right">20:00</span>
          </div>
        </div>

        {/* Tasks Area */}
        <div className="glass-panel p-8 rounded-xl flex flex-col">
          <h3 className="text-xl font-headline font-bold text-on-surface mb-6">待办事项</h3>
          <div className="space-y-4 flex-1">
            {tasks.map((task) => (
              <div 
                key={task.title}
                className={`p-4 bg-surface-container-high/50 rounded-lg border-l-2 ${task.color} group hover:bg-surface-container-highest transition-colors cursor-pointer`}
              >
                <div className="flex justify-between mb-1">
                  <span className={`text-[0.6rem] font-bold uppercase tracking-widest ${task.color.replace('border-', 'text-')}`}>{task.category}</span>
                  <span className="text-[0.6rem] text-on-surface-variant">{task.time}</span>
                </div>
                <p className="text-sm font-body text-on-surface">{task.title}</p>
              </div>
            ))}
          </div>
          <button className="mt-6 w-full py-2 border border-outline-variant hover:border-on-surface text-xs font-bold font-label uppercase tracking-widest transition-all">
            查看所有工作流
          </button>
        </div>
      </div>

      {/* Bottom Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {/* Risk Matrix */}
        <div className="glass-panel p-8 rounded-xl">
          <div className="flex items-center justify-between mb-6">
            <h3 className="text-xl font-headline font-bold text-on-surface flex items-center gap-2">
              <Shield className="w-5 h-5 text-error" />
              安全矩阵
            </h3>
            <span className="px-2 py-0.5 bg-error/10 text-error text-[0.6rem] font-bold rounded uppercase tracking-tighter">实时监控中</span>
          </div>
          <div className="space-y-4">
            {riskEvents.map((event) => (
              <div key={event.title} className="flex items-center py-4 border-b border-outline-variant/10 hover:bg-surface-container-low transition-all px-2 -mx-2 rounded group">
                <div className={`w-10 h-10 rounded-lg bg-surface-container-highest flex items-center justify-center mr-4 group-hover:scale-110 transition-transform`}>
                  <event.icon className={`w-5 h-5 ${event.color}`} />
                </div>
                <div className="flex-1">
                  <h4 className="text-sm font-bold text-on-surface">{event.title}</h4>
                  <p className="text-xs text-on-surface-variant font-label">{event.details}</p>
                </div>
                <div className="text-right">
                  <p className={`text-[0.6rem] font-bold uppercase ${event.color}`}>{event.status}</p>
                  <p className="text-[0.6rem] text-on-surface-variant">{event.time}</p>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Network Nodes */}
        <div className="glass-panel p-8 rounded-xl">
          <h3 className="text-xl font-headline font-bold text-on-surface mb-6">网络节点</h3>
          <div className="grid grid-cols-2 gap-4">
            {nodes.map((node) => (
              <div key={node.name} className="p-4 bg-surface-container-highest/30 border border-outline-variant/10 rounded-lg flex items-center justify-between group hover:border-secondary/50 transition-all">
                <div className="flex items-center gap-3">
                  <div className="w-8 h-8 rounded bg-surface flex items-center justify-center border border-outline-variant/30 font-bold text-[10px]">
                    {node.symbol}
                  </div>
                  <div>
                    <p className="text-xs font-bold text-on-surface">{node.name}</p>
                    <p className="text-[0.6rem] text-on-surface-variant font-label">{node.type}</p>
                  </div>
                </div>
                <div className="flex flex-col items-end">
                  <div className="pulse-dot"></div>
                  <p className="text-[0.6rem] text-secondary mt-1">{node.latency}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </motion.div>
  );
}
