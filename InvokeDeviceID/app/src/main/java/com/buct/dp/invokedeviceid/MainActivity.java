package com.buct.dp.invokedeviceid;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView device_id_view;
    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BaseService.BaseBinder binder = (BaseService.BaseBinder) service;
            device_id_view.setText(binder.getDeviceID());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        device_id_view = findViewById(R.id.device_id);

        Intent intent = new Intent(MainActivity.this, BaseService.class);
        startService(intent);
        bindService(intent, connection, BIND_ADJUST_WITH_ACTIVITY);

    }

}