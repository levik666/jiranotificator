package com.epam.jiranotificator.configuration.interceptor;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.epam.jiranotificator.configuration.annotations.AlertNotification;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.jiranotificator.configuration.annotations.Interceptor;
import com.epam.jiranotificator.configuration.context.JiraEventPublisher;

@Aspect
@Interceptor
public class NotificationInterceptor {
	
	private static final Logger LOG = LoggerFactory.getLogger(NotificationInterceptor.class);
	
	@Autowired
    JiraEventPublisher jiraEventPublisher;

    @Pointcut("execution(* *(..))")
    public void isExceptionThrown() {}

    @Pointcut("@annotation(com.epam.jiranotificator.configuration.annotations.AlertNotification)")
    public void isAnnotated() {}

    @AfterThrowing(pointcut =  "isExceptionThrown() && isAnnotated()", throwing = "exe")
    public void errorInterceptor(final JoinPoint joinPoint, final Throwable exe) {
        final MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();

        final Method method = methodSignature.getMethod();
        final AlertNotification alertNotification = method.getAnnotation(AlertNotification.class);

        final String subject = alertNotification.name();

        final Signature signature = joinPoint.getSignature();
        final String methodName = signature.getName();
        final String stuff = signature.toString();
        final String arguments = Arrays.toString(joinPoint.getArgs());

        final String message = "We have caught exception in method: " + methodName +
                " with arguments " + arguments +
                " and the full toString: " + stuff +
                " the exception is: " + exe.getMessage();

        LOG.error(message);
	    
		jiraEventPublisher.publish(subject, message);
        LOG.debug("Send mail with error message: " + message);
    }

}
