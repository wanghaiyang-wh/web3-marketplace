import React from 'react';
import { Search, Bell, Network, UserCircle } from 'lucide-react';

export default function TopBar() {
  return (
    <header className="fixed top-0 right-0 w-[calc(100%-18rem)] h-16 bg-surface/40 backdrop-blur-md border-b border-surface-container-high/30 flex items-center justify-between px-8 z-40">
      <div className="flex items-center gap-8">
        <div className="relative group">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant w-4 h-4" />
          <input 
            type="text" 
            placeholder="搜索资产、交易或网络..." 
            className="bg-surface-container-lowest border-none text-[0.6875rem] font-label tracking-wider px-10 py-2 w-64 focus:ring-1 focus:ring-secondary rounded-sm placeholder:text-on-surface-variant/50 uppercase transition-all"
          />
        </div>
        
        <div className="flex items-center gap-6 font-label text-[0.6875rem] uppercase tracking-wider">
          <div className="flex items-center gap-2">
            <div className="pulse-dot"></div>
            <span className="text-secondary font-bold">LUMINAL 终端</span>
          </div>
          <div className="flex gap-4 border-l border-outline-variant/30 pl-6">
            <span className="text-on-surface/80">交易额: <span className="text-[#00e0ff]">$4.2M</span></span>
            <span className="text-on-surface/80">成交量: <span className="text-on-surface">12.4k</span></span>
            <span className="text-on-surface/80">成功率: <span className="text-[#00ff99]">99.8%</span></span>
            <span className="text-on-surface/80">风险: <span className="text-error">0</span></span>
          </div>
        </div>
      </div>

      <div className="flex items-center gap-6">
        <div className="flex items-center gap-4">
          <button className="text-on-surface-variant hover:text-secondary transition-colors">
            <Bell className="w-5 h-5" />
          </button>
          <button className="text-on-surface-variant hover:text-secondary transition-colors">
            <Network className="w-5 h-5" />
          </button>
        </div>
        
        <div className="flex items-center gap-3 cursor-pointer group border-l border-outline-variant/30 pl-6">
          <div className="text-right">
            <p className="text-[0.65rem] font-bold text-on-surface">管理员_04X</p>
            <p className="text-[0.55rem] text-secondary tracking-widest uppercase">首席策展人</p>
          </div>
          <div className="w-8 h-8 rounded-full border border-primary/20 overflow-hidden">
            <img 
              src="https://lh3.googleusercontent.com/aida-public/AB6AXuDA0PERQhhdjGSVOV9p9s4mtl2lu8Iz-pCWUxkKZ2VYi03SQ2i19whyNnoNt4OwkC-hAWsrnOwLuVD5ILky-kFQFpuZbNxbCbAoiK3UQQpSCsZ1CXw6dVDyvsN4n2HdHRhryBatbe2WLzIOcuXqcY9HI7RNGKJ8kkMPM86qp7tQZDMWeI4uBYSwmQ8C22PQ1m7QFcYdq16-8JbOHqNq0whgCLmdzBoK9p89bhLWpb6c-iRZaDhyh3lHPCqOmz_39y_7J8EswlEz5SlT" 
              alt="Profile" 
              className="w-full h-full object-cover"
              referrerPolicy="no-referrer"
            />
          </div>
        </div>
      </div>
    </header>
  );
}
