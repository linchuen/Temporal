package controller

import (
	"math/rand"
	"temporal/order"
	"temporal/wallet"
)

type GuessNumberAPI struct {
	OrderService  *order.OrderService
	WalletService *wallet.WalletService
}

type GuessRequest struct {
	GuessNumber int
	BetAmount   int
}

type GuessResponse struct {
	OrderNo     string
	Odds        int
	Status      string
	Round       int
	CreatedTime string
}

func NewGuessNumberAPI() *GuessNumberAPI {
	return &GuessNumberAPI{}
}

func (api GuessNumberAPI) bet(request GuessRequest) *GuessResponse {
	// 1. 訂單生成
	order := api.OrderService.GenerateOrder(request.GuessNumber, request.BetAmount)

	// 2. 取得錢包
	wallet := api.WalletService.GetWallet()
	if wallet == nil {
		// 錢包不存在
		order.Status = "FAILED"
		api.OrderService.UpdateOrder(order.Round, -1)
		return &GuessResponse{
			OrderNo: order.OrderNo,
			Status:  "WALLET_NOT_FOUND",
		}
	}

	// 3. 檢查餘額足夠
	if wallet.Balance < request.BetAmount {
		order.Status = "FAILED"
		api.OrderService.UpdateOrder(order.Round, -1)
		return &GuessResponse{
			OrderNo: order.OrderNo,
			Status:  "INSUFFICIENT_BALANCE",
		}
	}

	// 4. 扣款
	api.WalletService.Subtract(request.BetAmount)
	order.Status = "PLACED"
	api.OrderService.UpdateOrder(order.Round, -1) // 尚未開獎，resultNumber -1

	// 5. 等待開獎（模擬）
	resultNumber := simulateDrawNumber()

	// 6. 結算訂單
	api.OrderService.UpdateOrder(order.Round, resultNumber)

	// 7. 取得更新後訂單資訊
	updatedOrders := api.OrderService.OrderRepository.GetRoundOrders(order.Round)
	for _, o := range updatedOrders {
		if o.OrderNo == order.OrderNo {
			order = &o
			break
		}
	}

	// 8. 派發獎勵（若贏）
	if order.Status == "WIN" {
		api.WalletService.Add(order.WinAmount)
	}

	// 回傳 Response
	return &GuessResponse{
		OrderNo:     order.OrderNo,
		Odds:        order.Odds,
		Status:      order.Status,
		Round:       order.Round,
		CreatedTime: order.CreatedTime,
	}
}

// 模擬開獎（1~10 隨機數）
func simulateDrawNumber() int {
	return rand.Intn(10) + 1
}
