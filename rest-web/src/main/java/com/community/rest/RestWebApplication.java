package com.community.rest;

import com.community.rest.domain.Board;
//import com.community.rest.domain.User;
import com.community.rest.domain.enums.BoardType;
import com.community.rest.repository.BoardRepository;
import com.community.rest.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class RestWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestWebApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner runner(UserRepository userRepository, BoardRepository boardRepository)
//            throws Exception {
//        return args -> {
//            User user = userRepository.save(User.builder()
//                    .name("hank")
//                    .password("test")
//                    .email("hank@email.com")
//                    .createdDate(LocalDateTime.now())
//                    .build());
//
//            IntStream.rangeClosed(1, 200).forEach(index ->
//                    boardRepository.save(Board.builder()
//                            .title("게시글" + index)
//                            .subTitle("순서" + index)
//                            .content("콘텐츠")
//                            .boardType(BoardType.free)
//                            .createdDate(LocalDateTime.now())
//                            .updatedDate(LocalDateTime.now())
//                            .user(user).build())
//            );
//        };
//
//    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @EnableWebSecurity
    static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.addAllowedOrigin(CorsConfiguration.ALL);
            configuration.addAllowedMethod(CorsConfiguration.ALL);
            configuration.addAllowedHeader(CorsConfiguration.ALL);
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);

            http.httpBasic()
                    .and().authorizeRequests()
                    .anyRequest().permitAll()
                    .and().cors().configurationSource(source)
                    .and().csrf().disable();
        }
//
//        @Bean
//        InMemoryUserDetailsManager userDetailsManager() {
//            User.UserBuilder commonUser = User.withUsername("commonUser");
//            User.UserBuilder hank = User.withUsername("hank");
//
//            List<UserDetails> userDetailsList = new ArrayList<>();
//            userDetailsList.add(commonUser.password("{noop}common").roles("USER").build());
//            userDetailsList.add(hank.password("{noop}test").roles("USER", "ADMIN").build());
//
//            return new InMemoryUserDetailsManager(userDetailsList);
//        }

    }

}