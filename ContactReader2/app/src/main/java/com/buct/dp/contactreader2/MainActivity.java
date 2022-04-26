package com.buct.dp.contactreader2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
            initListView();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    void initListView() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        contact_view = findViewById(R.id.contact_view);
        Class c = Class.forName("com.buct.dp.contactreader2.BaseClass");
        Object o = c.newInstance();
        Method m = c.getMethod("readContact", Context.class);
        List<Map<String, String>> list = (List<Map<String, String>>) invokeMethod(o, m, this);
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.contact_item, new String[]{"name", "number"}, new int[]{R.id.name, R.id.number});
        contact_view.setAdapter(adapter);
    }

    Object invokeMethod(Object obj, Method method, Object ...args) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(obj, args);
    }
}