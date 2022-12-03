package ru.course.sbp.delegate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import ru.course.sbp.constant.ProcessVariables;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusGetterDelegate implements JavaDelegate {

    boolean isException = false;

    public StatusGetterDelegate(boolean isExceptions) {
        this.isException = isException;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        log.info("StatusGetterDelegate: start execution");

        try {

            if (isException)
                throw new BpmnError("Test StatusGetterDelegate exception");

            boolean sbpStatus = (Boolean) delegateExecution.getVariable(ProcessVariables.SBP_STATUS);
            log.warn("[StatusGetterDelegate] Статус платежа СБП: sbpStatus={}", sbpStatus);

            if (isEmpty(sbpStatus)) {
                log.warn("[StatusGetterDelegate] Ошибка получения статуса отмены платежа СБП: sbpStatus={}", sbpStatus);
                return;
            }

        } catch (Exception e) {
            log.warn("[StatusGetterDelegate] exception=", e);
            throw e;
        }
    }
}
