package com.demo.pojo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertyTest {

    @Autowired
    FruitProperty fruitProperty;

    @Test
    public void test() {
        List<Fruit> fruitData = fruitProperty.getList();

        assertEquals(fruitData.get(0).getName(), "banana");
        assertEquals(fruitData.get(0).getColor(), "yellow");

        assertEquals(fruitData.get(1).getName(), "apple");
        assertEquals(fruitData.get(1).getColor(), "red");

        assertEquals(fruitData.get(2).getName(), "water melon");
        assertEquals(fruitData.get(2).getColor(), "green");

    }
}
