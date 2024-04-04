package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

/**
 * @Author: Dai
 * @Date: 2024/03/16 13:59
 * @Description: AlphaDaoHibernateImpl
 * @Version: 1.0
 */
@Repository("alphaHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao{

    @Override
    public String select() {
        return "Hibernate";
    }
}
