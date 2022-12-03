package ru.course.sbp.constant;

/**
 * Константы сообщений об ошибках.
 */
public class ErrorMessage {

    private ErrorMessage() {
        throw new IllegalStateException("Constant class");
    }

    public static final String NO_JWT = "JWT должен быть указан";
    public static final String NO_KAFKA_MESSAGE = "Сообщение для отправки в топик Kafka должно быть указано";
    public static final String NO_KAFKA_TOPIC = "Название топика Kafka должно быть указано";
    public static final String OBJECT_IS_NULL = "Получен пустой объект";
    public static final String NO_METRIC_TAG_VALUE = "Значение тега метрики должно быть указано";

}
