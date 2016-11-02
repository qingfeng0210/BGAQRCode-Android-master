package cn.bingoogolapple.qrcode.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.dacas.util.DialogUtil;
import com.example.dacas.util.HttpUtil;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cn.bingoogolapple.qrcode.zxingdemo.R;


/**
 * Created by qingf on 2016/9/28.
 */

public class LoginActivity extends Activity {
    public static final String URL = "http://login.servicesecurity.cn";
    public static String save_ticket;
    private Button bnLogin = null;
    private EditText nameEdit = null;
    private EditText pwdEdit = null;
    private CheckBox rememberPwd;
    private CheckBox showPwd;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nameEdit = (EditText)findViewById(R.id.edit_name);
        pwdEdit = (EditText)findViewById(R.id.edit_pwd);
        //密码状态
        pwdEdit.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        pref = getSharedPreferences("data",MODE_PRIVATE);
        editor = pref.edit();
        rememberPwd = (CheckBox) findViewById(R.id.remember_check);
        showPwd = (CheckBox) findViewById(R.id.show_password);
        showPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showPwd.isChecked()){//当showPwd被选中，密文以明文形式显示
                    pwdEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{//不选中，以密文形式显示
                    pwdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        bnLogin = (Button) findViewById(R.id.bnLogin);
        boolean isRemember = pref.getBoolean("remember_password",false);

        if(isRemember){

            //将账号和密码都设置到文本框中
            String account = pref.getString("user","");
            String password = pref.getString("password","");
            nameEdit.setText(account);
            pwdEdit.setText(password);
            rememberPwd.setChecked(true);
        }
        bnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String account = nameEdit.getText().toString();
                String password = pwdEdit.getText().toString();
                if(validate()){
                    if(loginPro()) {
                        if(rememberPwd.isChecked()){
                            editor.putBoolean("rememberPassword",true);
                            editor.putString("user",account);
                            editor.putString("password",password);
                        }
                        else{
                            editor.clear();
                        }
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, ScanChooseActivity.class);
                        startActivity(intent);
                        //finish();
                    }
                }

            }
        });
    }
    //实现保存服务器传回的Ticket
    private boolean saveTicket(String ticket){
        editor.putString("ticket",ticket);
        this.save_ticket = ticket;
        Log.d("Post","saved ticket!");
        return false;
    }
    // 对用户输入的用户名、密码进行校验
    private boolean validate()
    {
        String username = nameEdit.getText().toString().trim();
        if (username.equals(""))
        {
            DialogUtil.showDialog(this, "用户账户是必填项！", false);
            return false;
        }
        String pwd = pwdEdit.getText().toString().trim();
        if (pwd.equals(""))
        {
            DialogUtil.showDialog(this, "用户口令是必填项！", false);
            return false;
        }
        return true;
    }
    private boolean loginPro(){
        String  statusLogin;
        String  ticket;
        Log.d("PostID","begin");
        String result = doPost(URL);
        if(result!=null){
            Log.d("PostID",result);
            result = result.trim();
            try {
                JSONObject jsonObject = new JSONObject(result);
                statusLogin = jsonObject.getString("result");
                if(statusLogin.equals("0")){
                    //ticket = jsonObject.getString("ticket");
                    Log.d("PostID","正常登录");
                    //保存服务器传回的Ticket
                    //saveTicket(ticket);
                    return true;
                }else if(statusLogin.equals("1")){
                    String error = jsonObject.getString("error");
                    DialogUtil.showDialog(LoginActivity.this
                            , error, false);
                    Log.d("PostID",error);
                }else if(statusLogin.equals("2")){
                    String error = jsonObject.getString("error");
                    DialogUtil.showDialog(LoginActivity.this
                            , error, false);
                    Log.d("PostID",error);
                }else if(statusLogin.equals("3")){
                    String error = jsonObject.getString("error");
                    DialogUtil.showDialog(LoginActivity.this
                            , error, false);
                    Log.d("PostID",error);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
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
            String name = nameEdit.getText().toString().trim();
            String password = pwdEdit.getText().toString().trim();
           // Person person = new Person(name,password);
            //String raw = new Gson().toJson(person);
            String raw = "user="+name+"&password="+password;
            Log.d("PostID",raw);
            result = HttpUtil.postRequest(url,raw);
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
