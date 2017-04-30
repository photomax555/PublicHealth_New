package findhosp.com.publichealth.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import findhosp.com.publichealth.R;

public class HelloPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_page);

        showGPSDisabledAlertToUser();

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.hi);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

private void showGPSDisabledAlertToUser(){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    alertDialogBuilder.setMessage("คุณเปิดใช้งาน GPS แล้วหรือไม่?"+"\nกรุณาตรวจสอบการเปิดใช้งาน GPS")
            .setCancelable(false)
            .setPositiveButton("ไปยังหน้าตั้งค่าเปิดใช่งาน GPS",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent callGPSSettingIntent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(callGPSSettingIntent);
                        }
                    });
    alertDialogBuilder.setNegativeButton("ยกเลิก",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    showGPSDisabledAlertToUser();
                }

            });
    AlertDialog alert = alertDialogBuilder.create();
    alert.show();
}


        }
