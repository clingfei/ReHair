package com.example.rehair.dao;
import com.example.rehair.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

// 持久层，专门用于配置数据库的
// 需要在数据库中开启信息的操作，效果还算是ok的
// 接口专门用于数据库的各种处理过程的
@Repository
public interface UserDao {
    ReturnData insertUser(User user);

    String queryUserByName(String userName);

    Map<String, Object> queryUserPageByName(String userName);

    int addFriend(String userName, String futureFriendName);

    void deleteUser(String username);

    void deleteFriend(String userName);

    List<Map<String, Object>> queryFriendByName(String userName);

    int unfollow(String userName, String friendName);
}