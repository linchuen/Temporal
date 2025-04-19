package wallet

import (
	"time"
)

type WalletService struct {
	Wallet *Wallet
}

type Wallet struct {
	Balance     int
	CreatedTime string
	UpdatedTime string
}

func NewWalletService() *WalletService {
	return &WalletService{}
}

// 建立錢包
func (service *WalletService) NewWallet(balance int) *Wallet {
	now := time.Now().Format(time.RFC3339)
	service.Wallet = &Wallet{
		Balance:     balance,
		CreatedTime: now,
		UpdatedTime: now,
	}
	return service.Wallet
}

// 取得錢包
func (service *WalletService) GetWallet() *Wallet {
	return service.Wallet
}

// 設定金額（完全取代）
func (service *WalletService) UpdateAmount(amount int) *Wallet {
	if service.Wallet == nil {
		return nil
	}
	service.Wallet.Balance = amount
	service.Wallet.UpdatedTime = time.Now().Format(time.RFC3339)
	return service.Wallet
}

// 增加金額
func (service *WalletService) Add(amount int) *Wallet {
	if service.Wallet == nil {
		return nil
	}
	service.Wallet.Balance += amount
	service.Wallet.UpdatedTime = time.Now().Format(time.RFC3339)
	return service.Wallet
}

// 扣除金額
func (service *WalletService) Subtract(amount int) *Wallet {
	if service.Wallet == nil {
		return nil
	}
	service.Wallet.Balance -= amount
	service.Wallet.UpdatedTime = time.Now().Format(time.RFC3339)
	return service.Wallet
}
