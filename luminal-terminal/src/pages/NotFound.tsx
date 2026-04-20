import React from 'react';
import { Link } from 'react-router-dom';
import { Home, AlertCircle } from 'lucide-react';

export default function NotFound() {
  return (
    <div className="h-[60vh] flex flex-col items-center justify-center text-center">
      <div className="w-20 h-20 bg-error/10 rounded-full flex items-center justify-center mb-6">
        <AlertCircle className="w-10 h-10 text-error" />
      </div>
      <h1 className="text-4xl font-headline font-bold text-on-surface mb-2">404 - 模块未找到</h1>
      <p className="text-on-surface-variant font-body mb-8">您请求的终端模块不存在或正在维护中。</p>
      <Link 
        to="/" 
        className="px-6 py-3 bg-surface-container-high border border-outline-variant/30 rounded-lg flex items-center gap-2 text-xs font-bold font-label uppercase tracking-widest hover:border-secondary transition-all"
      >
        <Home className="w-4 h-4" />
        返回总览
      </Link>
    </div>
  );
}
