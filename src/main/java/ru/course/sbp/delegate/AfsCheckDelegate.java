package ru.course.sbp.delegate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.course.sbp.constant.ProcessVariables;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Component
@RequiredArgsConstructor
@Slf4j
public class AfsCheckDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        log.info("AfsCheckDelegate: start execution");

        try {
            Boolean afsStatus = (Boolean) delegateExecution.getVariable(ProcessVariables.AFS_STATUS);

            if (isEmpty(afsStatus)) {
                log.warn("[AfsCheckDelegate] Ошибка получения статуса Антифрода из AFS: afsStatus={}", afsStatus);
                return;
            }

            delegateExecution.setVariable(ProcessVariables.AFS_CHECK_RESULT, afsStatus);

            log.info("[AfsCheckDelegate] AFS_CHECK_RESULT={}", afsStatus);

        } catch (Exception e) {
            log.warn("[AfsCheckDelegate] exception: ", e);
            throw e;
        }

    }

}
