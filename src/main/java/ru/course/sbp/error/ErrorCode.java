package ru.course.sbp.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.course.sbp.constant.ServiceName;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 503
    SBP_NOT_ALLOWED("Платежи СБП запрещены", HttpStatus.SERVICE_UNAVAILABLE, ServiceName.MICROSERVICE),
    // 500
    ERROR("Ошибка сервера", HttpStatus.INTERNAL_SERVER_ERROR, ServiceName.MICROSERVICE),
    EMPTY_ROLES_LIST("Пустой список ролей от рolicy-service", HttpStatus.INTERNAL_SERVER_ERROR, ServiceName.MICROSERVICE),
    // 400
    BAD_ARGUMENT("Неверный параметр запроса", HttpStatus.BAD_REQUEST, ServiceName.MICROSERVICE),
    BAD_CURRENCY("Неверная валюта", HttpStatus.BAD_REQUEST, ServiceName.MICROSERVICE),
    OPERATION_DOUBLE("Дублирование операции", HttpStatus.BAD_REQUEST, ServiceName.MICROSERVICE),
    NOT_FOUND("Не найдено", HttpStatus.BAD_REQUEST, ServiceName.MICROSERVICE),
    // 403
    CHECK_ACCESS_FOR_ROLE("Недостаточно привилегий у роли для доступа к ресурсу", HttpStatus.FORBIDDEN, ServiceName.MICROSERVICE);

    private final String message;
    private final HttpStatus httpStatus;
    private final String service;

}
