package com.alibaba.mos.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author youxuehu
 * @version v1.0
 * @className JsonUtil
 * @date 2021/7/26 11:23 下午
 * @desrription 这是类的描述信息
 */
public class JsonUtil {

    public static boolean isJson(String content) {
        if(StringUtils.isEmpty(content)){
            return false;
        }
        boolean isJsonObject = true;
        boolean isJsonArray = true;
        try {
            JSONObject.parseObject(content);
        } catch (Exception e) {
            isJsonObject = false;
        }
        try {
            JSONObject.parseArray(content);
        } catch (Exception e) {
            isJsonArray = false;
        }
        if(!isJsonObject && !isJsonArray){
            return false;
        }
        return true;
    }

}
