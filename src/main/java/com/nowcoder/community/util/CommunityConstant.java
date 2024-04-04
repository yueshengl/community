package com.nowcoder.community.util;

/**
 * @Author: Dai
 * @Date: 2024/04/04 20:53
 * @Description: CommunityConstant
 * @Version: 1.0
 */
public interface CommunityConstant {

    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;
    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;
    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * 默认状态的登陆凭证的超时时间
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12; //12hours

    /**
     * 记住状态的登陆凭证的超时时间
     */
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;//100days
}
