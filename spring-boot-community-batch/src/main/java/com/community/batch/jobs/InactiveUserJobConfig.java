package com.community.batch.jobs;

import com.community.batch.domain.User;
import com.community.batch.domain.enums.UserStatus;
import com.community.batch.jobs.inactive.InactiveItemTasklet;
import com.community.batch.jobs.inactive.InactiveJobExecutionDecider;
import com.community.batch.jobs.inactive.listener.InactiveJobListener;
import com.community.batch.jobs.inactive.listener.InactiveStepListener;
import com.community.batch.jobs.readers.QueueItemReader;
import com.community.batch.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@AllArgsConstructor
@Configuration
public class InactiveUserJobConfig {

    private final static int CHUNK_SIZE = 15;
    private final EntityManagerFactory entityManagerFactory;

    private UserRepository userRepository;

    @Bean
    public Job inactiveUserJob(JobBuilderFactory jobBuilderFactory, InactiveJobListener inactiveJobListener,
                               //Step inactiveJobStep
                               Flow inactiveJobFlow

    ) {
        return jobBuilderFactory.get("inactiveUserJob")
                .preventRestart()
                .listener(inactiveJobListener)
//                .start(inactiveJobStep)
                .start(inactiveJobFlow).end()
                .build();
    }

    @Bean
    public Flow inactiveJobFlow(Step inactiveJobStep) {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("inactiveJobFlow");
        return flowBuilder
                .start(new InactiveJobExecutionDecider())
                .on(FlowExecutionStatus.FAILED.getName()).end()
                .on(FlowExecutionStatus.COMPLETED.getName()).to(inactiveJobStep)
                .end();
    }

    @Bean
    public Step inactiveJobStep(StepBuilderFactory stepBuilderFactory
            , ListItemReader<User> inactiveUserReader
//            , Tasklet inactiveItemTasklet
            , InactiveStepListener inactiveStepListener
            , TaskExecutor taskExecutor
    ) {
        return stepBuilderFactory.get("inactiveUserStep")
                .<User, User>chunk(CHUNK_SIZE) // <I, O>
////                .reader(inactiveUserReader())
////                .reader(inactiveUserJpaReader())
////                .reader(inactiveUserJpaReader)
                .reader(inactiveUserReader)
                .processor(inactiveUserProcessor())
                .writer(inactiveUserWriter())
                .listener(inactiveStepListener)
//                .tasklet(inactiveItemTasklet)
                .taskExecutor(taskExecutor)
                .throttleLimit(2)
                .build();
    }

////    @Bean
////    @StepScope
//    public QueueItemReader<User> inactiveUserReader() {
//        List<User> oldUsers = userRepository.findByUpdatedDateBeforeAndStatusEquals(
//                LocalDateTime.now().minusYears(1), UserStatus.ACTIVE
//        );
//        return new QueueItemReader<>(oldUsers);
//    }

    @Bean
    @StepScope
    public ListItemReader<User> inactiveUserReader(@Value("#{jobParameters[nowDate]}") Date nowDate, UserRepository userRepository) {
        LocalDateTime now = LocalDateTime.ofInstant(nowDate.toInstant(), ZoneId.systemDefault());
        List<User> oldUsers = userRepository.findByUpdatedDateBeforeAndStatusEquals(
//                LocalDateTime.now()
                    now
                        .minusYears(1), UserStatus.ACTIVE
        );
        return new ListItemReader<>(oldUsers);
    }

    public ItemProcessor<User, User> inactiveUserProcessor() {
//        return User::setInactive;
        return user -> user.setInactive();
    }

//    public ItemWriter<User> inactiveUserWriter() {
//        return ((List<? extends User> users) -> userRepository.saveAll(users));
//    }

//    @Bean(destroyMethod = "")
//    @StepScope
//    public JpaPagingItemReader<User> inactiveUserJpaReader() {
//        JpaPagingItemReader<User> jpaPagingItemReader = new JpaPagingItemReader(){
//            @Override
//            public int getPage() {
//                return 0;
//            }
//        };
//        jpaPagingItemReader.setQueryString(
//                "select u from User as u where u.updatedDate < :updatedDate and u.status = :status"
//        );
//
//        Map<String, Object> map = new HashMap<>();
//        LocalDateTime now = LocalDateTime.now();
//        map.put("updatedDate", now.minusYears(1));
//        map.put("status", UserStatus.ACTIVE);
//
//        jpaPagingItemReader.setParameterValues(map);
//        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
//        jpaPagingItemReader.setPageSize(CHUNK_SIZE);
//        return jpaPagingItemReader;
//    }

    private JpaItemWriter<User> inactiveUserWriter() {
        JpaItemWriter<User> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    @Bean
    public Tasklet inactiveItemTasklet() {
        return new InactiveItemTasklet(userRepository);
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("Batch_Task");
    }
}
