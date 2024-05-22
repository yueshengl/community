package com.nowcoder.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: Dai
 * @Date: 2024/05/21 11:01
 * @Description: ThreadPoolConfig
 * @Version: 1.0
 */
@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {
}
