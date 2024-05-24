package com.nowcoder.community;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Author: Dai
 * @Date: 2024/05/23 16:02
 * @Description: CommunityServletInitializer
 * @Version: 1.0
 */
public class CommunityServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CommunityApplication.class);
    }
}
