package com.cooba.workflow;

import com.cooba.dto.GuessRequest;
import com.cooba.entity.Order;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface GuessNumberActivity {

    // ① 產生訂單
    @ActivityMethod
    Order generateOrderActivity(GuessRequest request);

    // ② 扣款
    @ActivityMethod
    void subtractBalanceActivity(Order order);

    // ③ 更新訂單狀態為FAILED
    @ActivityMethod
    void updateOrderFailedActivity(Order order);

    // ④ 結算
    @ActivityMethod
    void settleOrderActivity(Order order, int resultNumber);

    // ⑤ 發獎金
    @ActivityMethod
    void addBalanceActivity(Order order);

    // ⑥ 結束訂單狀態
    @ActivityMethod
    void finalizeOrderActivity(Order order);
}
