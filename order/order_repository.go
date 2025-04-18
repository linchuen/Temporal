package order

type RoundRepository struct {
}

func (repository RoundRepository) GetRound() int {
	return 0
}

func (repository RoundRepository) GetNextRound() int {
	return repository.GetRound() + 1
}

type OrderRepository struct {
}

func (repository OrderRepository) CreateOrder(order Order) {

}

func (repository OrderRepository) UpdateOrder(order Order) {

}

func (repository OrderRepository) GetRoundOrders(round int) []Order {
	return []Order{}
}
