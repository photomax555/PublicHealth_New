package findhosp.com.publichealth.Animal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import findhosp.com.publichealth.Map.MapsAnimalFilter;
import findhosp.com.publichealth.R;

public class AnimalFilter extends AppCompatActivity {

    int anInt=0;
    RadioGroup radioGroup;
    LinearLayout linearLayout;
    Spinner typeSpinner;
    ArrayList<String> mType = new ArrayList<String>();
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_filter);

        radioGroup = (RadioGroup) findViewById(R.id.selected);
        linearLayout = (LinearLayout) findViewById(R.id.search_detail);
        typeSpinner = (Spinner) findViewById(R.id.typeselect);

        final Intent intent = getIntent();
        final String getUrl = intent.getStringExtra("GET_URL");
        final String getType = intent.getStringExtra("GET_TYPE");

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

        typeSpinner.setSelection(0);


        Button button = (Button) findViewById(R.id.btnsearch);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (anInt==0) {
                    Intent intent1 = new Intent(AnimalFilter.this, MapsAnimalFilter.class);
                    intent.putExtra("GET_URL", url);
                    startActivity(intent1);
                } else {
                    String type = typeSpinner.getSelectedItem().toString();

                    url = getUrl + "TYPE_NAME=" + type;
                    Intent intent1 = new Intent(AnimalFilter.this, MapsAnimalFilter.class);
                    intent1.putExtra("GET_URL", url);
                    intent1.putExtra("GET_TYPE", getType);
                    startActivity(intent1);
                }
            }


            ;

        });
    }
    private void createSpinnerData() {
        mType.add("รัฐบาล");
        mType.add("เอกชน");
    }
}
