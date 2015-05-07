package com.epam.jiranotificator.configuration.interceptor;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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
    public void errorInterceptor(final JoinPoint joinPoint, Throwable exe) {
        
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String stuff = signature.toString();
        String arguments = Arrays.toString(joinPoint.getArgs());
		String message = new StringBuilder()
				.append("We have caught exception in method: ")
				.append(methodName).append(" with arguments ")
				.append(arguments).append("\nand the full toString: ")
				.append(stuff).append("\nthe exception is: ")
				.append(exe.getMessage()).toString();
	    
		jiraEventPublisher.publish("[Jiranotificator ERROR]", message);
		LOG.debug("Send mail with error message: " + message);
    }
}
