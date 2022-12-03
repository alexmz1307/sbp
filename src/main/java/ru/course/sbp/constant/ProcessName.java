package ru.course.sbp.constant;

public class ProcessName {
    private ProcessName() {
        throw new IllegalStateException("Constant class");
    }

    public static final String C2B_PROCESS_NAME = "c2bProcess";
    public static final String B2C_PROCESS_NAME = "b2cProcess";
}
