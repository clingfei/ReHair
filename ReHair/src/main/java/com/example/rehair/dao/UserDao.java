package com.example.rehair.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;

class UserDao {
    private JdbcTemplate jdbcTemplate;
    public String queryUserById(String userName){
        String sql = "SELECT password from user where username = ?" ;
        jdbcTemplate.queryForObject(sql, new Object[]{userName}, ResultSet::getString);
    }
}
