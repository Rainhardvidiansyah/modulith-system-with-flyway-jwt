package com.rainhard.modulith.system.order.internal;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcOrderRepository {

    private final JdbcClient jdbcClient;


    public JdbcOrderRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
}
