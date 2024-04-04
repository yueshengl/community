package com.nowcoder.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * @Author: Dai
 * @Date: 2024/03/16 14:05
 * @Description: AlphaDaoMyBatisImpl
 * @Version: 1.0
 */
@Repository
@Primary
public class AlphaDaoMyBatisImpl implements AlphaDao{


    @Override
    public String select() {
        return "MyBatis";
    }
}
