package order

import (
	"database/sql"
	"log"
)

type OrderRepository struct {
	DB *sql.DB
}

func (r *OrderRepository) CreateOrder(order Order) {
	_, err := r.DB.Exec(`
		INSERT INTO orders (
			order_no, round, guess_number, bet_amount, status, created_time, updated_time
		) VALUES (?, ?, ?, ?, ?, ?, ?)`,
		order.OrderNo, order.Round, order.GuessNumber, order.BetAmount,
		order.Status, order.CreatedTime, order.UpdatedTime,
	)
	if err != nil {
		log.Printf("CreateOrder error: %v", err)
	}
}

func (r *OrderRepository) UpdateOrder(order Order) {
	_, err := r.DB.Exec(`
		UPDATE orders SET 
			result_number=?, win_amount=?, odds=?, status=?, updated_time=? 
		WHERE order_no=?`,
		order.ResultNumber, order.WinAmount, order.Odds, order.Status,
		order.UpdatedTime, order.OrderNo,
	)
	if err != nil {
		log.Printf("UpdateOrder error: %v", err)
	}
}

func (r *OrderRepository) GetRoundOrders(round int) []Order {
	rows, err := r.DB.Query(`
		SELECT 
			id, order_no, round, guess_number, result_number,
			bet_amount, win_amount, odds, status, created_time, updated_time
		FROM orders WHERE round=?`, round)
	if err != nil {
		log.Printf("GetRoundOrders error: %v", err)
		return nil
	}
	defer rows.Close()

	var orders []Order
	for rows.Next() {
		var order Order
		err := rows.Scan(
			&order.Id, &order.OrderNo, &order.Round, &order.GuessNumber, &order.ResultNumber,
			&order.BetAmount, &order.WinAmount, &order.Odds, &order.Status,
			&order.CreatedTime, &order.UpdatedTime,
		)
		if err != nil {
			log.Printf("Row scan error: %v", err)
			continue
		}
		orders = append(orders, order)
	}
	return orders
}
