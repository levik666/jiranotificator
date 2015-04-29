/**
 * 
 */
package com.epam.jiranotificator.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * GCMService unit test
 * 
 * @author Bohdan_Kolesnyk
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class GCMServiceTest {

    private GCMService gcmService;

    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
            "applicationContext.xml");

    @Test
    public void testGCMResponse() throws Exception {
        gcmService = applicationContext.getBean(GCMService.class);

        String apiKey = "AIzaSyCuiAjW2AlTqV4Nyv722RyR5SCBnMyX2c8";
        GCMContent content = new GCMContent();

        content.addRegId("APA91bGCPNxKNjNf0V-_27C6JqIjTihlOWiyYVL05Ny51ZipUzNyRQ0v_40I3hsfvybFdOUT24Bvhbu9o_w5I93yCAq-HaP-T_EwhKHx2zrQ3lTnaWAGuxzr2LEvBFMl4Jtk1mSM0GNzKW8LNTTLFrZrxf-ZduAN6g");
        content.createData("Test Title", "Test Message");

        assertEquals(200, gcmService.send(apiKey, content));
    }
}
