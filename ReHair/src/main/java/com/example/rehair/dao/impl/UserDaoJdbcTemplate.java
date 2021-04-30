package com.example.rehair.dao.impl;

import com.example.rehair.dao.UserDao;
import com.example.rehair.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
class UserDaoJdbcTemplateImpl implements UserDao {
    @Autowired
    public NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int insertUser(User user) {
        String sql = "INSERT INTO user (username, password, email) VALUES (:username, :password, :email)";
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("username", user.userName);
        param.put("password", user.passWd);
        param.put("email", user.email);

        return jdbcTemplate.update(sql, param);
    }
}
