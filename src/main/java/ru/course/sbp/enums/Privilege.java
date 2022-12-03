package ru.course.sbp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Перечисление привилегий микросервиса
 */
@Getter
@AllArgsConstructor
public enum Privilege {

//    CREATE_C2B_CFT_TRANSFERS("CREATE_C2B_CFT_TRANSFERS_RESOURCE", "CREATE_C2B_CFT_TRANSFERS_ACTION", "Совершение перевода C2B в ЦФТ"),
//    CREATE_C2B_CFT_TRANSFERS_STATES("CREATE_C2B_CFT_TRANSFERS_STATES_RESOURCE", "CREATE_C2B_CFT_TRANSFERS_STATES_ACTION", "Запрос статуса перевода С2В из ЦФТ"),
//    READ_C2B_TRANSFERS("READ_C2B_TRANSFERS_RESOURCE", "READ_C2B_TRANSFERS_ACTION", "Получение данных о переводе С2В"),
//    CREATE_C2B_SBP_TRANSFERS("CREATE_C2B_SBP_TRANSFERS_RESOURCE", "CREATE_C2B_SBP_TRANSFERS_ACTION", "Сохранение данных о новом переводе С2В"),
//    UPDATE_C2B_TRANSFERS("UPDATE_C2B_TRANSFERS_RESOURCE", "UPDATE_C2B_TRANSFERS_ACTION", "Обновление данных о переводе С2В"),
    CREATE_C2B_TRANSFERS_INIT("CREATE_C2B_TRANSFERS_INIT_RESOURCE", "CREATE_C2B_TRANSFERS_INIT_ACTION", "Инициализация перевода С2B"),
    CREATE_C2B_TRANSFERS_STAGE1("CREATE_C2B_TRANSFERS_STAGE1_RESOURCE", "CREATE_C2B_TRANSFERS_STAGE1_ACTION", "Подготовка перевода С2В - 1"),
    CREATE_C2B_TRANSFERS_STAGE2("CREATE_C2B_TRANSFERS_STAGE2_RESOURCE", "CREATE_C2B_TRANSFERS_STAGE2_ACTION", "Подготовка перевода С2В - 2"),
    READ_C2B_AFS_STATUS("READ_C2B_AFS_STATUS_RESOURCE", "READ_C2B_AFS_STATUS_ACTION", "Получение статуса АФС С2В"),
    UPDATE_C2B_AFS_STATUS("UPDATE_C2B_AFS_STATUS_RESOURCE", "UPDATE_C2B_AFS_STATUS_ACTION", "Изменение статуса АФС С2В"),
    READ_C2B_TRANSFERS_STATUS("READ_C2B_TRANSFERS_STATUS_RESOURCE", "READ_C2B_TRANSFERS_STATUS_ACTION", "Получение статуса перевода С2В"),

//    CREATE_B2C_CFT_TRANSFERS("CREATE_B2C_CFT_TRANSFERS_RESOURCE", "CREATE_B2C_CFT_TRANSFERS_ACTION", "Запрос на совершение перевода B2C в ЦФТ"),
//    CREATE_B2C_CFT_TRANSFERS_STATES("CREATE_B2C_CFT_TRANSFERS_STATES_RESOURCE", "CREATE_B2C_CFT_TRANSFERS_STATES_ACTION", "Запрос статуса перевода B2C из ЦФТ"),
//    CREATE_B2C_TRANSFERS("CREATE_B2C_TRANSFERS_RESOURCE", "CREATE_B2C_TRANSFERS_ACTION", "Сохранение данных о новом переводе B2C"),
//    UPDATE_B2C_TRANSFERS("UPDATE_B2C_TRANSFERS_RESOURCE", "UPDATE_B2C_TRANSFERS_ACTION", "Обновление данных о переводе B2C"),
//    CREATE_B2C_TRANSFERS_CHECKS("CREATE_B2C_TRANSFERS_CHECKS_RESOURCE", "CREATE_B2C_TRANSFERS_CHECKS_ACTION", "Проверка возможности перевода B2C"),
//    CREATE_B2C_TRANSFERS_INIT("CREATE_B2C_TRANSFERS_INIT_RESOURCE", "CREATE_B2C_TRANSFERS_INIT_ACTION", "Инициализация перевода B2C"),
    READ_B2C_AFS_STATUS("READ_B2C_AFS_STATUS_RESOURCE", "READ_B2C_AFS_STATUS_ACTION", "Получение статуса АФС B2C"),
    UPDATE_B2C_AFS_STATUS("UPDATE_B2C_AFS_STATUS_RESOURCE", "UPDATE_B2C_AFS_STATUS_ACTION", "Изменение статуса АФС B2C"),
//    READ_B2C_TRANSFERS("READ_B2C_TRANSFERS_RESOURCE", "READ_B2C_TRANSFERS_ACTION", "Получение данных о переводе B2C")
    ;

    /**
     * объект политики
     */
    private final String obj;

    /**
     * действие в политике
     */
    private final String act;

    /**
     * описание привилегии
     */
    private final String description;
}

