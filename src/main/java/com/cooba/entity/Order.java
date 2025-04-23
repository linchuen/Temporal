package com.cooba.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cooba.constant.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("orders")
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String orderNo;
    private int round;

    private int guessNumber;
    private int resultNumber;

    private int betAmount;
    private int winAmount;
    private int odds;
    private Status status;
    private String result;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
