package com.example.rehair.dao.impl;

import com.example.rehair.dao.UserDao;
import com.example.rehair.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

// 定义接口以后，需要实现对应的方法，使用继承的方式实现接口
// 实现以后，
@Repository
class UserDaoJdbcTemplateImpl implements UserDao {
    @Autowired
    public NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public ReturnData insertUser(User user) {
        String sql = "INSERT INTO user (username, password, email) VALUES (:username, :password, :email)";
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("username", user.getUserName());
        param.put("password", user.getPassWd());
        param.put("email", user.getEmail());

        try {
            jdbcTemplate.update(sql, param);
            return new ReturnData(true, "");
        } catch(DataAccessException e) {
            System.out.println(e.getMessage());
            String errMsg = e.getMessage();
            System.out.println(errMsg.substring(errMsg.length()-15));
            if (errMsg.endsWith("'user.username'")) {
                return new ReturnData(false, "duplicate username");
            }
            else if (errMsg.endsWith("'user.email'")) {
                return new ReturnData(false, "duplicate email");
            }
            else {
                return new ReturnData(false, "other errors");
            }
        }
    }

    public Map<String, Object> queryUserPageByName(String userName) {
        Map<String, Object> result = null;
        try {
            String sql = "SELECT username, email FROM user WHERE username = :username";
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("username", userName);
            result = jdbcTemplate.queryForMap(sql, m);
        } catch(EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
        }
        return result;
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

    public void deleteUser(String username) {
        String sql = "DELETE FROM user WHERE username = :username";
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("username", username);
        jdbcTemplate.update(sql, m);
    }

    public void deleteFriend(String userName) {
        String sql = "DELETE FROM friendlist WHERE username = :username";
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("username", userName);
        jdbcTemplate.update(sql, m);
        sql = "DELETE FROM friendlist WHERE friendname = :username";
        jdbcTemplate.update(sql, m);
    }

    @Override
    public int addFriend(String userName, String futureFriendName) {
        // 如何向数据库添加一条数据呢？不需要别的数据结构的
        // INSERT INTO Persons VALUES ('Gates', 'Bill', 'Xuanwumen 10', 'Beijing')
        // String sql = "INSERT INTO user (username, password, email) VALUES (:username, :password, :email)";
        String sql = "INSERT INTO friendlist (username, friendname) VALUES (:username, :friendname)";
        Map<String,Object> m = new HashMap<String, Object>();
        m.put("username", userName);
        m.put("friendname", futureFriendName);
        // jdbcTemplate.update(sql, m);

        try {
            jdbcTemplate.update(sql, m);
            return 1;
        } catch(DataAccessException e) {
            // 异常就暂时不处理了
            return 0;
        }
    }


}
