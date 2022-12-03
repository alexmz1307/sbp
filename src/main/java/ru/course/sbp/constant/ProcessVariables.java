package ru.course.sbp.constant;

public class ProcessVariables {
    //public static final String C2B_DTO = "c2bDto";

    public static final String AFS_STATUS = "afsStatus";
    public static final String CANCEL_SBP_PAY = "cancelSbpPay";

    public static final String HOLD_CODE_RESPONSE = "holdCode";
    public static final String UNHOLD_CODE_RESPONSE = "unholdCode";
    public static final String RTL_DOCUMENT_STATUS = "documentStatus";
    public static final String RTL_DOCUMENT_ERROR_CODE = "documentErrorCode";
    public static final String SBP_STATUS = "sbpStatus";
    public static final String AFS_CHECK_RESULT = "afsCheckResult";
    public static final String ZOLOTAYA_KORONA_STATUS = "koronaStatus";
    public static final String NEW_OMNI_STATUS = "newOmniStatus";
    public static final String OLD_OMNI_STATUS = "oldOmniStatus";
    public static final String NSPK_STATUS = "nspkStatus";
    public static final String CANCEL_SBP_PAY_OK = "cancelSbpPayOk";
    public static final String USER_CHOICE = "userChoice";

    private ProcessVariables() {
        throw new IllegalStateException("Constant class");
    }
}
