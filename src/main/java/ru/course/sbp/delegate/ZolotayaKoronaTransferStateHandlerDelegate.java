package ru.course.sbp.delegate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import ru.course.sbp.constant.ProcessVariables;

@Slf4j
@RequiredArgsConstructor
@Service
public class ZolotayaKoronaTransferStateHandlerDelegate implements JavaDelegate {

    boolean isException = false;

    public ZolotayaKoronaTransferStateHandlerDelegate(boolean isException) {
        this.isException = isException;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        log.info("ZolotayaKoronaTransferStateHandlerDelegate: start execution");

        try {

            if (isException)
                throw new BpmnError("Test ZolotayaKoronaTransferStateHandlerDelegate exception");

            String nspkStatus = (String) delegateExecution.getVariable(ProcessVariables.NSPK_STATUS);

            if (ObjectUtils.isEmpty(nspkStatus)) {
                log.warn("[ZolotayaKoronaTransferStateHandlerDelegate] Ошибка получения статуса платежа СБП: nspkStatus={}", nspkStatus);
                return;
            }

        } catch (Exception e) {
            log.warn("[ZolotayaKoronaTransferStateHandlerDelegate] exception=", e);
            throw e;
        }
    }
}
