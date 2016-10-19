package dbug.toshltest.common;

import android.content.Context;
import android.util.Log;

public class Logger {

    private static boolean isLoggingOn = true;

    public static void e(String message) {
        if (isLoggingOn) Log.e("Error", message);
    }

    public static void e(String TAG, String message) {
        if (isLoggingOn) Log.e(TAG, message);
    }

    public static void e(Class c, String message) {
        if (isLoggingOn) Log.e(c.getName(), message);
    }
}
