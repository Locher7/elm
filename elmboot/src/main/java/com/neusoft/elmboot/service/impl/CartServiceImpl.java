package com.neusoft.elmboot.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.elmboot.mapper.CartMapper;
import com.neusoft.elmboot.mapper.FoodMapper;
import com.neusoft.elmboot.po.Cart;
import com.neusoft.elmboot.po.Food;
import com.neusoft.elmboot.service.CartService;

import okhttp3.*;
import org.json.JSONObject;


@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartMapper cartMapper;
	private FoodMapper foodMapper;
	
	static final String API_KEY = "q9bCBGlhXARuiE9cuCrFkUNr";
	static final String SECRET_KEY = "YeiNIvB1PGsyBMtbEXnsmV0NO0I40DHE";

	@Override
	public List<Cart> listCart(Cart cart) {
		return cartMapper.listCart(cart);
	}
	
	@Override
	public int saveCart(Cart cart) {
		return cartMapper.saveCart(cart);
	}
	
	@Override
	public int updateCart(Cart cart) {
		return cartMapper.updateCart(cart);
	}
	
	@Override
	public int removeCart(Cart cart) {
		return cartMapper.removeCart(cart);
	}
	
	@Override
	public String aiSuggestion (List<Cart> list) throws IOException {
	    OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();
	    
	    StringBuffer foodlist = new StringBuffer("");
  	
	    for (Cart cart: list) {
	    	Food food = foodMapper.getFoodById(cart.getFoodId());
	    	String foodname = food.getFoodName();
	    	Integer quantity = cart.getQuantity();
	    	foodlist.append(quantity.toString() + "份" + foodname + "  ");
	    }
    	
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"messages\":[{\"role\":\"user\",\"content\":\"我这一顿吃了" + foodlist.toString() + "，计算我摄入了多少热量，特别注意，只需要告诉我数值即可，不要其他的废话，计算我需要做多少运动（跑步或者游泳或者骑自行车）才能消耗掉这些热量。 特别特别特别需要注意的是，请用下面的形式进行输出：您好，我是您的AI健康助手。换行 您本餐选购了" + foodlist.toString() + "  预计摄入 卡路里。换行 您需要进行_公里的慢跑或者_小时的游泳可消耗这些热量。\"}]}");
        Request request = new Request.Builder()
            .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant?access_token=" + getAccessToken())
            .method("POST", body)
            .addHeader("Content-Type", "application/json")
            .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        
        String rawString = response.body().string();
        
        Pattern pattern = Pattern.compile("\"result\":\"(.*?)\"");
        java.util.regex.Matcher matcher = pattern.matcher(rawString);

        // 查找匹配项并提取 "result" 部分的值
        if (matcher.find()) {
            String result = matcher.group(1);
            String output = result.replace("\\n\\n", System.lineSeparator());
            System.out.println(output);
            return output;
        }
        else {
        	System.out.println(rawString);
        	return rawString;
        }

	}
        



	/**
	 * 从用户的AK，SK生成鉴权签名（Access Token）
	 *
	 * @return 鉴权签名（Access Token）
	 * @throws IOException IO异常
	 */
    String getAccessToken() throws IOException {
    	OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();
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
