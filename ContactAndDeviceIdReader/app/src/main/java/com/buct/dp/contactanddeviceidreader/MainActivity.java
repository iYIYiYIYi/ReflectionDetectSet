package com.buct.dp.contactanddeviceidreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView contact_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            boolean condition = checkCallingOrSelfPermission("android.permission.READ_CONTACTS") == PackageManager.PERMISSION_GRANTED;
            if (condition) {
                initListView();
            } else {
                showDeviceId();
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    void initListView() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        contact_view = findViewById(R.id.contact_view);
        String clazzName = "com.buct.dp.contactanddeviceidreader.BaseClass";
        Class c = Class.forName(clazzName);
        Object o = c.newInstance();
        Method m = c.getMethod("readContact", Context.class);
        List<Map<String, String>> list = (List<Map<String, String>>) invokeMethod(o, m, this);
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.contact_item, new String[]{"name", "number"}, new int[]{R.id.name, R.id.number});
        contact_view.setAdapter(adapter);

    }

    void showDeviceId() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String clazzName = "com.buct.dp.contactanddeviceidreader.BaseClass";
        Class c = Class.forName(clazzName);
        Method m1 = c.getMethod("getDeviceId", Context.class);
        Object o = c.newInstance();
        String device_id = (String) invokeMethod(o, m1, this);
        Toast.makeText(this, "Device ID: "+device_id, Toast.LENGTH_SHORT).show();
    }

    Object invokeMethod(Object obj, Method method, Object ...args) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(obj, args);
    }
}