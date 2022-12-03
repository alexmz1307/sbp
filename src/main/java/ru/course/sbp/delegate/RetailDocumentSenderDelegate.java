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
public class RetailDocumentSenderDelegate implements JavaDelegate {

    boolean isException = false;

    public RetailDocumentSenderDelegate(boolean isException) {
        this.isException = isException;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        log.info("RetailDocumentSenderDelegate: start execution");

        try {

            if (isException)
                throw new BpmnError("Test RetailDocumentSenderDelegate exception");

        } catch (Exception e) {
            log.warn("[RetailDocumentSenderDelegate] exception=", e);
            throw e;
        }
    }

}
