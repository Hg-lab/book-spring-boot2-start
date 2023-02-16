package com.community.batch.controller;

import com.community.batch.domain.enums.UserStatus;
import com.community.batch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class OndemandLaunchController {

    private final JobLauncher jobLauncher;
    private final Job job;
    private final UserRepository userRepository;

    @RequestMapping("/batch/ondemand")
    public void handle() throws Exception{
        Date nowDate = new Date();
        JobParameters jobParameters = new JobParametersBuilder().addDate("nowDate", nowDate)
                .toJobParameters();
        jobLauncher.run(job, jobParameters);
    }
}
