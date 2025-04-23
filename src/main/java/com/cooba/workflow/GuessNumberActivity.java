package com.cooba.workflow;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface GuessNumberActivity {

    // ① 扣款
    @ActivityMethod
    void subtractBalanceActivity(String orderNo, int betAmount);

    // ② 結算
    @ActivityMethod
    void settleOrderActivity(String orderNo, int resultNumber);

    // ③ 發獎金
    @ActivityMethod
    void addBalanceActivity(String orderNo);

    // ④ 結束訂單狀態
    @ActivityMethod
    void finalizeOrderActivity(String orderNo);
}
