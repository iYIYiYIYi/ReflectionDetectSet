package com.buct.dp.invokedeviceid2;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseClass {

    public String getDeviceID(Context context) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        Class c =  Class.forName("android.telephony.TelephonyManager");
        Object t_manager = context.getSystemService(Context.TELEPHONY_SERVICE);
        Method getDeviceId = c.getMethod("getDeviceId");

        return (String) getDeviceId.invoke(t_manager);
    }

}
