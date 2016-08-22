package com.nmq.servlet;

import javax.imageio.stream.IIOByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by niemengquan on 2016/8/17.
 */
public class ValidateUtils {
    private static final String token="niemq";
    /**
     * 1）将token、timestamp、nonce三个参数进行字典序排序
     2）将三个参数字符串拼接成一个字符串进行sha1加密
     3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     * @param timestamp
     * @param nonce
     */
    public static String encode(String timestamp, String nonce) {
        String[] arrays=new String[]{token,timestamp,nonce};
        //排序
        Arrays.sort(arrays);
        //将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<arrays.length;i++){
            sb.append(arrays[i]);
        }
        String originalText = sb.toString();
        return SHA(originalText);

    }
    public static String SHA(String decript)  {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digest.update(decript.getBytes());
        byte[] messageDigest = digest.digest();
        // Create Hex String
        StringBuffer hexString = new StringBuffer();
        // 字节数组转换为 十六进制 数
        for (int i = 0; i < messageDigest.length; i++) {
            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        return hexString.toString();
    }


}
