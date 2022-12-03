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
public class ZolotoyaKoronaTransferHandlerDelegate implements JavaDelegate {

    boolean isException = false;

    public ZolotoyaKoronaTransferHandlerDelegate(boolean isException) {
        this.isException = isException;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        log.info("ZolotoyaKoronaTransferHandlerDelegate: start execution");

        try {

            if (isException)
                throw new BpmnError("Test ZolotoyaKoronaTransferHandlerDelegate exception");

            String koronaStatus = (String) delegateExecution.getVariable(ProcessVariables.ZOLOTAYA_KORONA_STATUS);

            if (ObjectUtils.isEmpty(koronaStatus)) {
                log.warn("[ZolotoyaKoronaTransferHandlerDelegate] Ошибка получения статуса отмены платежа СБП: koronaStatus={}", koronaStatus);
                return;
            }

        } catch (Exception e) {
            log.warn("[ZolotoyaKoronaTransferHandlerDelegate] exception=", e);
            throw e;
        }
    }
}
