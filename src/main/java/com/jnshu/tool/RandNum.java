package com.jnshu.tool;

import java.util.Random;

public class RandNum {
    public static String getRandLength(int intLength){
        Random random = new Random();
        // 获取随机数
        double pross = (1 + random.nextDouble()) * Math.pow(10, intLength);

        // 将获取的随机数转换为字符串
        String fixLengthString = String.valueOf(pross);

        // 返回固定长度随机数
        return fixLengthString.substring(1, intLength+1);
    }
}
