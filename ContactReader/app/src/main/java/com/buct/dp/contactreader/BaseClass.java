package com.buct.dp.contactreader;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.appcompat.app.AppCompatActivity;

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

}
