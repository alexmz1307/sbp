package ru.course.sbp.dto.c2b;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class C2bProcessStart {

    String businesKey;
    Boolean afsStatus;
    Boolean nspkPayStatus;
    Boolean cancelSbpPay;

}
