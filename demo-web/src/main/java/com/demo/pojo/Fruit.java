package com.demo.pojo;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Fruit {
    private String name;
    private String color;
}
