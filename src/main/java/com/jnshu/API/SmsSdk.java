package com.jnshu.API;

import com.cloopen.rest.sdk.CCPRestSDK;
import com.jnshu.tool.RandNum;


import java.util.HashMap;
import java.util.Set;

public class SmsSdk {
    private String AccountSid;
    private String AccountToken;
    private String AppId;
    private String Rand_Code = RandNum.getRandLength(4);

    public SmsSdk(String accountSid, String accountToken, String appId) {
        AccountSid = accountSid;
        AccountToken = accountToken;
        AppId = appId;
    }
    public String getRand_Code(){
        return Rand_Code;
    }
    public boolean SendMsg(String phone){
        HashMap<String,Object> result = null;
        //初始化sdk
        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init("app.cloopen.com","8883");
        restAPI.setAccount(AccountSid,AccountToken);
        restAPI.setAppId(AppId);
        result = restAPI.sendTemplateSMS(phone,"1",new String[]{Rand_Code,"5"});
        System.out.println("SDKTestVoiceVerify result=" + result);
        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
            return true;
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            return false;
        }
    }
}
