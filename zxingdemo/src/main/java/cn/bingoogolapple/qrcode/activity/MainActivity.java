package cn.bingoogolapple.qrcode.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dacas.util.HttpUtil;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    public static final String SCAN_URL = "http://192.168.191.1:8080/testapp6/login1";
    private String id_rand;
    private String ticket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id_rand = intent.getStringExtra("id_rand");
        Log.d("PostID","id_rand:"+id_rand);
        ticket = LoginActivity.save_ticket;
        Log.d("PostID","ticket:"+ticket);
        String result = "";
        result = doPost(SCAN_URL);
        Log.d("PostID",result);
        Log.d("PostID","before_ticket: "+ticket);
        Log.d("PostID","before_id_rand: "+id_rand);
        if(result.equals("true")){
            Intent intentTrue =new Intent(MainActivity.this,ConfirmActivity.class);
            intentTrue.putExtra("ticket",ticket);
            intentTrue.putExtra("id_rand",id_rand);
            startActivity(intentTrue);
        }
        else{
            Log.d("PostID","result is "+result);
            Intent intentFalse = new Intent(MainActivity.this,ErrorActivity.class);
            intentFalse.putExtra("ticket",ticket);
            intentFalse.putExtra("id_rand",id_rand);

            startActivity(intentFalse);
        }
    }
    /**
     * 用Post方式跟服务器传递数据
     * @param url
     * @return
     */

    private String doPost(String url){
        String result = "";
        try {
            //下面开始跟服务器传递数据，使用BasicNameValuePair
            Map<String ,String> map = new HashMap<String, String>();
            map.put("notification_type", "scan_code");
            map.put("ticket",ticket);
            map.put("id_rand",id_rand);
            JSONObject jsonObject = new JSONObject(map);
            result = HttpUtil.postRequest(url,jsonObject.toString());
            if(result==null)
                Log.d("PostID","scan result is null");
            else
                Log.d("PostID",result);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
