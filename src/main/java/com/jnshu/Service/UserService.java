package com.jnshu.Service;

import com.jnshu.pojo.User;
import org.springframework.stereotype.Service;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
@Service
public interface UserService {
    void message(String phone);

boolean checkUsername(String username);
    void insert(User user);

    JSONObject add(String verifyCode, User user);

    void delete(int id);

    User selectByName(String username);

    User selectById(int id);
    List<User> selectAll();

    boolean update(User user);

    JSONObject sendMail(String email);


    JSONObject sendMail(String email, HttpServletRequest httpServletRequest, String username) throws IOException;
}

