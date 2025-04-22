package com.cooba.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettleResponse {
    private int round;
    private int resultNumber;
    private int winAmount;
    private String message;
}
