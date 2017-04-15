package example.money;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Dialog mDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showAccessibilityServiceSettings(this);
    }

    private void showAccessibilityServiceSettings(Context context) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = new AlertDialog.Builder(context)
                .setMessage("使用微信自动服务需要打开辅助服务, 去设置?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction("android.settings.ACCESSIBILITY_SETTINGS");
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", null)
                .create();
        mDialog.show();
    }

}
