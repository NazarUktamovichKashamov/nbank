package common.extensions;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.HashMap;
import java.util.Map;

public class TImingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private Map<String, Long> startTimes = new HashMap<>();

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        String testName = context.getRequiredTestClass().getPackageName() + "." + context.getDisplayName();
        startTimes.put(testName, System.currentTimeMillis());
        System.out.println("Thread " + Thread.currentThread().getName() + ": test started" + testName);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        String testName = context.getRequiredTestClass().getPackageName() + "." + context.getDisplayName();
        long testDuration = System.currentTimeMillis() - startTimes.get(testName);
        System.out.println("Thread " + Thread.currentThread().getName() + ": test finished" + testName + "test duration " + testDuration);


    }
}
