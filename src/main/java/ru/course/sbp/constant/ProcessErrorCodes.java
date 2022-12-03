package ru.course.sbp.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessErrorCodes {

    HOLDING_ERROR("HOLDING_ERROR"),
    UNHOLDING_ERROR("UNHOLDING_ERROR");

    private String message;
}
