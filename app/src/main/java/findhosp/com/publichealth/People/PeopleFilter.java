package findhosp.com.publichealth.People;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import findhosp.com.publichealth.Map.MapsActivityFilter;
import findhosp.com.publichealth.R;

public class PeopleFilter extends AppCompatActivity {
    int anInt=0;
    RadioGroup radioGroup;
    LinearLayout linearLayout;
    Spinner typeSpinner;
    Spinner specializedSpinner;
    ArrayList<String> mType = new ArrayList<String>();
    ArrayList<String> mSpecialized = new ArrayList<String>();
    String getUrl="http://find-hosp.com/web_service/get_hhos.php?";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_filter);

        radioGroup = (RadioGroup) findViewById(R.id.selected);
        linearLayout = (LinearLayout) findViewById(R.id.search_detail);
        typeSpinner = (Spinner) findViewById(R.id.typeselect);
        specializedSpinner = (Spinner) findViewById(R.id.specializedselect);

        /*final Intent intent = getIntent();
        final String getUrl = intent.getStringExtra("GET_URL");*/


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.select_all) {
                    linearLayout.setVisibility(View.GONE);
                    anInt = 0;
                } else if (checkedId == R.id.select_filter) {
                    linearLayout.setVisibility(View.VISIBLE);
                    anInt = 1;
                }
            }
        });
        createSpinnerData();

        // Adapter ตัวแรก
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, mType);
        typeSpinner.setAdapter(adapterType);

        // Adapter ตัวสอง
        ArrayAdapter<String> adapterSpecialized = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, mSpecialized);
        specializedSpinner.setAdapter(adapterSpecialized);

        typeSpinner.setSelection(0);
        specializedSpinner.setSelection(0);


        Button button = (Button) findViewById(R.id.btnsearch);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (anInt==0) {
                    Intent intent1 = new Intent(PeopleFilter.this, MapsActivityFilter.class);
                    startActivity(intent1);
                } else {
                    String type = typeSpinner.getSelectedItem().toString();
                    String specialized = specializedSpinner.getSelectedItem().toString();

                    url = getUrl + "TYPE_NAME=" + type + "&&SPECIALIZED_NAME=" + specialized;
                    Intent intent1 = new Intent(PeopleFilter.this, MapsActivityFilter.class);
                    startActivity(intent1);
                }
            }


            ;

        });
    }
    private void createSpinnerData() {
        mType.add("รัฐบาล");
        mType.add("เอกชน");
        mSpecialized.add("ทั่วไป");
        mSpecialized.add("มะเร็ง");
        mSpecialized.add("ผิวหนัง");
        mSpecialized.add("ประสาท");
        mSpecialized.add("โรคติดต่อทางเพศสัมพันธ์");
        mSpecialized.add("หัวใจ");
        mSpecialized.add("ตา หู คอ จมูก");
    }
}
