package ru.course.sbp.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

/**
 * Ошибка при обращении к REST API.
 */
@Schema(description = "Ошибка при обращении к REST API")
@Data
@Accessors(chain = true)
public class ApiError {

    /**
     * Поле reason phrase
     */
    @Schema(description = "Reason phrase", example = "BAD REQUEST")
    private HttpStatus error;

    /**
     * Поле error code
     */
    @Schema(description = "Error code")
    private ErrorCode errorCode;

    private List<FieldError> errors;

    /**
     * Поле сообщение об ошибке
     */
    @Schema(description = "Сообщение об ошибке", example = "Полученные данные не прошли проверку")
    private String message;

    /**
     * A short description of this request, typically containing request URI and session id
     */
    @Schema(description = "A short description of this request, typically containing request URI and session id",
            example = "uri=//client-checks")
    private String path;

    /**
     * Поле HTTP status code
     */
    @Schema(description = "HTTP status code", example = "400")
    private int status;

    /**
     * Поле время ошибки
     */
    @Schema(description = "Время ошибки", example = "2021-08-03T11:23:39.220+00:00")
    private Date timestamp;

    /**
     * Ошибка в значении поля объекта.
     */
    @Schema(description = "Ошибка в значении поля объекта")
    @Data
    @Accessors(chain = true)
    public static class FieldError {

        /**
         * Поле сообщение об ошибке
         */
        @Schema(description = "Сообщение об ошибке", example = "Контрольная сумма указанного СНИЛС некорректна")
        private String defaultMessage;

        /**
         * The affected field of the object
         */
        @Schema(description = "The affected field of the object", example = "snils")
        private String field;

        /**
         * The name of the affected object
         */
        @Schema(description = "The name of the affected object")
        private String objectName;

    }

}
