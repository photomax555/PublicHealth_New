package findhosp.com.publichealth.Animal;

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

import findhosp.com.publichealth.Map.MapsAnimalFilter;
import findhosp.com.publichealth.R;

public class AnimalFilter extends AppCompatActivity {

    int anInt = 0;
    RadioGroup radioGroup;
    LinearLayout linearLayout;
    Spinner typeSpinner;
    ArrayList<String> mType = new ArrayList<String>();
    String url;
    RadioButton rd1, rd2;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_filter);

        radioGroup = (RadioGroup) findViewById(R.id.selected);
        linearLayout = (LinearLayout) findViewById(R.id.search_detail);
        typeSpinner = (Spinner) findViewById(R.id.typeselect);
        rd1 = (RadioButton) findViewById(R.id.select_all);
        rd2 = (RadioButton) findViewById(R.id.select_filter);

        final Intent intent = getIntent();
        final String getUrl = intent.getStringExtra("GET_URL");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.select_all) {
                    linearLayout.setVisibility(View.GONE);
                    rd1.setChecked(true);
                } else if (checkedId == R.id.select_filter) {
                    linearLayout.setVisibility(View.VISIBLE);
                    rd2.setChecked(true);
                }
            }
        });
        createSpinnerData();

        // Adapter ตัวแรก
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, mType);
        typeSpinner.setAdapter(adapterType);

        typeSpinner.setSelection(0);


        Button button = (Button) findViewById(R.id.btnsearch);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rd1.isChecked()) {
                    Intent intent1 = new Intent(AnimalFilter.this, MapsAnimalFilter.class);
                    intent1.putExtra("GET_URL", getUrl);
                    startActivity(intent1);
                } else if (rd2.isChecked()){

                    String type = typeSpinner.getSelectedItem().toString();

                    url = getUrl + "TYPE_NAME=" + type;
                    Intent intent1 = new Intent(AnimalFilter.this, MapsAnimalFilter.class);
                    intent1.putExtra("GET_URL", url);
                    startActivity(intent1);
                }
                //TextView textView = (TextView) findViewById(R.id.t1);
                //textView.setText(url);
            }



        });
    }

    private void createSpinnerData() {
        mType.add("รัฐบาล");
        mType.add("เอกชน");
    }
}
