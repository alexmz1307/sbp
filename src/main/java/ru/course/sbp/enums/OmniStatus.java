package ru.course.sbp.enums;

public enum OmniStatus {
    NOT_STARTED,
    //C2B
    START,
    AFS,
    RTL_HOLD,
    SBP_CONFIRM,
    SBP_WAIT_STATUS,
    RTL_UNHOLD,
    RTL_SEND_DOCUMENT,
    RTL_GET_STATUS,
    COMPLETED,
    DECLINED,
    ANALYSIS,
    //B2C
    SBP_CHECK,
    CFT_DATA,
    CFT_DEBIT,
    CFT_ROLLBACK,
    CFT_SEND_STATUS
}
