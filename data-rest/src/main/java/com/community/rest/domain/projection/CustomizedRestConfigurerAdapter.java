package com.community.rest.domain.projection;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class CustomizedRestConfigurerAdapter extends RepositoryRestConfigurerAdapter {


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//        super.configureRepositoryRestConfiguration(config);
        config.getProjectionConfiguration().addProjection(UserOnlyContainName.class);
    }
}
