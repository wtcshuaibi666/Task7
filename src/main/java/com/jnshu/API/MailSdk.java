package com.jnshu.API;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import com.jnshu.tool.MemcachedUtils;
import com.jnshu.tool.RandNum;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MailSdk {
    private static Logger logger= Logger.getLogger(MailSdk.class);
    private String apiUser;
    private String apiKey;

    // 邮件发送接口
    private String apiUrl;

    private String randomForMail= RandNum.getRandLength(6);
    private String sendBodyBegin = "<html><H1><a href=\"";
    private String sendBodyEnd = "\">点击验证邮箱,五分钟后失效</a></H1></html>";
    MailSdk(String apiUser, String apiKey, String apiUrl) {
        this.apiUser = apiUser;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
    }

    public boolean sendMail(String email,String httpUrl,String username) throws IOException {
//        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String sendBody=sendBodyBegin+httpUrl+"/sendMail/"+username+"/"+randomForMail+sendBodyEnd;
        logger.info(sendBody+"跳转的网址为");

        List params = new ArrayList();
        // 您需要登录SendCloud创建API_USER，使用API_USER和API_KEY才可以进行邮件的发送。
        params.add(new BasicNameValuePair("apiUser", apiUser));//用户名
        params.add(new BasicNameValuePair("apiKey", apiKey));//密码
        params.add(new BasicNameValuePair("from", "service@sendcloud.im"));//发送的账号名
        params.add(new BasicNameValuePair("fromName", "大可爱"));//发送人名
        params.add(new BasicNameValuePair("to", email));//发送到的邮箱名
        params.add(new BasicNameValuePair("subject", "邮箱短信验证"));//头部
        params.add(new BasicNameValuePair("html",sendBody));//中间主题
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);

        // 处理响应
        if ((response.getStatusLine().getStatusCode()) == HttpStatus.SC_OK) {
            String mailStatus =EntityUtils.toString(response.getEntity());
            //如果状态码为200，则添加邮箱数据到缓存；
            if (mailStatus.indexOf("请求成功")>0) {
                logger.info(randomForMail);
                //放入缓存中，设定缓存时间为5分钟。
              MemcachedUtils.set(randomForMail,email,new Date(1000 * 60 * 5));

                httpPost.releaseConnection();
                return true;
            }
        }
        httpPost.releaseConnection();
        return false;
    }


}
