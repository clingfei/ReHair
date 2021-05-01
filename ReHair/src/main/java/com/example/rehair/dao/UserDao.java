package com.example.rehair.dao;
import com.example.rehair.model.*;

import java.util.Map;

public interface UserDao {
    RegisterData insertUser(User user);

    String queryUserByName(String userName);

}