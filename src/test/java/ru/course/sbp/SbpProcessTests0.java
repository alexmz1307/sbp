package ru.course.sbp;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.community.process_test_coverage.junit5.platform7.ProcessEngineCoverageExtension;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.course.sbp.constant.ProcessVariables;
import ru.course.sbp.delegate.*;
import ru.course.sbp.delegate.utils.OmniStatusUpdater;

import java.util.HashMap;
import java.util.Map;

import static org.camunda.bpm.scenario.Scenario.run;
import static org.mockito.Mockito.*;

@Slf4j
//@ExtendWith(ProcessEngineExtension.class)
@ExtendWith(ProcessEngineCoverageExtension.class)
//@Import(ProcessEngineCoverageConfiguration.class)
@Deployment(resources = "bpmn/c2b.bpmn")
public class SbpProcessTests0 {

	final String PROCESS_KEY = "c2bProcess";

//	@ClassRule
//	public static ProcessEngineRule processEngineRule = new ProcessEngineRule(createProcessEngine());

//	@Rule
//	public ProcessEngineRule processEngineRule = new ProcessEngineRule(createProcessEngine());

//	static ProcessEngine processEngine;
//	ProcessEngine processEngine;
	ProcessScenario processScenario = mock(ProcessScenario.class);

	//IdentityService identityService = mock(IdentityService.class);

	//protected static ProcessEngine createProcessEngine() {

//	protected ProcessEngine createProcessEngine() {
////		//	processEngine = StandaloneInMemProcessEngineConfiguration
////		//			.createStandaloneInMemProcessEngineConfiguration()
////		//			.setJdbcUrl("jdbc:h2:mem:camunda;DB_CLOSE_DELAY=1000")
////		//			.buildProcessEngine();
////		//	return processEngine;
//
//		processEngine = SpringProcessWithCoverageEngineConfiguration
//				.createStandaloneInMemProcessEngineConfiguration()
//				.setJdbcUrl("jdbc:h2:mem:camunda;DB_CLOSE_DELAY=1000")
//				.buildProcessEngine();
//		return processEngine;
//	}

//	@RegisterExtension
//	ProcessEngineExtension extension = ProcessEngineExtension
//			.builder()
//			.useProcessEngine(processEngine)
//			.build();

//	@RegisterExtension
//	static ProcessEngineCoverageExtension extension = ProcessEngineCoverageExtension
//			.builder().assertClassCoverageAtLeast(0.5).build();

//	@RegisterExtension
//	public static ProcessEngineCoverageExtension extension = ProcessEngineExtensionProvider.extension;
//    public static ProcessEngineCoverageExtension extension = ProcessEngineCoverageExtension.builder(
//            new ProcessCoverageInMemProcessEngineConfiguration().setHistory(ProcessEngineConfiguration.HISTORY_FULL)).build();

	@BeforeEach
	public void setup() {
		Mocks.register("afsCheckDelegate", new AfsCheckDelegate());
		Mocks.register("omniStatusUpdater", new OmniStatusUpdater());
		//extension.setIdentityService(identityService);
	}

	@After
	public void calculateCoverage() {
//		for (org.camunda.bpm.engine.repository.Deployment deployment : deployedDummies) {
//			rule.getProcessEngine().getRepositoryService().deleteDeployment(deployment.getId());
//		}
//		deployedDummies.clear();

		// calculate coverage for all tests
//		ProcessTestCoverage.calculate(processEngineRule.getProcessEngine());
//		Mocks.reset();
	}

	@Test
	public void testCancelPath() {
//		Mocks.register("afsCheckDelegate", new AfsCheckDelegate());
//		Mocks.register("omniStatusUpdater", new OmniStatusUpdater());

		Mocks.register("cancelSbpPayDelegate", new CancelSbpPayDelegate(false));

		Map<String, Object> vars = new HashMap<>();
		vars.put(ProcessVariables.AFS_CHECK_RESULT, "FRAUD");

		// "given" part of the test
		when(processScenario.waitsAtServiceTask("StartEvent_c2b")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_afs")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_cancel_pay")).thenReturn(task -> task.complete());

		// "when" part of the test
		run(processScenario).startByKey(PROCESS_KEY, vars).execute();
		// "then" part of the test
		verify(processScenario).hasFinished("Event_end_after_declined");
	}

	@Test
	public void testCancelDeclinePath() {
		Mocks.register("cancelSbpPayDelegate", new CancelSbpPayDelegate(true));

		Map<String, Object> vars = new HashMap<>();
		vars.put(ProcessVariables.AFS_CHECK_RESULT, "FRAUD");

		// "given" part of the test
		when(processScenario.waitsAtServiceTask("StartEvent_c2b")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_afs")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_cancel_pay")).thenReturn(task -> task.handleBpmnError("Test CancelSbpPayDelegate exception"));
		when(processScenario.waitsAtServiceTask("Event_exception_cancel")).thenReturn(task -> task.complete());

		// "when" part of the test
		run(processScenario).startByKey(PROCESS_KEY, vars).execute();
		// "then" part of the test
		verify(processScenario).hasFinished("Event_end_after_declined");

	}

	@Test
	public void testHoldErrorCancelDeclinePath() {
		Mocks.register("cancelSbpPayDelegate", new CancelSbpPayDelegate(true));
		Mocks.register("holdSenderDelegate", new HoldSenderDelegate(true));
		Mocks.register("unholdSenderDelegate", new UnholdSenderDelegate());
		Mocks.register("statusGetterDelegate", new StatusGetterDelegate(true));

		Map<String, Object> vars = new HashMap<>();
		vars.put(ProcessVariables.AFS_CHECK_RESULT, "LEGIT");
		vars.put(ProcessVariables.SBP_STATUS, false);
		vars.put(ProcessVariables.NSPK_STATUS, "CONFIRMED");
		vars.put(ProcessVariables.ZOLOTAYA_KORONA_STATUS, "1");

		// "given" part of the test
		when(processScenario.waitsAtServiceTask("StartEvent_c2b")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_afs")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_rtl_hold")).thenReturn(task -> task.handleBpmnError("Test HoldSenderDelegate exception"));
		when(processScenario.waitsAtServiceTask("Event_exception_cancel")).thenReturn(task -> task.complete());

		// "when" part of the test
		run(processScenario).startByKey(PROCESS_KEY, vars).execute();
		// "then" part of the test
		verify(processScenario).hasFinished("Event_end_after_declined");
	}

	@Test
	public void testHoldOkSendErrorUnholdOkPath() {
		Mocks.register("holdSenderDelegate", new HoldSenderDelegate(false));
		Mocks.register("unholdSenderDelegate", new UnholdSenderDelegate());
		Mocks.register("statusGetterDelegate", new StatusGetterDelegate(true));
		Mocks.register("zolotoyaKoronaTransferHandlerDelegate", new ZolotoyaKoronaTransferHandlerDelegate(false));

		Map<String, Object> vars = new HashMap<>();
		vars.put(ProcessVariables.AFS_CHECK_RESULT, "LEGIT");
		vars.put(ProcessVariables.SBP_STATUS, false);
		vars.put(ProcessVariables.NSPK_STATUS, "CONFIRMED");
		vars.put(ProcessVariables.ZOLOTAYA_KORONA_STATUS, "1");
		vars.put(ProcessVariables.UNHOLD_CODE_RESPONSE, "0");

		// "given" part of the test
		when(processScenario.waitsAtServiceTask("StartEvent_c2b")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_afs")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_rtl_hold")).thenReturn(task -> task.handleBpmnError("Test HoldSenderDelegate exception"));
		when(processScenario.waitsAtTimerIntermediateEvent("Event_send_pay_timer")).thenReturn(task -> task.isEnded());
		when(processScenario.waitsAtTimerIntermediateEvent("Event_sbp_confirm_timer")).thenReturn(task -> task.isEnded());
		when(processScenario.waitsAtServiceTask("Activity_unhold")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Event_declined_after_unhold")).thenReturn(task -> task.complete());
		// "when" part of the test
		run(processScenario).startByKey(PROCESS_KEY, vars).execute();
		// "then" part of the test
		verify(processScenario).hasFinished("Event_end_after_declined");
	}

	@Test
	public void testHoldOkSendErrorUnholdErrorPath() {
		Mocks.register("holdSenderDelegate", new HoldSenderDelegate(false));
		Mocks.register("unholdSenderDelegate", new UnholdSenderDelegate(false));
		Mocks.register("statusGetterDelegate", new StatusGetterDelegate(true));
		Mocks.register("zolotoyaKoronaTransferHandlerDelegate", new ZolotoyaKoronaTransferHandlerDelegate(false));

		Map<String, Object> vars = new HashMap<>();
		vars.put(ProcessVariables.AFS_CHECK_RESULT, "LEGIT");
		vars.put(ProcessVariables.SBP_STATUS, false);
		vars.put(ProcessVariables.NSPK_STATUS, "CONFIRMED");
		vars.put(ProcessVariables.ZOLOTAYA_KORONA_STATUS, "1");
		vars.put(ProcessVariables.UNHOLD_CODE_RESPONSE, "1");
		vars.put(ProcessVariables.USER_CHOICE, "completed");

		// "given" part of the test
		when(processScenario.waitsAtServiceTask("StartEvent_c2b")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_afs")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_rtl_hold")).thenReturn(task -> task.handleBpmnError("Test HoldSenderDelegate exception"));
		when(processScenario.waitsAtTimerIntermediateEvent("Event_send_pay_timer")).thenReturn(task -> task.isEnded());
		when(processScenario.waitsAtTimerIntermediateEvent("Event_sbp_confirm_timer")).thenReturn(task -> task.isEnded());
		when(processScenario.waitsAtServiceTask("Activity_rtl_unhold")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtTimerIntermediateEvent("Event_rtl_unhold_timer")).thenReturn(task -> task.isEnded());
		when(processScenario.waitsAtTimerIntermediateEvent("Event_unhold_timer")).thenReturn(task -> task.isEnded());
		when(processScenario.waitsAtUserTask("Analysis_user_task")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Event_declined_after_unhold")).thenReturn(task -> task.complete());
		// "when" part of the test
		run(processScenario).startByKey(PROCESS_KEY, vars).execute();
		// "then" part of the test
		verify(processScenario).hasFinished("Event_end_after_analysis");
	}

	@Test
	public void testHoldOkSendOkStatusErrorAnalysisPath() {
		Mocks.register("holdSenderDelegate", new HoldSenderDelegate(false));
		Mocks.register("zolotoyaKoronaTransferHandlerDelegate", new ZolotoyaKoronaTransferHandlerDelegate(false));
		Mocks.register("zolotayaKoronaTransferStateHandlerDelegate", new ZolotayaKoronaTransferStateHandlerDelegate(false));
		Mocks.register("statusGetterDelegate", new StatusGetterDelegate(true));

		Map<String, Object> vars = new HashMap<>();
		vars.put(ProcessVariables.AFS_CHECK_RESULT, "LEGIT");
		vars.put(ProcessVariables.SBP_STATUS, false);
		vars.put(ProcessVariables.NSPK_STATUS, "CONFIRMED");
		vars.put(ProcessVariables.ZOLOTAYA_KORONA_STATUS, "0");
		vars.put(ProcessVariables.USER_CHOICE, "completed");

		// "given" part of the test
		when(processScenario.waitsAtServiceTask("StartEvent_c2b")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_afs")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_rtl_hold")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtTimerIntermediateEvent("Event_sbp_status_timer")).thenReturn(task -> task.isEnded());
		when(processScenario.waitsAtTimerIntermediateEvent("Event_sub_sbp_wait_status_timer")).thenReturn(task -> task.isEnded());
		when(processScenario.waitsAtServiceTask("Activity_sbp_wait_status")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtTimerIntermediateEvent("Event_get_nspk_status_timer")).thenReturn(task -> task.isEnded());
		when(processScenario.waitsAtTimerIntermediateEvent("Event_nspk_status_timer")).thenReturn(task -> task.isEnded());
		when(processScenario.waitsAtServiceTask("Activity_get_nspk_status")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtUserTask("Analysis_user_task")).thenReturn(task -> task.complete());

		// "when" part of the test
		run(processScenario).startByKey(PROCESS_KEY, vars).execute();
		// "then" part of the test
		verify(processScenario).hasFinished("Event_end_after_analysis");
	}

	@Test
	public void testHoldOkSendOkStatusErrorUnholdPath() {
		Mocks.register("holdSenderDelegate", new HoldSenderDelegate(false));
		Mocks.register("unholdSenderDelegate", new UnholdSenderDelegate(false));
		Mocks.register("zolotoyaKoronaTransferHandlerDelegate", new ZolotoyaKoronaTransferHandlerDelegate(false));
		Mocks.register("zolotayaKoronaTransferStateHandlerDelegate", new ZolotayaKoronaTransferStateHandlerDelegate(false));
		Mocks.register("statusGetterDelegate", new StatusGetterDelegate(true));

		Map<String, Object> vars = new HashMap<>();
		vars.put(ProcessVariables.AFS_CHECK_RESULT, "LEGIT");
		vars.put(ProcessVariables.SBP_STATUS, true);
		vars.put(ProcessVariables.NSPK_STATUS, "REFUSED");
		vars.put(ProcessVariables.ZOLOTAYA_KORONA_STATUS, "0");
		vars.put(ProcessVariables.UNHOLD_CODE_RESPONSE, "0");
		vars.put(ProcessVariables.USER_CHOICE, "completed");

		// "given" part of the test
		when(processScenario.waitsAtServiceTask("StartEvent_c2b")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_afs")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_rtl_hold")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtTimerIntermediateEvent("Event_sbp_status_timer")).thenReturn(task -> task.isEnded());
		when(processScenario.waitsAtTimerIntermediateEvent("Event_sub_sbp_wait_status_timer")).thenReturn(task -> task.isEnded());
		when(processScenario.waitsAtServiceTask("Activity_sbp_wait_status")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_get_nspk_status")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtUserTask("Analysis_user_task")).thenReturn(task -> task.complete());

		// "when" part of the test
		run(processScenario).startByKey(PROCESS_KEY, vars).execute();
		// "then" part of the test
		verify(processScenario).hasFinished("Event_end_after_declined");
	}

	@Test
	public void testHappyPath() {
		Mocks.register("holdSenderDelegate", new HoldSenderDelegate(false));
		Mocks.register("unholdSenderDelegate", new UnholdSenderDelegate(false));
		Mocks.register("zolotoyaKoronaTransferHandlerDelegate", new ZolotoyaKoronaTransferHandlerDelegate(false));
		Mocks.register("zolotayaKoronaTransferStateHandlerDelegate", new ZolotayaKoronaTransferStateHandlerDelegate(false));
		Mocks.register("statusGetterDelegate", new StatusGetterDelegate(false));
		Mocks.register("retailDocumentSenderDelegate", new RetailDocumentSenderDelegate(false));

		Map<String, Object> vars = new HashMap<>();
		vars.put(ProcessVariables.AFS_CHECK_RESULT, "LEGIT");
		vars.put(ProcessVariables.SBP_STATUS, true);
		vars.put(ProcessVariables.NSPK_STATUS, "CONFIRMED");
		vars.put(ProcessVariables.ZOLOTAYA_KORONA_STATUS, "0");
		vars.put(ProcessVariables.RTL_DOCUMENT_STATUS, "PROCESSED");
		vars.put(ProcessVariables.RTL_DOCUMENT_ERROR_CODE, "0");

		// "given" part of the test
		when(processScenario.waitsAtServiceTask("StartEvent_c2b")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_afs")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_rtl_hold")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_sbp_wait_status")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_rtl_send_doc")).thenReturn(task -> task.complete());

		// "when" part of the test
		run(processScenario).startByKey(PROCESS_KEY, vars).execute();
		// "then" part of the test
		verify(processScenario).hasFinished("Event_end_completed");
	}

	@Test
	public void testAbsTimeoutAnalysisPath() {
		Mocks.register("holdSenderDelegate", new HoldSenderDelegate(false));
		Mocks.register("unholdSenderDelegate", new UnholdSenderDelegate(false));
		Mocks.register("zolotoyaKoronaTransferHandlerDelegate", new ZolotoyaKoronaTransferHandlerDelegate(false));
		Mocks.register("zolotayaKoronaTransferStateHandlerDelegate", new ZolotayaKoronaTransferStateHandlerDelegate(false));
		Mocks.register("statusGetterDelegate", new StatusGetterDelegate(false));
		Mocks.register("retailDocumentSenderDelegate", new RetailDocumentSenderDelegate(false));

		Map<String, Object> vars = new HashMap<>();
		vars.put(ProcessVariables.AFS_CHECK_RESULT, "LEGIT");
		vars.put(ProcessVariables.SBP_STATUS, true);
		vars.put(ProcessVariables.NSPK_STATUS, "CONFIRMED");
		vars.put(ProcessVariables.ZOLOTAYA_KORONA_STATUS, "0");
		//vars.put(ProcessVariables.UNHOLD_CODE_RESPONSE, "0");
		vars.put(ProcessVariables.USER_CHOICE, "completed");
		vars.put(ProcessVariables.RTL_DOCUMENT_STATUS, "WAIT");
		vars.put(ProcessVariables.RTL_DOCUMENT_ERROR_CODE, "1");

		// "given" part of the test
		when(processScenario.waitsAtServiceTask("StartEvent_c2b")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_afs")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_rtl_hold")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_sbp_wait_status")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtServiceTask("Activity_rtl_send_doc")).thenReturn(task -> task.complete());
		when(processScenario.waitsAtTimerIntermediateEvent("Event_wait_rtl_exec_doc_timer")).thenReturn(task -> task.isEnded());
		when(processScenario.waitsAtTimerIntermediateEvent("Event_sub_rtl_exec_doc_timer")).thenReturn(task -> task.isEnded());
		when(processScenario.waitsAtUserTask("Analysis_user_task")).thenReturn(task -> task.complete());

		// "when" part of the test
		run(processScenario).startByKey(PROCESS_KEY, vars).execute();
		// "then" part of the test
		verify(processScenario).hasFinished("Event_end_after_analysis");
	}
}
