package com.community.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing // JobBuilderFactory, StepBuilderFactory 의존성 주입
public class BatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class);
    }
}