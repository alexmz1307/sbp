package ru.course.sbp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.course.sbp.dto.c2b.C2bProcessStart;
import ru.course.sbp.service.C2bService;
import ru.course.sbp.service.CamundaProcessInitializerService;

@Service
@RequiredArgsConstructor
@Slf4j
public class C2bServiceImpl implements C2bService {

    private final CamundaProcessInitializerService camundaProcessInitializerService;

    @Override
    public void initC2bPayment(C2bProcessStart c2bProcessStart) {

        camundaProcessInitializerService.startC2bProcess(c2bProcessStart);

    }
}
