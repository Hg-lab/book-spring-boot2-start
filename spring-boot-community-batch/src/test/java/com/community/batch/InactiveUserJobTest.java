package com.community.batch;

import com.community.batch.domain.enums.UserStatus;
import com.community.batch.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class InactiveUserJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void Inactivate_User_Test() throws Exception {
        Date nowDate = new Date();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(
                new JobParametersBuilder().addDate("nowDate", nowDate)
                        .toJobParameters()
        );

        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
        assertEquals(0, userRepository.findByUpdatedDateBeforeAndStatusEquals(
                LocalDateTime.now().minusYears(1), UserStatus.ACTIVE).size());
    }
}
