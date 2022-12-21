package com.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AutoconfigurationApplicationTests {

    @Value("${property.test.name}")
    private String propertyTestName;

    @Value("${propertyTest}")
    private String propertyTest;

    @Value("${noKey:default value}")
    private String defaultValue;

    @Value("${propertyTestList}")
    private String[] propertyTestArray;

    @Value("#{'${propertyTestList}'.split(',')}")
    private List<String> propertyTestList;

    @Test
    public void testValue(){
        assertEquals(propertyTestName, "property depth test");
        assertEquals(propertyTest, "test");
        assertEquals(defaultValue, "default value");

        assertEquals(propertyTestArray[0], "a");
        assertEquals(propertyTestArray[1], "b");
        assertEquals(propertyTestArray[2], "c");

        assertEquals(propertyTestList.get(0), "a");
        assertEquals(propertyTestList.get(1), "b");
        assertEquals(propertyTestList.get(2), "c");

    }
}