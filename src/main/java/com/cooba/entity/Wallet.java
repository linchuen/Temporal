package com.cooba.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Wallet {
    private int Balance;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
