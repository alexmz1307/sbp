package ru.course.sbp.delegate.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import ru.course.sbp.constant.ProcessVariables;
import ru.course.sbp.enums.OmniStatus;

@RequiredArgsConstructor
@Service
@Slf4j
public class OmniStatusUpdater implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            OmniStatus newOmniStatus = OmniStatus.valueOf(delegateExecution.getVariable(ProcessVariables.NEW_OMNI_STATUS).toString());
            log.info("[OmniStatusUpdater] xCorrId={} Установлен новый статус для СБП: {}", newOmniStatus);

        } catch (Exception e) {
            log.warn("[OmniStatusUpdater] exception={}", e);
            throw e;
        }
    }

}
