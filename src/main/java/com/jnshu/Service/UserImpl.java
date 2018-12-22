package com.jnshu.Service;

import com.jnshu.API.MailSdk;
import com.jnshu.API.SmsSdk;
import com.jnshu.mapper.UserMapper;
import com.jnshu.pojo.User;
import com.whalin.MemCached.MemCachedClient;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class UserImpl implements UserService {
    private static Logger logger= Logger.getLogger(UserImpl.class);
    @Autowired
    private SmsSdk smsSdk;
    private MemCachedClient memCachedClient;
    @Autowired
    private MailSdk mailSdk;
    @Autowired
    private UserMapper userMapper;
    @Override
    public void message(String phone){
        logger.info("用户的手机号"+phone);
       smsSdk.SendMsg(phone);
    }
    @Override
    public void insert(User user){
        userMapper.doInsert(user);

    }
    @Override
    public JSONObject add(String verifyCode, User user){
        logger.info("用户输入的值"+verifyCode);
        String s_verifyCode=smsSdk.getRand_Code();
        logger.info("验证码为"+s_verifyCode);
        JSONObject jsonObject=new JSONObject();
        if (verifyCode.equals(s_verifyCode)){
            userMapper.doInsert(user);
            jsonObject.put("data","报名成功");
            return jsonObject;
        }else {
            jsonObject.put("data","验证码错误");
            return jsonObject;
        }
    }

    @Override
    public void delete(int id){
        userMapper.doDelete(id);
    }

    @Override
    public User selectByName(String username){
        return userMapper.findByName(username);
    }

    @Override
        public User selectById(int id) {
        return userMapper.findById(id);
    }
    @Override
    public List<User> selectAll(){
        return userMapper.findAll();
    }
    @Override
    public boolean update(User user){
        userMapper.doUpdate(user);
        return false;
    }
    @Override
    public boolean checkUsername(String username){
        User user = userMapper.findByName(username);
        if (username == null){
            return true;
        }
        return false;
    }

    @Override
    public JSONObject sendMail(String email) {
        return null;
    }

    @Override
    public JSONObject sendMail(String email, HttpServletRequest httpServletRequest,String username) throws IOException {
        logger.info("用户输入的值"+email);
     String httpUrl=httpServletRequest.getScheme()+"://"+httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort()+httpServletRequest.getContextPath();
        logger.info(httpUrl);
        JSONObject jsonObject=new JSONObject();

        if ((mailSdk.sendMail(email,httpUrl,username))){
            jsonObject.put("data", "发送邮件成功，请去邮箱验证");
            return jsonObject;
        }else {
            jsonObject.put("data","你的邮箱有误或发送失败");
            return jsonObject;
        }

    }
}
