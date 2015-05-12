package com.epam.jiranotificator.configuration.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Ignore
public class NotificationInterceptorTest {

    private static final String TEST_NAME = "test_name";

    @Spy
    @InjectMocks
    private NotificationInterceptor notificationInterceptor = new NotificationInterceptor();

    @Test
    public void shouldSuccessfulPerformErrorInterceptor(){
        final JoinPoint joinPoint = mock(JoinPoint.class);
        final Signature signature = mock(Signature.class);
        when(signature.getName()).thenReturn(TEST_NAME);
        when(joinPoint.getSignature()).thenReturn(signature);
        final Throwable throwable = mock(Throwable.class);
        notificationInterceptor.errorInterceptor(joinPoint, throwable);
    }
}
