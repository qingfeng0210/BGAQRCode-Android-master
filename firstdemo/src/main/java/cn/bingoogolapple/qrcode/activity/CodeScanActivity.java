package cn.bingoogolapple.qrcode.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import cn.bingoogolapple.qrcode.zxingdemo.R;

/**
 * Created by qingf on 2016/9/22.
 */
public class CodeScanActivity extends AppCompatActivity implements QRCodeView.Delegate {
    private static final String TAG = CodeScanActivity.class.getSimpleName();
    private QRCodeView mQRCodeView;
    private TextView infoTextView = null;
    private ScrollView scrollView = null;
    private String save_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scan);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        infoTextView = (TextView) findViewById(R.id.code_info);
        scrollView = (ScrollView) findViewById(R.id.code_scroll_view);

        mQRCodeView = (ZXingView) findViewById(R.id.zxing_view);
        mQRCodeView.setDelegate(this);

        mQRCodeView.startSpot();
        mQRCodeView.changeToScanQRCodeStyle();

        Log.d("PostID","*******");

    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
    @Override
    public void onScanQRCodeSuccess(String result) {
        this.save_result = result;
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
        mQRCodeView.startSpot();
        Log.d("PostID","############");
        if(result!=null)
            Log.d("PostID",result);
        else
            Log.d("PostID","result is null...");
        Intent intent = new Intent(CodeScanActivity.this,MainActivity.class);
        intent.putExtra("id_rand",result);
        startActivity(intent);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
        infoTextView.append("打开相机出错" + "\n");
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

}
