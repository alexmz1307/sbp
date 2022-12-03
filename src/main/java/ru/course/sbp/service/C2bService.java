package ru.course.sbp.service;

import ru.course.sbp.dto.c2b.C2bProcessStart;

public interface C2bService {
    void initC2bPayment(C2bProcessStart c2bProcessStart);
}
