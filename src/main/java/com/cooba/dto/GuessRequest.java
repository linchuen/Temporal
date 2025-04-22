package com.cooba.dto;


import lombok.Data;

@Data
public class GuessRequest {
    private int guessNumber;
    private int betAmount;
}