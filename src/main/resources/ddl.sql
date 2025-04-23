CREATE DATABASE `guess`

CREATE TABLE guess.rounds (
    round INT PRIMARY KEY,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE guess.orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL,
    round INT NOT NULL,
    guess_number INT NOT NULL,
    result_number INT DEFAULT 0,
    bet_amount INT NOT NULL,
    win_amount INT DEFAULT 0,
    odds INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    result VARCHAR(20) NULL,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);