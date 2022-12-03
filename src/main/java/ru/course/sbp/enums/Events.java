package ru.course.sbp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Бизнес события
 */
@Getter
@AllArgsConstructor
public enum Events {

    C2B_GET_QRC_DATA,//Подготовка перевода С2В этап1
    C2B_GET_QRC_DATA_NSPK,//Запрос данных по QR-коду
    C2B_CHECK_AVAILABILITY_FUNDS,//Подготовка перевода С2В этап2
    C2B_START,//Инициализация перевода С2В
    C2B_PAYMENT_NSPK,//Совершение перевода
    C2B_FINAL_STATE_NSPK,//Получение статуса перевода
    C2B_GET_FINAL_STATE_NSPK,//Запрос статуса перевода из платформы
    C2B_SEND_FINAL_STATUS,//Передача финального статуса перевода в ДБО
    C2B_SEND_TRANSFER_STATUS,//Передача статуса перевода по входящему из ДБО запросу
    C2B_AFS_CHECK,//Отправка документа на проверку в AFS

    B2C_START,//Инициализация перевода B2C
    B2C_REFUND_REQUEST_NSPK,//Получение входящего запроса на создание перевода В2С
    B2C_PAYMENT_CHECK_NSPK,//Проверка возможности совершения перевода
    B2C_PAYMENT_NSPK,//Запрос на совершение перевода в платформу ЦФТ
    B2C_FINAL_STATE_NSPK,//Получение статуса перевода в СБП
    B2C_GET_FINAL_STATE_NSPK,//Запрос статуса перевода из платформы ЦФТ
    B2C_AFS_CHECK//Отправка запроса на проверку данных в AFS
;
}
