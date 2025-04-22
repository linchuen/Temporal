package com.cooba.workflow;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface GuessNumberActivity {

    // ① 產生訂單
    @ActivityMethod
    void generateOrderActivity(String orderId);

    // ② 扣款
    @ActivityMethod
    void subtractBalanceActivity(String orderId);

    // ③ 更新訂單狀態為 PENDING
    @ActivityMethod
    void updateOrderStatusActivity(String orderId);

    // ④ 結算
    @ActivityMethod
    void settleOrderActivity(String orderId);

    // ⑤ 發獎金
    @ActivityMethod
    void addBalanceActivity(String orderId);

    // ⑥ 結束訂單狀態
    @ActivityMethod
    void finalizeOrderActivity(String orderId);
}
