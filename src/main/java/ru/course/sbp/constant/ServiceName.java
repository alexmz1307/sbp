package ru.course.sbp.constant;

/**
 * Константы доменного имени и названия микросервиса
 */
public class ServiceName {

    private ServiceName() {
        throw new IllegalStateException("Constant class");
    }

    public static final String MICROSERVICE = "sbp";
    public static final String DOMAIN = "payment-domain";

}
