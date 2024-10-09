package tc.airl2;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import java.util.Map;

public class u {
    
    public static final String TAG = "u";
    private static Context context;
    public static void setContext(Context ctx){
        context=ctx;
    }
    public static int getInt(String p1,int p2){
        SharedPreferences sp=context.getSharedPreferences("settings",context.MODE_PRIVATE);
        return sp.getInt(p1,p2);
    }
    public static Map<String, ?> getAll(){
        return context.getSharedPreferences("hiddenPkgs",context.MODE_PRIVATE).getAll();
    }
    public static boolean isHidden(String pkgName){
        SharedPreferences sp=context.getSharedPreferences("hiddenPkgs",context.MODE_PRIVATE);
        return sp.getBoolean(pkgName, false);
    }
    public static void setHidden(String pkgName, boolean state){
        SharedPreferences.Editor sp=context.getSharedPreferences("hiddenPkgs",context.MODE_PRIVATE).edit();
        sp.putBoolean(pkgName,state).apply();
    }
    public static float px2dp(Context context,float px) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float pxValue = px;
        float dpValue = pxValue / metrics.density;
        return dpValue;
    }
}
