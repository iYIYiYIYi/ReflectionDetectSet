package com.buct.dp.invokedeviceid;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseService extends Service {

    private String device_id;
    private final BaseBinder baseBinder = new BaseBinder();

    public class BaseBinder extends Binder {
        BaseService getService() {
            return BaseService.this;
        }

        String getDeviceID() {
            return BaseService.this.device_id;
        }
    }

    public BaseService() {
    }

    @SuppressLint("HardwareIds")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
//
            Class c =  Class.forName("android.telephony.TelephonyManager");
            Object t_manager = getSystemService(Context.TELEPHONY_SERVICE);
            Method getDeviceId = c.getMethod("getDeviceId");

            device_id = (String) getDeviceId.invoke(t_manager);
            Log.d("DeviceId", "device id:"+device_id);
            Toast.makeText(getApplicationContext(), "device id:"+device_id,  Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "get device id failed",  Toast.LENGTH_SHORT).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return baseBinder;
    }
}