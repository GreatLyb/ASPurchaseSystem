package com.lyb.purchasesystem.consta;

import java.util.HashMap;
import java.util.Map;

/**
 * ASPurchaseSystem
 * 类描述：
 * 类传参：
 *
 * @Author： create by Lyb on 2020-04-07 14:48
 */
public class ParamsMapUtils {

   public static Map<String,String> getLoginParams(String acc,String pwd){
       Map<String,String> map=new HashMap<>();

       map.put("username", acc);
       map.put("password", pwd);

       return map;
   }

    public static Map<String, String> getUserInfoParams(String token) {
        Map<String, String> map = new HashMap<>();
        map.put("id", token);
        return map;
    }
}
