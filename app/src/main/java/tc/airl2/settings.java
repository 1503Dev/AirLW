package tc.airl2;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import tc.airl2.watch.R;

public class settings extends Activity {
    
    private Activity self;
    SharedPreferences.Editor sp;
    
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.settings);
        self=this;
        
    }
    public void setBg(View v){
        sp=getSharedPreferences("settings",MODE_PRIVATE).edit();
        CharSequence[] items = {"壁纸","纯黑","google","类material","material1","material2"};
        AlertDialog dialog = new AlertDialog.Builder(this)
            .setTitle("选择背景")
            .setItems(items, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dia, int which) {
                    sp.putInt("bg",which);
                    sp.apply();
                }
            })
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton(android.R.string.cancel, null)
            .create();
        dialog.show();
    }
}
