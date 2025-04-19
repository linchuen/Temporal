package order

import (
	"database/sql"
	"log"
)

type RoundRepository struct {
	DB *sql.DB
}

func (r *RoundRepository) GetRound() int {
	var round int
	err := r.DB.QueryRow("SELECT round FROM rounds ORDER BY id DESC LIMIT 1").Scan(&round)
	if err != nil {
		if err == sql.ErrNoRows {
			return 0
		}
		log.Printf("GetRound error: %v", err)
		return 0
	}
	return round
}

func (r *RoundRepository) GetNextRound() int {
	current := r.GetRound()
	next := current + 1
	_, err := r.DB.Exec("INSERT INTO rounds (round) VALUES (?)", next)
	if err != nil {
		log.Printf("Insert new round failed: %v", err)
	}
	return next
}
