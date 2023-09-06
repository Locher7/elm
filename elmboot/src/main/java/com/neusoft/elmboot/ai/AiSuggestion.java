package com.neusoft.elmboot.ai;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.elmboot.mapper.CartMapper;
import com.neusoft.elmboot.po.Cart;

import ch.qos.logback.core.boolex.Matcher;

import java.io.*;
import java.util.List;
import java.util.regex.Pattern;

public class AiSuggestion {
    public static final String API_KEY = "q9bCBGlhXARuiE9cuCrFkUNr";
    public static final String SECRET_KEY = "YeiNIvB1PGsyBMtbEXnsmV0NO0I40DHE";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public static void main(String []args) throws IOException{
    	

    	CartMapper cartMapper;
    

    	
    	String foodlist = "1份猪肉水饺   2份蛋炒饭";
    	String quantity;
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"messages\":[{\"role\":\"user\",\"content\":\"我这一顿吃了" + foodlist + "，计算我摄入了多少热量，特别注意，只需要告诉我数值即可，不要其他的废话，计算我需要做多少运动（跑步或者游泳或者骑自行车）才能消耗掉这些热量。 特别特别特别需要注意的是，请用下面的形式进行输出：您好，我是您的AI健康助手。换行 您本餐选购了" + foodlist + "，预计摄入 卡路里。换行 您需要进行 _公里的慢跑或者_小时的游泳可消耗这些热量。\"}]}");
        Request request = new Request.Builder()
            .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant?access_token=" + getAccessToken())
            .method("POST", body)
            .addHeader("Content-Type", "application/json")
            .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        //System.out.println(response.body().string());
        
        String rawString = response.body().string();
        
        Pattern pattern = Pattern.compile("\"result\":\"(.*?)\"");
        java.util.regex.Matcher matcher = pattern.matcher(rawString);

        // 查找匹配项并提取 "result" 部分的值
        if (matcher.find()) {
            String result = matcher.group(1);
            String output = result.replace("\\n\\n", System.lineSeparator());
            System.out.println(output);
        }
        else {
        	System.out.println(rawString);
        }
        


    }
    
    
    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }
    
}
