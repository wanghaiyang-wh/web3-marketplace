import React from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from './Sidebar';
import TopBar from './TopBar';

export default function Layout() {
  return (
    <div className="flex min-h-screen bg-surface">
      <Sidebar />
      <div className="flex-1 flex flex-col ml-72">
        <TopBar />
        <main className="flex-1 p-8 pt-24 overflow-y-auto custom-scrollbar relative">
          <Outlet />
          
          {/* Background Atmospheric Glows */}
          <div className="fixed top-[-10%] right-[-10%] w-[500px] h-[500px] bg-secondary/5 rounded-full blur-[120px] pointer-events-none -z-10"></div>
          <div className="fixed bottom-[-10%] left-[20%] w-[600px] h-[600px] bg-primary/5 rounded-full blur-[150px] pointer-events-none -z-10"></div>
        </main>
      </div>
    </div>
  );
}
