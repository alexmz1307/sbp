package ru.course.sbp.constant;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class WorkflowErrorException extends RuntimeException {
    private Date timestamp;
    private int status;
    private String error;
    private String message;
    private ProcessErrorCodes errorCode;

    private void init(ProcessErrorCodes errorCode) {
        this.timestamp = new Date();
        this.errorCode = errorCode;
    }

    public WorkflowErrorException(ProcessErrorCodes errorCode) {
        init(errorCode);
        this.message = errorCode.getMessage();
    }

    public WorkflowErrorException(ProcessErrorCodes errorCode, String message) {
        init(errorCode);
        this.message = message;
    }
}
