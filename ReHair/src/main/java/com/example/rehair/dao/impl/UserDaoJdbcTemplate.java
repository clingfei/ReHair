package com.example.rehair.dao.impl;

import com.example.rehair.dao.UserDao;
import com.example.rehair.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Repository
class UserDaoJdbcTemplateImpl implements UserDao {
    @Autowired
    public NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public RegisterData insertUser(User user) {
        String sql = "INSERT INTO user (username, password, email) VALUES (:username, :password, :email)";
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("username", user.getUserName());
        param.put("password", user.getPassWd());
        param.put("email", user.getEmail());

        try {
            jdbcTemplate.update(sql, param);
            return new RegisterData(true, "");
        } catch(DataAccessException e) {
            System.out.println(e.getMessage());
            String errMsg = e.getMessage();
            System.out.println(errMsg.substring(errMsg.length()-15));
            if (errMsg.endsWith("'user.username'")) {
                return new RegisterData(false, "duplicate username");
            }
            else if (errMsg.endsWith("'user.email'")) {
                return new RegisterData(false, "duplicate email");
            }
            else {
                return new RegisterData(false, "other errors");
            }
        }
    }

    @Override
    public String queryUserByName(String userName) {
        Map<String, Object> result = null;
        try {
            String sql = "SELECT * FROM user WHERE username = :username";
            Map<String,Object> m = new HashMap<String, Object>();
            m.put("username", userName);
            result = jdbcTemplate.queryForMap(sql,m);
        }catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
        }
        return (String) result.get("password");
    }
}
