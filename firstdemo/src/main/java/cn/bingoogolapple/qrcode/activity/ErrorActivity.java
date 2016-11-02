package cn.bingoogolapple.qrcode.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.bingoogolapple.qrcode.zxingdemo.R;

/**
 * Created by qingf on 2016/9/30.
 */

public class ErrorActivity extends Activity {
    private TextView text_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        text_back = (TextView) findViewById(R.id.text_back);
        text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(ErrorActivity.this,ScanChooseActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}
