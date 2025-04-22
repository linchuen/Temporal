package com.cooba.dto;

import com.cooba.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuessResponse {
    private String orderNo;
    private int odds;
    private Status status;
    private int round;
    private LocalDateTime createdTime;
    private String message;
    private int balance;
}
