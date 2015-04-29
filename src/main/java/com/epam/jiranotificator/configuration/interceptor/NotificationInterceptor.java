package com.epam.jiranotificator.configuration.interceptor;

import com.epam.jiranotificator.configuration.annotations.Interceptor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Aspect
@Interceptor
public class NotificationInterceptor {

    @Pointcut("execution(* *(..))")
    public void isExceptionThrown() {}

    @Pointcut("@annotation(com.epam.jiranotificator.configuration.annotations.AlertNotification)")
    public void isAnnotated() {}

    @AfterThrowing(pointcut =  "isExceptionThrown() && isAnnotated()", throwing = "exe")
    public void errorInterceptor(final JoinPoint joinPoint, Throwable exe) {
        //TODO Will be replace when SMTP service added.
        System.out.println("Okay - we're in the handler...");

        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String stuff = signature.toString();
        String arguments = Arrays.toString(joinPoint.getArgs());

        System.out.println("Write something in the log... We have caught exception in method: "
                + methodName + " with arguments "
                + arguments + "\nand the full toString: " + stuff + "\nthe exception is: "
                + exe.getMessage());
    }
}
