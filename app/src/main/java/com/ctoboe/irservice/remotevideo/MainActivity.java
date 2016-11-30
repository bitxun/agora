


package com.ctoboe.irservice.remotevideo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity {
//    private TextView remoteCamera;
//    private TextView remoteMonitor;

    private Switch switchButton;
    private Button startButton;
    private final int REMOTE_CAMERA = 0;
    private final int REMOTE_MONITOR = 1;
    private int switchFlag = 1;
    public static String Vendorkey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
//        remoteCamera = (TextView) findViewById(R.id.remote_camera);
//        remoteMonitor = (TextView) findViewById(R.id.remote_monitor);
        switchButton = (Switch) findViewById(R.id.switch1);
        startButton = (Button) findViewById(R.id.start_bt);

        switchButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    switchFlag = REMOTE_CAMERA;
                    Toast.makeText(MainActivity.this, "start remote camera mode", Toast.LENGTH_SHORT).show();
//                    Vendorkey = "62b0cc4b55c246c7bc375f0742f6312c";
                } else {
                    switchFlag = REMOTE_MONITOR;
                    Toast.makeText(MainActivity.this, "start remote monitor mode", Toast.LENGTH_SHORT).show();
//                    Vendorkey = "dc04e6bd2cee4fd1bda39feef17d7bcd";
                }
            }
        });
        startButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (switchFlag == REMOTE_CAMERA) {
                    Intent intent = new Intent(MainActivity.this, ChannelActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, ChannelActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void onStop() {
        super.onStop();
        Log.e("error", "STOP");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.e("error", "destroy");
    }
}

