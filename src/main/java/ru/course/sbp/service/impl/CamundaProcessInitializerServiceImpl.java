package ru.course.sbp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngines;
import org.springframework.stereotype.Service;
import ru.course.sbp.constant.ProcessName;
import ru.course.sbp.constant.ProcessVariables;
import ru.course.sbp.dto.c2b.C2bProcessStart;
import ru.course.sbp.service.CamundaProcessInitializerService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CamundaProcessInitializerServiceImpl implements CamundaProcessInitializerService {

    @Override
    public void startC2bProcess(C2bProcessStart c2bProcessStart) {
        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put(ProcessVariables.AFS_STATUS, c2bProcessStart.getAfsStatus());
            variables.put(ProcessVariables.SBP_STATUS, false);
            variables.put(ProcessVariables.UNHOLD_CODE_RESPONSE, "-1");
            variables.put(ProcessVariables.ZOLOTAYA_KORONA_STATUS, "-1");
            ProcessEngines.getDefaultProcessEngine()
                    .getRuntimeService()
                    .startProcessInstanceByKey(ProcessName.C2B_PROCESS_NAME, c2bProcessStart.getBusinesKey(), variables);
        }catch(Exception e){
            log.warn("CamundaProcessInitializerServiceImpl Exception={}", e);
            throw e;
        }
    }
}
