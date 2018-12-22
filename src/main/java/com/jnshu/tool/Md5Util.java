package com.jnshu.tool;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Md5Util {
    /**
     * 根据输入的字符串生成固定的32位MD5码
     * 获取十六进制字符串形式的MD5摘要
     * @param str
     * 输入的字符串
     * @return MD5码
     */
    public final static String getMd5(String str) {
        //MessageDigest 类为应用程序提供信息摘要算法的功能
        MessageDigest mdInst = null;
        try {
            //返回实现指定摘要算法的 MessageDigest 对象。public static MessageDigest getInstance(String algorithm)
            //algorithm 所请求算法的名称
            mdInst = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        mdInst.update(str.getBytes());//  使用指定的 byte 数组更新摘要。
        byte[] md = mdInst.digest();// 获得密文, 通过执行诸如填充之类的最终操作完成哈希计算。在调用此方法之后，摘要被重置。
        return StrConvertUtil.byteArrToHexStr(md);
    }


    /**
     * MD5加盐加密
     */
    public static String generate(String password) {
        // 生成一个16位的随机数
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        //public int nextInt(int n)该方法的作用是生成一个随机的int值，该值介于[0,n)的区间
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        //长度不满足16位，补0
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        // 生成最终的加密盐
        String salt = sb.toString();
        //将这16位数字和密码相加，然后再获取MD5算法摘要
        password = Md5Util.getMd5(password + salt);
        //创建一个字符数组，长度位为48
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * 验证加盐后是否和原文一致,校验密码是否正确
     */
    public static boolean verify(String password, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return Md5Util.getMd5(password + salt).equals(new String(cs1));
    }
    public final static String getMultipartFileMd5(MultipartFile multipartFile){
        try {
            byte[] uploadBytes = multipartFile.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            String hashString = new BigInteger(1, digest).toString(16);
            return hashString;
        } catch (Exception e) {
        }
        return null;
    }
}
