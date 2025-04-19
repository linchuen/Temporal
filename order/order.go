package order

import (
	"fmt"
	"time"
)

type OrderService struct {
	roundRepository *RoundRepository
	orderRepository *OrderRepository
}

type Round struct {
	Round int
}

type Order struct {
	Id      int
	OrderNo string
	Round   int

	GuessNumber  int
	ResultNumber int

	BetAmount int
	WinAmount int
	Odds      int
	Status    string

	CreatedTime string
	UpdatedTime string
}

func (api OrderService) GenerateOrder(guessNumber int, betAmount int) *Order {
	currentRound := api.roundRepository.GetNextRound()

	orderNo := fmt.Sprintf("ORD-%d-%d", currentRound, time.Now().UnixNano())
	now := time.Now().Format(time.RFC3339)

	order := Order{
		OrderNo:     orderNo,
		Round:       currentRound,
		GuessNumber: guessNumber,
		BetAmount:   betAmount,
		Status:      "PENDING",
		CreatedTime: now,
		UpdatedTime: now,
	}

	api.orderRepository.CreateOrder(order)
	return &order
}

func (api OrderService) UpdateOrder(round int, resultNumber int) {
	orders := api.orderRepository.GetRoundOrders(round)

	for _, order := range orders {
		order.ResultNumber = resultNumber
		order.UpdatedTime = time.Now().Format(time.RFC3339)

		if order.GuessNumber == resultNumber {
			order.Odds = 10
			order.WinAmount = order.BetAmount * order.Odds
			order.Status = "WIN"
		} else {
			order.Odds = 0
			order.WinAmount = 0
			order.Status = "LOSE"
		}

		api.orderRepository.UpdateOrder(order)
	}
}
