package findhosp.com.publichealth.People;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import findhosp.com.publichealth.Map.MapsActivityAll;
import findhosp.com.publichealth.R;

public class PeopleSelect extends AppCompatActivity {
    String getUrl="http://find-hosp.com/web_service/get_hhos.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_select);

        Button btnNear = (Button) findViewById(R.id.button3);
        btnNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PeopleSelect.this,MapsActivityAll.class);
                intent.putExtra("GET_URL",getUrl);
                startActivity(intent);
            }
        });

        Button btnFilter = (Button) findViewById(R.id.button4);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PeopleSelect.this,PeopleFilter.class);
                intent.putExtra("GET_URL",getUrl);
                startActivity(intent);
            }
        });


    }

}
