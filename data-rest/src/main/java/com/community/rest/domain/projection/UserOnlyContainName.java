package com.community.rest.domain.projection;

import org.springframework.data.rest.core.config.Projection;
import com.community.rest.domain.User;

@Projection(name = "getOnlyName", types = {User.class})
public interface UserOnlyContainName {

    String getName();
}
