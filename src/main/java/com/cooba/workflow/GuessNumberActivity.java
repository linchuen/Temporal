package com.cooba.workflow;

import com.cooba.entity.Order;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface GuessNumberActivity {

    // ① 產生訂單
    @ActivityMethod
    Order generateOrderActivity(int guessNumber, int betAmount);

    // ② 扣款
    @ActivityMethod
    void subtractBalanceActivity(Order order);

    // ③ 結算
    @ActivityMethod
    void settleOrderActivity(Order order);

    // ④ 發獎金
    @ActivityMethod
    void addBalanceActivity(Order order);
}
