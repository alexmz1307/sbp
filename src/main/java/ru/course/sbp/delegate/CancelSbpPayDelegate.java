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
public class CancelSbpPayDelegate implements JavaDelegate {

    boolean isException = false;

    public CancelSbpPayDelegate(boolean isException) {
        this.isException = isException;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        log.info("CancelSbpPayDelegate: start execution");

        try {

            if (isException)
                throw new BpmnError("Test CancelSbpPayDelegate exception");

            Boolean cancelSbpPay = (Boolean) delegateExecution.getVariable(ProcessVariables.CANCEL_SBP_PAY);

            if (isEmpty(cancelSbpPay)) {
                log.warn("[CancelSbpPayDelegate] Ошибка получения статуса отмены платежа СБП: cancelSbpPay={}", cancelSbpPay);
                return;
            }

        } catch (Exception e) {
            log.warn("[CancelSbpPayDelegate] exception=", e);
            throw e;
        }
    }
}
