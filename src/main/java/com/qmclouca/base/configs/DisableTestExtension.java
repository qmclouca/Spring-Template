package com.qmclouca.base.configs;

import com.qmclouca.base.utils.annotations.DisableTest;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DisableTestExtension implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        if (context.getTestMethod().isPresent()) {
            if (context.getTestMethod().get().isAnnotationPresent(DisableTest.class)) {
                String reason = context.getTestMethod().get().getAnnotation(DisableTest.class).reason();
                return ConditionEvaluationResult.disabled(reason);
            }
        }
        return ConditionEvaluationResult.enabled("Test enabled");
    }
}