package cn.bingoogolapple.qrcode.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.dacas.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bingoogolapple.qrcode.zxingdemo.R;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.dacas.util.HttpUtil.getRequest;

/**
 * Created by qingf on 2016/9/22.
 */
public class ScanChooseActivity extends Activity implements EasyPermissions.PermissionCallbacks{
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private static final String LOG_OUT_URL = HttpUtil.BASE_URL+"/service/logout";
    public static final String COOKIE_URL = HttpUtil.BASE_URL+"/service/flush?sn=";
    private final int COOKIE_WHAT =0x1233;
    private Button scan_bt;
    private Button lg_out;
    private static String result = "";
    private static int test_flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_scan);
        scan_bt = (Button) findViewById(R.id.scan_bt);
        lg_out = (Button) findViewById(R.id.log_out);
        scan_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanChooseActivity.this, CodeScanActivity.class);
                startActivity(intent);
            }
        });
        lg_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String strResult = getRequest(LOG_OUT_URL);
                    Log.d("PostID",strResult);

                    Intent intent = new Intent(ScanChooseActivity.this,LoginActivity.class);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        final Handler myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == COOKIE_WHAT) {
                    Log.d("PostID", "handleMessage: "+msg+"test_flag: "+test_flag);
                }
            }
        };
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    String curResult = HttpUtil.getRequest(COOKIE_URL);
                    JSONArray jsonArray = new JSONArray(curResult);
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject object = (JSONObject) jsonArray.getJSONObject(i);

                    }
                    ++test_flag;
                    if(test_flag%5==1) {
                        myHandler.sendEmptyMessage(COOKIE_WHAT);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },0,1200);

    }
    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQrcodePermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQrcodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }

}
