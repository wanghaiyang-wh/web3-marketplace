import { ethers } from 'ethers'

declare global {
  interface Window {
    ethereum?: any
  }
}

export class Web3Service {
  private provider: ethers.BrowserProvider | null = null
  private signer: ethers.Signer | null = null

  async connectWallet(): Promise<string> {
    if (typeof window.ethereum === 'undefined') {
      throw new Error('请安装MetaMask或其他Web3钱包')
    }

    try {
      const accounts = await window.ethereum.request({
        method: 'eth_requestAccounts'
      })

      this.provider = new ethers.BrowserProvider(window.ethereum)
      this.signer = await this.provider.getSigner()

      return accounts[0]
    } catch (error) {
      console.error('连接钱包失败:', error)
      throw error
    }
  }

  async getSigner(): Promise<ethers.Signer> {
    if (!this.signer) {
      await this.connectWallet()
    }
    return this.signer!
  }

  async getBalance(address: string): Promise<string> {
    if (!this.provider) {
      this.provider = new ethers.BrowserProvider(window.ethereum!)
    }
    const balance = await this.provider.getBalance(address)
    return ethers.formatEther(balance)
  }

  async signMessage(message: string): Promise<string> {
    const signer = await this.getSigner()
    return await signer.signMessage(message)
  }

  async switchNetwork(chainId: number): Promise<void> {
    const chainIdHex = `0x${chainId.toString(16)}`

    try {
      await window.ethereum.request({
        method: 'wallet_switchEthereumChain',
        params: [{ chainId: chainIdHex }]
      })
    } catch (error: any) {
      if (error.code === 4902) {
        throw new Error('请添加网络到您的钱包')
      }
      throw error
    }
  }

  getNetworkName(chainId: number): string {
    const networks: Record<number, string> = {
      1: 'Ethereum Mainnet',
      5: 'Goerli Testnet',
      56: 'BSC Mainnet',
      97: 'BSC Testnet',
      137: 'Polygon Mainnet',
      80001: 'Polygon Mumbai'
    }
    return networks[chainId] || `Chain ${chainId}`
  }

  isWalletConnected(): boolean {
    return typeof window.ethereum !== 'undefined' && window.ethereum.isConnected()
  }
}

export const web3Service = new Web3Service()
