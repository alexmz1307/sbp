package ru.course.sbp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.course.sbp.dto.c2b.C2bProcessStart;
import ru.course.sbp.service.C2bService;

@Tag(name = "Перевод С2В", description = "Перевод C2B")
@RestController
@RequiredArgsConstructor
@Slf4j
public class C2bController {

    private final C2bService c2bService;

    @PostMapping("c2b/transfers/start")
    @Operation(summary = "Запрос старта процесса выполнения перевода С2B", description = "В процессе выполнения происходит проверка в AFS, " +
            "проверка достаточности средств, проверка ЗОД, холдирование средств, проведение платежа в НСПК, отправка документа в Ритейл, " +
            "информирование ДБО, расхолдирование средств в случае ошибки")
    public void c2bInit(
            @Parameter(description = "Запрос старта процесса С2B")
            @RequestBody C2bProcessStart c2BProcessStart) {
        c2bService.initC2bPayment(c2BProcessStart);
    }

}
