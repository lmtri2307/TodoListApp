package com.example.todolist.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Converter {
//    private Context context;
//    public Converter(Context context){
//        this.context = context;
//    }
    public static float dpToPixel(Context context, int dp){
        Resources resources = context.getResources();
        float cornerRadiusInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return cornerRadiusInPx;
    }

    public static <T> T fromJsonString(String jsonString, Class <T> tClass){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T result = null;
        try {
            result = objectMapper.readValue(jsonString, tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T fromJsonString(String jsonString, TypeReference<T> tTypeReference){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T result = null;
        try {
            result = objectMapper.readValue(jsonString, tTypeReference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }


}
