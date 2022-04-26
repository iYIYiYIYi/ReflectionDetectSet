package com.buct.dp.contactanddeviceidreader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseClass {

    public List<Map<String, String>> readContact(Context context) {
        List<Map<String, String>> list = new ArrayList<>();
        try (Cursor cursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null)) {
            while (cursor.moveToNext()) {
                Map<String, String> map = new HashMap<>();
                int i_name = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(i_name);
                int i_number = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(i_number);
                map.put("name", name);
                map.put("number", number);
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @SuppressLint("HardwareIds")
    public String getDeviceId(Context context) {
        try {
            @SuppressLint("WrongConstant") TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephony == null)
                return null;
            return telephony.getDeviceId();
        } catch (NullPointerException ex) {
            return null;
        }
    }

}

