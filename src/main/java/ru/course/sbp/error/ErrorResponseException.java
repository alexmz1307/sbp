package ru.course.sbp.error;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ErrorResponseException extends RuntimeException {
    private Date timestamp;
    private int status;
    private HttpStatus httpStatus;
    private String error;
    private String message;
    private ErrorCode errorCode;

    private void init(ErrorCode errorCode) {
        this.timestamp = new Date();
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
        this.status = this.httpStatus.value();
        this.error = this.httpStatus.getReasonPhrase();
    }

    public ErrorResponseException(ErrorCode errorCode) {
        init(errorCode);
        this.message = errorCode.getMessage();
    }

    public ErrorResponseException(ErrorCode errorCode, String message) {
        init(errorCode);
        this.message = message;
    }
}
