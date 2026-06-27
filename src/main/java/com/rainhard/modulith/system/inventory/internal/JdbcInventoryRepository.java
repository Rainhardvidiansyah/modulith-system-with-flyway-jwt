package com.rainhard.modulith.system.inventory.internal;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcInventoryRepository {


    private final JdbcClient jdbcClient;


    public JdbcInventoryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
}
