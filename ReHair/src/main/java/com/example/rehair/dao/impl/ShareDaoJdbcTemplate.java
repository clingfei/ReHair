package com.example.rehair.dao.impl;

import com.example.rehair.dao.ShareDao;
import com.example.rehair.model.ReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class ShareDaoJdbcTemplateImpl implements ShareDao {
    @Autowired
    public NamedParameterJdbcTemplate jdbcTemplate;

    public int getShareCount(String userName) {
        System.out.println(userName);
        String sql = "select COUNT(seqid) AS sum from article  where username = :username";
        Map<String, Object> result = null;
        try {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("username", userName);
            result = jdbcTemplate.queryForMap(sql, m);
            System.out.println(result);
            return Integer.parseInt(String.valueOf(result.get("sum")));
        } catch(EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
            return -1;
        }

    }

    @Override
    public int createShare(String userName, String textContent, Date date, int seqid) {
        if (seqid == 0) return -1;

        String sql = "INSERT INTO article (username, content, photopath, count, time, seqid) VALUES (:username, :content, :photopath, :count, :time, :seqid)";
        Map<String,Object> m = new HashMap<String, Object>();
        m.put("username", userName);
        m.put("content", textContent);
        m.put("count", 0);
        m.put("time", date);
        m.put("photopath", "");
        m.put("seqid", seqid);
        try {
            // 完成基本数据库的插入操作，还是比较合理的
            jdbcTemplate.update(sql, m);
            return seqid;
        } catch(DataAccessException e) {
            // 异常就暂时不处理了
            System.out.println(e.getMessage());
            return -1;
        }
    }

    @Override
    public String findArticlePhotoPath(String userName, Date date) {
        System.out.println(date);
        Map<String, Object> result = null;
        try {
            String sql = "SELECT * FROM article WHERE username = :username AND time = :date";
            Map<String,Object> m = new HashMap<String, Object>();
            m.put("username", userName);
            m.put("date", date);

            result = jdbcTemplate.queryForMap(sql,m);
        }catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
        }
        return (String) result.get("photopath");

    }

    @Override
    public void reloadArticlePhotoPath(String userName, Date date, String pathToPic) {
        // update account set balance = balance - 200,cowpea = cowpea - 300 where account_id = 333 and balance >= 200 and cowpea >= 300
        Map<String, Object> result = null;
        try {
            String sql = "UPDATE  article SET photopath = :photopath WHERE username = :username AND time = :date";
            Map<String,Object> m = new HashMap<String, Object>();
            m.put("username", userName);
            m.put("date", date);
            m.put("photopath", pathToPic);
            // 这里也用到了异常处理
            jdbcTemplate.update(sql,m);
        }catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
        }
        return;

    }

    public ArrayList<Map<String, Object>> queryArticleByName(String userName) {
        try {
            String sql = "SELECT * FROM Article  WHERE username = :username";
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("username", userName);
            return (ArrayList<Map<String, Object>>) jdbcTemplate.queryForList(sql, m);
        } catch(EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ReturnData deleteArticle(String userName, int seqid) {

        String sql = "DELETE FROM article WHERE username = :username AND seqid = :seqid";
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("username", userName);
        m.put("seqid", seqid);
        jdbcTemplate.update(sql, m);

        return new ReturnData(true, "");
    }
}
