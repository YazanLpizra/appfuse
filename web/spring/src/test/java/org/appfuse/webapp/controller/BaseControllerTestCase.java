package org.appfuse.webapp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:/applicationContext-resources.xml",
    "classpath:/applicationContext-dao.xml",
    "classpath:/applicationContext-service.xml",
    "classpath*:/applicationContext.xml", // for modular archetypes
    "/WEB-INF/applicationContext*.xml",
    "/WEB-INF/dispatcher-servlet.xml"
})
public abstract class BaseControllerTestCase {
    protected transient final Log log = LogFactory.getLog(getClass());
    private int smtpPort;

    @Autowired
    private ApplicationContext applicationContext;

    @Before
    public void onSetUp() {
        smtpPort = Integer.parseInt(System.getProperty("smtp.port",
            String.valueOf((new Random().nextInt(9999 - 1000) + 1000))));
        log.debug("SMTP Port set to: " + smtpPort);
        // change the port on the mailSender so it doesn't conflict with an
        // existing SMTP server on localhost
        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) applicationContext.getBean("mailSender");
        mailSender.setPort(getSmtpPort());
        mailSender.setHost("localhost");
    }

    protected int getSmtpPort() {
        return smtpPort;
    }
}
