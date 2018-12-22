package com.jnshu.controller;

import com.jnshu.API.MailSdk;
import com.jnshu.API.QnYun;
import com.jnshu.Service.UserService;
import com.jnshu.pojo.User;
import com.jnshu.tool.Md5Util;
import com.jnshu.tool.MemcachedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.jnshu.tool.SdkTools.sdkTools;

@Controller
public class UserController {
    private static Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    MailSdk mailSdk;
    @Autowired
    QnYun ossApiQiNiuYun;
    //登录界面
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    //判断登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password) throws Exception {
        long loginTime = System.currentTimeMillis();//登录时间为系统时间
        User user = userService.selectByName(username); //查看username对应的用户的信息
        boolean result=Md5Util.verify(password,user.getPassword());
        logger.info(result);
        if (user != null && result) {
                logger.info("登录成功");
                return "redirect:/form";
            } else {
            logger.info("输入账号或密码错误");
            return "login";
        }
    }
    //用户界面
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String form(Model model) {
        List<User> userList = userService.selectAll();
        model.addAttribute("user", userList);
        return "form";
    }
    @RequestMapping(value = "/form",method = RequestMethod.POST)
    public String update(User user, Model model){

        userService.update(user);
        this.form(model);
        return "redirect:/form";
    }
    //信息界面
    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public void message(String phone) {
        userService.message(phone);
    }
    //注册界面
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject toRegister(@RequestParam(value = "verifyCode", required = false) String verifyCode, User user) {
        String username = user.getUsername();
        String userpassword = user.getPassword();
        String password = Md5Util.generate(userpassword);
        JSONObject jsonObject = new JSONObject();
        if (userService.selectByName(username) != null){
            jsonObject.put("data", "用户名重复");
            return jsonObject;
        }else {
            user.setPassword(password);
            return this.userService.add(verifyCode, user);
        }
    }
    //跳转邮箱发送页面
//    @RequestMapping(value = "/sendMail", method = RequestMethod.GET)
//    public String Mail() {
//        return "mail";
//    }
//    @RequestMapping(value = "/user/{id}/profile", method = RequestMethod.GET)
//    public String toUpdate(@PathVariable("id") int id , Model model)
//    {
//        User user = userService.selectById(id);
//        model.addAttribute("update_user",user);
//        return "update";
//    }
@RequestMapping(value = "/updateUser",method = RequestMethod.POST)
public String updateUser(Model model,User user) {
    if (userService.update(user)) {
        user = userService.selectById(user.getId());
        model.addAttribute("user", user);
        return "error";
    }
     return "redirect:/form";
}
    @RequestMapping(value = "/getUser/{id}",method = RequestMethod.GET)
    public String getUser(@PathVariable int id,Model model){
        model.addAttribute("user", userService.selectById(id));
        return "update";
    }

    @RequestMapping(value = "/delUser/{id}",method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable int id, @Validated User user, BindingResult result) {
        userService.delete(id);
        return "redirect:/form";

    }
//    @RequestMapping(value = "/sendMail", method = RequestMethod.GET)
//    public String Mail() {
//        return "mail";
//    }

    @RequestMapping(value = "/user/{id}/profileMail", method = RequestMethod.GET)
    public String toMail(@PathVariable("id") int id , Model model)
    {
        User user = userService.selectById(id);
        model.addAttribute("mail",user);
        return "mail";
    }

    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject toSendMail(@RequestParam String email, HttpServletRequest httpServletRequest,String username) throws Exception {
        return  userService.sendMail(email,httpServletRequest,username);
    }
    //判断邮件链接
    @RequestMapping(value = "/sendMail/{username}/{randomForMail}", method = RequestMethod.GET)
    @ResponseBody
    public boolean mail(@PathVariable(value = "randomForMail") String randomForMail ,@PathVariable(value = "username") String username) {
        logger.info("缓存中的数据"+MemcachedUtils.get(randomForMail));
        logger.info("用户ming"+username);
        User user=userService.selectByName(username);
        String mail = (String) MemcachedUtils.get(randomForMail);
        logger.info(mail);
        if (mail != null) {

            logger.info("邮箱验证通过" + mail);
            //获取对应的邮箱存入到表格中
            user.setEmail(mail);
            userService.update(user);
            MemcachedUtils.delete(randomForMail);
            return true;
        }
        return false;
    }
    @RequestMapping(value = "/updateImage", method = RequestMethod.GET)
    public String updateImg() {
        return "updateImage";
    }
    //调用七牛云方法，上传图片
    @RequestMapping("/updateImage")
    @ResponseBody
    public JSONObject updateImage(MultipartFile item_pic, String username) {
        String fileName = ossApiQiNiuYun.updateFile(username, item_pic);
        logger.info(username);
        logger.info(fileName);
        JSONObject jsonObject = new JSONObject();
        if (fileName.equals("error")) {
            jsonObject.put("data", "注册失败");
            return jsonObject;
        } else {

            User user = userService.selectByName(username);
            user.setPicture(fileName);
            jsonObject.put("data", "图像上传成功");
            this.userService.update(user);
        }
        return jsonObject;
    }
    //图片迁移
    @RequestMapping(value = "/fileRemoval", method = RequestMethod.GET)
    @ResponseBody
    public Boolean test(){

        return sdkTools.qiNiuFileToAliyun("wtc666", "http://pjjxtg1q7.bkt.clouddn.com/");
    }
}
