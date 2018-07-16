package com.testlink.importcase;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ActionOnDuplicate;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionType;
import br.eti.kinoshita.testlinkjavaapi.constants.TestCaseStatus;
import br.eti.kinoshita.testlinkjavaapi.constants.TestImportance;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestCaseStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {
	public static  final  Logger logger = LoggerFactory.getLogger(DemoApplication.class);


	/**
	 * 注意此处利用spring创建单例bean  https://www.cnblogs.com/wangzhongqiu/p/6636066.html
	 * @return
	 * @throws Exception
	 */
	@Bean
	public TestLinkAPI getTestLinkAPI() throws Exception{
		String url = "http://127.0.0.1:80/testlink/lib/api/xmlrpc/v1/xmlrpc.php";
		String devKey = "a7f8cddbfdde842ae54824199f2b5d41";

		URL testlinkURL = new URL(url);
		return new TestLinkAPI(testlinkURL, devKey);
	}
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
		TestLinkAPI api = applicationContext.getBean(TestLinkAPI.class);
		//获取所有项目
//		TestProject[] apiProjects = api.getProjects();
//
//		/**
//		 * 注意项目名称只能创建一次 testProjectName 否则报错 testProjectPrefix不能重复
//		 */
//		TestProject testProject = api.createTestProject(/*名称*/"B端小程序商户版111111",/*前缀*/"b2222",/*项目描述*/"好好学习",
//				/*增强功能:启用产品需求功能*/false,
//				/*增强功能:启用测试优先级*/true,
//				/*增强功能:启用测试自动化 (API keys)*/true,
//				/*增强功能:启用设备管理*/false,
//				/*可用性:活动的*/true,
//				/*可用性:公共*/true
//		);
//		Integer testProjectId = testProject.getId();
//		TestSuite testSuite = api.createTestSuite(testProjectId,
//				"登录",
//				"登录模块要注意",
//				null, 1,
//				true,
//				/*如果出现name重复则报错*/
//				ActionOnDuplicate.BLOCK);
//		TestSuite testSuite1 = api.createTestSuite(testProjectId,
//				"注册",
//				"注册模块要注意",
//				null, 2,
//				true,
//				/*如果出现name重复则报错*/
//				ActionOnDuplicate.BLOCK);
//		Integer testSuiteId = testSuite.getId();
//		logger.error("创建项目id=[{}],testSuite1=[{}],testSuite1=[{}]",testProjectId,testSuite.getId(),testSuite1.getId());

//		对于已经存在的testProjectId如何查看？
//		对于已经存在的testSuiteId如何查看？
		Integer testProjectId = 10; //23973
		Integer testSuiteId = 11;  //37138

		List<TestCaseStep> steps = new ArrayList<>();
		{
			TestCaseStep testCaseStep = new TestCaseStep();
			testCaseStep.setExecutionType(ExecutionType.MANUAL);
			testCaseStep.setActive(true);
			testCaseStep.setActions("步骤动作1");
			testCaseStep.setExpectedResults("期望结果1");
			//必填
			testCaseStep.setNumber(1);
			//初始化默认都是1
			testCaseStep.setTestCaseVersionId(1);
			steps.add(testCaseStep);
		}
		{
			TestCaseStep testCaseStep2 = new TestCaseStep();
			testCaseStep2.setExecutionType(ExecutionType.MANUAL);
			testCaseStep2.setActive(true);
			testCaseStep2.setActions("步骤动作2");
			testCaseStep2.setExpectedResults("期望结果2");
			//必填
			testCaseStep2.setNumber(2);
			//初始化默认都是1
			testCaseStep2.setTestCaseVersionId(1);
			steps.add(testCaseStep2);
		}

		TestCase testCase = api.createTestCase("testCaseName6", testSuiteId, testProjectId,
				/*登录名称*/"admin",
				/*摘要*/
				"summary",
				/*创建步骤为空*/steps,
				/*前提*/"preconditions",
				/*状态:草稿*/TestCaseStatus.DRAFT,
				/*重要性：低*/TestImportance.LOW,
				/*测试方式 :手工 */ExecutionType.MANUAL, 1, null
				,
				/*检查重复名称*/true,
				/*重复出现如何处理，阻塞*/ActionOnDuplicate.BLOCK);
		logger.info("生成之后的数据:"+testCase.toString());


	}
}
