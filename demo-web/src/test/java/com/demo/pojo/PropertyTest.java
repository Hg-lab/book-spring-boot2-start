package com.demo.pojo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertyTest {

    @Autowired
    FruitProperty fruitProperty;

    @Test
    public void test() {
//        List<Map> list = fruitProperty.getList();
//
//        assertThat(list.get(0).get("name"), is("banana"));
//        assertThat(list.get(0).get("color"), is("yellow"));
//
//        assertThat(list.get(1).get("name"), is("apple"));
//        assertThat(list.get(1).get("color"), is("red"));
//
//        assertThat(list.get(2).get("name"), is("water melon"));
//        assertThat(list.get(2).get("color"), is("green"));

        /* Fruit POJO 매핑 테스트 */
        List<Fruit> list = fruitProperty.getList();

        assertThat(list.get(0).getName(), is("banana"));
        assertThat(list.get(0).getColor(), is("yellow"));

        assertThat(list.get(1).getName(), is("apple"));
        assertThat(list.get(1).getColor(), is("red"));

        assertThat(list.get(2).getName(), is("water melon"));
        assertThat(list.get(2).getColor(), is("green"));

    }
}