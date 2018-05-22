package com.cnblogs.yjmyzz.blockchain.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;


/**
 * @author yangjunming
 */
public class FastJsonUtil {

    public String toJson(Object object) {
        if (null == object) {
            return null;
        }
        return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }


    public <T> T fromJson(String jsonString, TypeReference<T> typeReference) {
        try {
            return JSON.parseObject(jsonString, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


}