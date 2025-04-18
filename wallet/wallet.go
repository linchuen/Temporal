package wallet

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

func (service WalletService) NewWallet(balance int) *Wallet {
	service.Wallet = &Wallet{
		Balance:     balance,
		CreatedTime: "",
		UpdatedTime: "",
	}
	return service.Wallet
}

func (service WalletService) GetWallet() *Wallet {
	return service.Wallet
}

func (service WalletService) UpdateAmount(amount int) *Wallet {
	service.Wallet.Balance = amount
	service.Wallet.UpdatedTime = ""
	return service.Wallet
}

func (service WalletService) Add(amount int) *Wallet {
	service.Wallet.Balance += amount
	service.Wallet.UpdatedTime = ""
	return service.Wallet
}

func (service WalletService) Subtract(amount int) *Wallet {
	service.Wallet.Balance -= amount
	service.Wallet.UpdatedTime = ""
	return service.Wallet
}
