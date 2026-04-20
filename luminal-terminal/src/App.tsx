/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import Overview from './pages/Overview';
import Security from './pages/Security';
import Monitor from './pages/Monitor';
import Assets from './pages/Assets';
import SaaS from './pages/SaaS';
import DAO from './pages/DAO';
import Settings from './pages/Settings';
import NotFound from './pages/NotFound';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Overview />} />
          <Route path="security" element={<Security />} />
          <Route path="monitor" element={<Monitor />} />
          <Route path="assets" element={<Assets />} />
          <Route path="saas" element={<SaaS />} />
          <Route path="dao" element={<DAO />} />
          <Route path="settings" element={<Settings />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

