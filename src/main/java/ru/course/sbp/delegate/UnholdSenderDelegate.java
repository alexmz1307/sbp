package ru.course.sbp.delegate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnholdSenderDelegate implements JavaDelegate {
    boolean isException = false;

    public UnholdSenderDelegate(boolean isException) {
        this.isException = isException;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        log.info("UnholdSenderDelegate: start execution");

        try {

            if (isException)
                throw new BpmnError("Test UnholdSenderDelegate exception");

        } catch (Exception e) {
            log.warn("[UnholdSenderDelegate] exception=", e);
            throw e;
        }
    }
}
