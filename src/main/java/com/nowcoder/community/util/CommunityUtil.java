package com.nowcoder.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Dai
 * @Date: 2024/04/04 16:52
 * @Description: CommunityUtil
 * @Version: 1.0
 */
public class CommunityUtil {

    //生成随机字符串
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    //MD5加密
    // hello -> abc123def456
    // hello + 3e4a8 -> abc123def456abc
    public static String md5(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    /**
     * 生成包含代码、消息和可选数据的JSON字符串。
     * @param code 操作状态码。
     * @param msg 操作消息或提示。
     * @param map 包含额外数据的映射，可以为空。如果非空，其所有键值对将被添加到JSON对象中。
     * @return 表示给定参数的JSON对象的字符串表示。
     */
    public static String getJsonString(int code, String msg, Map<String,Object> map){
        JSONObject json = new JSONObject();
        json.put("code",code); // 添加状态码
        json.put("msg",msg); // 添加消息
        if(map != null){
            for(String key : map.keySet()){
                json.put(key,map.get(key)); // 遍历并添加映射中的所有键值对
            }
        }
        return json.toJSONString(); // 将JSON对象转换为字符串
    }

    /**
     * 生成包含状态码和消息的JSON字符串。
     * @param code 状态码。
     * @param msg 消息内容。
     * @return 返回格式化的JSON字符串。
     */
    public static String getJsonString(int code, String msg){
        return getJsonString(code,msg,null);
    }

    /**
     * 生成包含状态码的JSON字符串。
     * @param code 状态码。
     * @return 返回格式化的JSON字符串。
     */
    public static String getJsonString(int code){
        return getJsonString(code,null,null);
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("name","张三");
        map.put("age",25);
        System.out.println(getJsonString(0,"操作成功",map));
    }
}
