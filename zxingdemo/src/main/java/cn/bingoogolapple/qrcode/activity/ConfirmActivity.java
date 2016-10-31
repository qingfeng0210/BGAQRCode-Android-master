package cn.bingoogolapple.qrcode.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dacas.util.HttpUtil;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cn.bingoogolapple.qrcode.zxingdemo.R;

/**
 * Created by qingf on 2016/9/30.
 */

public class ConfirmActivity extends Activity implements View.OnClickListener {

    public static final String CONFIRM_URL = "http://192.168.191.1:8080/testapp6/login1";
    private Button btConfirm;
    private Button btCancle;
    private String confirm_cancel = "cancel";
    private TextView close_view;
    private String id_rand;
    private String ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        id_rand = intent.getStringExtra("id_rand");
        ticket = intent.getStringExtra("ticket");

        Log.d("PostID","after_ticket: "+ticket);
        Log.d("PostID","after_id_rand: "+id_rand);
        setContentView(R.layout.activity_confirm);
        close_view = (TextView) findViewById(R.id.close_view);
        btConfirm = (Button) findViewById(R.id.confirm_login);
        btCancle = (Button) findViewById(R.id.cancel_login);
        close_view.setOnClickListener(ConfirmActivity.this);
        btConfirm.setOnClickListener(ConfirmActivity.this);
        btCancle.setOnClickListener(ConfirmActivity.this);
    }


    @Override
    public void onClick(View view) {
        String result ="";
        switch (view.getId()){
            case R.id.close_view:
                Intent intentClose = new Intent(ConfirmActivity.this,ScanChooseActivity.class);
                startActivity(intentClose);
                break;
            case R.id.confirm_login:
                confirm_cancel = "confirm";
                result = doPost(CONFIRM_URL);
                if(result.equals("true")){
                    Log.d("PostID","login true");
                    Toast.makeText(this,"允许登录成功！",Toast.LENGTH_SHORT).show();
                    Intent intentMain = new Intent(ConfirmActivity.this,ScanChooseActivity.class);
                    startActivity(intentMain);
                }
                else {
                    Toast.makeText(this,"允许登录失败！",Toast.LENGTH_SHORT).show();
                    Log.d("PostID", "receive error1");
                    if(result==null)
                        Log.d("PostID","receive result null");
                    else
                        Log.d("PostID",result);
                    Intent intentError = new Intent(ConfirmActivity.this,ErrorActivity.class);
                    startActivity(intentError);
                }
                break;
            case R.id.cancel_login:
                confirm_cancel = "cancel";
                result = doPost(CONFIRM_URL);
                if(result.equals("true")) {
                    Log.d("PostID", "login false");
                    Toast.makeText(this, "取消登录成功！", Toast.LENGTH_SHORT).show();
                    Intent intentMain = new Intent(ConfirmActivity.this, ScanChooseActivity.class);
                    startActivity(intentMain);
                }
                else {
                    Toast.makeText(this,"取消登录失败！",Toast.LENGTH_SHORT).show();
                    Log.d("PostID", "receive error2");
                    if(result==null)
                        Log.d("PostID","receive result null");
                    else
                        Log.d("PostID",result);
                    Intent intentError = new Intent(ConfirmActivity.this,ErrorActivity.class);
                    startActivity(intentError);
                }
                break;
            default:
                break;
        }
        Log.d("PostID",result);
    }

    /**
     * 用Post方式跟服务器传递数据
     * @param url
     * @return
     */

    private String doPost(String url){
        String result = "";
        try {

            Log.d("PostID","after_ticket_2: "+ticket);
            Log.d("PostID","after_id_rand_2: "+id_rand);
            //下面开始跟服务器传递数据，使用BasicNameValuePair
            Map<String ,String> map = new HashMap<String, String>();
            map.put("ticket",ticket);
            map.put("id_rand",id_rand);
            map.put("notification_type", "click_code");
            map.put("click_type",confirm_cancel);
            JSONObject jsonObject = new JSONObject(map);
            result = HttpUtil.postRequest(url,jsonObject.toString());
            if(result==null)
                Log.d("PostID","result is null");
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
