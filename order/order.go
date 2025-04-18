package order

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
	return &Order{}
}

func (api OrderService) UpdateOrder(round int, resultNumber int) *Order {
	return &Order{}
}
