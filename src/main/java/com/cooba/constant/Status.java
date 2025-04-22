package com.cooba.constant;

import lombok.Getter;

@Getter
public enum Status {
    INIT,
    FAILED,
    PENDING,
    SETTLE,
    AWARD,
    DONE;
}
