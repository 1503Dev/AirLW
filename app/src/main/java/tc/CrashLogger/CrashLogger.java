package tc.CrashLogger;

import android.app.Application;

public class CrashLogger extends Application {
    public void onCreate() {
        super.onCreate();
        CrashLoggerCore.getInstance().init(getApplicationContext());
    }
}
