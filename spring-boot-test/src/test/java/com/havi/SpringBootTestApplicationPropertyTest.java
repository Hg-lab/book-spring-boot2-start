package com.havi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/* value와 properties */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"property.value=propertyTest",
        "value=test"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {SpringBootTestApplication.class})
public class SpringBootTestApplicationPropertyTest {

    @Value("${value}")
    private String value;

    @Value("${property.value}")
    private String propertyValue;

    @Test
    public void contextLoads() {
        assertEquals(value, "test");
        assertEquals(propertyValue, "propertyTest");
    }
}