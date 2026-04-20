import React from 'react';
import { 
  Settings as SettingsIcon, 
  Shield, 
  Bell, 
  Globe, 
  Lock, 
  Cpu,
  Database,
  ChevronRight
} from 'lucide-react';
import { motion } from 'motion/react';

const settingGroups = [
  { 
    title: '系统核心', 
    icon: Cpu, 
    items: [
      { label: 'AI 响应阈值', value: '0.85 (严格)', desc: '控制自动风控系统的敏感度' },
      { label: '节点同步频率', value: '250ms', desc: '跨链数据刷新间隔' },
    ]
  },
  { 
    title: '安全与隐私', 
    icon: Shield, 
    items: [
      { label: '双重身份验证', value: '已开启', desc: '管理员操作需要硬件钱包确认' },
      { label: 'IP 白名单', value: '3 个地址', desc: '限制后台访问来源' },
    ]
  },
  { 
    title: '通知设置', 
    icon: Bell, 
    items: [
      { label: '紧急警报', value: '全渠道', desc: '包括 Telegram, Discord 和邮件' },
      { label: '每日摘要', value: '09:00 UTC', desc: '平台运营数据汇总' },
    ]
  }
];

export default function Settings() {
  return (
    <motion.div 
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      className="max-w-4xl space-y-8"
    >
      <div className="mb-10">
        <h2 className="font-headline text-3xl font-bold text-on-surface tracking-tight">系统设置</h2>
        <p className="text-on-surface-variant mt-1 font-body">配置终端全局参数、安全策略和通知偏好。</p>
      </div>

      <div className="space-y-6">
        {settingGroups.map((group) => (
          <div key={group.title} className="glass-panel rounded-xl overflow-hidden border border-outline-variant/10">
            <div className="px-6 py-4 bg-surface-container-high/50 border-b border-outline-variant/10 flex items-center gap-3">
              <group.icon className="w-5 h-5 text-primary" />
              <h3 className="text-sm font-headline font-bold uppercase tracking-widest">{group.title}</h3>
            </div>
            <div className="divide-y divide-outline-variant/10">
              {group.items.map((item) => (
                <div key={item.label} className="px-6 py-6 flex items-center justify-between hover:bg-surface-container-low transition-colors cursor-pointer group">
                  <div>
                    <p className="text-sm font-bold text-on-surface">{item.label}</p>
                    <p className="text-xs text-on-surface-variant mt-1">{item.desc}</p>
                  </div>
                  <div className="flex items-center gap-4">
                    <span className="text-xs font-mono text-secondary bg-secondary/5 px-2 py-1 rounded">{item.value}</span>
                    <ChevronRight className="w-4 h-4 text-on-surface-variant group-hover:translate-x-1 transition-transform" />
                  </div>
                </div>
              ))}
            </div>
          </div>
        ))}
      </div>

      <div className="pt-6 flex justify-end gap-4">
        <button className="px-6 py-2 border border-outline-variant text-xs font-bold font-label uppercase tracking-widest hover:bg-surface-variant transition-all">
          重置默认
        </button>
        <button className="px-6 py-2 bg-primary text-on-primary text-xs font-bold font-label uppercase tracking-widest rounded-md hover:brightness-110 transition-all shadow-lg shadow-primary/20">
          保存更改
        </button>
      </div>
    </motion.div>
  );
}
