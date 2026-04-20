import React from 'react';
import { NavLink } from 'react-router-dom';
import { 
  LayoutDashboard, 
  ShieldAlert, 
  Activity, 
  Database, 
  Handshake, 
  Gavel, 
  Settings,
  Wallet
} from 'lucide-react';
import { motion } from 'motion/react';

const navItems = [
  { icon: LayoutDashboard, label: '平台总览', path: '/' },
  { icon: ShieldAlert, label: '安全与风险', path: '/security' },
  { icon: Activity, label: '链上监控', path: '/monitor' },
  { icon: Database, label: '资产管理', path: '/assets' },
  { icon: Handshake, label: '商户 SaaS', path: '/saas' },
  { icon: Gavel, label: 'DAO 治理', path: '/dao' },
  { icon: Settings, label: '系统设置', path: '/settings' },
];

export default function Sidebar() {
  return (
    <aside className="h-screen w-72 fixed left-0 top-0 border-r border-surface-container-high/30 bg-surface/80 backdrop-blur-xl flex flex-col py-8 px-4 z-50 shadow-[40px_0_40px_-20px_rgba(136,173,255,0.06)] font-headline tracking-tight">
      <div className="mb-10 px-4">
        <h1 className="text-2xl font-bold tracking-widest text-on-surface">LUMINAL</h1>
        <p className="text-[10px] text-primary/60 tracking-[0.2em] mt-1 uppercase">数字策展管理端</p>
      </div>
      
      <nav className="flex-1 space-y-1">
        {navItems.map((item) => (
          <NavLink
            key={item.path}
            to={item.path}
            className={({ isActive }) => `
              flex items-center gap-3 px-4 py-3 rounded-lg transition-all duration-300 group
              ${isActive 
                ? 'text-primary border-l-2 border-secondary bg-gradient-to-r from-primary/10 to-transparent scale-100' 
                : 'text-on-surface/60 hover:text-on-surface hover:bg-surface-container-high/50 scale-95 hover:scale-100'}
            `}
          >
            <item.icon className="w-5 h-5" />
            <span className="text-sm font-medium">{item.label}</span>
          </NavLink>
        ))}
      </nav>

      <div className="mt-auto px-4 pt-6 border-t border-surface-container-high/30">
        <button className="w-full bg-gradient-to-r from-primary to-primary-container text-on-primary-container py-3 rounded-md text-[0.6875rem] font-bold tracking-widest uppercase hover:brightness-110 transition-all active:scale-95 shadow-lg shadow-primary/10">
          连接钱包
        </button>
        
        <div className="mt-6 flex items-center gap-3 px-2">
          <div className="relative">
            <img 
              src="https://lh3.googleusercontent.com/aida-public/AB6AXuB7LnDTR_F7sWB9XXh1v5-JkGNo3lPirfh_3uS0iStCf7MAxE4paONx0IkI3YpH0b_BsPn6m_-0M2U0zgs1t33TX3OOAyxUNXJ_t-1eJNLVQxkW41JE5vNF7WQ4-TB7DbZuQDf9tOoGuhUcfen6Kcur2AzKRmRcAgeUzVVTL7pcBNEHopKZ-j948ZBU0uMV9Vg0ZfH5-wcDLQ8drdvjKdJWHGpuAUv2MW9AoQ86lVKJcZRu5dXCRAeHqi9pajqVl_aU6XkTlI1DI8dL" 
              alt="Avatar" 
              className="w-10 h-10 rounded-full border border-primary/20"
              referrerPolicy="no-referrer"
            />
            <div className="absolute -bottom-0.5 -right-0.5 w-3 h-3 bg-[#00ff99] border-2 border-surface rounded-full"></div>
          </div>
          <div>
            <p className="text-xs font-bold text-on-surface">Curator_01</p>
            <p className="text-[0.6rem] text-on-surface-variant uppercase tracking-tighter">管理终端</p>
          </div>
        </div>
      </div>
    </aside>
  );
}
