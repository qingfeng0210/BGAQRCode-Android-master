package cn.bingoogolapple.qrcode.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.dacas.util.HttpUtil;

import java.util.List;

import cn.bingoogolapple.qrcode.zxingdemo.R;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by qingf on 2016/9/22.
 */
public class ScanChooseActivity extends Activity implements EasyPermissions.PermissionCallbacks{
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private static final String LOG_OUT_URL = HttpUtil.BASE_URL+"/service/logout";

    private Button scan_bt;
    private Button lg_out;
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
                    String strResult = HttpUtil.getRequestCookie_sn(LOG_OUT_URL);
                    Log.d("PostID",strResult);

                    Intent intent = new Intent(ScanChooseActivity.this,LoginActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
